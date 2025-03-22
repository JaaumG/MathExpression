package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DerivateEvaluatorTests {

    @Test
    @DisplayName("Basic derivative")
    void testBasicDerivative() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2x + 3");
        assertEquals(BigDecimalUtils.valueOf(2), expression.derivativeForX(5));
    }

    @Test
    @DisplayName("Derivative of a constant")
    void testDerivativeOfConstant() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("5");
        assertEquals(BigDecimalUtils.valueOf(0), expression.derivativeForX(10));
    }

    @Test
    @DisplayName("Derivative of power function")
    void testDerivativeOfPower() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x^2");
        assertEquals(BigDecimalUtils.valueOf(6), expression.derivativeForX(3));
    }

    @Test
    @DisplayName("Derivative of power function with negative exponent")
    void testDerivativeOfPowerWithNegativeExponent() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x^-1");
        assertEquals(BigDecimalUtils.valueOf(-0.25), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of polynomial")
    void testDerivativeOfPolynomial() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("3x^3 + 2x^2 + 5x + 1");
        assertEquals(BigDecimalUtils.valueOf(49), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of product")
    void testDerivativeOfProduct() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x * (2x + 1)");
        assertEquals(BigDecimalUtils.valueOf(9), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of quotient")
    void testDerivativeOfQuotient() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("(x^2) / (x + 1)");
        assertEquals(BigDecimalUtils.valueOf(0.888888888888889), expression.derivativeForX(2));
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Derivative of sine function")
    void testDerivativeOfSine() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sin(x)");
        assertEquals(BigDecimal.ZERO, expression.derivativeForX(Math.PI));
    }

    @Test
    @DisplayName("Derivative of cosine function")
    void testDerivativeOfCosine() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("cos(x)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.derivativeForX(0));
    }

    @Test
    @DisplayName("Derivative of tangent function")
    void testDerivativeOfTangent() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("tan(x)");
        assertEquals(BigDecimalUtils.valueOf(2), expression.derivativeForX(Math.PI/4));
    }

    @Test
    @DisplayName("Derivative of exponential function")
    void testDerivativeOfExponential() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("e^x");
        assertEquals(BigDecimalUtils.valueOf(1), expression.derivativeForX(0));
    }

    @Test
    @DisplayName("Derivative of logarithmic function")
    void testDerivativeOfLogarithm() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("ln(x)");
        assertEquals(BigDecimalUtils.valueOf(0.5), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of composition - sin(x^2)")
    void testDerivativeOfCompositionSinX2() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sin(x^2)");
        assertEquals(BigDecimalUtils.multiply(BigDecimalUtils.valueOf(4), BigDecimalUtils.cos(BigDecimalUtils.valueOf(4))).floatValue(), expression.derivativeForX(2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Derivative of composition - sqrt(x)")
    void testDerivativeOfCompositionSqrt() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sqrt(x)");
        assertEquals(BigDecimalUtils.valueOf(0.25), expression.derivativeForX(4));
    }

    @Test
    @DisplayName("Derivative of composition - ln(sin(x))")
    void testDerivativeOfCompositionLnSin() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("ln(sin(x))");
        assertEquals(BigDecimalUtils.divide(BigDecimal.ONE, BigDecimalUtils.tan(BigDecimalUtils.divide(BigDecimalUtils.PI, BigDecimalUtils.valueOf(6)))).floatValue(), expression.derivativeForX(BigDecimalUtils.divide(BigDecimalUtils.PI, BigDecimalUtils.valueOf(6)).doubleValue()).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Derivative of abs function")
    void testDerivativeOfAbs() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("abs(x)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.derivativeForX(5));
        assertEquals(BigDecimalUtils.valueOf(-1), expression.derivativeForX(-5));
    }

    @Test
    @DisplayName("Derivative of hyperbolic functions")
    void testDerivativeOfHyperbolicFunctions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sinh(x)");
        assertEquals(BigDecimalUtils.valueOf(Math.cosh(1)), expression.derivativeForX(1));

        expression = Expression.ofBigDecimal("cosh(x)");
        assertEquals(BigDecimalUtils.sinh(BigDecimal.ONE).floatValue(), expression.derivativeForX(1).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Derivative with parentheses")
    void testDerivativeWithParentheses() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("(x + 1) * (x - 1)");
        assertEquals(BigDecimalUtils.valueOf(4), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of implicit multiplication")
    void testDerivativeOfImplicitMultiplication() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2(x^2 + 3x)");
        assertEquals(BigDecimalUtils.valueOf(14), expression.derivativeForX(2));
    }

//    @Test
//    @DisplayName("Derivative with variables and constants")
//    void testDerivativeWithVariablesAndConstants() {
//        Expression<BigDecimal> expression = Expression.ofBigDecimal()("3x^2 + 2y + 5")
//                .withVariable("y", BigDecimalUtils.valueOf(10));
//        assertEquals(BigDecimalUtils.valueOf(12), expression.derivativeForX(2));
//    }

    @Test
    @DisplayName("Second derivative")
    void testSecondDerivative() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x^3 + 2x^2");
        Expression firstDerivative = Expression.ofBigDecimal("3x^2 + 4x");
        assertEquals(BigDecimalUtils.valueOf(16), firstDerivative.derivativeForX(2));
    }

//    @Test
//    @DisplayName("Derivative of function with multiple variables")
//    void testDerivativeWithMultipleVariables() {
//        Expression<BigDecimal> expression = Expression.ofBigDecimal()("x^2 + 2x*y + y^2")
//                .withVariable("y", BigDecimalUtils.valueOf(3));
//        assertEquals(BigDecimalUtils.valueOf(10), expression.derivativeForX(2));
//    }

    @Test
    @DisplayName("Derivative of rational function")
    void testDerivativeOfRationalFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("(x^2 - 1) / (x - 2)");
        assertEquals(BigDecimalUtils.valueOf(-2), expression.derivativeForX(3));
    }

    @Test
    @Disabled("Need to throw exception, no validation is in function right now")
    @DisplayName("Derivative at point of non-differentiability")
    void testDerivativeAtNonDifferentiabilityPoint() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("abs(x)");
        assertThrows(ArithmeticException.class, () -> expression.derivativeForX(0));
    }

    @Test
    @DisplayName("Derivative with decimal values")
    void testDerivativeWithDecimalValues() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("1.5x^2 + 2.3x + 4.7");
        assertEquals(BigDecimalUtils.valueOf(8.3), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of constant multiple")
    void testDerivativeOfConstantMultiple() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("5 * sin(x)");
        assertEquals(BigDecimalUtils.valueOf(5 * Math.cos(1)), expression.derivativeForX(1));
    }

    @Test
    @DisplayName("Derivative of trig function with coefficient")
    void testDerivativeOfTrigWithCoefficient() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sin(2x)");
        assertEquals(BigDecimalUtils.multiply(BigDecimal.TWO,BigDecimalUtils.cos(BigDecimal.TWO)).floatValue(), expression.derivativeForX(1).floatValue(), 0.0001);
    }
}