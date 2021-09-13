package com.eugenelu;

import java.math.BigInteger;

public class Series {
    public static Fraction nextFraction(Fraction rhs, long index) {
        var numerator = rhs.getNumerator();
        var denominator = rhs.getDenominator();
        return new Fraction(index + 3, 2 * index * index);
    }

    public static BigFraction nextFraction(BigFraction rhs, long index) {
        var numerator = rhs.getNumerator();
        var denominator = rhs.getDenominator();
        return new BigFraction(index + 3, 2 * index * index);
    }

    public static Fraction sum(long n) {
        if (n < 1) return new Fraction();

        Fraction curFraction = new Fraction(4, 2);
        Fraction res = curFraction.clone();
        long i = 1;

        System.out.println(curFraction);
        for (; i < n; ++i) {
            res = res.add(curFraction = nextFraction(curFraction, i + 1));
            System.out.println("+ " + curFraction);
        }
        return res;
    }

    public static BigFraction sumBigFraction(long n) {
        if (n < 1) return new BigFraction();

        BigFraction curFraction = new BigFraction(4, 2);
        BigFraction res = curFraction.clone();
        long i = 1;

        System.out.println(curFraction);
        for (; i < n; ++i) {
            res = res.add(curFraction = nextFraction(curFraction, i + 1));
            System.out.println("+ " + curFraction);
        }
        return res;
    }
}
