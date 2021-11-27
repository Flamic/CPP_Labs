package com.eugenelu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileThread {
    @FunctionalInterface
    public interface FileArgRunnable {
        void run(FileWrapper arg);
    }

    private Thread thread;
    private FileWrapper currentDirectory;

    public FileThread(FileArgRunnable task, FileWrapper currentDirectory) {
        thread = new Thread(() -> task.run(currentDirectory));
        this.currentDirectory = currentDirectory;
    }

    public FileThread(Thread thread, FileWrapper currentDirectory) {
        this.thread = thread;
        this.currentDirectory = currentDirectory;
    }
}
