package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PercentageBasedAdditionEvaluatorTests {

    @Test
    @DisplayName("Basic addition with percentage")
    void basicAdditionWithPercentage() {
        Expression<BigDecimal> expression = new Expression<>("2000 + 50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(3000), expression.evaluate());
    }

    @Test
    @DisplayName("Basic subtraction with percentage")
    void basicSubtractionWithPercentage() {
        Expression<BigDecimal> expression = new Expression<>("1500 - 10%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1350), expression.evaluate());
    }

    @Test
    @DisplayName("Basic multiplication with percentage")
    void basicMultiplicationWithPercentage() {
        Expression<BigDecimal> expression = new Expression<>("100 * 20%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(20), expression.evaluate());
    }

    @Test
    @DisplayName("Basic division with percentage")
    void basicDivisionWithPercentage() {
        Expression<BigDecimal> expression = new Expression<>("500 / 50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1000), expression.evaluate());
    }

    @Test
    @DisplayName("Sum and subtraction of percentage")
    void sumAndSubtractionOfPercentage() {
        Expression<BigDecimal> expression = new Expression<>("1000 + 25% - 10%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1125), expression.evaluate());
    }


    @Test
    @DisplayName("Sum and multiplication of percentage")
    void sumAndMultiplicationOfPercentage() {
        Expression<BigDecimal> expression = new Expression<>("100 + 10% * 2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(100.2), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction and division of percentage")
    void subtractionAndDivisionOfPercentage() {
        Expression<BigDecimal> expression = new Expression<>("1000 - 20% / 4", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(999.95), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication and addition of percentage")
    void multiplicationAndAdditionOfPercentage() {
        Expression<BigDecimal> expression = new Expression<>("500 * 10% + 300", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(350), expression.evaluate());
    }

    @Test
    @DisplayName("Division and addition of percentage")
    void divisionAndAdditionOfPercentage() {
        Expression<BigDecimal> expression = new Expression<>("1000 / 25% + 50", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(4050), expression.evaluate());
    }

    @Test
    @DisplayName("Sum with multiplication inside parentheses")
    void sumWithMultiplicationInsideParentheses() {
        Expression<BigDecimal> expression = new Expression<>("1000 + (50% * 2)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1001), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction with addition of percentages inside parentheses")
    void subtractionWithAdditionOfPercentagesInsideParentheses() {
        Expression<BigDecimal> expression = new Expression<>("200 - (10% + 5%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(199.895), expression.evaluate());
    }

    @Test
    @DisplayName("Sum with subtraction of percentages inside parentheses")
    void sumWithSubtractionOfPercentagesInsideParentheses() {
        Expression<BigDecimal> expression = new Expression<>("150 + (20% - 10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(150.18), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication with percentage inside parentheses")
    void multiplicationWithPercentageInsideParentheses() {
        Expression<BigDecimal> expression = new Expression<>("(100 + 20%) * 2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(240), expression.evaluate());
    }

    @Test
    @DisplayName("Division with addition of percentages inside parentheses")
    void divisionWithAdditionOfPercentagesInsideParentheses() {
        Expression<BigDecimal> expression = new Expression<>("300 / (50% + 50%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(400), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction and addition of percentages")
    void subtractionAndAdditionOfPercentages() {
        Expression<BigDecimal> expression = new Expression<>("2000 - 50% + 25%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1250), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication with sum of percentages inside parentheses")
    void multiplicationWithSumOfPercentagesInsideParentheses() {
        Expression<BigDecimal> expression = new Expression<>("100 * (10% + 10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(11), expression.evaluate());
    }

    @Test
    @DisplayName("Division with sum of percentages inside parentheses")
    void divisionWithSumOfPercentagesInsideParentheses() {
        Expression<BigDecimal> expression = new Expression<>("500 / (25% + 25%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1600), expression.evaluate());
    }

    @Test
    @DisplayName("Sum with multiplication and negative percentage")
    void sumWithMultiplicationAndNegativePercentage() {
        Expression<BigDecimal> expression = new Expression<>("1000 + 50% * -2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(999), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction with addition of negative percentages inside parentheses")
    void subtractionWithAdditionOfNegativePercentagesInsideParentheses() {
        Expression<BigDecimal> expression = new Expression<>("200 - (10% + -5%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(199.905), expression.evaluate());
    }

    @Test
    @DisplayName("Negative sum with subtraction and negative percentage")
    void negativeSumWithSubtractionAndNegativePercentage() {
        Expression<BigDecimal> expression = new Expression<>("-150 + (20% - 10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-149.82), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication with negative percentage inside parentheses")
    void multiplicationWithNegativePercentageInsideParentheses() {
        Expression<BigDecimal> expression = new Expression<>("(100 + -20%) * 2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(160), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction and addition with negative percentages")
    void subtractionAndAdditionWithNegativePercentages() {
        Expression<BigDecimal> expression = new Expression<>("2000 - -50% + -25%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(2250), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication with sum of positive and negative percentages inside parentheses")
    void multiplicationWithSumOfPositiveAndNegativePercentagesInsideParentheses() {
        Expression<BigDecimal> expression = new Expression<>("-100 * (10% + -10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-9), expression.evaluate());
    }

    @Test
    @DisplayName("Negative sum with percentage")
    void negativeSumWithPercentage() {
        Expression<BigDecimal> expression = new Expression<>("-2000 + 50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-3000), expression.evaluate());
    }

    @Test
    @DisplayName("Positive sum with negative percentage")
    void positiveSumWithNegativePercentage() {
        Expression<BigDecimal> expression = new Expression<>("2000 + -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1000), expression.evaluate());
    }

    @Test
    @DisplayName("Negative subtraction with negative percentage")
    void negativeSubtractionWithNegativePercentage() {
        Expression<BigDecimal> expression = new Expression<>("2000 - -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(3000), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction with large percentage")
    void subtractionWithLargePercentage() {
        Expression<BigDecimal> expression = new Expression<>("100 - 200%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-100), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication with negative percentage")
    void multiplicationWithNegativePercentage() {
        Expression<BigDecimal> expression = new Expression<>("100 * -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-50), expression.evaluate());
    }

    @Test
    @DisplayName("Division with negative percentage")
    void divisionWithNegativePercentage() {
        Expression<BigDecimal> expression = new Expression<>("500 / -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-1000), expression.evaluate());
    }

    @Test
    @DisplayName("Multiple percentage operations in sequence")
    void multiplePercentageOperationsInSequence() {
        Expression<BigDecimal> expression = new Expression<>("1000 + 10% + 20% + 5%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1386), expression.evaluate());
    }

    @Test
    @DisplayName("Nested percentages in parentheses")
    void nestedPercentagesInParentheses() {
        Expression<BigDecimal> expression = new Expression<>("100 + (10% + (5% + 2%))", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(100.151), expression.evaluate());
    }

    @Test
    @DisplayName("Complex expressions with multiple nested percentages")
    void complexExpressionsWithMultipleNestedPercentages() {
        Expression<BigDecimal> expression = new Expression<>("1000 * (1 + 20%) / (1 - 10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimalUtils.add(BigDecimalUtils.valueOf(1333), BigDecimalUtils.divide(BigDecimal.ONE, BigDecimalUtils.valueOf(3))), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage with decimal points")
    void percentageWithDecimalPoints() {
        Expression<BigDecimal> expression = new Expression<>("100 + 12.5%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(112.5), expression.evaluate());
    }

    @Test
    @DisplayName("Very small percentage values")
    void verySmallPercentageValues() {
        Expression<BigDecimal> expression = new Expression<>("1000 + 0.01%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1000.1), expression.evaluate());
    }

    @Test
    @DisplayName("Very large percentage values")
    void veryLargePercentageValues() {
        Expression<BigDecimal> expression = new Expression<>("100 + 1000%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1100), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage applied to negative numbers")
    void percentageAppliedToNegativeNumbers() {
        Expression<BigDecimal> expression = new Expression<>("-500 + 20%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-600), expression.evaluate());
    }

    @Test
    @DisplayName("Percentages with variables")
    void percentagesWithVariables() {
        Expression<BigDecimal> expression = new Expression<>("base + rate%", new PercentageBasedAdditionEvaluator())
                .withVariable("base", BigDecimal.valueOf(1000))
                .withVariable("rate", BigDecimal.valueOf(15));
        assertEquals(BigDecimal.valueOf(1150), expression.evaluate());
    }

    @Test
    @DisplayName("Variable as percentage value")
    void variableAsPercentageValue() {
        Expression<BigDecimal> expression = new Expression<>("amount * rate%", new PercentageBasedAdditionEvaluator())
                .withVariable("amount", BigDecimal.valueOf(200))
                .withVariable("rate", BigDecimal.valueOf(7.5));
        assertEquals(BigDecimal.valueOf(15), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage with mathematical functions")
    void percentageWithMathematicalFunctions() {
        Expression<BigDecimal> expression = new Expression<>("1000 + sqrt(100)%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1100), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage with trigonometric functions")
    void percentageWithTrigonometricFunctions() {
        Expression<BigDecimal> expression = new Expression<>("100 + sin(pi/2)%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(101), expression.evaluate());
    }

    @Test
    @DisplayName("Chained percentage operations with different operators")
    void chainedPercentageOperationsWithDifferentOperators() {
        Expression<BigDecimal> expression = new Expression<>("1000 + 50% - 25% * 2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1499.5), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage increment/decrement pattern")
    void percentageIncrementDecrementPattern() {
        Expression<BigDecimal> expression = new Expression<>("base * (1 + rate%)", new PercentageBasedAdditionEvaluator())
                .withVariable("base", BigDecimal.valueOf(1000))
                .withVariable("rate", BigDecimal.valueOf(5));
        assertEquals(BigDecimal.valueOf(1050), expression.evaluate());

        expression = new Expression("base * (1 - rate%)", new PercentageBasedAdditionEvaluator())
                .withVariable("base", BigDecimal.valueOf(1000))
                .withVariable("rate", BigDecimal.valueOf(5));
        assertEquals(BigDecimal.valueOf(950), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage with absolute value function")
    void percentageWithAbsoluteValueFunction() {
        Expression<BigDecimal> expression = new Expression<>("100 + abs(-30)%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(130), expression.evaluate());
    }

    @Test
    @DisplayName("Multiple percentage operators applied to the same value")
    void multiplePercentageOperatorsAppliedToSameValue() {
        Expression<BigDecimal> expression = new Expression<>("100%% * 10", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(0.1), expression.evaluate());
    }

    @Test
    @DisplayName("Compound percentage growth over iterations")
    void compoundPercentageGrowthOverIterations() {
        Expression<BigDecimal> expression = new Expression<>("principal * (1 + rate%)^periods", new PercentageBasedAdditionEvaluator())
                .withVariable("principal", BigDecimal.valueOf(1000))
                .withVariable("rate", BigDecimal.valueOf(10))
                .withVariable("periods", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(1331), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage points difference calculation")
    void percentagePointsDifferenceCalculation() {
        Expression<BigDecimal> expression = new Expression<>("(newValue - oldValue) / oldValue * 100", new PercentageBasedAdditionEvaluator())
                .withVariable("newValue", BigDecimal.valueOf(150))
                .withVariable("oldValue", BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(50), expression.evaluate());
    }

    @Test
    @DisplayName("Mixing percentage symbols with percentage words")
    void mixingPercentageSymbolsWithPercentageWords() {
        Expression<BigDecimal> expression = new Expression<>("50% + percent(30)", new PercentageBasedAdditionEvaluator()).withFunction("percent", a -> a.divide(BigDecimal.valueOf(100)));
        assertEquals(BigDecimal.valueOf(0.8), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage with ceiling and floor functions")
    void percentageWithCeilingAndFloorFunctions() {
        Expression<BigDecimal> expression = new Expression<>("ceil(100 + 12.3%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(113), expression.evaluate());

        expression = new Expression("floor(100 + 12.7%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(112), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage expressions with rounding")
    void percentageExpressionsWithRounding() {
        Expression<BigDecimal> expression = new Expression<>("round(100 + 12.5%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(113), expression.evaluate());
    }

    @Test
    @DisplayName("Tax calculation with percentage")
    void taxCalculationWithPercentage() {
        Expression<BigDecimal> expression = new Expression<>("subtotal + subtotal * tax%", new PercentageBasedAdditionEvaluator())
                .withVariable("subtotal", BigDecimal.valueOf(100))
                .withVariable("tax", BigDecimal.valueOf(7));
        assertEquals(BigDecimal.valueOf(107), expression.evaluate());
    }

    @Test
    @DisplayName("Discount calculation with percentage")
    void discountCalculationWithPercentage() {
        Expression<BigDecimal> expression = new Expression<>("price * (1 - discount%)", new PercentageBasedAdditionEvaluator())
                .withVariable("price", BigDecimal.valueOf(100))
                .withVariable("discount", BigDecimal.valueOf(15));
        assertEquals(BigDecimal.valueOf(85), expression.evaluate());
    }

    @Test
    @DisplayName("Tip calculation with percentage")
    void tipCalculationWithPercentage() {
        Expression<BigDecimal> expression = new Expression<>("bill + bill * tip%", new PercentageBasedAdditionEvaluator())
                .withVariable("bill", BigDecimal.valueOf(50))
                .withVariable("tip", BigDecimal.valueOf(18));
        assertEquals(BigDecimal.valueOf(59), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage increase over multiple periods")
    void percentageIncreaseOverMultiplePeriods() {
        Expression<BigDecimal> expression = new Expression<>("initial * (1 + rate%)^periods", new PercentageBasedAdditionEvaluator())
                .withVariable("initial", BigDecimal.valueOf(100))
                .withVariable("rate", BigDecimal.valueOf(5))
                .withVariable("periods", BigDecimal.valueOf(5));
        assertEquals(BigDecimal.valueOf(127.62815625), expression.evaluate());
    }

    @Test
    @DisplayName("Multiple discounts applied sequentially")
    void multipleDiscountsAppliedSequentially() {
        Expression<BigDecimal> expression = new Expression<>("price * (1 - discount1%) * (1 - discount2%)", new PercentageBasedAdditionEvaluator())
                .withVariable("price", BigDecimal.valueOf(100))
                .withVariable("discount1", BigDecimal.valueOf(10))
                .withVariable("discount2", BigDecimal.valueOf(5));
        assertEquals(BigDecimal.valueOf(85.5), expression.evaluate());
    }

    @Test
    @DisplayName("Calculation with zero percentage")
    void calculationWithZeroPercentage() {
        Expression<BigDecimal> expression = new Expression<>("value + 0%", new PercentageBasedAdditionEvaluator())
                .withVariable("value", BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), expression.evaluate());
    }

    @Test
    @DisplayName("Division by percentage near zero should throw exception")
    void divisionByPercentageNearZeroShouldThrowException() {
        Expression<BigDecimal> expression = new Expression<>("100 / 0%", new PercentageBasedAdditionEvaluator());
        assertThrows(ArithmeticException.class, expression::evaluate);
    }
}
