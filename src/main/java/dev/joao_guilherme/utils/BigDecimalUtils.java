package dev.joao_guilherme.utils;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public abstract class BigDecimalUtils {

    private static MathContext mathContext = MathContext.DECIMAL128;
    public static final BigDecimal PI = BigDecimalMath.pi(mathContext);
    public static final BigDecimal E = BigDecimalMath.e(mathContext);

    private BigDecimalUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void withMathContext(MathContext mathContext) {
        BigDecimalUtils.mathContext = mathContext;
    }

    public static BigDecimal valueOf(long value) {
        return new BigDecimal(value, mathContext);
    }

    public static BigDecimal valueOf(double value) {
        return new BigDecimal(String.valueOf(value), mathContext);
    }

    public static BigDecimal valueOf(String value) {
        return new BigDecimal(value, mathContext);
    }

    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        return a.subtract(b, mathContext);
    }

    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b, mathContext);
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, mathContext);
    }

    public static BigDecimal pow(BigDecimal base, BigDecimal exponent) {
        return BigDecimalMath.pow(base, exponent, mathContext);
    }

    public static BigDecimal factorial(BigDecimal a) {
        return BigDecimalMath.factorial(a, mathContext);
    }

    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        return a.multiply(b, mathContext);
    }

    public static BigDecimal sqrt(BigDecimal a) {
        return BigDecimalMath.sqrt(a, mathContext);
    }

    public static BigDecimal nthRoot(BigDecimal a, BigDecimal n) {
        return BigDecimalMath.root(a, n, mathContext);
    }

    public static BigDecimal log(BigDecimal base, BigDecimal a) {
        return divide(BigDecimalMath.log(base, mathContext), BigDecimalMath.log(a, mathContext));
    }

    public static BigDecimal ln(BigDecimal value) {
        return divide(BigDecimalMath.log(value, mathContext), BigDecimalMath.log(E, mathContext));
    }

    public static BigDecimal exp(BigDecimal value) {
        return BigDecimalMath.exp(value, mathContext);
    }

    public static BigDecimal sin(BigDecimal arg) {
        return BigDecimalMath.sin(arg, mathContext);
    }

    public static BigDecimal asin(BigDecimal arg) {
        return BigDecimalMath.asin(arg, mathContext);
    }

    public static BigDecimal sinh(BigDecimal arg) {
        return BigDecimalMath.sinh(arg, mathContext);
    }

    public static BigDecimal cos(BigDecimal arg) {
        return BigDecimalMath.cos(arg, mathContext);
    }

    public static BigDecimal acos(BigDecimal arg) {
        if (arg.compareTo(BigDecimal.ONE) == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimalMath.acos(arg, mathContext);
    }

    public static BigDecimal cosh(BigDecimal arg) {
        return BigDecimalMath.cosh(arg, mathContext);
    }

    public static BigDecimal tan(BigDecimal arg) {
        return BigDecimalMath.tan(arg, mathContext);
    }

    public static BigDecimal atan(BigDecimal arg) {
        return BigDecimalMath.atan(arg, mathContext);
    }

    public static BigDecimal tanh(BigDecimal arg) {
        return BigDecimalMath.tanh(arg, mathContext);
    }

    public static BigDecimal abs(BigDecimal a) {
        return a.abs(mathContext);
    }

    public static BigDecimal ceil(BigDecimal a) {
        return a.setScale(0, RoundingMode.CEILING);
    }

    public static BigDecimal floor(BigDecimal a) {
        return a.setScale(0, RoundingMode.FLOOR);
    }

    public static BigDecimal removeScientificNotation(BigDecimal a) {
        return new BigDecimal(a.toPlainString());
    }

    public static BigDecimal max(BigDecimal... values) {
        BigDecimal max = values[0];
        for (BigDecimal value : values) {
            if (value.compareTo(max) > 0) {
                max = value;
            }
        }
        return max;
    }

    public static BigDecimal min(BigDecimal... values) {
        BigDecimal min = values[0];
        for (BigDecimal value : values) {
            if (value.compareTo(min) < 0) {
                min = value;
            }
        }
        return min;
    }

    public static BigDecimal round(BigDecimal value) {
        return value.setScale(0, RoundingMode.HALF_UP);
    }

    public static BigDecimal mod(BigDecimal a, BigDecimal b) {
        return a.remainder(b, mathContext);
    }
}
