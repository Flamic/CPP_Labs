package com.eugenelu;

import javax.swing.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private final static int DEFAULT_THREADS_COUNT = 4;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FileHandler handler = new FileHandler();
        String filePath;
        String pattern;
        int threadsCount;
        boolean useExecutorService = false;

        System.out.print("Enter file path: ");
        filePath = sc.nextLine();

        System.out.print("Enter minimum file size: ");
        try {
            handler.setMinFileSize(Integer.parseInt(sc.nextLine()));
        }
        catch (Exception ignored) {}

        System.out.print("Enter pattern for file name: ");
        pattern = sc.nextLine();
        if (!pattern.isEmpty()) handler.setPattern(pattern);

        System.out.print("Enter threads count: ");
        try {
            threadsCount = Integer.parseInt(sc.nextLine());
        }
        catch (Exception e) {
            threadsCount = DEFAULT_THREADS_COUNT;
        }

        if (threadsCount < 1) {
            System.out.format("There must be 1 or more threads. Setting default value - %d threads\n",
                    DEFAULT_THREADS_COUNT);
            threadsCount = DEFAULT_THREADS_COUNT;
        }
        if (threadsCount != 1) {
            System.out.println("Use executor service? (y/n)");
            useExecutorService = sc.nextLine().equals("y");
        }

        handler.run(filePath, threadsCount, useExecutorService);
        System.out.println("Matching size files count: " + handler.getMatchingSizeFilesCount());
        System.out.println("Matching pattern files count: " + handler.getMatchingPatternFilesCount());
        System.out.println("Subdirectories count: " + handler.getSubdirectoriesCount());
        System.out.println("Elapsed time: " + handler.getExecutionTime() + " ms");
    }
}
