import dev.joao_guilherme.Expression;
import dev.joao_guilherme.evaluators.PercentageBasedAdditionEvaluator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PercentageBasedAdditionEvaluatorTests {

    @Test
    @DisplayName("Basic addition with percentage")
    void basicAdditionWithPercentage() {
        Expression expression = new Expression("2000 + 50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(3000), expression.evaluate());
    }

    @Test
    @DisplayName("Basic subtraction with percentage")
    void basicSubtractionWithPercentage() {
        Expression expression = new Expression("1500 - 10%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1350), expression.evaluate());
    }

    @Test
    @DisplayName("Basic multiplication with percentage")
    void basicMultiplicationWithPercentage() {
        Expression expression = new Expression("100 * 20%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(20), expression.evaluate());
    }

    @Test
    @DisplayName("Basic division with percentage")
    void basicDivisionWithPercentage() {
        Expression expression = new Expression("500 / 50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1000), expression.evaluate());
    }

    @Test
    @DisplayName("Sum and subtraction of percentage")
    void sumAndSubtractionOfPercentage() {
        Expression expression = new Expression("1000 + 25% - 10%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1125), expression.evaluate());
    }


    @Test
    @DisplayName("Sum and multiplication of percentage")
    void sumAndMultiplicationOfPercentage() {
        Expression expression = new Expression("100 + 10% * 2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(100.2), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction and division of percentage")
    void subtractionAndDivisionOfPercentage() {
        Expression expression = new Expression("1000 - 20% / 4", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(999.95), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication and addition of percentage")
    void multiplicationAndAdditionOfPercentage() {
        Expression expression = new Expression("500 * 10% + 300", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(350), expression.evaluate());
    }

    @Test
    @DisplayName("Division and addition of percentage")
    void divisionAndAdditionOfPercentage() {
        Expression expression = new Expression("1000 / 25% + 50", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(4050), expression.evaluate());
    }

    @Test
    @DisplayName("Sum with multiplication inside parentheses")
    void sumWithMultiplicationInsideParentheses() {
        Expression expression = new Expression("1000 + (50% * 2)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1001), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction with addition of percentages inside parentheses")
    void subtractionWithAdditionOfPercentagesInsideParentheses() {
        Expression expression = new Expression("200 - (10% + 5%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(199.895), expression.evaluate());
    }

    @Test
    @DisplayName("Sum with subtraction of percentages inside parentheses")
    void sumWithSubtractionOfPercentagesInsideParentheses() {
        Expression expression = new Expression("150 + (20% - 10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(150.18), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication with percentage inside parentheses")
    void multiplicationWithPercentageInsideParentheses() {
        Expression expression = new Expression("(100 + 20%) * 2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(240), expression.evaluate());
    }

    @Test
    @DisplayName("Division with addition of percentages inside parentheses")
    void divisionWithAdditionOfPercentagesInsideParentheses() {
        Expression expression = new Expression("300 / (50% + 50%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(400), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction and addition of percentages")
    void subtractionAndAdditionOfPercentages() {
        Expression expression = new Expression("2000 - 50% + 25%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1250), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication with sum of percentages inside parentheses")
    void multiplicationWithSumOfPercentagesInsideParentheses() {
        Expression expression = new Expression("100 * (10% + 10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(11), expression.evaluate());
    }

    @Test
    @DisplayName("Division with sum of percentages inside parentheses")
    void divisionWithSumOfPercentagesInsideParentheses() {
        Expression expression = new Expression("500 / (25% + 25%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1600), expression.evaluate());
    }

    @Test
    @DisplayName("Sum with multiplication and negative percentage")
    void sumWithMultiplicationAndNegativePercentage() {
        Expression expression = new Expression("1000 + 50% * -2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(999), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction with addition of negative percentages inside parentheses")
    void subtractionWithAdditionOfNegativePercentagesInsideParentheses() {
        Expression expression = new Expression("200 - (10% + -5%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(199.905), expression.evaluate());
    }

    @Test
    @DisplayName("Negative sum with subtraction and negative percentage")
    void negativeSumWithSubtractionAndNegativePercentage() {
        Expression expression = new Expression("-150 + (20% - 10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-149.82), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication with negative percentage inside parentheses")
    void multiplicationWithNegativePercentageInsideParentheses() {
        Expression expression = new Expression("(100 + -20%) * 2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(160), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction and addition with negative percentages")
    void subtractionAndAdditionWithNegativePercentages() {
        Expression expression = new Expression("2000 - -50% + -25%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(2250), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication with sum of positive and negative percentages inside parentheses")
    void multiplicationWithSumOfPositiveAndNegativePercentagesInsideParentheses() {
        Expression expression = new Expression("-100 * (10% + -10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-9), expression.evaluate());
    }

    @Test
    @DisplayName("Negative sum with percentage")
    void negativeSumWithPercentage() {
        Expression expression = new Expression("-2000 + 50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-3000), expression.evaluate());
    }

    @Test
    @DisplayName("Positive sum with negative percentage")
    void positiveSumWithNegativePercentage() {
        Expression expression = new Expression("2000 + -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1000), expression.evaluate());
    }

    @Test
    @DisplayName("Negative subtraction with negative percentage")
    void negativeSubtractionWithNegativePercentage() {
        Expression expression = new Expression("2000 - -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(3000), expression.evaluate());
    }

    @Test
    @DisplayName("Subtraction with large percentage")
    void subtractionWithLargePercentage() {
        Expression expression = new Expression("100 - 200%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-100), expression.evaluate());
    }

    @Test
    @DisplayName("Multiplication with negative percentage")
    void multiplicationWithNegativePercentage() {
        Expression expression = new Expression("100 * -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-50), expression.evaluate());
    }

    @Test
    @DisplayName("Division with negative percentage")
    void divisionWithNegativePercentage() {
        Expression expression = new Expression("500 / -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-1000), expression.evaluate());
    }
}
