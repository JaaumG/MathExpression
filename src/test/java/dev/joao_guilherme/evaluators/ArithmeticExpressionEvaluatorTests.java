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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 + 2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Basic addition with negative numbers")
    void basicAdditionWithNegativeNumbers() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 + -2");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Basic subtraction")
    void basicSubtraction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 - 2");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Assert subtraction order is correct")
    void assertSubtractionOrder() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 - 3");
        assertEquals(BigDecimalUtils.valueOf(-1), expression.evaluate());

        expression = Expression.ofBigDecimal("3 - 2");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Basic subtraction with negative numbers")
    void basicSubtractionWithNegativeNumbers() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 - -2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Basic multiplication")
    void basicMultiplication() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 * 2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Basic multiplication with negative numbers")
    void basicMultiplicationWithNegativeNumbers() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 * -2");
        assertEquals(BigDecimalUtils.valueOf(-4), expression.evaluate());
    }

    @Test
    @DisplayName("Basic division")
    void basicDivision() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 / 2");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Assert division order is correct")
    void assertDivisionOrder() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("12 / 3");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());

        expression = Expression.ofBigDecimal("3 / 2");
        assertEquals(BigDecimalUtils.valueOf(1.5), expression.evaluate());
    }

    @Test
    @DisplayName("Basic division with negative numbers")
    void basicDivisionWithNegativeNumbers() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 / -2");
        assertEquals(BigDecimalUtils.valueOf(-1), expression.evaluate());
    }

    @Test
    @DisplayName("Basic division with multiplication")
    void basicDivisionWithMultiplication() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("(150 - 100)/100*100");
        assertEquals(BigDecimalUtils.valueOf(50), expression.evaluate());
    }

    @Test
    @DisplayName("Basic exponentiation")
    void basicExponentiation() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 ^ 2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Basic exponentiation with negative numbers")
    void basicExponentiationWithNegativeNumbers() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 ^ -2");
        assertEquals(BigDecimalUtils.valueOf(0.25), expression.evaluate());
    }

    @Test
    @DisplayName("Basic factorial")
    void basicFactorial() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("5!");
        assertEquals(BigDecimalUtils.valueOf(120), expression.evaluate());
    }

    @Test
    @DisplayName("Basic square root")
    void basicSquareRoot() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sqrt(4)");
        assertEquals(BigDecimalUtils.valueOf(2), expression.evaluate());
    }

    @Test
    @DisplayName("Basic logarithm")
    void basicLogarithm() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("log(5,4)");
        assertEquals(BigDecimalUtils.log(BigDecimalUtils.valueOf(5), BigDecimalUtils.valueOf(4)), expression.evaluate());
    }

    @Test
    @DisplayName("Basic natural logarithm")
    void basicNaturalLogarithm() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("ln(5)");
        assertEquals(BigDecimalUtils.ln(BigDecimalUtils.valueOf(5)), expression.evaluate());
    }

    @Test
    @DisplayName("Basic nth root")
    void basicNthRoot() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("nrt(125,3)");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Basic implicit multiplication")
    void basicImplicitMultiplication() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2(3)");
        assertEquals(BigDecimalUtils.valueOf(6), expression.evaluate());
    }

    @Test
    @DisplayName("Implicit multiplication")
    void implicitMultiplication() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("10(8 * 2)");
        assertEquals(BigDecimalUtils.valueOf(160), expression.evaluate());
    }

    @Test
    @DisplayName("Implicit negative multiplication")
    void implicitNegativeMultiplication() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("-10(8 * 2)");
        assertEquals(BigDecimalUtils.valueOf(-160), expression.evaluate());
    }

    @Test
    @DisplayName("Basic implicit multiplication with function")
    void basicImplicitMultiplicationWithFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sqrt(4)2");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Trigonometric sine function")
    void trigonometricSineFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sin(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());

        expression = Expression.ofBigDecimal("sin(pi/2)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Trigonometric cosine function")
    void trigonometricCosineFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("cos(0)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());

        expression = Expression.ofBigDecimal("cos(pi)");
        assertEquals(BigDecimalUtils.valueOf(-1), expression.evaluate());
    }

    @Test
    @DisplayName("Trigonometric tangent function")
    void trigonometricTangentFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("tan(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());

        expression = Expression.ofBigDecimal("tan(pi/4)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Absolute value function")
    void absoluteValueFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("abs(-5)");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());

        expression = Expression.ofBigDecimal("abs(5)");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Ceiling function")
    void ceilingFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("ceil(5.2)");
        assertEquals(BigDecimalUtils.valueOf(6), expression.evaluate());

        expression = Expression.ofBigDecimal("ceil(-5.2)");
        assertEquals(BigDecimalUtils.valueOf(-5), expression.evaluate());
    }

    @Test
    @DisplayName("Floor function")
    void floorFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("floor(5.8)");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());

        expression = Expression.ofBigDecimal("floor(-5.8)");
        assertEquals(BigDecimalUtils.valueOf(-6), expression.evaluate());
    }

    @Test
    @DisplayName("Basic percentage")
    void basicPercentage() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("50%");
        assertEquals(BigDecimalUtils.valueOf(0.5), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage addition")
    void percentageAddition() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("50% + 50%");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage subtraction")
    void percentageSubtraction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("100% - 50%");
        assertEquals(BigDecimalUtils.valueOf(0.5), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage multiplication")
    void percentageMultiplication() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("50% * 200");
        assertEquals(BigDecimalUtils.valueOf(100), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage division")
    void percentageDivision() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("50% / 2");
        assertEquals(BigDecimalUtils.valueOf(0.25), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage with negative number")
    void percentageWithNegativeNumber() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("-50%");
        assertEquals(BigDecimalUtils.valueOf(-0.5), expression.evaluate());
    }

    @Test
    @DisplayName("Percentage with decimal number")
    void percentageWithDecimalNumber() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("50.5%");
        assertEquals(BigDecimalUtils.valueOf(0.505), expression.evaluate());
    }

    @Test
    @DisplayName("Combined operations with correct order of precedence")
    void combinedOperationsWithPrecedence() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 + 3 * 4");
        assertEquals(BigDecimalUtils.valueOf(14), expression.evaluate());

        expression = Expression.ofBigDecimal("2 * 3 + 4");
        assertEquals(BigDecimalUtils.valueOf(10), expression.evaluate());

        expression = Expression.ofBigDecimal("8 - 2 * 3");
        assertEquals(BigDecimalUtils.valueOf(2), expression.evaluate());
    }

    @Test
    @DisplayName("Parentheses controlling order of operations")
    void parenthesesControllingOrder() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("(2 + 3) * 4");
        assertEquals(BigDecimalUtils.valueOf(20), expression.evaluate());

        expression = Expression.ofBigDecimal("2 * (3 + 4)");
        assertEquals(BigDecimalUtils.valueOf(14), expression.evaluate());

        expression = Expression.ofBigDecimal("(8 - 2) * 3");
        assertEquals(BigDecimalUtils.valueOf(18), expression.evaluate());
    }

    @Test
    @DisplayName("Nested parentheses")
    void nestedParentheses() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 * (3 + (4 - 1))");
        assertEquals(BigDecimalUtils.valueOf(12), expression.evaluate());

        expression = Expression.ofBigDecimal("((2 + 3) * 2) ^ 2");
        assertEquals(BigDecimalUtils.valueOf(100), expression.evaluate());
    }

    @Test
    @DisplayName("Complex arithmetic expressions")
    void complexArithmeticExpressions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("3 + 4 * 2 / (1 - 5) ^ 2");
        assertEquals(BigDecimalUtils.valueOf(3.5), expression.evaluate());

        expression = Expression.ofBigDecimal("2 ^ 3 + (10 / 2) - 4 * 2");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Mathematical constants")
    void mathematicalConstants() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("pi");
        assertEquals(BigDecimalUtils.PI, expression.evaluate());

        expression = Expression.ofBigDecimal("e");
        assertEquals(BigDecimalUtils.E, expression.evaluate());
    }

    @Test
    @DisplayName("Constants in expressions")
    void constantsInExpressions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2 * pi");
        assertEquals(BigDecimalUtils.multiply(BigDecimal.TWO, BigDecimalUtils.PI), expression.evaluate());

        expression = Expression.ofBigDecimal("e ^ 2");
        assertEquals(BigDecimalUtils.pow(BigDecimalUtils.E, BigDecimal.TWO), expression.evaluate());
    }

    @Test
    @DisplayName("Combination of trigonometric functions")
    void combinationOfTrigonometricFunctions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sin(pi/4) ^ 2 + cos(pi/4) ^ 2");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());

        expression = Expression.ofBigDecimal("tan(pi/4) - sin(pi/4) / cos(pi/4)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Inverse trigonometric functions")
    void inverseTrigonometricFunctions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("asin(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());

        expression = Expression.ofBigDecimal("acos(1)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());

        expression = Expression.ofBigDecimal("atan(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Hyperbolic functions")
    void hyperbolicFunctions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sinh(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());

        expression = Expression.ofBigDecimal("cosh(0)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());

        expression = Expression.ofBigDecimal("tanh(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Round function")
    void roundFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("round(3.5)");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());

        expression = Expression.ofBigDecimal("round(3.49)");
        assertEquals(BigDecimalUtils.valueOf(3), expression.evaluate());

        expression = Expression.ofBigDecimal("round(-3.5)");
        assertEquals(BigDecimalUtils.valueOf(-4), expression.evaluate());
    }

    @Test
    @DisplayName("Max and Min functions")
    void maxAndMinFunctions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("max(3, 5)");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());

        expression = Expression.ofBigDecimal("min(3, 5)");
        assertEquals(BigDecimalUtils.valueOf(3), expression.evaluate());

        expression = Expression.ofBigDecimal("max(-3, -5)");
        assertEquals(BigDecimalUtils.valueOf(-3), expression.evaluate());
    }

    @Test
    @DisplayName("Sign function")
    void signFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sign(5)");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());

        expression = Expression.ofBigDecimal("sign(-5)");
        assertEquals(BigDecimalUtils.valueOf(-1), expression.evaluate());

        expression = Expression.ofBigDecimal("sign(0)");
        assertEquals(BigDecimalUtils.valueOf(0), expression.evaluate());
    }

    @Test
    @DisplayName("Nested functions")
    void nestedFunctions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sqrt(abs(-16))");
        assertEquals(BigDecimalUtils.valueOf(4), expression.evaluate());

        expression = Expression.ofBigDecimal("sin(cos(0))");
        assertEquals(BigDecimalUtils.sin(BigDecimalUtils.cos(BigDecimal.ZERO)).stripTrailingZeros(), expression.evaluate());
    }

    @Test
    @DisplayName("Factorial with decimal number")
    void factorialWithDecimalNumber() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("5.5!");
        assertEquals(BigDecimalUtils.factorial(BigDecimalUtils.valueOf(5.5)), expression.evaluate());
    }

    @Test
    @DisplayName("Expression with whitespace variations")
    void expressionWithWhitespaceVariations() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("2+3*4");
        assertEquals(BigDecimalUtils.valueOf(14), expression.evaluate());

        expression = Expression.ofBigDecimal(" 2 + 3 * 4 ");
        assertEquals(BigDecimalUtils.valueOf(14), expression.evaluate());
    }

    @Test
    @Disabled("Need to implement scientific notation")
    @DisplayName("Scientific notation")
    void scientificNotation() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("1e3");
        assertEquals(BigDecimalUtils.valueOf(1000), expression.evaluate());

        expression = Expression.ofBigDecimal("1.5e-2");
        assertEquals(BigDecimalUtils.valueOf(0.015), expression.evaluate());
    }

    @Test
    @Disabled("Need to implement modulo operator")
    @DisplayName("Modulo operation")
    void moduloOperation() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("7 % 3");
        assertEquals(BigDecimalUtils.valueOf(1), expression.evaluate());

        expression = Expression.ofBigDecimal("-7 % 3");
        assertEquals(BigDecimalUtils.valueOf(-1), expression.evaluate());
    }

    @Test
    @DisplayName("Complex expressions with percentages")
    void complexExpressionsWithPercentages() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("100 + 10%");
        assertEquals(BigDecimalUtils.valueOf(100.1), expression.evaluate());
    }

    @Test
    @DisplayName("Logarithm with different bases")
    void logarithmWithDifferentBases() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("log(100)");  // Common log (base 10)
        assertEquals(BigDecimalUtils.valueOf(2), expression.evaluate());

        expression = Expression.ofBigDecimal("log(8, 2)");  // Log base 2
        assertEquals(BigDecimalUtils.valueOf(3), expression.evaluate());
    }

    @Test
    @DisplayName("Division by zero should throw exception")
    void divisionByZeroShouldThrowException() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("5 / 0");
        assertThrows(ArithmeticException.class, expression::evaluate);
    }

    @Test
    @Disabled("Need to throw exception, no validation is in function right now")
    @DisplayName("Invalid expression should throw exception")
    void invalidExpressionShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Expression.ofBigDecimal("2 +"));
        assertThrows(IllegalArgumentException.class, () -> Expression.ofBigDecimal("* 2"));
    }

    @Test
    @DisplayName("Functions with missing arguments should throw exception")
    void functionsWithMissingArgumentsShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Expression.ofBigDecimal("sin()").evaluate());
        assertThrows(IllegalArgumentException.class, () -> Expression.ofBigDecimal("log()").evaluate());
    }

    @Test
    @DisplayName("Diagnostic: Division followed by multiplication")
    void diagnosticDivisionFollowedByMultiplication() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("(150 - 100)/100*100");
        BigDecimal result = expression.evaluate();
        assertEquals(BigDecimalUtils.valueOf(50), result);
    }

    @Test
    @DisplayName("Diagnostic: Basic division and multiplication operations with parentheses")
    void diagnosticBasicDivisionMultiplicationWithParentheses() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("((150 - 100)/100)*100");
        BigDecimal result = expression.evaluate();
        assertEquals(BigDecimalUtils.valueOf(50), result);
    }

    @Test
    @DisplayName("Diagnostic: Multiplication first, then division")
    void diagnosticMultiplicationFirstThenDivision() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("(150 - 100)*100/100");
        BigDecimal result = expression.evaluate();
        assertEquals(BigDecimalUtils.valueOf(50), result);
    }

    @Test
    @DisplayName("Simple division test")
    void testSimpleDivision() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("10/2");
        assertEquals(BigDecimalUtils.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Simple multiplication test")
    void testSimpleMultiplication() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("5*10");
        assertEquals(BigDecimalUtils.valueOf(50), expression.evaluate());
    }

    @Test
    @DisplayName("Test precedence between division and multiplication")
    void testPrecedenceBetweenDivisionAndMultiplication() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("20/4*5");
        assertEquals(BigDecimalUtils.valueOf(25), expression.evaluate());

        expression = Expression.ofBigDecimal("20*4/5");
        assertEquals(BigDecimalUtils.valueOf(16), expression.evaluate());
    }

    @Test
    @DisplayName("Additional test with more complex expressions")
    void testAdditionalComplexExpressions() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("20/4*5+10");
        assertEquals(BigDecimalUtils.valueOf(35), expression.evaluate());

        expression = Expression.ofBigDecimal("20/4*5-10");
        assertEquals(BigDecimalUtils.valueOf(15), expression.evaluate());

        expression = Expression.ofBigDecimal("10+20/4*5");
        assertEquals(BigDecimalUtils.valueOf(35), expression.evaluate());
    }
}