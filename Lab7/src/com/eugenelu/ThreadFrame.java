package com.eugenelu;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ThreadFrame extends JFrame {
    Timer timer = new java.util.Timer("Thread monitor");
    JTable table;
    ThreadTableModel model;

    public ThreadFrame(List<FileThread> threads) {
        super("Thread monitor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new ThreadTableModel(threads);
        table = new JTable(model);
        table.getColumnModel().getColumn(table.getColumnCount() - 1).setPreferredWidth(500);
        getContentPane().add(new JScrollPane(table));
        setPreferredSize(new Dimension(1000,450));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        toFront();
        setState(Frame.NORMAL);
        requestFocus();
        setAlwaysOnTop(true);
    }

    public ThreadFrame(List<FileThread> threads, int updateTime) {
        this(threads);
        start(updateTime);
    }

    public void update() {
        model.update();
    }

    public void start(int updateTime) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() { update(); }
        }, 0, updateTime);
    }

    public void stop() {
        timer.cancel();
    }
}
