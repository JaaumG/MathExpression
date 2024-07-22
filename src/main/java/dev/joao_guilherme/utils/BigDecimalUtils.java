package dev.joao_guilherme.utils;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class BigDecimalUtils {

    private static MathContext MATH_CONTEXT = MathContext.DECIMAL128;
    public static final BigDecimal PI = BigDecimalMath.pi(MATH_CONTEXT);
    public static final BigDecimal E = BigDecimalMath.e(MATH_CONTEXT);

    public static void withMathContext(MathContext mathContext) {
        MATH_CONTEXT = mathContext;
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b, MATH_CONTEXT);
    }

    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b, MATH_CONTEXT);
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, MATH_CONTEXT);
    }

    public static BigDecimal pow(BigDecimal base, BigDecimal exponent) {
        return BigDecimalMath.pow(base, exponent, MATH_CONTEXT);
    }

    public static BigDecimal factorial(BigDecimal a) {
        return BigDecimalMath.factorial(a, MATH_CONTEXT);
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b, MATH_CONTEXT);
    }

    public static BigDecimal sqrt(BigDecimal a) {
        return BigDecimalMath.sqrt(a, MATH_CONTEXT);
    }

    public static BigDecimal nthRoot(BigDecimal a, BigDecimal n) {
        return BigDecimalMath.root(a, n, MATH_CONTEXT);
    }

    public static BigDecimal log(BigDecimal base, BigDecimal a) {
        return divide(BigDecimalMath.log(base, MATH_CONTEXT), BigDecimalMath.log(a, MATH_CONTEXT));
    }

    public static BigDecimal ln(BigDecimal value) {
        return divide(BigDecimalMath.log(value, MATH_CONTEXT), BigDecimalMath.log(E, MATH_CONTEXT));
    }

    public static BigDecimal exp(BigDecimal value) {
        return BigDecimalMath.exp(value, MATH_CONTEXT);
    }

    public static BigDecimal sin(BigDecimal arg) {
        return BigDecimalMath.sin(arg, MATH_CONTEXT);
    }

    public static BigDecimal cos(BigDecimal arg) {
        return BigDecimalMath.cos(arg, MATH_CONTEXT);
    }

    public static BigDecimal tan(BigDecimal arg) {
        return BigDecimalMath.tan(arg, MATH_CONTEXT);
    }

    public static BigDecimal abs(BigDecimal a) {
        return a.abs(MATH_CONTEXT);
    }

    public static BigDecimal ceil(BigDecimal a) {
        return a.setScale(0, RoundingMode.CEILING);
    }

    public static BigDecimal floor(BigDecimal a) {
        return a.setScale(0, RoundingMode.FLOOR);
    }
}
