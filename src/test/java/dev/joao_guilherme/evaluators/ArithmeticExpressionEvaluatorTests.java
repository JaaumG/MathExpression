package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @DisplayName("Basic division with multiplication")
    void basicDivisionWithMultiplication() {
        Expression expression = new Expression("(150 - 100)/100*100");
        assertEquals(BigDecimalUtils.valueOf(50), expression.evaluate());
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
    @DisplayName("Implicit multiplication")
    void implicitMultiplication() {
        Expression expression = new Expression("10(8 * 2)");
        assertEquals(BigDecimalUtils.valueOf(160), expression.evaluate());
    }

    @Test
    @DisplayName("Implicit negative multiplication")
    void implicitNegativeMultiplication() {
        Expression expression = new Expression("-10(8 * 2)");
        assertEquals(BigDecimalUtils.valueOf(-160), expression.evaluate());
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

    @Test
    @DisplayName("Basic percentage")
    void basicPercentage() {
        Expression expression = new Expression("50%");
        assertEquals(BigDecimalUtils.valueOf(0.5), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage addition")
    void percentageAddition() {
        Expression expression = new Expression("50% + 50%");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage subtraction")
    void percentageSubtraction() {
        Expression expression = new Expression("100% - 50%");
        assertEquals(BigDecimalUtils.valueOf(0.5), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage multiplication")
    void percentageMultiplication() {
        Expression expression = new Expression("50% * 200");
        assertEquals(BigDecimalUtils.valueOf(100), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage division")
    void percentageDivision() {
        Expression expression = new Expression("50% / 2");
        assertEquals(BigDecimalUtils.valueOf(0.25), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage with negative number")
    void percentageWithNegativeNumber() {
        Expression expression = new Expression("-50%");
        assertEquals(BigDecimalUtils.valueOf(-0.5), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage with decimal number")
    void percentageWithDecimalNumber() {
        Expression expression = new Expression("50.5%");
        assertEquals(BigDecimalUtils.valueOf(0.505), expression.evaluate());
    }

    @Test
    @DisplayName("Combined operations with correct order of precedence")
    void combinedOperationsWithPrecedence() {
        Expression expression = new Expression("2 + 3 * 4");
        assertEquals(BigDecimalUtils.valueOf(14), expression.evaluate());

        expression = new Expression("2 * 3 + 4");
        assertEquals(BigDecimalUtils.valueOf(10), expression.evaluate());

        expression = new Expression("8 - 2 * 3");
        assertEquals(BigDecimalUtils.valueOf(2), expression.evaluate());
    }

    @Test
    @DisplayName("Parentheses controlling order of operations")
    void parenthesesControllingOrder() {
        Expression expression = new Expression("(2 + 3) * 4");
        assertEquals(BigDecimalUtils.valueOf(20), expression.evaluate());

        expression = new Expression("2 * (3 + 4)");
        assertEquals(BigDecimalUtils.valueOf(14), expression.evaluate());

        expression = new Expression("(8 - 2) * 3");
        assertEquals(BigDecimalUtils.valueOf(18), expression.evaluate());
    }

    @Test
    @DisplayName("Nested parentheses")
    void nestedParentheses() {
        Expression expression = new Expression("2 * (3 + (4 - 1))");
        assertEquals(BigDecimalUtils.valueOf(12), expression.evaluate());

        expression = new Expression("((2 + 3) * 2) ^ 2");
        assertEquals(BigDecimalUtils.valueOf(100), expression.evaluate());
    }

    @Test
    @DisplayName("Complex arithmetic expressions")
    void complexArithmeticExpressions() {
        Expression expression = new Expression("3 + 4 * 2 / (1 - 5) ^ 2");
        assertEquals(BigDecimalUtils.valueOf(3.5), expression.evaluate());

        expression = new Expression("2 ^ 3 + (10 / 2) - 4 * 2");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Mathematical constants")
    void mathematicalConstants() {
        Expression expression = new Expression("pi");
        assertEquals(BigDecimalUtils.PI, expression.evaluate());

        expression = new Expression("e");
        assertEquals(BigDecimalUtils.E, expression.evaluate());
    }

    @Test
    @DisplayName("Constants in expressions")
    void constantsInExpressions() {
        Expression expression = new Expression("2 * pi");
        assertEquals(BigDecimalUtils.multiply(BigDecimal.TWO, BigDecimalUtils.PI), expression.evaluate());

        expression = new Expression("e ^ 2");
        assertEquals(BigDecimalUtils.pow(BigDecimalUtils.E, BigDecimal.TWO), expression.evaluate());
    }

    @Test
    @DisplayName("Combination of trigonometric functions")
    void combinationOfTrigonometricFunctions() {
        Expression expression = new Expression("sin(pi/4) ^ 2 + cos(pi/4) ^ 2");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());

        expression = new Expression("tan(pi/4) - sin(pi/4) / cos(pi/4)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Inverse trigonometric functions")
    void inverseTrigonometricFunctions() {
        Expression expression = new Expression("asin(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());

        expression = new Expression("acos(1)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());

        expression = new Expression("atan(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Hyperbolic functions")
    void hyperbolicFunctions() {
        Expression expression = new Expression("sinh(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());

        expression = new Expression("cosh(0)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());

        expression = new Expression("tanh(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Round function")
    void roundFunction() {
        Expression expression = new Expression("round(3.5)");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());

        expression = new Expression("round(3.49)");
        assertEquals(BigDecimalUtils.valueOf(3), expression.evaluate());

        expression = new Expression("round(-3.5)");
        assertEquals(BigDecimalUtils.valueOf(-4), expression.evaluate());
    }

    @Test
    @DisplayName("Max and Min functions")
    void maxAndMinFunctions() {
        Expression expression = new Expression("max(3, 5)");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());

        expression = new Expression("min(3, 5)");
        assertEquals(BigDecimalUtils.valueOf(3), expression.evaluate());

        expression = new Expression("max(-3, -5)");
        assertEquals(BigDecimalUtils.valueOf(-3), expression.evaluate());
    }

    @Test
    @DisplayName("Sign function")
    void signFunction() {
        Expression expression = new Expression("sign(5)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());

        expression = new Expression("sign(-5)");
        assertEquals(BigDecimalUtils.valueOf(-1), expression.evaluate());

        expression = new Expression("sign(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Nested functions")
    void nestedFunctions() {
        Expression expression = new Expression("sqrt(abs(-16))");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());

        expression = new Expression("sin(cos(0))");
        assertEquals(BigDecimalUtils.sin(BigDecimalUtils.cos(BigDecimal.ZERO)).stripTrailingZeros(), expression.evaluate());
    }

    @Test
    @DisplayName("Factorial with decimal number")
    void factorialWithDecimalNumber() {
        Expression expression = new Expression("5.5!");
        assertEquals(BigDecimalUtils.factorial(BigDecimalUtils.valueOf(5.5)), expression.evaluate());
    }

    @Test
    @DisplayName("Expression with whitespace variations")
    void expressionWithWhitespaceVariations() {
        Expression expression = new Expression("2+3*4");
        assertEquals(BigDecimalUtils.valueOf(14), expression.evaluate());

        expression = new Expression(" 2 + 3 * 4 ");
        assertEquals(BigDecimalUtils.valueOf(14), expression.evaluate());
    }

    @Test
    @Disabled("Need to implement scientific notation")
    @DisplayName("Scientific notation")
    void scientificNotation() {
        Expression expression = new Expression("1e3");
        assertEquals(BigDecimalUtils.valueOf(1000), expression.evaluate());

        expression = new Expression("1.5e-2");
        assertEquals(BigDecimalUtils.valueOf(0.015), expression.evaluate());
    }

    @Test
    @Disabled("Need to implement modulo operator")
    @DisplayName("Modulo operation")
    void moduloOperation() {
        Expression expression = new Expression("7 % 3");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());

        expression = new Expression("-7 % 3");
        assertEquals(BigDecimalUtils.valueOf(-1), expression.evaluate());
    }

    @Test
    @DisplayName("Complex expressions with percentages")
    void complexExpressionsWithPercentages() {
        Expression expression = new Expression("100 + 10%");
        assertEquals(BigDecimalUtils.valueOf(100.1), expression.evaluate());
    }

    @Test
    @DisplayName("Logarithm with different bases")
    void logarithmWithDifferentBases() {
        Expression expression = new Expression("log(100)");  // Common log (base 10)
        assertEquals(BigDecimalUtils.valueOf(2), expression.evaluate());

        expression = new Expression("log(8, 2)");  // Log base 2
        assertEquals(BigDecimalUtils.valueOf(3), expression.evaluate());
    }

    @Test
    @DisplayName("Division by zero should throw exception")
    void divisionByZeroShouldThrowException() {
        Expression expression = new Expression("5 / 0");
        assertThrows(ArithmeticException.class, expression::evaluate);
    }

    @Test
    @Disabled("Need to throw exception, no validation is in function right now")
    @DisplayName("Invalid expression should throw exception")
    void invalidExpressionShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Expression("2 +"));
        assertThrows(IllegalArgumentException.class, () -> new Expression("* 2"));
    }

    @Test
    @DisplayName("Functions with missing arguments should throw exception")
    void functionsWithMissingArgumentsShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Expression("sin()").evaluate());
        assertThrows(IllegalArgumentException.class, () -> new Expression("log()").evaluate());
    }

    @Test
    @DisplayName("Diagnostic: Division followed by multiplication")
    void diagnosticDivisionFollowedByMultiplication() {
        Expression expression = new Expression("(150 - 100)/100*100");
        BigDecimal result = expression.evaluate();
        assertEquals(BigDecimalUtils.valueOf(50), result);
    }

    @Test
    @DisplayName("Diagnostic: Basic division and multiplication operations with parentheses")
    void diagnosticBasicDivisionMultiplicationWithParentheses() {
        Expression expression = new Expression("((150 - 100)/100)*100");
        BigDecimal result = expression.evaluate();
        assertEquals(BigDecimalUtils.valueOf(50), result);
    }

    @Test
    @DisplayName("Diagnostic: Multiplication first, then division")
    void diagnosticMultiplicationFirstThenDivision() {
        Expression expression = new Expression("(150 - 100)*100/100");
        BigDecimal result = expression.evaluate();
        assertEquals(BigDecimalUtils.valueOf(50), result);
    }

    @Test
    @DisplayName("Simple division test")
    void testSimpleDivision() {
        Expression expression = new Expression("10/2");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Simple multiplication test")
    void testSimpleMultiplication() {
        Expression expression = new Expression("5*10");
        assertEquals(BigDecimalUtils.valueOf(50), expression.evaluate());
    }

    @Test
    @DisplayName("Test precedence between division and multiplication")
    void testPrecedenceBetweenDivisionAndMultiplication() {
        Expression expression = new Expression("20/4*5");
        assertEquals(BigDecimalUtils.valueOf(25), expression.evaluate());

        expression = new Expression("20*4/5");
        assertEquals(BigDecimalUtils.valueOf(16), expression.evaluate());
    }

    @Test
    @DisplayName("Additional test with more complex expressions")
    void testAdditionalComplexExpressions() {
        Expression expression = new Expression("20/4*5+10");
        assertEquals(BigDecimalUtils.valueOf(35), expression.evaluate());

        expression = new Expression("20/4*5-10");
        assertEquals(BigDecimalUtils.valueOf(15), expression.evaluate());

        expression = new Expression("10+20/4*5");
        assertEquals(BigDecimalUtils.valueOf(35), expression.evaluate());
    }
}