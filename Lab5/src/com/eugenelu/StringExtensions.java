package com.eugenelu;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringExtensions {
    public final static Pattern PENULTIMATE_WORD_PATTERN = Pattern.compile("([\\w'-]+)[\\s;,:\"-]+[\\w'-]+[\\s\"]*[.?!]"); //"(\\w*)\\W*\\s+\\S+\\s*[.?!]");

    public static LinkedList<String> getPenultimateWord(String str) {
        LinkedList<String> words = new LinkedList<>();
        Matcher mat = PENULTIMATE_WORD_PATTERN.matcher(str);

        while (mat.find())
            words.add(mat.group(1));

        return words;
    }

    public static String readInput(String stopString) {
        Scanner sc = new Scanner(System.in);
        StringBuilder input = new StringBuilder();
        String str;
        while (true) {
            str = sc.nextLine();
            if (str.equals(stopString))
                return input.toString();
            input.append(str);
        }
    }
}
