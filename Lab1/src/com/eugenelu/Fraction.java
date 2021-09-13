package com.eugenelu;

import java.math.BigInteger;

public class Fraction {
    private long numerator;
    private long denominator;

    public Fraction() {
        this.numerator = 0;
        this.denominator = 0;
    }

    public Fraction(long numerator, long denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction clone() {
        return new Fraction(numerator, denominator);
    }

    public long getNumerator() {
        return numerator;
    }

    public void setNumerator(long numerator) {
        this.numerator = numerator;
    }

    public long getDenominator() {
        return denominator;
    }

    public void setDenominator(long denominator) {
        this.denominator = denominator;
    }

    public Fraction add(Fraction rhs) {
        var denominator = Util.lcm(this.denominator, rhs.denominator);
        var numerator = denominator / this.denominator * this.numerator
                + denominator / rhs.denominator * rhs.numerator;
        return new Fraction(numerator, denominator).reduce();
    }

    public BigFraction toBigFraction() {
        return new BigFraction(this.numerator, this.denominator);
    }

    @Override
    public String toString() {
        var integerPart = numerator / denominator;
        return integerPart + " " + (numerator % denominator) + "/" + denominator;
    }

    public Fraction reduce() {
        var gcd = Util.gcd(this.numerator, this.denominator);
        return new Fraction(this.numerator / gcd, this.denominator/gcd);
    }
}
