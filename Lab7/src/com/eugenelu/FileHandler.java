package com.eugenelu;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FileHandler {
    public static class Statistics {
        public long matchingSizeFilesCount;
        public long matchingPatternFilesCount;
        public long subdirectoriesCount;
    }

    private final int UPDATE_TIME = 5;
    private final ConcurrentLinkedQueue<File> synchronizedQueue = new ConcurrentLinkedQueue<>();
    private final List<FileThread> threads = Collections.synchronizedList(new LinkedList<>());
    private final AtomicLong matchingSizeFilesCount = new AtomicLong();
    private final AtomicLong matchingPatternFilesCount = new AtomicLong();
    private final AtomicLong subdirectoriesCount = new AtomicLong();
    private final AtomicInteger activeThreadsCount = new AtomicInteger();

    private ThreadFrame frame;
    private long executionTime;
    private FileFilter pattern = null;
    private long minFileSize = -1;

    public void setMinFileSize(long size) {
        this.minFileSize = size;
    }

    public void setPattern(@NotNull String pattern) {
        this.pattern = new WildcardFileFilter(pattern);
    }

    public long getMatchingSizeFilesCount() {
        return matchingSizeFilesCount.get();
    }

    public long getMatchingPatternFilesCount() {
        return matchingPatternFilesCount.get();
    }

    public long getSubdirectoriesCount() {
        return subdirectoriesCount.get();
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void run(String path) {
        var directory = new FileWrapper(new File(path));
        synchronizedQueue.add(directory.getFile());
        threads.add(new FileThread(Thread.currentThread(), directory));
        display(UPDATE_TIME);
        executionTime = System.currentTimeMillis();
        while (!synchronizedQueue.isEmpty()) {
            directory.setFile(synchronizedQueue.remove());
            search(directory.getFile());
        }
        executionTime = System.currentTimeMillis() - executionTime;
    }

    public void run(String path, int threadsCount, boolean useExecutorService) {
        if (threadsCount < 1) return;
        if (threadsCount == 1) {
            run(path);
            return;
        }

        List<FileThread> newThreads = new LinkedList<>();
        var directory = new File(path);
        synchronizedQueue.add(directory);
        activeThreadsCount.addAndGet(threadsCount);

        for (int i = 0; i < threadsCount; ++i) {
            newThreads.add(new FileThread((dir) -> {
                var s = new Statistics();
                threads.add(new FileThread(Thread.currentThread(), dir));
                searching:
                while (true) {
                    if (synchronizedQueue.isEmpty()) {
                        activeThreadsCount.decrementAndGet();
                        while (synchronizedQueue.isEmpty()) {
                            if (activeThreadsCount.get() == 0) break searching;
                        }
                        activeThreadsCount.incrementAndGet();
                    }
                    dir.setFile(synchronizedQueue.poll());
                    search(dir.getFile(), s);
                }
                matchingSizeFilesCount.addAndGet(s.matchingSizeFilesCount);
                matchingPatternFilesCount.addAndGet(s.matchingPatternFilesCount);
                subdirectoriesCount.addAndGet(s.subdirectoriesCount);
            }, new FileWrapper(new File(""))));
        }

        display(UPDATE_TIME);
        run(newThreads, true, useExecutorService);
    }

    private void run(List<FileThread> threads, boolean block, boolean useExecutorService) {
        if (useExecutorService) {
            var executor = Executors.newFixedThreadPool(threads.size());
            for (var thread: threads) executor.submit(thread.getThread());
            executor.shutdown();
            if (block) {
                try {
                    startTimer();
                    if (!executor.awaitTermination(900, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                    stopTimer();
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                    stopTimer();
                    e.printStackTrace();
                }
            }
        }
        else {
            for (var thread : threads) thread.getThread().start();
            if (block) {
                try {
                    startTimer();
                    for (var thread : threads) thread.getThread().join();
                    stopTimer();
                } catch (InterruptedException e) {
                    stopTimer();
                    e.printStackTrace();
                }
            }
        }
    }

    private void display(int updateInterval) {
        SwingUtilities.invokeLater(() -> {
            frame = new ThreadFrame(threads, updateInterval);
        });
    }

    private void search(File directory, Statistics s) {
        if (directory == null || directory.isFile()) return;
        var fileObjects = directory.listFiles();
        if (fileObjects == null) return;
        var matchingFiles = directory.listFiles(pattern);
        var files = Arrays.stream(fileObjects).filter(File::isFile).toList();
        var directories = Arrays.stream(fileObjects).filter(File::isDirectory).toList();

        s.matchingSizeFilesCount += files.stream().filter(f -> f.length() > minFileSize).count();

        if (matchingFiles != null) {
            s.matchingPatternFilesCount += Arrays.stream(matchingFiles).filter(File::isFile).count();
        }

        s.subdirectoriesCount += directories.size();
        synchronizedQueue.addAll(directories);
    }

    private void search(File directory) {
        var s = new Statistics();
        search(directory, s);
        matchingSizeFilesCount.addAndGet(s.matchingSizeFilesCount);
        matchingPatternFilesCount.addAndGet(s.matchingPatternFilesCount);
        subdirectoriesCount.addAndGet(s.subdirectoriesCount);
    }

    private void startTimer() {
        executionTime = System.currentTimeMillis();
    }

    private void stopTimer() {
        executionTime = System.currentTimeMillis() - executionTime;
    }
}
