import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArithmeticExpressionEvaluatorTests {

    @Test
    @DisplayName("Basic addition")
    void basicAddition() {
        Expression expression = new Expression("2 + 2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Basic addition with negative numbers")
    void basicAdditionWithNegativeNumbers() {
        Expression expression = new Expression("2 + -2");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Basic subtraction")
    void basicSubtraction() {
        Expression expression = new Expression("2 - 2");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Assert subtraction order is correct")
    void assertSubtractionOrder() {
        Expression expression = new Expression("2 - 3");
        assertEquals(BigDecimalUtils.valueOf(-1), expression.evaluate());

        expression = new Expression("3 - 2");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Basic subtraction with negative numbers")
    void basicSubtractionWithNegativeNumbers() {
        Expression expression = new Expression("2 - -2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Basic multiplication")
    void basicMultiplication() {
        Expression expression = new Expression("2 * 2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Basic multiplication with negative numbers")
    void basicMultiplicationWithNegativeNumbers() {
        Expression expression = new Expression("2 * -2");
        assertEquals(BigDecimalUtils.valueOf(-4), expression.evaluate());
    }

    @Test
    @DisplayName("Basic division")
    void basicDivision() {
        Expression expression = new Expression("2 / 2");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Assert division order is correct")
    void assertDivisionOrder() {
        Expression expression = new Expression("12 / 3");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());

        expression = new Expression("3 / 2");
        assertEquals(BigDecimalUtils.valueOf(1.5), expression.evaluate());
    }

    @Test
    @DisplayName("Basic division with negative numbers")
    void basicDivisionWithNegativeNumbers() {
        Expression expression = new Expression("2 / -2");
        assertEquals(BigDecimalUtils.valueOf(-1), expression.evaluate());
    }

    @Test
    @DisplayName("Basic exponentiation")
    void basicExponentiation() {
        Expression expression = new Expression("2 ^ 2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Basic exponentiation with negative numbers")
    void basicExponentiationWithNegativeNumbers() {
        Expression expression = new Expression("2 ^ -2");
        assertEquals(BigDecimalUtils.valueOf(0.25), expression.evaluate());
    }

    @Test
    @DisplayName("Basic factorial")
    void basicFactorial() {
        Expression expression = new Expression("5!");
        assertEquals(BigDecimalUtils.valueOf(120), expression.evaluate());
    }

    @Test
    @DisplayName("Basic square root")
    void basicSquareRoot() {
        Expression expression = new Expression("sqrt(4)");
        assertEquals(BigDecimalUtils.valueOf(2), expression.evaluate());
    }

    @Test
    @DisplayName("Basic logarithm")
    void basicLogarithm() {
        Expression expression = new Expression("log(5,4)");
        assertEquals(BigDecimalUtils.log(BigDecimalUtils.valueOf(5), BigDecimalUtils.valueOf(4)), expression.evaluate());
    }

    @Test
    @DisplayName("Basic natural logarithm")
    void basicNaturalLogarithm() {
        Expression expression = new Expression("ln(5)");
        assertEquals(BigDecimalUtils.ln(BigDecimalUtils.valueOf(5)), expression.evaluate());
    }

    @Test
    @DisplayName("Basic nth root")
    void basicNthRoot() {
        Expression expression = new Expression("nrt(125,3)");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Basic implicit multiplication")
    void basicImplicitMultiplication() {
        Expression expression = new Expression("2(3)");
        assertEquals(BigDecimalUtils.valueOf(6), expression.evaluate());
    }

    @Test
    @DisplayName("Basic implicit multiplication with function")
    void basicImplicitMultiplicationWithFunction() {
        Expression expression = new Expression("sqrt(4)2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Trigonometric sine function")
    void trigonometricSineFunction() {
        Expression expression = new Expression("sin(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());

        expression = new Expression("sin(pi/2)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Trigonometric cosine function")
    void trigonometricCosineFunction() {
        Expression expression = new Expression("cos(0)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());

        expression = new Expression("cos(pi)");
        assertEquals(BigDecimalUtils.valueOf(-1), expression.evaluate());
    }

    @Test
    @DisplayName("Trigonometric tangent function")
    void trigonometricTangentFunction() {
        Expression expression = new Expression("tan(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());

        expression = new Expression("tan(pi/4)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Absolute value function")
    void absoluteValueFunction() {
        Expression expression = new Expression("abs(-5)");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());

        expression = new Expression("abs(5)");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Ceiling function")
    void ceilingFunction() {
        Expression expression = new Expression("ceil(5.2)");
        assertEquals(BigDecimalUtils.valueOf(6), expression.evaluate());

        expression = new Expression("ceil(-5.2)");
        assertEquals(BigDecimalUtils.valueOf(-5), expression.evaluate());
    }

    @Test
    @DisplayName("Floor function")
    void floorFunction() {
        Expression expression = new Expression("floor(5.8)");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());

        expression = new Expression("floor(-5.8)");
        assertEquals(BigDecimalUtils.valueOf(-6), expression.evaluate());
    }
}