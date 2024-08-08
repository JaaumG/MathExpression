import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

class EquationEvaluatorTests {

    @Test
    @DisplayName("Basic addition")
    void testBasicAddition() {
        Expression expression = new Expression("2 + x = 4");
        assertEquals(BigDecimalUtils.valueOf(2), expression.solveForX());
    }

    @Test
    @Disabled("Need to fix")
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
    @Disabled("Need to fix")
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
    @Disabled("Need to fix")
    @DisplayName("Complex equation with division and subtraction")
    void testComplexDivisionSubtraction() {
        Expression expression = new Expression("x / 2 - 3 = 5");
        assertEquals(BigDecimalUtils.valueOf(16), expression.solveForX());
    }

    @Test
    @Disabled("Need to fix")
    @DisplayName("Complex equation with multiple operations")
    void testMultipleOperations() {
        Expression expression = new Expression("2 * x + 3 - x / 2 = 10");
        assertEquals(BigDecimalUtils.valueOf(4.7), expression.solveForX());
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
}
