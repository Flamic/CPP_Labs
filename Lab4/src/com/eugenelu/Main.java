package com.eugenelu;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.eugenelu.StringTools.*;

public class Main {
    public static final String DATA_FILE_PATH = "data\\data.txt";

    public static void main(String[] args) {
        String data;
        try {
            data = Files.readString(Path.of(DATA_FILE_PATH));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        Set<String> selectedWords = getComplexWords(splitIntoWords(data));

        System.out.printf("File content:\n\"%s\"\n\n", data);
        System.out.print("Processed content:\n\"");
        for (var sentence : splitIntoSentences(data)) {
            var firstWord = colorize(getFirstWord(sentence), TEXT_COLOR.BLUE);
            for (var word : selectedWords)
                sentence = replace(sentence, word, firstWord);
            System.out.print(sentence);
        }
    }
}
