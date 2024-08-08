import dev.joao_guilherme.Expression;
import dev.joao_guilherme.evaluators.PercentageBasedAdditionEvaluator;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VariableTests {

    @Test
    @DisplayName("Basic implicit multiplication with variable")
    void basicImplicitMultiplicationWithVariable() {
        Expression expression = new Expression("x(3)").withVariable("x", BigDecimal.valueOf(2));
        assertEquals(BigDecimal.valueOf(6), expression.evaluate());
    }

    @Test
    @DisplayName("Basic implicit multiplication with function and variable")
    void basicImplicitMultiplicationWithFunctionAndVariable() {
        Expression expression = new Expression("sqrt(x)2").withVariable("x", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Using constants e and pi")
    void usingConstants() {
        Expression expression = new Expression("e^pi + 1");
        BigDecimal expected = BigDecimalUtils.pow(BigDecimalUtils.E, BigDecimalUtils.PI).add(BigDecimal.ONE);
        assertEquals(expected, expression.evaluate());
    }

    @Test
    @DisplayName("Basic addition with variable")
    void basicAdditionWithVariable() {
        Expression expression = new Expression("x + 5").withVariable("x", BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(15), expression.evaluate());
    }

    @Test
    @DisplayName("Basic subtraction with variable")
    void basicSubtractionWithVariable() {
        Expression expression = new Expression("x - 5").withVariable("x", BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Basic multiplication with variable")
    void basicMultiplicationWithVariable() {
        Expression expression = new Expression("x * 5").withVariable("x", BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(50), expression.evaluate());
    }

    @Test
    @DisplayName("Basic division with variable")
    void basicDivisionWithVariable() {
        Expression expression = new Expression("x / 5").withVariable("x", BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(2), expression.evaluate());
    }

    @Test
    @DisplayName("Variable in complex expression")
    void variableInComplexExpression() {
        Expression expression = new Expression("3x + 2y - z")
                .withVariable("x", BigDecimal.valueOf(2))
                .withVariable("y", BigDecimal.valueOf(3))
                .withVariable("z", BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(11), expression.evaluate());
    }

    @Test
    @DisplayName("Nested functions with variable")
    void nestedFunctionsWithVariable() {
        Expression expression = new Expression("sqrt(x^2 + y^2)").withVariable("x", BigDecimal.valueOf(3)).withVariable("y", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Variable in percentage expression")
    void variableInPercentageExpression() {
        Expression expression = new Expression("x + 10%", new PercentageBasedAdditionEvaluator()).withVariable("x", BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(110), expression.evaluate());
    }

    @Test
    @DisplayName("Variable in exponent expression")
    void variableInExponentExpression() {
        Expression expression = new Expression("2^x").withVariable("x", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(8), expression.evaluate());
    }

    @Test
    @DisplayName("Variable with implicit multiplication and parentheses")
    void variableWithImplicitMultiplicationAndParentheses() {
        Expression expression = new Expression("2(x + 3)").withVariable("x", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(14), expression.evaluate());
    }

    @Test
    @DisplayName("Multiple variables in one expression")
    void multipleVariablesInOneExpression() {
        Expression expression = new Expression("a * b + c")
                .withVariable("a", BigDecimal.valueOf(2))
                .withVariable("b", BigDecimal.valueOf(3))
                .withVariable("c", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(10), expression.evaluate());
    }
}
