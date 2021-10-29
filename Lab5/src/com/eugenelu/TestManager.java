package com.eugenelu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

public class TestManager {
    @Test
    public void test1() {
        LinkedList<String> expectedResult = new LinkedList<String>();
        Assertions.assertEquals(expectedResult, StringExtensions.getPenultimateWord(
                "Hello!"
        ));
    }

    @Test
    public void test2() {
        LinkedList<String> expectedResult = new LinkedList<String>(List.of("Hello"));
        Assertions.assertEquals(expectedResult, StringExtensions.getPenultimateWord(
                "Hello world!"
        ));
    }

    @Test
    public void test3() {
        LinkedList<String> expectedResult = new LinkedList<String>(List.of("Hello"));
        Assertions.assertEquals(expectedResult, StringExtensions.getPenultimateWord(
                "Hello world!?a.. b ."
        ));
    }

    @Test
    public void test4() {
        LinkedList<String> expectedResult = new LinkedList<String>(List.of("Hello", "are", "I'm"));
        Assertions.assertEquals(expectedResult, StringExtensions.getPenultimateWord(
                "Hello user! How are you? I'm fine."
        ));
    }

    @Test
    public void test5() {
        LinkedList<String> expectedResult = new LinkedList<String>();
        Assertions.assertEquals(expectedResult, StringExtensions.getPenultimateWord(
                ""
        ));
    }

    @Test
    public void test6() {
        LinkedList<String> expectedResult = new LinkedList<String>();
        Assertions.assertEquals(expectedResult, StringExtensions.getPenultimateWord(
                "! ? .  .   ."
        ));
    }

    @Test
    public void test7() {
        LinkedList<String> expectedResult = new LinkedList<String>(
                List.of("cool", "are", "well-done", "Hello", "whole")
        );
        Assertions.assertEquals(expectedResult, StringExtensions.getPenultimateWord(
                "Some   cool   sentence. If you are\n reading this then you  are    cute! " +
                        "This is a well-done -   steak  . Hello? \"Hello  world \" ?  Hello   to the whole  wooorld!"
        ));
    }
}
