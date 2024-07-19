import dev.joao_guilherme.Expression;
import dev.joao_guilherme.evaluators.PercentageBasedAdditionEvaluator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PercentageBasedAdditionEvaluatorTests {

    @Test
    @DisplayName("Basic addition with percentage")
    public void basicAdditionWithPercentage() {
        Expression expression = new Expression("2000 + 50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(3000).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic subtraction with percentage")
    public void basicSubtractionWithPercentage() {
        Expression expression = new Expression("1500 - 10%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1350).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic multiplication with percentage")
    public void basicMultiplicationWithPercentage() {
        Expression expression = new Expression("100 * 20%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(20).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic division with percentage")
    public void basicDivisionWithPercentage() {
        Expression expression = new Expression("500 / 50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1000).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Sum and subtraction of percentage")
    public void sumAndSubtractionOfPercentage() {
        Expression expression = new Expression("1000 + 25% - 10%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1125).compareTo(expression.evaluate()), 0);
    }


    @Test
    @DisplayName("Sum and multiplication of percentage")
    public void sumAndMultiplicationOfPercentage() {
        Expression expression = new Expression("100 + 10% * 2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(100.2).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Subtraction and division of percentage")
    public void subtractionAndDivisionOfPercentage() {
        Expression expression = new Expression("1000 - 20% / 4", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(999.95).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Multiplication and addition of percentage")
    public void multiplicationAndAdditionOfPercentage() {
        Expression expression = new Expression("500 * 10% + 300", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(350).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Division and addition of percentage")
    public void divisionAndAdditionOfPercentage() {
        Expression expression = new Expression("1000 / 25% + 50", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(4050).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Sum with multiplication inside parentheses")
    public void sumWithMultiplicationInsideParentheses() {
        Expression expression = new Expression("1000 + (50% * 2)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1001).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Subtraction with addition of percentages inside parentheses")
    public void subtractionWithAdditionOfPercentagesInsideParentheses() {
        Expression expression = new Expression("200 - (10% + 5%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(199.895).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Sum with subtraction of percentages inside parentheses")
    public void sumWithSubtractionOfPercentagesInsideParentheses() {
        Expression expression = new Expression("150 + (20% - 10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(149.9).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Multiplication with percentage inside parentheses")
    public void multiplicationWithPercentageInsideParentheses() {
        Expression expression = new Expression("(100 + 20%) * 2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(240).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Division with addition of percentages inside parentheses")
    public void divisionWithAdditionOfPercentagesInsideParentheses() {
        Expression expression = new Expression("300 / (50% + 50%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(400).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Subtraction and addition of percentages")
    public void subtractionAndAdditionOfPercentages() {
        Expression expression = new Expression("2000 - 50% + 25%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1250).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Multiplication with sum of percentages inside parentheses")
    public void multiplicationWithSumOfPercentagesInsideParentheses() {
        Expression expression = new Expression("100 * (10% + 10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(11).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Division with sum of percentages inside parentheses")
    public void divisionWithSumOfPercentagesInsideParentheses() {
        Expression expression = new Expression("500 / (25% + 25%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1600).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Sum with multiplication and negative percentage")
    public void sumWithMultiplicationAndNegativePercentage() {
        Expression expression = new Expression("1000 + 50% * -2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(999).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Subtraction with addition of negative percentages inside parentheses")
    public void subtractionWithAdditionOfNegativePercentagesInsideParentheses() {
        Expression expression = new Expression("200 - (10% + -5%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(199.905).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Negative sum with subtraction and negative percentage")
    public void negativeSumWithSubtractionAndNegativePercentage() {
        Expression expression = new Expression("-150 + (20% - 10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-150.1).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Multiplication with negative percentage inside parentheses")
    public void multiplicationWithNegativePercentageInsideParentheses() {
        Expression expression = new Expression("(100 + -20%) * 2", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(160).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Subtraction and addition with negative percentages")
    public void subtractionAndAdditionWithNegativePercentages() {
        Expression expression = new Expression("2000 - -50% + -25%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(2250).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Multiplication with sum of positive and negative percentages inside parentheses")
    public void multiplicationWithSumOfPositiveAndNegativePercentagesInsideParentheses() {
        Expression expression = new Expression("-100 * (10% + -10%)", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-9).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Negative sum with percentage")
    public void negativeSumWithPercentage() {
        Expression expression = new Expression("-2000 + 50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-3000).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Positive sum with negative percentage")
    public void positiveSumWithNegativePercentage() {
        Expression expression = new Expression("2000 + -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(1000).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Negative subtraction with negative percentage")
    public void negativeSubtractionWithNegativePercentage() {
        Expression expression = new Expression("2000 - -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(3000).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Subtraction with large percentage")
    public void subtractionWithLargePercentage() {
        Expression expression = new Expression("100 - 200%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-100).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Multiplication with negative percentage")
    public void multiplicationWithNegativePercentage() {
        Expression expression = new Expression("100 * -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-50).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Division with negative percentage")
    public void divisionWithNegativePercentage() {
        Expression expression = new Expression("500 / -50%", new PercentageBasedAdditionEvaluator());
        assertEquals(BigDecimal.valueOf(-1000).compareTo(expression.evaluate()), 0);
    }

}
