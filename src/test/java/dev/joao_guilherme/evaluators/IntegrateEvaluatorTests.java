package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IntegrateEvaluatorTests {

    @Test
    @DisplayName("Basic integration")
    void testBasicIntegration() {
        Expression expression = new Expression("2x + 3");
        assertEquals(BigDecimalUtils.valueOf(40), expression.integrateForX(0, 5));
    }

    @Test
    @DisplayName("Integration of a constant")
    void testIntegrationOfConstant() {
        Expression expression = new Expression("5");
        assertEquals(BigDecimalUtils.valueOf(50), expression.integrateForX(0, 10));
    }

    @Test
    @DisplayName("Integration of power function")
    void testIntegrationOfPower() {
        Expression expression = new Expression("x^2");
        assertEquals(9, expression.integrateForX(0, 3).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration of polynomial")
    void testIntegrationOfPolynomial() {
        Expression expression = new Expression("3x^2 + 2x + 1");
        assertEquals(BigDecimalUtils.valueOf(14).floatValue(), expression.integrateForX(0, 2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration with negative limits")
    void testIntegrationWithNegativeLimits() {
        Expression expression = new Expression("x^3");
        assertEquals(BigDecimalUtils.valueOf(0), expression.integrateForX(-1, 1));
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Integration with negative function")
    void testIntegrationWithNegativeFunction() {
        Expression expression = new Expression("-x^2");
        assertEquals(BigDecimalUtils.valueOf(-9), expression.integrateForX(0, 3));
    }

    @Test
    @DisplayName("Integration of sine function")
    void testIntegrationOfSine() {
        Expression expression = new Expression("sin(x)");
        assertEquals(2, expression.integrateForX(0, Math.PI).floatValue(), 0.0001);
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Integration of cosine function")
    void testIntegrationOfCosine() {
        Expression expression = new Expression("cos(x)");
        assertEquals(BigDecimal.ZERO, expression.integrateForX(0, Math.PI));
    }

    @Test
    @DisplayName("Integration of exponential function")
    void testIntegrationOfExponential() {
        Expression expression = new Expression("e^x");
        assertEquals(1.7183, expression.integrateForX(0, 1).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration of logarithmic function")
    void testIntegrationOfLogarithm() {
        Expression expression = new Expression("ln(x)");
        assertEquals(BigDecimalUtils.valueOf(2 * Math.log(2) - 1).floatValue(), expression.integrateForX(1, 2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration of 1/x")
    void testIntegrationOfOneOverX() {
        Expression expression = new Expression("1/x");
        assertEquals(Math.log(2), expression.integrateForX(1, 2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration of trigonometric product")
    void testIntegrationOfTrigonometricProduct() {
        Expression expression = new Expression("sin(x) * cos(x)");
        assertEquals(0.5, expression.integrateForX(0, Math.PI/2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration with composition - sin(x^2)")
    void testIntegrationWithCompositionSinX2() {
        // Note: This has no elementary antiderivative, numerical approximation is used
        Expression expression = new Expression("sin(x^2)");
        assertEquals(0.310268, expression.integrateForX(0, 1).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration of sqrt(x)")
    void testIntegrationOfSqrt() {
        Expression expression = new Expression("sqrt(x)");
        assertEquals(0.66666, expression.integrateForX(0, 1).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration of rational function")
    void testIntegrationOfRationalFunction() {
        Expression expression = new Expression("1/(1+x^2)");
        assertEquals(BigDecimalUtils.valueOf(Math.PI/4).floatValue(), expression.integrateForX(0, 1).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration of absolute value")
    void testIntegrationOfAbsoluteValue() {
        Expression expression = new Expression("abs(x)");
        assertEquals(BigDecimal.ONE, expression.integrateForX(-1, 1));
    }


    @Test
    @DisplayName("Integration with variables and constants")
    void testIntegrationWithVariablesAndConstants() {
        Expression expression = new Expression("a*x^2 + b*x + c")
                .withVariable("a", BigDecimalUtils.valueOf(2))
                .withVariable("b", BigDecimalUtils.valueOf(3))
                .withVariable("c", BigDecimalUtils.valueOf(1));
        assertEquals(13.33333, expression.integrateForX(0, 2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration with implicit multiplication")
    void testIntegrationWithImplicitMultiplication() {
        Expression expression = new Expression("2(x^2 + 3x)");
        assertEquals(17.33333, expression.integrateForX(0, 2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration of hyperbolic functions")
    void testIntegrationOfHyperbolicFunctions() {
        Expression expression = new Expression("sinh(x)");
        assertEquals(Math.cosh(1) - 1, expression.integrateForX(0, 1).floatValue(), 0.0001);

        expression = new Expression("cosh(x)");
        assertEquals(Math.sinh(1), expression.integrateForX(0, 1).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration of tangent function")
    void testIntegrationOfTangent() {
        Expression expression = new Expression("tan(x)");
        assertEquals(BigDecimalUtils.valueOf(-Math.log(Math.cos(Math.PI/4))).floatValue(),
                expression.integrateForX(0, Math.PI/4).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration with parentheses")
    void testIntegrationWithParentheses() {
        Expression expression = new Expression("(x + 1) * (x - 1)");
        assertEquals(-1.3333, expression.integrateForX(-1, 1).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration with decimal values")
    void testIntegrationWithDecimalValues() {
        Expression expression = new Expression("1.5x^2 + 2.3x + 4.7");
        assertEquals(18, expression.integrateForX(0, 2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Definite integration with same limits")
    void testDefiniteIntegrationWithSameLimits() {
        Expression expression = new Expression("x^2 + 2x + 1");
        assertEquals(BigDecimalUtils.valueOf(0), expression.integrateForX(3, 3));
    }

    @Test
    @DisplayName("Integration of inverse trigonometric functions")
    void testIntegrationOfInverseTrigonometricFunctions() {
        Expression expression = new Expression("asin(x)");
        assertEquals(0.127825, expression.integrateForX(0, 0.5).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration of function with discontinuity")
    void testIntegrationOfFunctionWithDiscontinuity() {
        Expression expression = new Expression("1/x");
        assertThrows(ArithmeticException.class, () -> expression.integrateForX(-1, 1));
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Integration using numerical methods")
    void testIntegrationUsingNumericalMethods() {
        Expression expression = new Expression("e^(-x^2)");
        assertEquals(BigDecimalUtils.valueOf(0.7468), expression.integrateForX(0, 1));
    }

    @Test
    @DisplayName("Integration with substitution")
    void testIntegrationWithSubstitution() {
        Expression expression = new Expression("x*sqrt(1-x^2)");
        assertEquals(BigDecimalUtils.valueOf(1.0/3.0).floatValue(), expression.integrateForX(0, 1).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration by parts verification")
    void testIntegrationByPartsVerification() {
        Expression expression = new Expression("x*ln(x)");
        assertEquals(0.63629, expression.integrateForX(1, 2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration with fractional powers")
    void testIntegrationWithFractionalPowers() {
        Expression expression = new Expression("x^(3/2)");
        assertEquals(2.2627, expression.integrateForX(0, 2).floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Integration with negative powers")
    void testIntegrationWithNegativePowers() {
        Expression expression = new Expression("x^(-1/2)");
        assertEquals(1.236067978, expression.integrateForX(0.25, 1.25).floatValue(), 0.0001);
    }
}
