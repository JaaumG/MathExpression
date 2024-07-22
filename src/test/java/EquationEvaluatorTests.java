import dev.joao_guilherme.Expression;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EquationEvaluatorTests {

    @Test
    @DisplayName("Basic addition")
    public void testBasicAddition() {
        Expression expression = new Expression("2 + x = 4");
        assertEquals(BigDecimal.valueOf(2).compareTo(expression.solveForX()), 0);
    }

    @Test
    @Disabled("Need to fix")
    @DisplayName("Basic subtraction")
    public void testBasicSubtraction() {
        Expression expression = new Expression("x - 3 = 7");
        assertEquals(BigDecimal.valueOf(10).compareTo(expression.solveForX()), 0);
    }

    @Test
    @DisplayName("Basic multiplication")
    public void testBasicMultiplication() {
        Expression expression = new Expression("3 * x = 12");
        assertEquals(BigDecimal.valueOf(4).compareTo(expression.solveForX()), 0);
    }

    @Test
    @DisplayName("Basic division")
    public void testBasicDivision() {
        Expression expression = new Expression("x / 4 = 3");
        assertEquals(BigDecimal.valueOf(12).compareTo(expression.solveForX()), 0);
    }

    @Test
    @Disabled("Need to fix")
    @DisplayName("Complex equation with addition and subtraction")
    public void testComplexAdditionSubtraction() {
        Expression expression = new Expression("2 + x - 3 = 7");
        assertEquals(BigDecimal.valueOf(8).compareTo(expression.solveForX()), 0);
    }

    @Test
    @DisplayName("Complex equation with multiplication and addition")
    public void testComplexMultiplicationAddition() {
        Expression expression = new Expression("2 * x + 3 = 11");
        assertEquals(BigDecimal.valueOf(4).compareTo(expression.solveForX()), 0);
    }

    @Test
    @Disabled("Need to fix")
    @DisplayName("Complex equation with division and subtraction")
    public void testComplexDivisionSubtraction() {
        Expression expression = new Expression("x / 2 - 3 = 5");
        assertEquals(BigDecimal.valueOf(16).compareTo(expression.solveForX()), 0);
    }

    @Test
    @Disabled("Need to fix")
    @DisplayName("Complex equation with multiple operations")
    public void testMultipleOperations() {
        Expression expression = new Expression("2 * x + 3 - x / 2 = 10");
        assertEquals(BigDecimal.valueOf(4.7).compareTo(expression.solveForX()), 0);
    }

    @Test
    @DisplayName("Equation with negative solution")
    public void testNegativeSolution() {
        Expression expression = new Expression("3 * x + 2 = -4");
        assertEquals(BigDecimal.valueOf(-2).compareTo(expression.solveForX()), 0);
    }

    @Test
    @DisplayName("Equation with fractions")
    public void testFractionSolution() {
        Expression expression = new Expression("2.5 * x = 5");
        assertEquals(BigDecimal.valueOf(2).compareTo(expression.solveForX()), 0);
    }
}
