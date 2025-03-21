package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.functions.Function;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FunctionImplicitInstantiationTests {

    @Test
    @DisplayName("Instantiating a function")
    void instantiatingFunction() {
        Expression expression = new Expression("sum(5,4,3,2,1)").withFunction("sum", (args -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                sum = sum.add(arg);
            }
            return sum;
        }));
        assertEquals(BigDecimal.valueOf(15), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a multiplication function")
    void instantiatingMultiplicationFunction() {
        Expression expression = new Expression("multiply(2,3,4)").withFunction("multiply", (args -> {
            BigDecimal product = BigDecimal.ONE;
            for (BigDecimal arg : args) {
                product = product.multiply(arg);
            }
            return product;
        }));
        assertEquals(BigDecimal.valueOf(24), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a maximum function")
    void instantiatingMaximumFunction() {
        Expression expression = new Expression("max(1,5,3,4,2)").withFunction("max", (args -> {
            BigDecimal max = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                if (arg.compareTo(max) > 0) {
                    max = arg;
                }
            }
            return max;
        }));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a minimum function")
    void instantiatingMinimumFunction() {
        Expression expression = new Expression("min(1,5,3,4,2)").withFunction("min", (args -> {
            BigDecimal min = args[0];
            for (BigDecimal arg : args) {
                if (arg.compareTo(min) < 0) {
                    min = arg;
                }
            }
            return min;
        }));
        assertEquals(BigDecimal.valueOf(1), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating an average function")
    void instantiatingAverageFunction() {
        Expression expression = new Expression("avg(1,2,3,4,5)").withFunction("avg", (args -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                sum = sum.add(arg);
            }
            return BigDecimalUtils.divide(sum, BigDecimal.valueOf(args.length));
        }));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a power function")
    void instantiatingPowerFunction() {
        Expression expression = new Expression("power(2,3)").withFunction("power", (args -> args[0].pow(args[1].intValue())));
        assertEquals(BigDecimal.valueOf(8), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a factorial function")
    void instantiatingFactorialFunction() {
        Expression expression = new Expression("factorial(5)").withFunction("factorial", (args -> {
            BigDecimal result = BigDecimal.ONE;
            for (int i = 1; i <= args[0].intValue(); i++) {
                result = result.multiply(BigDecimal.valueOf(i));
            }
            return result;
        }));
        assertEquals(BigDecimal.valueOf(120), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a custom percentage function")
    void instantiatingCustomPercentageFunction() {
        Expression expression = new Expression("customPercent(2000, 50)").withFunction("customPercent", (args -> {
            BigDecimal percent = args[1].divide(BigDecimal.valueOf(100));
            return args[0].add(args[0].multiply(percent));
        }));
        assertEquals(BigDecimal.valueOf(3000), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a custom discount function")
    void instantiatingCustomDiscountFunction() {
        Expression expression = new Expression("customDiscount(2000, 50)").withFunction("customDiscount", (args -> {
            BigDecimal discount = args[1].divide(BigDecimal.valueOf(100));
            return args[0].subtract(args[0].multiply(discount));
        }));
        assertEquals(BigDecimal.valueOf(1000), expression.evaluate());
    }

    @Test
    @DisplayName("Bhaskara function")
    void bhaskaraFunction() {
        Expression expression = new Expression("bhaskara(a, b, c)").withFunction("bhaskara", (args -> {
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
        assertEquals(BigDecimal.valueOf(2), expression.evaluate());

        // Another test case for the equation 2x^2 - 4x + 2 = 0, which has one root 1 (repeated)
        expression.withVariable("a", BigDecimal.valueOf(2))
                .withVariable("b", BigDecimal.valueOf(-4))
                .withVariable("c", BigDecimal.valueOf(2));
        assertEquals(BigDecimal.valueOf(1), expression.evaluate());

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

    @Test
    @DisplayName("Instantiating a greatest common divisor function")
    void instantiatingGCDFunction() {
        Expression expression = new Expression("gcd(48,18)").withFunction("gcd", (args -> {
            if (args.length != 2) throw new IllegalArgumentException("GCD function takes two arguments");
            BigInteger a = args[0].toBigInteger();
            BigInteger b = args[1].toBigInteger();
            return new BigDecimal(a.gcd(b));
        }));
        assertEquals(BigDecimal.valueOf(6), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a least common multiple function")
    void instantiatingLCMFunction() {
        Expression expression = new Expression("lcm(48,18)").withFunction("lcm", (args -> {
            if (args.length != 2) throw new IllegalArgumentException("LCM function takes two arguments");
            BigInteger a = args[0].toBigInteger();
            BigInteger b = args[1].toBigInteger();
            return new BigDecimal(a.multiply(b).divide(a.gcd(b)));
        }));
        assertEquals(BigDecimal.valueOf(144), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a compound interest function")
    void instantiatingCompoundInterestFunction() {
        Expression expression = new Expression("compoundInterest(1000, 0.05, 10)").withFunction("compoundInterest", (args -> {
            if (args.length != 3) throw new IllegalArgumentException("Compound Interest function takes three arguments");
            BigDecimal principal = args[0];
            BigDecimal rate = args[1];
            int timesCompounded = args[2].intValue();
            return principal.multiply(BigDecimal.valueOf(Math.pow(1 + rate.doubleValue(), timesCompounded)));
        }));
        assertEquals(BigDecimal.valueOf(1628.894626777442), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a Fibonacci sequence function")
    void instantiatingFibonacciFunction() {
        Expression expression = new Expression("fibonacci(10)").withFunction("fibonacci", (args -> {
            if (args.length != 1) throw new IllegalArgumentException("Fibonacci function takes one argument");
            int n = args[0].intValue();
            BigDecimal[] fib = new BigDecimal[n + 1];
            fib[0] = BigDecimal.ZERO;
            fib[1] = BigDecimal.ONE;
            for (int i = 2; i <= n; i++) {
                fib[i] = fib[i - 1].add(fib[i - 2]);
            }
            return fib[n];
        }));
        assertEquals(BigDecimal.valueOf(55), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a median function")
    void instantiatingMedianFunction() {

        Function medianFunction = (args -> {
            Arrays.sort(args);
            int middle = args.length / 2;
            if (args.length % 2 == 1) {
                return args[middle];
            } else {
                return args[middle - 1].add(args[middle]).divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
            }
        });
        Expression expression = new Expression("median(1,5,3,9,7)").withFunction("median", (medianFunction));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());

        expression = new Expression("median(1,5,3,9)").withFunction("median", (medianFunction));
        assertEquals(BigDecimal.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a standard deviation function")
    void instantiatingStandardDeviationFunction() {
        Expression expression = new Expression("stddev(2,4,4,4,5,5,7,9)").withFunction("stddev", (args -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                sum = sum.add(arg);
            }
            BigDecimal mean = sum.divide(BigDecimal.valueOf(args.length), RoundingMode.HALF_UP);

            BigDecimal sumOfSquaredDifferences = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                BigDecimal difference = arg.subtract(mean);
                sumOfSquaredDifferences = sumOfSquaredDifferences.add(difference.pow(2));
            }

            BigDecimal variance = sumOfSquaredDifferences.divide(BigDecimal.valueOf(args.length), RoundingMode.HALF_UP);

            return BigDecimal.valueOf(Math.sqrt(variance.doubleValue()));
        }));

        BigDecimal result = expression.evaluate();
        assertEquals(2.0, result.doubleValue(), 0.1);
    }

    @Test
    @DisplayName("Instantiating a modular exponentiation function")
    void instantiatingModularExponentiationFunction() {
        Expression expression = new Expression("modPow(2, 10, 1000)").withFunction("modPow", (args -> {
            if (args.length != 3) throw new IllegalArgumentException("modPow function takes three arguments");
            BigDecimal base = args[0];
            int exponent = args[1].intValue();
            BigDecimal modulus = args[2];

            BigDecimal result = BigDecimal.ONE;
            for (int i = 0; i < exponent; i++) {
                result = result.multiply(base).remainder(modulus);
            }

            return result;
        }));

        assertEquals(BigDecimal.valueOf(24), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a logarithm with custom base function")
    void instantiatingCustomBaseLogarithmFunction() {
        Expression expression = new Expression("customLog(1000, 10)").withFunction("customLog", (args -> {
            if (args.length != 2) throw new IllegalArgumentException("customLog function takes two arguments");
            double value = args[0].doubleValue();
            double base = args[1].doubleValue();

            return BigDecimal.valueOf(Math.log(value) / Math.log(base));
        }));

        assertEquals(3.0, expression.evaluate().doubleValue(), 0.0001);
    }

    @Test
    @DisplayName("Instantiating an arithmetic sequence sum function")
    void instantiatingArithmeticSequenceSumFunction() {
        Expression expression = new Expression("arithmeticSum(1, 100, 1)").withFunction("arithmeticSum", (args -> {
            if (args.length != 3) throw new IllegalArgumentException("arithmeticSum function takes three arguments");
            BigDecimal firstTerm = args[0];
            BigDecimal lastTerm = args[1];
            BigDecimal numberOfTerms = args[2];

            return numberOfTerms.multiply(firstTerm.add(lastTerm)).divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        }));

        assertEquals(BigDecimal.valueOf(51), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a geometric sequence sum function")
    void instantiatingGeometricSequenceSumFunction() {
        Expression expression = new Expression("geometricSum(1, 2, 10)").withFunction("geometricSum", (args -> {
            if (args.length != 3) throw new IllegalArgumentException("geometricSum function takes three arguments");
            BigDecimal firstTerm = args[0];
            BigDecimal ratio = args[1];
            int numberOfTerms = args[2].intValue();

            BigDecimal rPowerN = BigDecimal.valueOf(Math.pow(ratio.doubleValue(), numberOfTerms));
            BigDecimal numerator = firstTerm.multiply(BigDecimal.ONE.subtract(rPowerN));
            BigDecimal denominator = BigDecimal.ONE.subtract(ratio);

            return numerator.divide(denominator, RoundingMode.HALF_UP);
        }));

        assertEquals(BigDecimal.valueOf(1023), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a root-finding function (Newton's method)")
    void instantiatingNewtonMethodRootFindingFunction() {
        Expression expression = new Expression("findRoot(10, 0.0001)").withFunction("findRoot", (args -> {
            if (args.length != 2) throw new IllegalArgumentException("findRoot function takes two arguments");
            BigDecimal target = args[0];
            BigDecimal tolerance = args[1];

            BigDecimal guess = target.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
            BigDecimal lastGuess;

            do {
                lastGuess = guess;
                guess = lastGuess.add(target.divide(lastGuess, MathContext.DECIMAL128))
                        .divide(BigDecimal.valueOf(2), MathContext.DECIMAL128);

                BigDecimal diff = guess.subtract(lastGuess).abs();
                if (diff.compareTo(tolerance) <= 0) {
                    break;
                }
            } while (true);

            return guess;
        }));

        BigDecimal result = expression.evaluate();
        assertEquals(3.16227766, result.doubleValue(), 0.0001);
    }

    @Test
    @DisplayName("Instantiating a prime checking function")
    void instantiatingPrimeCheckingFunction() {
        Expression expression = new Expression("isPrime(17)").withFunction("isPrime", (args -> {
            if (args.length != 1) throw new IllegalArgumentException("isPrime function takes one argument");
            int number = args[0].intValue();

            if (number <= 1) return BigDecimal.ZERO;
            if (number <= 3) return BigDecimal.ONE;
            if (number % 2 == 0 || number % 3 == 0) return BigDecimal.ZERO;

            int limit = (int) Math.sqrt(number);
            for (int i = 5; i <= limit; i += 6) {
                if (number % i == 0 || number % (i + 2) == 0) return BigDecimal.ZERO;
            }

            return BigDecimal.ONE;
        }));

        assertEquals(BigDecimal.ONE, expression.evaluate());

        expression = new Expression("isPrime(15)").withFunction("isPrime", (args -> {
            if (args.length != 1) throw new IllegalArgumentException("isPrime function takes one argument");
            int number = args[0].intValue();

            if (number <= 1) return BigDecimal.ZERO;
            if (number <= 3) return BigDecimal.ONE;
            if (number % 2 == 0 || number % 3 == 0) return BigDecimal.ZERO;

            int limit = (int) Math.sqrt(number);
            for (int i = 5; i <= limit; i += 6) {
                if (number % i == 0 || number % (i + 2) == 0) return BigDecimal.ZERO;
            }

            return BigDecimal.ONE;
        }));

        assertEquals(BigDecimal.ZERO, expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a hyperbolic functions")
    void instantiatingHyperbolicFunctions() {
        Expression expression = new Expression("sinh(1)").withFunction("sinh", (args -> {
            if (args.length != 1) throw new IllegalArgumentException("sinh function takes one argument");
            double x = args[0].doubleValue();
            return BigDecimal.valueOf(Math.sinh(x));
        }));

        assertEquals(Math.sinh(1), expression.evaluate().doubleValue(), 0.0001);

        expression = new Expression("cosh(1)").withFunction("cosh", (args -> {
            if (args.length != 1) throw new IllegalArgumentException("cosh function takes one argument");
            double x = args[0].doubleValue();
            return BigDecimal.valueOf(Math.cosh(x));
        }));

        assertEquals(Math.cosh(1), expression.evaluate().doubleValue(), 0.0001);

        expression = new Expression("tanh(1)").withFunction("tanh", (args -> {
            if (args.length != 1) throw new IllegalArgumentException("tanh function takes one argument");
            double x = args[0].doubleValue();
            return BigDecimal.valueOf(Math.tanh(x));
        }));

        assertEquals(Math.tanh(1), expression.evaluate().doubleValue(), 0.0001);
    }

    @Test
    @DisplayName("Instantiating a combinations function (nCr)")
    void instantiatingCombinationsFunction() {
        Expression expression = new Expression("nCr(10, 3)").withFunction("nCr", (args -> {
            if (args.length != 2) throw new IllegalArgumentException("nCr function takes two arguments");
            int n = args[0].intValue();
            int r = args[1].intValue();

            if (r < 0 || r > n) return BigDecimal.ZERO;

            if (r > n - r) r = n - r;

            BigDecimal result = BigDecimal.ONE;
            for (int i = 0; i < r; i++) {
                result = result.multiply(BigDecimal.valueOf(n - i))
                        .divide(BigDecimal.valueOf(i + 1), RoundingMode.HALF_UP);
            }

            return result;
        }));

        assertEquals(BigDecimal.valueOf(120), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a permutations function (nPr)")
    void instantiatingPermutationsFunction() {
        Expression expression = new Expression("nPr(10, 3)").withFunction("nPr", (args -> {
            if (args.length != 2) throw new IllegalArgumentException("nPr function takes two arguments");
            int n = args[0].intValue();
            int r = args[1].intValue();

            if (r < 0 || r > n) return BigDecimal.ZERO;

            BigDecimal result = BigDecimal.ONE;
            for (int i = 0; i < r; i++) {
                result = result.multiply(BigDecimal.valueOf(n - i));
            }

            return result;
        }));

        assertEquals(BigDecimal.valueOf(720), expression.evaluate());
    }

    @Test
    @Disabled("this test is disabled because the function is not implemented yet")
    @DisplayName("Instantiating a weighted average function")
    void instantiatingWeightedAverageFunction() {
        Expression expression = new Expression("weightedAvg([1,2,3], [0.2,0.3,0.5])").withFunction("weightedAvg", (args -> {
            if (args.length != 2) throw new IllegalArgumentException("weightedAvg function takes two arguments (values and weights)");

            BigDecimal[] values = new BigDecimal[]{BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3)};
            BigDecimal[] weights = new BigDecimal[]{BigDecimal.valueOf(0.2), BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.5)};

            if (values.length != weights.length)
                throw new IllegalArgumentException("Values and weights arrays must have the same length");

            BigDecimal weightedSum = BigDecimal.ZERO;
            BigDecimal totalWeight = BigDecimal.ZERO;

            for (int i = 0; i < values.length; i++) {
                weightedSum = weightedSum.add(values[i].multiply(weights[i]));
                totalWeight = totalWeight.add(weights[i]);
            }

            if (totalWeight.compareTo(BigDecimal.ONE) != 0) {
                weightedSum = weightedSum.divide(totalWeight, RoundingMode.HALF_UP);
            }

            return weightedSum;
        }));

        assertEquals(BigDecimal.valueOf(2.3), expression.evaluate());
    }

    @Test
    @Disabled("this test is disabled because the function is not implemented yet")
    @DisplayName("Instantiating a conditional function")
    void instantiatingConditionalFunction() {
        Expression expression = new Expression("ifThen(1>0, 10, 20)").withFunction("ifThen", (args -> {
            if (args.length != 3) throw new IllegalArgumentException("ifThen function takes three arguments (condition, trueValue, falseValue)");

            BigDecimal condition = args[0];
            BigDecimal trueValue = args[1];
            BigDecimal falseValue = args[2];

            if (condition.compareTo(BigDecimal.ZERO) > 0) {
                return trueValue;
            } else {
                return falseValue;
            }
        }));

        assertEquals(BigDecimal.valueOf(10), expression.evaluate());

        expression = new Expression("ifThen(0>1, 10, 20)").withFunction("ifThen", (args -> {
            if (args.length != 3) throw new IllegalArgumentException("ifThen function takes three arguments (condition, trueValue, falseValue)");

            BigDecimal condition = args[0];
            BigDecimal trueValue = args[1];
            BigDecimal falseValue = args[2];

            if (condition.compareTo(BigDecimal.ZERO) > 0) {
                return trueValue;
            } else {
                return falseValue;
            }
        }));

        assertEquals(BigDecimal.valueOf(20), expression.evaluate());
    }

    @Test
    @DisplayName("Function with invalid arguments should throw exception")
    void functionWithInvalidArgumentsShouldThrowException() {
        Expression expression = new Expression("sqrt(-1)").withFunction("sqrt", (args -> {
            if (args.length != 1) throw new IllegalArgumentException("sqrt function takes one argument");
            double value = args[0].doubleValue();
            if (value < 0) throw new ArithmeticException("Cannot calculate square root of negative number");
            return BigDecimal.valueOf(Math.sqrt(value));
        }));

        assertThrows(ArithmeticException.class, expression::evaluate);
    }

    @Test
    @DisplayName("Instantiating a scientific notation conversion function")
    void instantiatingScientificNotationFunction() {
        Expression expression = new Expression("toScientific(1234.5678, 2)").withFunction("toScientific", (args -> {
            if (args.length != 2) throw new IllegalArgumentException("toScientific function takes two arguments (number, precision)");

            double number = args[0].doubleValue();
            int precision = args[1].intValue();

            int exponent = (int) Math.floor(Math.log10(Math.abs(number)));

            double mantissa = number / Math.pow(10, exponent);

            mantissa = Math.round(mantissa * Math.pow(10, precision)) / Math.pow(10, precision);

            return BigDecimal.valueOf(mantissa);
        }));

        assertEquals(1.23, expression.evaluate().doubleValue(), 0.0001);
    }
}
