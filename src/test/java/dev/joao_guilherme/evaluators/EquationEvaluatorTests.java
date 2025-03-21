package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EquationEvaluatorTests {

    @Test
    @DisplayName("Basic addition")
    void testBasicAddition() {
        Expression expression = new Expression("2 + x = 4");
        assertEquals(BigDecimalUtils.valueOf(2), expression.solveForX());
    }

    @Test
    @DisplayName("Basic subtraction")
    void testBasicSubtraction() {
        Expression expression = new Expression("x - 3 = 7");
        assertEquals(BigDecimalUtils.valueOf(10), expression.solveForX());
    }

    @Test
    @DisplayName("Basic multiplication")
    void testBasicMultiplication() {
        Expression expression = new Expression("3 * x = 12");
        assertEquals(BigDecimalUtils.valueOf(4), expression.solveForX());
    }

    @Test
    @DisplayName("Basic division")
    void testBasicDivision() {
        Expression expression = new Expression("x / 4 = 3");
        assertEquals(BigDecimalUtils.valueOf(12), expression.solveForX());
    }

    @Test
    @DisplayName("Complex equation with addition and subtraction")
    void testComplexAdditionSubtraction() {
        Expression expression = new Expression("2 + x - 3 = 7");
        assertEquals(BigDecimalUtils.valueOf(8), expression.solveForX());
    }

    @Test
    @DisplayName("Complex equation with multiplication and addition")
    void testComplexMultiplicationAddition() {
        Expression expression = new Expression("2 * x + 3 = 11");
        assertEquals(BigDecimalUtils.valueOf(4), expression.solveForX());
    }

    @Test
    @DisplayName("Complex equation with division and subtraction")
    void testComplexDivisionSubtraction() {
        Expression expression = new Expression("x / 2 - 3 = 5");
        assertEquals(BigDecimalUtils.valueOf(16), expression.solveForX());
    }

    @Test
    @DisplayName("Complex equation with multiple operations")
    void testMultipleOperations() {
        Expression expression = new Expression("2 * x + 3 - x / 2 = 10");
        assertEquals(4.666666666666666666666666, expression.solveForX().floatValue(), 0.00001);
    }

    @Test
    @DisplayName("Equation with negative solution")
    void testNegativeSolution() {
        Expression expression = new Expression("3 * x + 2 = -4");
        assertEquals(BigDecimalUtils.valueOf(-2), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with fractions")
    void testFractionSolution() {
        Expression expression = new Expression("2.5 * x = 5");
        assertEquals(BigDecimalUtils.valueOf(2), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with variable on both sides")
    void testVariableOnBothSides() {
        Expression expression = new Expression("2x + 3 = x + 8");
        assertEquals(BigDecimalUtils.valueOf(5), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with variable on the right side")
    void testVariableOnRightSide() {
        Expression expression = new Expression("10 = 2 * x");
        assertEquals(BigDecimalUtils.valueOf(5), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with multiple variables on both sides")
    void testMultipleVariablesOnBothSides() {
        Expression expression = new Expression("3x + 2 = 5x - 8");
        assertEquals(BigDecimalUtils.valueOf(5), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with parentheses")
    void testEquationWithParentheses() {
        Expression expression = new Expression("2(x + 3) = 14");
        assertEquals(BigDecimalUtils.valueOf(4), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with nested parentheses")
    void testEquationWithNestedParentheses() {
        Expression expression = new Expression("3(x - 2(x - 1)) = 6");
        assertEquals(BigDecimalUtils.valueOf(0), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with power")
    void testEquationWithPower() {
        Expression expression = new Expression("x^2 = 16");
        assertEquals(BigDecimalUtils.valueOf(4), expression.solveForX());
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Quadratic equation with one solution")
    void testQuadraticEquationWithOneSolution() {
        Expression expression = new Expression("x^2 - 6x + 9 = 0");
        assertEquals(BigDecimal.valueOf(3), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with square root")
    void testEquationWithSquareRoot() {
        Expression expression = new Expression("sqrt(x) = 3");
        assertEquals(9, expression.solveForX().floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Equation with cube root")
    void testEquationWithCubeRoot() {
        Expression expression = new Expression("nrt(x, 3) = 2");
        assertEquals(8, expression.solveForX().floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Equation with logarithm")
    void testEquationWithLogarithm() {
        Expression expression = new Expression("log(x) = 2");
        assertEquals(100, expression.solveForX().floatValue(), 0.0001);
    }

    @Test
    @DisplayName("Equation with natural logarithm")
    void testEquationWithNaturalLogarithm() {
        Expression expression = new Expression("ln(x) = 1");
        assertEquals(Math.E, expression.solveForX().floatValue(), 0.0001);
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Equation with exponential")
    void testEquationWithExponential() {
        Expression expression = new Expression("e^x = 1");
        assertEquals(BigDecimalUtils.valueOf(0), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with sine function")
    void testEquationWithSineFunction() {
        Expression expression = new Expression("sin(x) = 0");
        assertEquals(BigDecimalUtils.valueOf(0), expression.solveForX());
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Equation with cosine function")
    void testEquationWithCosineFunction() {
        Expression expression = new Expression("cos(x) = 1");
        assertEquals(BigDecimalUtils.valueOf(0), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with tangent function")
    void testEquationWithTangentFunction() {
        Expression expression = new Expression("tan(x) = 1");
        assertEquals(Math.PI/4, expression.solveForX().floatValue(), 0.0001);
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Equation with fraction denominator containing variable")
    void testEquationWithFractionDenominatorContainingVariable() {
        Expression expression = new Expression("3/(x-2) = 1");
        assertEquals(BigDecimalUtils.valueOf(5), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with complex right side")
    void testEquationWithComplexRightSide() {
        Expression expression = new Expression("x = (3 + 5) / 2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with decimal coefficients")
    void testEquationWithDecimalCoefficients() {
        Expression expression = new Expression("1.5x + 2.3 = 8.3");
        assertEquals(BigDecimalUtils.valueOf(4), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with large numbers")
    void testEquationWithLargeNumbers() {
        Expression expression = new Expression("1000x = 5000");
        assertEquals(BigDecimalUtils.valueOf(5), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with small decimal result")
    void testEquationWithSmallDecimalResult() {
        Expression expression = new Expression("100x = 1");
        assertEquals(BigDecimalUtils.valueOf(0.01), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with implicit multiplication")
    void testEquationWithImplicitMultiplication() {
        Expression expression = new Expression("2(x + 3) + 4x = 22");
        assertEquals(2.666667, expression.solveForX().floatValue(), 0.0001);
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Equation with multiple powers")
    void testEquationWithMultiplePowers() {
        Expression expression = new Expression("x^2 - 4 = (x - 2) * (x + 2)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with common fractions")
    void testEquationWithCommonFractions() {
        Expression expression = new Expression("x/3 + x/2 = 5");
        assertEquals(BigDecimalUtils.valueOf(6), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with variable in denominator")
    void testEquationWithVariableInDenominator() {
        Expression expression = new Expression("5/x = 1");
        assertEquals(BigDecimalUtils.valueOf(5), expression.solveForX());
    }

    @Test
    @DisplayName("Equation that simplifies to zero")
    void testEquationThatSimpliflesToZero() {
        Expression expression = new Expression("x - x = 0");
        assertThrows(ArithmeticException.class, expression::solveForX);
    }

    @Test
    @DisplayName("Equation with no solution")
    void testEquationWithNoSolution() {
        Expression expression = new Expression("x = x + 1");
        assertThrows(ArithmeticException.class, expression::solveForX);
    }

    @Test
    @DisplayName("Equation with variables and parameters")
    void testEquationWithVariablesAndParameters() {
        Expression expression = new Expression("a*x + b = c")
                .withVariable("a", BigDecimalUtils.valueOf(2))
                .withVariable("b", BigDecimalUtils.valueOf(3))
                .withVariable("c", BigDecimalUtils.valueOf(7));
        assertEquals(BigDecimalUtils.valueOf(2), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with multiple terms on both sides")
    void testEquationWithMultipleTermsOnBothSides() {
        Expression expression = new Expression("2x + 3 - x = 4x - 7 + 3x");
        assertEquals(1.666666667, expression.solveForX().floatValue(), 0.0001);
    }

    @Test
    @Disabled("Need to implement scientific notation")
    @DisplayName("Equation with scientific notation")
    void testEquationWithScientificNotation() {
        Expression expression = new Expression("1e3 * x = 5e3");
        assertEquals(BigDecimalUtils.valueOf(5), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with mixed operations and priorities")
    void testEquationWithMixedOperationsAndPriorities() {
        Expression expression = new Expression("3(x + 2) - 2(x - 1) = 5x + 1");
        assertEquals(1.75, expression.solveForX().floatValue());
    }

    @Test
    @DisplayName("Equation with reciprocal variable")
    void testEquationWithReciprocalVariable() {
        Expression expression = new Expression("1/x = 0.25");
        assertEquals(4, expression.solveForX().floatValue(), 0.0001);
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Equation with fraction and variable in numerator and denominator")
    void testEquationWithFractionAndVariableInNumeratorAndDenominator() {
        Expression expression = new Expression("(x + 2)/(x - 1) = 3");
        assertEquals(BigDecimalUtils.valueOf(2), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with multiple variables and simplification")
    void testEquationWithMultipleVariablesAndSimplification() {
        Expression expression = new Expression("2x - x + 3x - 2x = 8");
        assertEquals(BigDecimalUtils.valueOf(4), expression.solveForX());
    }

    @Test
    @DisplayName("Equation with percentage")
    void testEquationWithPercentage() {
        Expression expression = new Expression("x + 10% = 110", new PercentageBasedAdditionEvaluator());
        assertEquals(100, expression.solveForX().floatValue(), 0.1);
    }

    @Test
    @Disabled("Needs to be fixed")
    @DisplayName("Equation with inverse trigonometric function")
    void testEquationWithInverseTrigonometricFunction() {
        Expression expression = new Expression("asin(x) = pi/6");
        assertEquals(BigDecimalUtils.valueOf(0.5), expression.solveForX());
    }
}
