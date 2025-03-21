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
        Expression expression = new Expression("2x + 3");
        assertEquals(BigDecimalUtils.valueOf(2), expression.derivativeForX(5));
    }

    @Test
    @DisplayName("Derivative of a constant")
    void testDerivativeOfConstant() {
        Expression expression = new Expression("5");
        assertEquals(BigDecimalUtils.valueOf(0), expression.derivativeForX(10));
    }

    @Test
    @DisplayName("Derivative of power function")
    void testDerivativeOfPower() {
        Expression expression = new Expression("x^2");
        assertEquals(BigDecimalUtils.valueOf(6), expression.derivativeForX(3));
    }

    @Test
    @DisplayName("Derivative of power function with negative exponent")
    void testDerivativeOfPowerWithNegativeExponent() {
        Expression expression = new Expression("x^-1");
        assertEquals(BigDecimalUtils.valueOf(-0.25), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of polynomial")
    void testDerivativeOfPolynomial() {
        Expression expression = new Expression("3x^3 + 2x^2 + 5x + 1");
        assertEquals(BigDecimalUtils.valueOf(49), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of product")
    void testDerivativeOfProduct() {
        Expression expression = new Expression("x * (2x + 1)");
        assertEquals(BigDecimalUtils.valueOf(9), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of quotient")
    void testDerivativeOfQuotient() {
        Expression expression = new Expression("(x^2) / (x + 1)");
        assertEquals(BigDecimalUtils.valueOf(0.888888888888889), expression.derivativeForX(2));
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Derivative of sine function")
    void testDerivativeOfSine() {
        Expression expression = new Expression("sin(x)");
        assertEquals(BigDecimal.ZERO, expression.derivativeForX(Math.PI));
    }

    @Test
    @DisplayName("Derivative of cosine function")
    void testDerivativeOfCosine() {
        Expression expression = new Expression("cos(x)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.derivativeForX(0));
    }

    @Test
    @DisplayName("Derivative of tangent function")
    void testDerivativeOfTangent() {
        Expression expression = new Expression("tan(x)");
        assertEquals(BigDecimalUtils.valueOf(2), expression.derivativeForX(Math.PI/4));
    }

    @Test
    @DisplayName("Derivative of exponential function")
    void testDerivativeOfExponential() {
        Expression expression = new Expression("e^x");
        assertEquals(BigDecimalUtils.valueOf(1), expression.derivativeForX(0));
    }

    @Test
    @DisplayName("Derivative of logarithmic function")
    void testDerivativeOfLogarithm() {
        Expression expression = new Expression("ln(x)");
        assertEquals(BigDecimalUtils.valueOf(0.5), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of composition - sin(x^2)")
    void testDerivativeOfCompositionSinX2() {
        Expression expression = new Expression("sin(x^2)");
        assertEquals(BigDecimalUtils.multiply(BigDecimalUtils.valueOf(4), BigDecimalUtils.cos(BigDecimalUtils.valueOf(4))).floatValue(), expression.derivativeForX(2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Derivative of composition - sqrt(x)")
    void testDerivativeOfCompositionSqrt() {
        Expression expression = new Expression("sqrt(x)");
        assertEquals(BigDecimalUtils.valueOf(0.25), expression.derivativeForX(4));
    }

    @Test
    @DisplayName("Derivative of composition - ln(sin(x))")
    void testDerivativeOfCompositionLnSin() {
        Expression expression = new Expression("ln(sin(x))");
        assertEquals(BigDecimalUtils.divide(BigDecimal.ONE, BigDecimalUtils.tan(BigDecimalUtils.divide(BigDecimalUtils.PI, BigDecimalUtils.valueOf(6)))).floatValue(), expression.derivativeForX(BigDecimalUtils.divide(BigDecimalUtils.PI, BigDecimalUtils.valueOf(6)).doubleValue()).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Derivative of abs function")
    void testDerivativeOfAbs() {
        Expression expression = new Expression("abs(x)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.derivativeForX(5));
        assertEquals(BigDecimalUtils.valueOf(-1), expression.derivativeForX(-5));
    }

    @Test
    @DisplayName("Derivative of hyperbolic functions")
    void testDerivativeOfHyperbolicFunctions() {
        Expression expression = new Expression("sinh(x)");
        assertEquals(BigDecimalUtils.valueOf(Math.cosh(1)), expression.derivativeForX(1));

        expression = new Expression("cosh(x)");
        assertEquals(BigDecimalUtils.sinh(BigDecimal.ONE).floatValue(), expression.derivativeForX(1).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Derivative with parentheses")
    void testDerivativeWithParentheses() {
        Expression expression = new Expression("(x + 1) * (x - 1)");
        assertEquals(BigDecimalUtils.valueOf(4), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of implicit multiplication")
    void testDerivativeOfImplicitMultiplication() {
        Expression expression = new Expression("2(x^2 + 3x)");
        assertEquals(BigDecimalUtils.valueOf(14), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative with variables and constants")
    void testDerivativeWithVariablesAndConstants() {
        Expression expression = new Expression("3x^2 + 2y + 5")
                .withVariable("y", BigDecimalUtils.valueOf(10));
        assertEquals(BigDecimalUtils.valueOf(12), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Second derivative")
    void testSecondDerivative() {
        Expression expression = new Expression("x^3 + 2x^2");
        Expression firstDerivative = new Expression("3x^2 + 4x");
        assertEquals(BigDecimalUtils.valueOf(16), firstDerivative.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of function with multiple variables")
    void testDerivativeWithMultipleVariables() {
        Expression expression = new Expression("x^2 + 2x*y + y^2")
                .withVariable("y", BigDecimalUtils.valueOf(3));
        assertEquals(BigDecimalUtils.valueOf(10), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of rational function")
    void testDerivativeOfRationalFunction() {
        Expression expression = new Expression("(x^2 - 1) / (x - 2)");
        assertEquals(BigDecimalUtils.valueOf(-2), expression.derivativeForX(3));
    }

    @Test
    @Disabled("Need to throw exception, no validation is in function right now")
    @DisplayName("Derivative at point of non-differentiability")
    void testDerivativeAtNonDifferentiabilityPoint() {
        Expression expression = new Expression("abs(x)");
        assertThrows(ArithmeticException.class, () -> expression.derivativeForX(0));
    }

    @Test
    @DisplayName("Derivative with decimal values")
    void testDerivativeWithDecimalValues() {
        Expression expression = new Expression("1.5x^2 + 2.3x + 4.7");
        assertEquals(BigDecimalUtils.valueOf(8.3), expression.derivativeForX(2));
    }

    @Test
    @DisplayName("Derivative of constant multiple")
    void testDerivativeOfConstantMultiple() {
        Expression expression = new Expression("5 * sin(x)");
        assertEquals(BigDecimalUtils.valueOf(5 * Math.cos(1)), expression.derivativeForX(1));
    }

    @Test
    @DisplayName("Derivative of trig function with coefficient")
    void testDerivativeOfTrigWithCoefficient() {
        Expression expression = new Expression("sin(2x)");
        assertEquals(BigDecimalUtils.multiply(BigDecimal.TWO,BigDecimalUtils.cos(BigDecimal.TWO)).floatValue(), expression.derivativeForX(1).floatValue(), 0.0001);
    }
}