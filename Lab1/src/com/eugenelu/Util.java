package com.eugenelu;

import java.math.BigInteger;

public class Util {
    public static BigInteger lcm(BigInteger lhs, BigInteger rhs) {
        return lhs.multiply(rhs).divide(lhs.gcd(rhs));
    }

    public static long lcm(long lhs, long rhs) {
        return lhs * rhs / gcd(lhs, rhs);
    }

    public static long gcd(long lhs, long rhs) {
        long tmp;
        if (rhs > lhs) {
            tmp = lhs;
            lhs = rhs;
            rhs = tmp;
        }

        while ((tmp = lhs % rhs) != 0) {
            lhs = rhs;
            rhs = tmp;
        }
        return rhs;
    }
}
