package com.eugenelu;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ThreadTableModel extends AbstractTableModel {
    private final List<FileThread> threads;

    public ThreadTableModel(List<FileThread> threads) {
        this.threads = threads;
    }

    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> long.class;
            case 1 -> String.class;
            case 2 -> Thread.State.class;
            case 3 -> int.class;
            case 4 -> boolean.class;
            case 5 -> String.class;
            default -> Object.class;
        };
    }

    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> "ID";
            case 1 -> "Name";
            case 2 -> "State";
            case 3 -> "Priority";
            case 4 -> "Is active";
            case 5 -> "Current path";
            default -> "";
        };
    }

    public int getRowCount() {
        return threads.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        FileThread thread = threads.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> thread.getThread().getId();
            case 1 -> thread.getThread().getName();
            case 2 -> thread.getThread().getState();
            case 3 -> thread.getThread().getPriority();
            case 4 -> thread.getThread().isAlive();
            case 5 -> thread.getCurrentDirectory() == null ? "" : thread.getCurrentDirectory().getAbsolutePath();
            default -> "";
        };
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        System.out.println("\u001B[31mIllegal access: you cannot edit this table\u001B[0m");
    }

    public void update() {
        fireTableDataChanged();
    }
}
