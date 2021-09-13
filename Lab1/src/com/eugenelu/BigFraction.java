package com.eugenelu;

import java.math.BigInteger;

public class BigFraction {
    private final BigInteger MAX_LONG_VALUE = BigInteger.valueOf(Long.MAX_VALUE);
    private BigInteger numerator;
    private BigInteger denominator;

    public BigFraction() {
        this.numerator = BigInteger.ZERO;
        this.denominator = BigInteger.ZERO;
    }

    public BigFraction(long numerator, long denominator) {
        this.numerator = BigInteger.valueOf(numerator);
        this.denominator = BigInteger.valueOf(denominator);
    }

    public BigFraction(BigInteger numerator, BigInteger denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public BigFraction clone() {
        return new BigFraction(numerator, denominator);
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    public void setNumerator(long numerator) {
        this.numerator = BigInteger.valueOf(numerator);
    }

    public void setNumerator(BigInteger numerator) {
        this.numerator = numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    public void setDenominator(long denominator) {
        this.denominator = BigInteger.valueOf(denominator);
    }

    public void setDenominator(BigInteger denominator) {
        this.denominator = denominator;
    }

    public BigFraction add(BigFraction rhs) {
        var denominator = Util.lcm(this.denominator, rhs.denominator);
        var numerator = denominator
                .divide(this.denominator)
                .multiply(this.numerator)
                .add(denominator
                        .divide(rhs.denominator)
                        .multiply(rhs.numerator));

        return new BigFraction(numerator, denominator).reduce();
    }

    public boolean isFraction() {
        return MAX_LONG_VALUE.compareTo(numerator) <= 0
                && MAX_LONG_VALUE.compareTo(denominator) <= 0;
    }

    public Fraction toFraction() {
        if (!isFraction()) throw new ArithmeticException("Fraction type overflow");
        return new Fraction(numerator.longValue(), denominator.longValue());
    }

    @Override
    public String toString() {
        var integerPart = numerator.divide(denominator);
        return integerPart + " " + (numerator.mod(denominator)) + "/" + denominator;
    }

    public BigFraction reduce() {
        var gcd = numerator.gcd(denominator);
        return new BigFraction(this.numerator.divide(gcd), this.denominator.divide(gcd));
    }
}
