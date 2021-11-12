package com.eugenelu;

import java.util.*;

import static java.util.Map.entry;

public class StringTools {
    public enum TEXT_COLOR {
        BLACK,
        RED,
        GREEN,
        YELLOW,
        BLUE,
        PURPLE,
        CYAN,
        WHITE
    }

    private static final Map<TEXT_COLOR, String> TEXT_COLOR_VALUES = Map.ofEntries(
            entry(TEXT_COLOR.BLACK, "\u001B[30m"),
            entry(TEXT_COLOR.RED, "\u001B[31m"),
            entry(TEXT_COLOR.GREEN, "\u001B[32m"),
            entry(TEXT_COLOR.YELLOW, "\u001B[33m"),
            entry(TEXT_COLOR.BLUE, "\u001B[34m"),
            entry(TEXT_COLOR.PURPLE, "\u001B[35m"),
            entry(TEXT_COLOR.CYAN, "\u001B[36m"),
            entry(TEXT_COLOR.WHITE, "\u001B[37m")
    );
    private static final String RESET = "\u001B[0m";

    public static String colorize(String str, TEXT_COLOR color) {
        return TEXT_COLOR_VALUES.get(color) + str + RESET;
    }
}
