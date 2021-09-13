package com.eugenelu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long n;

        while (true) {
            System.out.print("Enter count of fractions (n): ");
            n = sc.nextLong();
            if (n < 1) {
                System.out.println("Incorrect value. Enter positive non-zero integer number");
            }
            else break;
        }

        System.out.println(" = "
                + (n > 15 ? Series.sumBigFraction(n).toString() : Series.sum(n).toString()));

        sc.close();
    }
}
