package com.eugenelu;

public class Main {
    public static void main(String[] args) {
        String input = StringExtensions.readInput("");
        if (input.isEmpty()) {
            System.out.println("<Input is empty>");
            return;
        }

        System.out.println("List of target words:");
        var words = StringExtensions.getPenultimateWord(input);
        if (words.isEmpty()) {
            System.out.println("<No words>");
            return;
        }

        words.forEach(System.out::println);
    }
}
