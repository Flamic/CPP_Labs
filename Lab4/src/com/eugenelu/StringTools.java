package com.eugenelu;

import java.text.BreakIterator;
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

    public static Set<String> getComplexWords(Set<String> dictionary) {
        Set<String> complexWords = new HashSet<>();

        for (var word : dictionary) {
            var pos = new boolean[word.length() + 1];

            pos[0] = true;
            for (int i = 0; i < word.length(); i++) {
                if (!pos[i]) continue;
                for (int j = i + 1;
                     j < word.length() || (i != 0 && j == word.length());
                     j++) {
                    if (dictionary.contains(word.substring(i, j)))
                        pos[j] = true;
                }
            }
            if (pos[word.length()])
                complexWords.add(word);
        }

        return complexWords;
    }

    public static List<String> splitIntoSentences(String str) {
        BreakIterator bi = BreakIterator.getSentenceInstance();
        bi.setText(str);
        List<String> sentences = new LinkedList<>();

        for (int i = 0; bi.next() != BreakIterator.DONE; i = bi.current()) {
            sentences.add(str.substring(i, bi.current()));
        }

        return sentences;
    }

    public static Set<String> splitIntoWords(String str) {
        BreakIterator bi = BreakIterator.getWordInstance();
        bi.setText(str);
        Set<String> words = new HashSet<>();

        for (int i = 0; bi.next() != BreakIterator.DONE; i = bi.current()) {
            words.add(str.substring(i, bi.current()));
        }

        return words;
    }

    public static String getFirstWord(String str) {
        BreakIterator bi = BreakIterator.getWordInstance();
        bi.setText(str);

        if (bi.next() == BreakIterator.DONE) return "";
        return str.substring(0, bi.current());
    }

    public static String replace(String str, String oldWord, String newWord) {
        StringBuilder builder = new StringBuilder(str);
        String lowerCaseOldWord = oldWord.toLowerCase();

        for (int i = builder.toString().toLowerCase().indexOf(oldWord);
             i != -1;
             i = builder.toString().toLowerCase().indexOf(lowerCaseOldWord, i + newWord.length())) {
            builder.replace(i, i + oldWord.length(), newWord);
        }
        return builder.toString();
    }

    public static String colorize(String str, TEXT_COLOR color) {
        return TEXT_COLOR_VALUES.get(color) + str + RESET;
    }
}
