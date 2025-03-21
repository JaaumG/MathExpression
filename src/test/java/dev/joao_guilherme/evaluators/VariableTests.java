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

    @Test
    @DisplayName("Variable substitution")
    void variableSubstitution() {
        Expression expression = new Expression("x + 2");
        expression.withVariable("x", BigDecimalUtils.valueOf(3));
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());

        expression = new Expression("2 * x + y");
        expression.withVariable("x", BigDecimalUtils.valueOf(3));
        expression.withVariable("y", BigDecimalUtils.valueOf(4));
        assertEquals(BigDecimalUtils.valueOf(10), expression.evaluate());
    }

    @Test
    @DisplayName("Multiple variable substitutions")
    void multipleVariableSubstitutions() {
        Expression expression = new Expression("a * b + c / d");
        expression.withVariable("a", BigDecimalUtils.valueOf(2));
        expression.withVariable("b", BigDecimalUtils.valueOf(3));
        expression.withVariable("c", BigDecimalUtils.valueOf(8));
        expression.withVariable("d", BigDecimalUtils.valueOf(4));
        assertEquals(BigDecimalUtils.valueOf(8), expression.evaluate());
    }

    @Test
    @DisplayName("Variables in trigonometric functions")
    void variablesInTrigonometricFunctions() {
        Expression expression = new Expression("sin(x)").withVariable("x", BigDecimal.valueOf(0));
        assertEquals(BigDecimal.valueOf(0), expression.evaluate());

        expression = new Expression("cos(x)").withVariable("x", BigDecimal.valueOf(0));
        assertEquals(BigDecimal.valueOf(1), expression.evaluate());

        expression = new Expression("tan(x)").withVariable("x", BigDecimalUtils.divide(BigDecimalUtils.PI, BigDecimal.valueOf(4)));
        assertEquals(BigDecimal.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Variables with function chain")
    void variablesWithFunctionChain() {
        Expression expression = new Expression("sin(cos(x))").withVariable("x", BigDecimal.valueOf(0));
        assertEquals(BigDecimalUtils.sin(BigDecimal.valueOf(1)).stripTrailingZeros(), expression.evaluate());
    }

    @Test
    @DisplayName("Variable in factorial expressions")
    void variableInFactorialExpressions() {
        Expression expression = new Expression("x!").withVariable("x", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(24), expression.evaluate());
    }

    @Test
    @DisplayName("Multiple occurrences of the same variable")
    void multipleOccurrencesSameVariable() {
        Expression expression = new Expression("x^2 + 2*x + 1").withVariable("x", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(16), expression.evaluate());
    }

    @Test
    @DisplayName("Variables with logarithmic functions")
    void variablesWithLogarithmicFunctions() {
        Expression expression = new Expression("log(x)").withVariable("x", BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(2), expression.evaluate());

        expression = new Expression("ln(x)").withVariable("x", BigDecimalUtils.E);
        assertEquals(BigDecimal.valueOf(1), expression.evaluate());

        expression = new Expression("log(x,y)").withVariable("x", BigDecimal.valueOf(8)).withVariable("y", BigDecimal.valueOf(2));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());
    }

    @Test
    @DisplayName("Variables with absolute value")
    void variablesWithAbsoluteValue() {
        Expression expression = new Expression("abs(x)").withVariable("x", BigDecimal.valueOf(-5));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());

        expression = new Expression("abs(x-y)").withVariable("x", BigDecimal.valueOf(3)).withVariable("y", BigDecimal.valueOf(7));
        assertEquals(BigDecimal.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Case sensitivity in variables")
    void caseSensitivityVariables() {
        Expression expression = new Expression("x + X")
                .withVariable("x", BigDecimal.valueOf(2))
                .withVariable("X", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Long variable names")
    void longVariableNames() {
        Expression expression = new Expression("longVariableName1 * longVariableName2")
                .withVariable("longVariableName1", BigDecimal.valueOf(2.5))
                .withVariable("longVariableName2", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(10), expression.evaluate());
    }

    @Test
    @DisplayName("Variables with nth root")
    void variablesWithNthRoot() {
        Expression expression = new Expression("nrt(x,y)")
                .withVariable("x", BigDecimal.valueOf(27))
                .withVariable("y", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());
    }

    @Test
    @DisplayName("Complex expression with multiple variables and operations")
    void complexExpressionMultipleVariablesOperations() {
        Expression expression = new Expression("a*sin(b) + c*cos(d) - e*sqrt(f)")
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
        Expression expression = new Expression("x + y")
                .withVariable("x", BigDecimal.valueOf(2))
                .withVariable("y", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());

        expression.withVariable("x", BigDecimal.valueOf(4));
        assertEquals(BigDecimal.valueOf(7), expression.evaluate());
    }

    @Test
    @DisplayName("Variable with floor and ceiling functions")
    void variableWithFloorAndCeilingFunctions() {
        Expression expression = new Expression("floor(x)")
                .withVariable("x", BigDecimal.valueOf(3.7));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());

        expression = new Expression("ceil(x)")
                .withVariable("x", BigDecimal.valueOf(3.2));
        assertEquals(BigDecimal.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Variable with round function")
    void variableWithRoundFunction() {
        Expression expression = new Expression("round(x)")
                .withVariable("x", BigDecimal.valueOf(3.49));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());

        expression = new Expression("round(x)")
                .withVariable("x", BigDecimal.valueOf(3.51));
        assertEquals(BigDecimal.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Nested variable expressions")
    void nestedVariableExpressions() {
        Expression expression = new Expression("a*(b*(c+d))")
                .withVariable("a", BigDecimal.valueOf(2))
                .withVariable("b", BigDecimal.valueOf(3))
                .withVariable("c", BigDecimal.valueOf(4))
                .withVariable("d", BigDecimal.valueOf(1));
        assertEquals(BigDecimal.valueOf(30), expression.evaluate());
    }

    @Test
    @DisplayName("Missing variable should throw exception")
    void missingVariableShouldThrowException() {
        Expression expression = new Expression("x + y").withVariable("x", BigDecimal.valueOf(2));
        assertThrows(IllegalArgumentException.class, expression::evaluate);
    }

    @Test
    @DisplayName("Variable with max and min functions")
    void variableWithMaxAndMinFunctions() {
        Expression expression = new Expression("max(x, y)")
                .withVariable("x", BigDecimal.valueOf(7))
                .withVariable("y", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(7), expression.evaluate());

        expression = new Expression("min(x, y)")
                .withVariable("x", BigDecimal.valueOf(7))
                .withVariable("y", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());
    }

    @Test
    @DisplayName("Variable with sign function")
    void variableWithSignFunction() {
        Expression expression = new Expression("sign(x)")
                .withVariable("x", BigDecimal.valueOf(7));
        assertEquals(BigDecimal.valueOf(1), expression.evaluate());

        expression = new Expression("sign(x)")
                .withVariable("x", BigDecimal.valueOf(-3.2));
        assertEquals(BigDecimal.valueOf(-1), expression.evaluate());

        expression = new Expression("sign(x)")
                .withVariable("x", BigDecimal.valueOf(0));
        assertEquals(BigDecimal.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Chained variable assignments")
    void chainedVariableAssignments() {
        Expression expression = new Expression("a + b + c")
                .withVariable("a", BigDecimal.valueOf(1))
                .withVariable("b", BigDecimal.valueOf(2))
                .withVariable("c", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(6), expression.evaluate());
    }

    @Test
    @DisplayName("Variables with negative values")
    void variablesWithNegativeValues() {
        Expression expression = new Expression("a * b")
                .withVariable("a", BigDecimal.valueOf(-2))
                .withVariable("b", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(-6), expression.evaluate());

        expression = new Expression("a * b")
                .withVariable("a", BigDecimal.valueOf(-2))
                .withVariable("b", BigDecimal.valueOf(-3));
        assertEquals(BigDecimal.valueOf(6), expression.evaluate());
    }
}
