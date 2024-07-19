import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FunctionImplicitInstatiationTests {

    @Test
    @DisplayName("Instantiating a function")
    public void instantiatingFunction() {
        Expression expression = new Expression("sum(5,4,3,2,1)").withFunction("sum", ((args) -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                sum = sum.add(arg);
            }
            return sum;
        }));
        assertEquals(BigDecimal.valueOf(15).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Instantiating a multiplication function")
    public void instantiatingMultiplicationFunction() {
        Expression expression = new Expression("multiply(2,3,4)").withFunction("multiply", ((args) -> {
            BigDecimal product = BigDecimal.ONE;
            for (BigDecimal arg : args) {
                product = product.multiply(arg);
            }
            return product;
        }));
        assertEquals(BigDecimal.valueOf(24).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Instantiating a maximum function")
    public void instantiatingMaximumFunction() {
        Expression expression = new Expression("max(1,5,3,4,2)").withFunction("max", ((args) -> {
            BigDecimal max = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                if (arg.compareTo(max) > 0) {
                    max = arg;
                }
            }
            return max;
        }));
        assertEquals(BigDecimal.valueOf(5).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Instantiating a minimum function")
    public void instantiatingMinimumFunction() {
        Expression expression = new Expression("min(1,5,3,4,2)").withFunction("min", ((args) -> {
            BigDecimal min = args[0];
            for (BigDecimal arg : args) {
                if (arg.compareTo(min) < 0) {
                    min = arg;
                }
            }
            return min;
        }));
        assertEquals(BigDecimal.valueOf(1).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Instantiating an average function")
    public void instantiatingAverageFunction() {
        Expression expression = new Expression("avg(1,2,3,4,5)").withFunction("avg", ((args) -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                sum = sum.add(arg);
            }
            return BigDecimalUtils.divide(sum, BigDecimal.valueOf(args.length));
        }));
        assertEquals(BigDecimal.valueOf(3).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Instantiating a power function")
    public void instantiatingPowerFunction() {
        Expression expression = new Expression("power(2,3)").withFunction("power", ((args) -> args[0].pow(args[1].intValue())));
        assertEquals(BigDecimal.valueOf(8).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Instantiating a factorial function")
    public void instantiatingFactorialFunction() {
        Expression expression = new Expression("factorial(5)").withFunction("factorial", ((args) -> {
            BigDecimal result = BigDecimal.ONE;
            for (int i = 1; i <= args[0].intValue(); i++) {
                result = result.multiply(BigDecimal.valueOf(i));
            }
            return result;
        }));
        assertEquals(BigDecimal.valueOf(120).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Instantiating a custom percentage function")
    public void instantiatingCustomPercentageFunction() {
        Expression expression = new Expression("customPercent(2000, 50)").withFunction("customPercent", ((args) -> {
            BigDecimal percent = args[1].divide(BigDecimal.valueOf(100));
            return args[0].add(args[0].multiply(percent));
        }));
        assertEquals(BigDecimal.valueOf(3000).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Instantiating a custom discount function")
    public void instantiatingCustomDiscountFunction() {
        Expression expression = new Expression("customDiscount(2000, 50)").withFunction("customDiscount", ((args) -> {
            BigDecimal discount = args[1].divide(BigDecimal.valueOf(100));
            return args[0].subtract(args[0].multiply(discount));
        }));
        assertEquals(BigDecimal.valueOf(1000).compareTo(expression.evaluate()), 0);
    }

    @Test
    @DisplayName("Bhaskara function")
    public void bhaskaraFunction() {
        Expression expression = new Expression("bhaskara(a, b, c)").withFunction("bhaskara", ((args) -> {
            BigDecimal a = args[0];
            BigDecimal b = args[1];
            BigDecimal c = args[2];
            BigDecimal discriminant = b.pow(2).subtract(a.multiply(c).multiply(BigDecimal.valueOf(4)));
            if (discriminant.compareTo(BigDecimal.ZERO) < 0) {
                throw new ArithmeticException("No real roots");
            }
            BigDecimal sqrtDiscriminant = BigDecimal.valueOf(Math.sqrt(discriminant.doubleValue()));
            BigDecimal root1 = b.negate().add(sqrtDiscriminant).divide(a.multiply(BigDecimal.valueOf(2)), RoundingMode.HALF_UP);
            BigDecimal root2 = b.negate().subtract(sqrtDiscriminant).divide(a.multiply(BigDecimal.valueOf(2)), RoundingMode.HALF_UP);
            return root1.max(root2); // Return the greater root for simplicity
        }));

        // Test case for the equation x^2 - 3x + 2 = 0, which has roots 1 and 2
        expression.withVariable("a", BigDecimal.valueOf(1))
                .withVariable("b", BigDecimal.valueOf(-3))
                .withVariable("c", BigDecimal.valueOf(2));
        assertEquals(BigDecimal.valueOf(2).compareTo(expression.evaluate()), 0);

        // Another test case for the equation 2x^2 - 4x + 2 = 0, which has one root 1 (repeated)
        expression.withVariable("a", BigDecimal.valueOf(2))
                .withVariable("b", BigDecimal.valueOf(-4))
                .withVariable("c", BigDecimal.valueOf(2));
        assertEquals(BigDecimal.valueOf(1).compareTo(expression.evaluate()), 0);

        // Test case for an equation with no real roots (2x^2 + 4x + 5 = 0)
        expression.withVariable("a", BigDecimal.valueOf(2))
                .withVariable("b", BigDecimal.valueOf(4))
                .withVariable("c", BigDecimal.valueOf(5));
        try {
            expression.evaluate();
            fail("Expected ArithmeticException for no real roots");
        } catch (ArithmeticException e) {
            assertEquals("No real roots", e.getMessage());
        }
    }
}
