package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StepByStepTests {

    @Test
    @DisplayName("Basic step by step evaluation")
    void basicStepByStepEvaluation() {
        List<String> steps = new Expression("2 + 2 * 3").steps();
        List<String> expectedSteps = List.of("2+2*3", "2*3", "6", "2+6", "8");
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step evaluation with parentheses")
    void stepByStepWithParentheses() {
        List<String> steps = new Expression("(2 + 3) * 4").steps();
        List<String> expectedSteps = List.of("(2+3)*4", "2+3", "5", "5*4", "20");
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Evaluation with multiple operators")
    void multipleOperatorsEvaluation() {
        List<String> steps = new Expression("2 + 3 * 4 - 5").steps();
        List<String> expectedSteps = List.of("2+3*4-5", "3*4", "12", "2+12", "14", "14-5", "9");
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step evaluation with division")
    void stepByStepWithDivision() {
        List<String> steps = new Expression("8 / 2 + 3").steps();
        List<String> expectedSteps = List.of("8/2+3", "8/2", "4", "4+3", "7");
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Complex expression with parentheses and multiple operations")
    void complexExpressionWithParentheses() {
        List<String> steps = new Expression("(3 + 5) * (2 + 2) - 4").steps();
        List<String> expectedSteps = List.of(
                "(3+5)*(2+2)-4",
                "3+5",
                "8",
                "2+2",
                "4",
                "8*4",
                "32",
                "32-4",
                "28"
        );
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step evaluation with exponentiation")
    void stepByStepWithExponentiation() {
        List<String> steps = new Expression("2 + 3 ^ 2").steps();
        List<String> expectedSteps = List.of("2+3^2",
                "3^2",
                "9",
                "2+9",
                "11");
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Evaluation with mixed operators and parentheses")
    void mixedOperatorsWithParentheses() {
        List<String> steps = new Expression("2 * (3 + 5) - 4 / 2").steps();
        List<String> expectedSteps = List.of(
                "2*(3+5)-4/2",
                "3+5",
                "8",
                "2*8",
                "16",
                "4/2",
                "2",
                "16-2",
                "14"
        );
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Evaluation with negative numbers")
    void negativeNumbersEvaluation() {
        List<String> steps = new Expression("-2 + 5 * 3").steps();
        List<String> expectedSteps = List.of(
                "-2+5*3",
                "5*3",
                "15",
                "-2+15",
                "13");
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step evaluation with multiple parentheses")
    void multipleParenthesesEvaluation() {
        List<String> steps = new Expression("((2 + 3) * (4 - 1)) + 5").steps();
        List<String> expectedSteps = List.of(
                "((2+3)*(4-1))+5",
                "(2+3)*(4-1)",
                "2+3",
                "5",
                "4-1",
                "3",
                "5*3",
                "15",
                "15+5",
                "20"
        );
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step evaluation with complex nested operations")
    void complexNestedOperations() {
        List<String> steps = new Expression("((2 + 3) * (5 - 2)) / 3 + 1").steps();
        List<String> expectedSteps = List.of(
                "((2+3)*(5-2))/3+1",
                "(2+3)*(5-2)",
                "2+3",
                "5",
                "5-2",
                "3",
                "5*3",
                "15",
                "15/3",
                "5",
                "5+1",
                "6"
        );
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Evaluation with division by zero")
    void divisionByZero() {
        try {
            new Expression("5 / 0").steps();
        } catch (ArithmeticException e) {
            assertEquals("Division by zero", e.getMessage());
        }
    }

    @Test
    @DisplayName("Evaluation with very large numbers")
    void largeNumbersEvaluation() {
        List<String> steps = new Expression("1000000 * 5000").steps();
        List<String> expectedSteps = List.of("1000000*5000", "5000000000");
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Evaluation with very small decimal numbers")
    void smallDecimalNumbersEvaluation() {
        List<String> steps = new Expression("100 * 0.01").steps();
        List<String> expectedSteps = List.of("100*0.01", "1");
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Evaluation with functions")
    void functionsEvaluation() {
        List<String> steps = new Expression("sqrt(4)").steps();
        List<String> expectedSteps = List.of("sqrt(4)", "2");
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step with custom operator")
    void stepByStepWithCustomOperator() {
        List<String> steps = new Expression("10 a 5")
                .withOperator('a', 1, BigDecimal::add)
                .steps();
        List<String> expectedSteps = List.of(
                "10a5",
                "15"
        );
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step with custom function")
    void stepByStepWithCustomFunction() {
        List<String> steps = new Expression("timesTwo(15)")
                .withFunction("timesTwo", a -> a.multiply(BigDecimal.TWO))
                .steps();
        List<String> expectedSteps = List.of(
                "timesTwo(15)",
                "30"
        );
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step with custom function and operator")
    void stepByStepWithCustomFunctionAndOperator() {
        List<String> steps = new Expression("double(5 a 3)")
                .withOperator('a', 1, BigDecimal::add)
                .withFunction("double", val -> val.multiply(BigDecimal.TWO))
                .steps();
        List<String> expectedSteps = List.of(
                "double(5a3)",
                "8",
                "double(8)",
                "16"
        );
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step with constants")
    void stepByStepWithConstants() {
        String piTimesTwo = new Expression("pi*2").evaluate().toPlainString();

        List<String> steps = new Expression("pi * 2").steps();
        List<String> expectedSteps = List.of(
                "pi*2",
                BigDecimalUtils.PI.toPlainString()+"*2",
                piTimesTwo
        );
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step with nested functions")
    void stepByStepWithNestedFunctions() {
        List<String> steps = new Expression("sqrt(abs(-16))").steps();
        List<String> expectedSteps = List.of(
                "sqrt(abs(-16))",
                "abs(-16)",
                "16",
                "sqrt(16)",
                "4"
        );
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step respects left-to-right associativity for same precedence")
    void stepByStepLeftToRightAssociativity() {
        List<String> steps = new Expression("100 / 10 * 5").steps();
        List<String> expectedSteps = List.of(
                "100/10*5",
                "100/10",
                "10",
                "10*5",
                "50"
        );
        assertEquals(expectedSteps, steps);
    }

    @Test
    @DisplayName("Step by step with subtraction and addition (left-to-right)")
    void stepByStepWithSubtractionAndAddition() {
        List<String> steps = new Expression("20 - 10 + 5").steps();
        List<String> expectedSteps = List.of(
                "20-10+5",
                "20-10",
                "10",
                "10+5",
                "15"
        );
        assertEquals(expectedSteps, steps);
    }
}
