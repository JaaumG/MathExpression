package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VariableTests {

    @Test
    @DisplayName("Basic implicit multiplication with variable")
    void basicImplicitMultiplicationWithVariable() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x(3)").withVariable("x", BigDecimal.valueOf(2));
        assertEquals(BigDecimal.valueOf(6), expression.evaluate());
    }

    @Test
    @DisplayName("Basic implicit multiplication with function and variable")
    void basicImplicitMultiplicationWithFunctionAndVariable() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sqrt(x)2").withVariable("x", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Using constants e and pi")
    void usingConstants() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("e^pi + 1");
        BigDecimal expected = BigDecimalUtils.pow(BigDecimalUtils.E, BigDecimalUtils.PI).add(BigDecimal.ONE);
        assertEquals(expected, expression.evaluate());
    }

    @Test
    @DisplayName("Basic addition with variable")
    void basicAdditionWithVariable() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x + 5").withVariable("x", BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(15), expression.evaluate());
    }

    @Test
    @DisplayName("Basic subtraction with variable")
    void basicSubtractionWithVariable() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x - 5").withVariable("x", BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Basic multiplication with variable")
    void basicMultiplicationWithVariable() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x * 5").withVariable("x", BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(50), expression.evaluate());
    }

    @Test
    @DisplayName("Basic division with variable")
    void basicDivisionWithVariable() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x / 5").withVariable("x", BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(2), expression.evaluate());
    }

    @Test
    @DisplayName("Variable in complex expression")
    void variableInComplexExpression() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("3x + 2y - z")
                .withVariable("x", BigDecimal.valueOf(2))
                .withVariable("y", BigDecimal.valueOf(3))
                .withVariable("z", BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(11), expression.evaluate());
    }

    @Test
    @DisplayName("Nested functions with variable")
    void nestedFunctionsWithVariable() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sqrt(x^2 + y^2)").withVariable("x", BigDecimal.valueOf(3)).withVariable("y", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Variable in percentage expression")
    void variableInPercentageExpression() {
        Expression<BigDecimal> expression = new Expression<>("x + 10%", new PercentageBasedAdditionEvaluator()).withVariable("x", BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(110), expression.evaluate());
    }

    @Test
    @DisplayName("Variable in exponent expression")
    void variableInExponentExpression() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2^x").withVariable("x", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(8), expression.evaluate());
    }

    @Test
    @DisplayName("Variable with implicit multiplication and parentheses")
    void variableWithImplicitMultiplicationAndParentheses() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2(x + 3)").withVariable("x", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(14), expression.evaluate());
    }

    @Test
    @DisplayName("Multiple variables in one expression")
    void multipleVariablesInOneExpression() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("a * b + c")
                .withVariable("a", BigDecimal.valueOf(2))
                .withVariable("b", BigDecimal.valueOf(3))
                .withVariable("c", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(10), expression.evaluate());
    }

    @Test
    @DisplayName("Variable substitution")
    void variableSubstitution() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x + 2");
        expression.withVariable("x", BigDecimalUtils.valueOf(3));
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());

        expression = Expression.ofBigDecimal("2 * x + y");
        expression.withVariable("x", BigDecimalUtils.valueOf(3));
        expression.withVariable("y", BigDecimalUtils.valueOf(4));
        assertEquals(BigDecimalUtils.valueOf(10), expression.evaluate());
    }

    @Test
    @DisplayName("Multiple variable substitutions")
    void multipleVariableSubstitutions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("a * b + c / d");
        expression.withVariable("a", BigDecimalUtils.valueOf(2));
        expression.withVariable("b", BigDecimalUtils.valueOf(3));
        expression.withVariable("c", BigDecimalUtils.valueOf(8));
        expression.withVariable("d", BigDecimalUtils.valueOf(4));
        assertEquals(BigDecimalUtils.valueOf(8), expression.evaluate());
    }

    @Test
    @DisplayName("Variables in trigonometric functions")
    void variablesInTrigonometricFunctions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sin(x)").withVariable("x", BigDecimal.valueOf(0));
        assertEquals(BigDecimal.valueOf(0), expression.evaluate());

        expression = Expression.ofBigDecimal("cos(x)").withVariable("x", BigDecimal.valueOf(0));
        assertEquals(BigDecimal.valueOf(1), expression.evaluate());

        expression = Expression.ofBigDecimal("tan(x)").withVariable("x", BigDecimalUtils.divide(BigDecimalUtils.PI, BigDecimal.valueOf(4)));
        assertEquals(BigDecimal.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Variables with function chain")
    void variablesWithFunctionChain() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sin(cos(x))").withVariable("x", BigDecimal.valueOf(0));
        assertEquals(BigDecimalUtils.sin(BigDecimal.valueOf(1)).stripTrailingZeros(), expression.evaluate());
    }

    @Test
    @DisplayName("Variable in factorial expressions")
    void variableInFactorialExpressions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x!").withVariable("x", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(24), expression.evaluate());
    }

    @Test
    @DisplayName("Multiple occurrences of the same variable")
    void multipleOccurrencesSameVariable() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x^2 + 2*x + 1").withVariable("x", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(16), expression.evaluate());
    }

    @Test
    @DisplayName("Variables with logarithmic functions")
    void variablesWithLogarithmicFunctions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("log(x)").withVariable("x", BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(2), expression.evaluate());

        expression = Expression.ofBigDecimal("ln(x)").withVariable("x", BigDecimalUtils.E);
        assertEquals(BigDecimal.valueOf(1), expression.evaluate());

        expression = Expression.ofBigDecimal("log(x,y)").withVariable("x", BigDecimal.valueOf(8)).withVariable("y", BigDecimal.valueOf(2));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());
    }

    @Test
    @DisplayName("Variables with absolute value")
    void variablesWithAbsoluteValue() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("abs(x)").withVariable("x", BigDecimal.valueOf(-5));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());

        expression = Expression.ofBigDecimal("abs(x-y)").withVariable("x", BigDecimal.valueOf(3)).withVariable("y", BigDecimal.valueOf(7));
        assertEquals(BigDecimal.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Case sensitivity in variables")
    void caseSensitivityVariables() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x + X")
                .withVariable("x", BigDecimal.valueOf(2))
                .withVariable("X", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Long variable names")
    void longVariableNames() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("longVariableName1 * longVariableName2")
                .withVariable("longVariableName1", BigDecimal.valueOf(2.5))
                .withVariable("longVariableName2", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(10), expression.evaluate());
    }

    @Test
    @DisplayName("Variables with nth root")
    void variablesWithNthRoot() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("nrt(x,y)")
                .withVariable("x", BigDecimal.valueOf(27))
                .withVariable("y", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());
    }

    @Test
    @DisplayName("Complex expression with multiple variables and operations")
    void complexExpressionMultipleVariablesOperations() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("a*sin(b) + c*cos(d) - e*sqrt(f)")
                .withVariable("a", BigDecimal.valueOf(2))
                .withVariable("b", BigDecimalUtils.divide(BigDecimalUtils.PI, BigDecimal.TWO))
                .withVariable("c", BigDecimal.valueOf(3))
                .withVariable("d", BigDecimal.valueOf(0))
                .withVariable("e", BigDecimal.valueOf(1.5))
                .withVariable("f", BigDecimal.valueOf(16));

        assertEquals(BigDecimal.valueOf(-1), expression.evaluate());
    }

    @Test
    @DisplayName("Variable overriding")
    void variableOverriding() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x + y")
                .withVariable("x", BigDecimal.valueOf(2))
                .withVariable("y", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());

        expression.withVariable("x", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(7), expression.evaluate());
    }

    @Test
    @DisplayName("Variable with floor and ceiling functions")
    void variableWithFloorAndCeilingFunctions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("floor(x)")
                .withVariable("x", BigDecimal.valueOf(3.7));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());

        expression = Expression.ofBigDecimal("ceil(x)")
                .withVariable("x", BigDecimal.valueOf(3.2));
        assertEquals(BigDecimal.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Variable with round function")
    void variableWithRoundFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("round(x)")
                .withVariable("x", BigDecimal.valueOf(3.49));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());

        expression = Expression.ofBigDecimal("round(x)")
                .withVariable("x", BigDecimal.valueOf(3.51));
        assertEquals(BigDecimal.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Nested variable expressions")
    void nestedVariableExpressions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("a*(b*(c+d))")
                .withVariable("a", BigDecimal.valueOf(2))
                .withVariable("b", BigDecimal.valueOf(3))
                .withVariable("c", BigDecimal.valueOf(4))
                .withVariable("d", BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(30), expression.evaluate());
    }

    @Test
    @DisplayName("Missing variable should throw exception")
    void missingVariableShouldThrowException() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("x + y").withVariable("x", BigDecimal.valueOf(2));
        assertThrows(IllegalArgumentException.class, expression::evaluate);
    }

    @Test
    @DisplayName("Variable with max and min functions")
    void variableWithMaxAndMinFunctions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("max(x, y)")
                .withVariable("x", BigDecimal.valueOf(7))
                .withVariable("y", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(7), expression.evaluate());

        expression = Expression.ofBigDecimal("min(x, y)")
                .withVariable("x", BigDecimal.valueOf(7))
                .withVariable("y", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());
    }

    @Test
    @DisplayName("Variable with sign function")
    void variableWithSignFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sign(x)")
                .withVariable("x", BigDecimal.valueOf(7));
        assertEquals(BigDecimal.valueOf(1), expression.evaluate());

        expression = Expression.ofBigDecimal("sign(x)")
                .withVariable("x", BigDecimal.valueOf(-3.2));
        assertEquals(BigDecimal.valueOf(-1), expression.evaluate());

        expression = Expression.ofBigDecimal("sign(x)")
                .withVariable("x", BigDecimal.valueOf(0));
        assertEquals(BigDecimal.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Chained variable assignments")
    void chainedVariableAssignments() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("a + b + c")
                .withVariable("a", BigDecimal.valueOf(1))
                .withVariable("b", BigDecimal.valueOf(2))
                .withVariable("c", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(6), expression.evaluate());
    }

    @Test
    @DisplayName("Variables with negative values")
    void variablesWithNegativeValues() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("a * b")
                .withVariable("a", BigDecimal.valueOf(-2))
                .withVariable("b", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(-6), expression.evaluate());

        expression = Expression.ofBigDecimal("a * b")
                .withVariable("a", BigDecimal.valueOf(-2))
                .withVariable("b", BigDecimal.valueOf(-3));
        assertEquals(BigDecimal.valueOf(6), expression.evaluate());
    }
}
