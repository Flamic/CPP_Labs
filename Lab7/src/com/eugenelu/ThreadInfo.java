package com.eugenelu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadInfo {
    private long id;
    private String name;
    private Thread.State state;
    private int priority;
    private boolean isActive;
    private String currentDirectory;

    public ThreadInfo(Thread thread) {
        id = thread.getId();
        name = thread.getName();
        state = thread.getState();
        priority = thread.getPriority();
        isActive = thread.isAlive();

    }
}
