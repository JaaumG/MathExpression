import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpressionTests {

    @Test
    @DisplayName("Basic addition")
    public void basicAddition() {
        Expression expression = new Expression("2 + 2");
        assertEquals(new BigDecimal(4).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic subtraction")
    public void basicSubtraction() {
        Expression expression = new Expression("2 - 2");
        assertEquals(new BigDecimal(0).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic multiplication")
    public void basicMultiplication() {
        Expression expression = new Expression("2 * 2");
        assertEquals(new BigDecimal(4).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic division")
    public void basicDivision() {
        Expression expression = new Expression("2 / 2");
        assertEquals(new BigDecimal(1).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic exponentiation")
    public void basicExponentiation() {
        Expression expression = new Expression("2 ^ 2");
        assertEquals(new BigDecimal(4).compareTo(expression.evaluate()), 0);
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
        assertEquals(new BigDecimal("1.6094379124341003").compareTo(expression.evaluate()), 0);
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
    @DisplayName("Basic implicit multiplication with variable")
    public void basicImplicitMultiplicationWithVariable() {
        Expression expression = new Expression("x(3)").withVariable("x", new BigDecimal(2));
        assertEquals(new BigDecimal(6).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic implicit multiplication with function")
    public void basicImplicitMultiplicationWithFunction() {
        Expression expression = new Expression("sqrt(4)2");
        assertEquals(new BigDecimal(4).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Basic implicit multiplication with function and variable")
    public void basicImplicitMultiplicationWithFunctionAndVariable() {
        Expression expression = new Expression("sqrt(x)2").withVariable("x", new BigDecimal(4));
        assertEquals(new BigDecimal(4).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Using constants e and pi")
    public void usingConstants() {
        Expression expression = new Expression("e^pi + 1");
        assertEquals(BigDecimalUtils.pow(BigDecimalUtils.E, BigDecimalUtils.PI).add(BigDecimal.ONE).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Instantiating a function")
    public void instantiatingFunction() {
        Expression expression = new Expression("sum(5,4,3,2,1)").withFunction("sum", ((value, args) -> {
            BigDecimal sum = value;
            for (BigDecimal arg : args) {
                sum = sum.add(arg);
            }
            return sum;
        }));
        assertEquals(new BigDecimal(15).compareTo(expression.evaluate()), 0);
    }
}