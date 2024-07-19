import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArithmeticExpressionEvaluatorTests {

    @Test
    @DisplayName("Basic addition")
    public void basicAddition() {
        Expression expression = new Expression("2 + 2");
        assertEquals(new BigDecimal(4).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic addition with negative numbers")
    public void basicAdditionWithNegativeNumbers() {
        Expression expression = new Expression("2 + -2");
        assertEquals(new BigDecimal(0).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic subtraction")
    public void basicSubtraction() {
        Expression expression = new Expression("2 - 2");
        assertEquals(new BigDecimal(0).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Assert subtraction order is correct")
    public void assertSubtractionOrder() {
        Expression expression = new Expression("2 - 3");
        assertEquals(new BigDecimal(-1).compareTo(expression.evaluate()), 0);

        expression = new Expression("3 - 2");
        assertEquals(new BigDecimal(1).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic subtraction with negative numbers")
    public void basicSubtractionWithNegativeNumbers() {
        Expression expression = new Expression("2 - -2");
        assertEquals(new BigDecimal(4).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic multiplication")
    public void basicMultiplication() {
        Expression expression = new Expression("2 * 2");
        assertEquals(new BigDecimal(4).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic multiplication with negative numbers")
    public void basicMultiplicationWithNegativeNumbers() {
        Expression expression = new Expression("2 * -2");
        assertEquals(new BigDecimal(-4).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic division")
    public void basicDivision() {
        Expression expression = new Expression("2 / 2");
        assertEquals(new BigDecimal(1).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Assert division order is correct")
    public void assertDivisionOrder() {
        Expression expression = new Expression("12 / 3");
        assertEquals(new BigDecimal("4").compareTo(expression.evaluate()), 0);

        expression = new Expression("3 / 2");
        assertEquals(new BigDecimal("1.5").compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic division with negative numbers")
    public void basicDivisionWithNegativeNumbers() {
        Expression expression = new Expression("2 / -2");
        assertEquals(new BigDecimal(-1).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic exponentiation")
    public void basicExponentiation() {
        Expression expression = new Expression("2 ^ 2");
        assertEquals(new BigDecimal(4).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic exponentiation with negative numbers")
    public void basicExponentiationWithNegativeNumbers() {
        Expression expression = new Expression("2 ^ -2");
        assertEquals(new BigDecimal("0.25").compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic factorial")
    public void basicFactorial() {
        Expression expression = new Expression("5!");
        assertEquals(new BigDecimal(120).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic square root")
    public void basicSquareRoot() {
        Expression expression = new Expression("sqrt(4)");
        assertEquals(new BigDecimal(2).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic logarithm")
    public void basicLogarithm() {
        Expression expression = new Expression("log(5,4)");
        assertEquals(BigDecimalUtils.log(BigDecimal.valueOf(5), BigDecimal.valueOf(4)).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic natural logarithm")
    public void basicNaturalLogarithm() {
        Expression expression = new Expression("ln(5)");
        assertEquals(BigDecimalUtils.ln(BigDecimal.valueOf(5)).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic nth root")
    public void basicNthRoot() {
        Expression expression = new Expression("nrt(125,3)");
        assertEquals(new BigDecimal(5).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic implicit multiplication")
    public void basicImplicitMultiplication() {
        Expression expression = new Expression("2(3)");
        assertEquals(new BigDecimal(6).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic implicit multiplication with function")
    public void basicImplicitMultiplicationWithFunction() {
        Expression expression = new Expression("sqrt(4)2");
        assertEquals(new BigDecimal(4).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Trigonometric sine function")
    public void trigonometricSineFunction() {
        Expression expression = new Expression("sin(0)");
        assertEquals(new BigDecimal(0).compareTo(expression.evaluate()), 0);

        expression = new Expression("sin(pi/2)");
        assertEquals(BigDecimal.ONE.compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Trigonometric cosine function")
    public void trigonometricCosineFunction() {
        Expression expression = new Expression("cos(0)");
        assertEquals(BigDecimal.ONE.compareTo(expression.evaluate()), 0);

        expression = new Expression("cos(pi)");
        assertEquals(new BigDecimal(-1).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Trigonometric tangent function")
    public void trigonometricTangentFunction() {
        Expression expression = new Expression("tan(0)");
        assertEquals(new BigDecimal(0).compareTo(expression.evaluate()), 0);

        expression = new Expression("tan(pi/4)");
        assertEquals(BigDecimal.ONE.compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Absolute value function")
    public void absoluteValueFunction() {
        Expression expression = new Expression("abs(-5)");
        assertEquals(new BigDecimal(5).compareTo(expression.evaluate()), 0);

        expression = new Expression("abs(5)");
        assertEquals(new BigDecimal(5).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Ceiling function")
    public void ceilingFunction() {
        Expression expression = new Expression("ceil(5.2)");
        assertEquals(new BigDecimal(6).compareTo(expression.evaluate()), 0);

        expression = new Expression("ceil(-5.2)");
        assertEquals(new BigDecimal(-5).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Floor function")
    public void floorFunction() {
        Expression expression = new Expression("floor(5.8)");
        assertEquals(new BigDecimal(5).compareTo(expression.evaluate()), 0);

        expression = new Expression("floor(-5.8)");
        assertEquals(new BigDecimal(-6).compareTo(expression.evaluate()), 0);
    }
}