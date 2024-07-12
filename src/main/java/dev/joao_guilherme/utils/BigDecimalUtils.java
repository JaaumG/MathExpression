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
}
