package dev.joao_guilherme.utils;

import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.*;

public abstract class BigDecimalUtils {

    private static MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    public static void withMathContext(MathContext mathContext) {
        MATH_CONTEXT = mathContext;
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, MATH_CONTEXT);
    }

    public static BigDecimal pow(BigDecimal base, BigDecimal exponent) {
        if (exponent.compareTo(ZERO) == 0) return ONE;
        if (exponent.compareTo(ONE) == 0) return base;
        if (exponent.compareTo(ZERO) < 0) return ONE.divide(pow(base, exponent.negate()), MATH_CONTEXT);
        if (exponent.scale() == 0) return base.pow(exponent.intValue());

        double lnBase = Math.log(base.doubleValue());
        double result = Math.exp(exponent.doubleValue() * lnBase);
        return new BigDecimal(result, MATH_CONTEXT);
    }

    public static BigDecimal factorial(BigDecimal a) {
        if (a.compareTo(ZERO) < 0)
            throw new UnsupportedOperationException("Cannot calculate factorial of a negative number");
        if (a.compareTo(ZERO) == 0) return ONE;
        if (a.compareTo(ONE) == 0) return ONE;
        if (a.scale() == 0) {
            BigDecimal result = ONE;
            for (BigDecimal i = ONE; i.compareTo(a) <= 0; i = i.add(ONE)) {
                result = result.multiply(i);
            }
            return result;
        }
        double result = 1;
        for (double i = 1; i <= a.doubleValue(); i++) {
            result *= i;
        }
        return new BigDecimal(result, MATH_CONTEXT);
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b, MATH_CONTEXT);
    }

    public static BigDecimal sqrt(BigDecimal a) {
        if (a.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("Cannot compute the square root of a negative number.");
        }
        if (a.equals(ZERO)) {
            return ZERO;
        }

        BigDecimal guess = a.divide(TWO, MATH_CONTEXT);
        boolean more = true;

        while (more) {
            BigDecimal nextGuess = a.divide(guess, MATH_CONTEXT);
            nextGuess = nextGuess.add(guess);
            nextGuess = nextGuess.divide(TWO, MATH_CONTEXT);
            more = nextGuess.subtract(guess).abs().compareTo(ZERO) != 0;
            guess = nextGuess;
        }

        return guess;
    }
}
