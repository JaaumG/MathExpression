package dev.joao_guilherme.utils;

import java.math.BigDecimal;
import java.math.MathContext;

public abstract class BigDecimalUtils {

    private static MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    public static void withMathContext(MathContext mathContext) {
        MATH_CONTEXT = mathContext;
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, MATH_CONTEXT);
    }

    public static BigDecimal pow(BigDecimal base, BigDecimal exponent) {
        if (exponent.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ONE;
        if (exponent.compareTo(BigDecimal.ONE) == 0) return base;
        if (exponent.compareTo(BigDecimal.ZERO) < 0) return BigDecimal.ONE.divide(pow(base, exponent.negate()), MATH_CONTEXT);
        if (exponent.scale() == 0) return base.pow(exponent.intValue());

        double lnBase = Math.log(base.doubleValue());
        double result = Math.exp(exponent.doubleValue() * lnBase);
        return new BigDecimal(result, MATH_CONTEXT);
    }

    public static BigDecimal factorial(BigDecimal a) {
        if (a.compareTo(BigDecimal.ZERO) < 0)
            throw new UnsupportedOperationException("Cannot calculate factorial of a negative number");
        if (a.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ONE;
        if (a.compareTo(BigDecimal.ONE) == 0) return BigDecimal.ONE;
        if (a.scale() == 0) {
            BigDecimal result = BigDecimal.ONE;
            for (BigDecimal i = BigDecimal.ONE; i.compareTo(a) <= 0; i = i.add(BigDecimal.ONE)) {
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
}
