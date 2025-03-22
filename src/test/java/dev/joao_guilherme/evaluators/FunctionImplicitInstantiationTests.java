package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.functions.VarArgsFunction;
import dev.joao_guilherme.utils.BigDecimalUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class FunctionImplicitInstantiationTests {

    @Test
    @DisplayName("Instantiating a function")
    void instantiatingFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sum(5,4,3,2,1)").withVarArgsFunction("sum", (args -> {
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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("multiply(2,3,4)").withVarArgsFunction("multiply", (args -> {
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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("max(1,5,3,4,2)").withVarArgsFunction("max", (args -> {
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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("min(1,5,3,4,2)").withVarArgsFunction("min", (args -> {
            BigDecimal min = args.getFirst();
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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("avg(1,2,3,4,5)").withVarArgsFunction("avg", (args -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                sum = sum.add(arg);
            }
            return BigDecimalUtils.divide(sum, BigDecimal.valueOf(args.size()));
        }));
        assertEquals(BigDecimal.valueOf(3), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a power function")
    void instantiatingPowerFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("power(2,3)").withFunction("power", ((a, b) -> a.pow(b.intValue())));
        assertEquals(BigDecimal.valueOf(8), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a factorial function")
    void instantiatingFactorialFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("factorial(5)").withFunction("factorial", (args -> {
            BigDecimal result = BigDecimal.ONE;
            for (int i = 1; i <= args.intValue(); i++) {
                result = result.multiply(BigDecimal.valueOf(i));
            }
            return result;
        }));
        assertEquals(BigDecimal.valueOf(120), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a custom percentage function")
    void instantiatingCustomPercentageFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("customPercent(2000, 50)").withFunction("customPercent", ((a,b) -> {
            BigDecimal percent = b.divide(BigDecimal.valueOf(100));
            return a.add(a.multiply(percent));
        }));
        assertEquals(BigDecimal.valueOf(3000), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a custom discount function")
    void instantiatingCustomDiscountFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("customDiscount(2000, 50)").withFunction("customDiscount", ((a, b) -> {
            BigDecimal discount = b.divide(BigDecimal.valueOf(100));
            return a.subtract(a.multiply(discount));
        }));
        assertEquals(BigDecimal.valueOf(1000), expression.evaluate());
    }

    @Test
    @DisplayName("Bhaskara function")
    void bhaskaraFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("bhaskara(a, b, c)").withVarArgsFunction("bhaskara", (args -> {
            BigDecimal a = args.get(0);
            BigDecimal b = args.get(1);
            BigDecimal c = args.get(2);
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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("gcd(48,18)").withFunction("gcd", ((a, b) -> {
            BigInteger A = a.toBigInteger();
            BigInteger B = b.toBigInteger();
            return new BigDecimal(A.gcd(B));
        }));
        assertEquals(BigDecimal.valueOf(6), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a least common multiple function")
    void instantiatingLCMFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("lcm(48,18)").withFunction("lcm", ((a, b) -> {
            BigInteger A = a.toBigInteger();
            BigInteger B = b.toBigInteger();
            return new BigDecimal(A.multiply(B).divide(A.gcd(B)));
        }));
        assertEquals(BigDecimal.valueOf(144), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a compound interest function")
    void instantiatingCompoundInterestFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("compoundInterest(1000, 0.05, 10)").withVarArgsFunction("compoundInterest", (args -> {
            if (args.size() != 3) throw new IllegalArgumentException("Compound Interest function takes three arguments");
            BigDecimal principal = args.get(0);
            BigDecimal rate = args.get(1);
            int timesCompounded = args.get(2).intValue();
            return principal.multiply(BigDecimal.valueOf(Math.pow(1 + rate.doubleValue(), timesCompounded)));
        }));
        assertEquals(BigDecimal.valueOf(1628.894626777442), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a Fibonacci sequence function")
    void instantiatingFibonacciFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("fibonacci(10)").withFunction("fibonacci", (a -> {
            int n = a.intValue();
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

        VarArgsFunction<BigDecimal> medianFunction = (args -> {
            args = new ArrayList<>(args);
            Collections.sort(args);
            int middle = args.size() / 2;
            if (args.size() % 2 == 1) {
                return args.get(middle);
            } else {
                return args.get(middle - 1).add(args.get(middle)).divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
            }
        });
        Expression<BigDecimal> expression = Expression.ofBigDecimal("median(1,5,3,9,7)").withVarArgsFunction("median", (medianFunction));
        assertEquals(BigDecimal.valueOf(5), expression.evaluate());

        expression = Expression.ofBigDecimal("median(1,5,3,9)").withVarArgsFunction("median", (medianFunction));
        assertEquals(BigDecimal.valueOf(4), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a standard deviation function")
    void instantiatingStandardDeviationFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("stddev(2,4,4,4,5,5,7,9)").withVarArgsFunction("stddev", (args -> {
            BigDecimal sum = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                sum = sum.add(arg);
            }
            BigDecimal mean = sum.divide(BigDecimal.valueOf(args.size()), RoundingMode.HALF_UP);

            BigDecimal sumOfSquaredDifferences = BigDecimal.ZERO;
            for (BigDecimal arg : args) {
                BigDecimal difference = arg.subtract(mean);
                sumOfSquaredDifferences = sumOfSquaredDifferences.add(difference.pow(2));
            }

            BigDecimal variance = sumOfSquaredDifferences.divide(BigDecimal.valueOf(args.size()), RoundingMode.HALF_UP);

            return BigDecimal.valueOf(Math.sqrt(variance.doubleValue()));
        }));

        BigDecimal result = expression.evaluate();
        assertEquals(2.0, result.doubleValue(), 0.1);
    }

    @Test
    @DisplayName("Instantiating a modular exponentiation function")
    void instantiatingModularExponentiationFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("modPow(2, 10, 1000)").withVarArgsFunction("modPow", (args -> {
            if (args.size() != 3) throw new IllegalArgumentException("modPow function takes three arguments");
            BigDecimal base = args.get(0);
            int exponent = args.get(1).intValue();
            BigDecimal modulus = args.get(2);

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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("customLog(1000, 10)").withFunction("customLog", ((a, b) -> {
            double value = a.doubleValue();
            double base = b.doubleValue();

            return BigDecimal.valueOf(Math.log(value) / Math.log(base));
        }));

        assertEquals(3.0, expression.evaluate().doubleValue(), 0.0001);
    }

    @Test
    @DisplayName("Instantiating an arithmetic sequence sum function")
    void instantiatingArithmeticSequenceSumFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("arithmeticSum(1, 100, 1)").withVarArgsFunction("arithmeticSum", (args -> {
            if (args.size() != 3) throw new IllegalArgumentException("arithmeticSum function takes three arguments");
            BigDecimal firstTerm = args.get(0);
            BigDecimal lastTerm = args.get(1);
            BigDecimal numberOfTerms = args.get(2);

            return numberOfTerms.multiply(firstTerm.add(lastTerm)).divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP);
        }));

        assertEquals(BigDecimal.valueOf(51), expression.evaluate());
    }

    @Test
    @DisplayName("Instantiating a geometric sequence sum function")
    void instantiatingGeometricSequenceSumFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("geometricSum(1, 2, 10)").withVarArgsFunction("geometricSum", (args -> {
            if (args.size() != 3) throw new IllegalArgumentException("geometricSum function takes three arguments");
            BigDecimal firstTerm = args.get(0);
            BigDecimal ratio = args.get(1);
            int numberOfTerms = args.get(2).intValue();

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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("findRoot(10, 0.0001)").withFunction("findRoot", ((target, tolerance) -> {
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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("isPrime(17)").withFunction("isPrime", (a -> {
            int number = a.intValue();

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

        expression = Expression.ofBigDecimal("isPrime(15)").withFunction("isPrime", (a -> {
            int number = a.intValue();

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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sinh(1)").withFunction("sinh", (a -> {
            double x = a.doubleValue();
            return BigDecimal.valueOf(Math.sinh(x));
        }));

        assertEquals(Math.sinh(1), expression.evaluate().doubleValue(), 0.0001);

        expression = Expression.ofBigDecimal("cosh(1)").withFunction("cosh", (a -> {
            double x = a.doubleValue();
            return BigDecimal.valueOf(Math.cosh(x));
        }));

        assertEquals(Math.cosh(1), expression.evaluate().doubleValue(), 0.0001);

        expression = Expression.ofBigDecimal("tanh(1)").withFunction("tanh", (a -> {
            double x = a.doubleValue();
            return BigDecimal.valueOf(Math.tanh(x));
        }));

        assertEquals(Math.tanh(1), expression.evaluate().doubleValue(), 0.0001);
    }

    @Test
    @DisplayName("Instantiating a combinations function (nCr)")
    void instantiatingCombinationsFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("nCr(10, 3)").withFunction("nCr", ((a, b) -> {
            int n = a.intValue();
            int r = b.intValue();

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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("nPr(10, 3)").withFunction("nPr", ((a, b) -> {
            int n = a.intValue();
            int r = b.intValue();

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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("weightedAvg([1,2,3], [0.2,0.3,0.5])").withVarArgsFunction("weightedAvg", (args -> {
            if (args.size() != 2) throw new IllegalArgumentException("weightedAvg function takes two arguments (values and weights)");

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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("ifThen(1>0, 10, 20)").withVarArgsFunction("ifThen", (args -> {
            if (args.size() != 3) throw new IllegalArgumentException("ifThen function takes three arguments (condition, trueValue, falseValue)");

            BigDecimal condition = args.get(0);
            BigDecimal trueValue = args.get(1);
            BigDecimal falseValue = args.get(2);

            if (condition.compareTo(BigDecimal.ZERO) > 0) {
                return trueValue;
            } else {
                return falseValue;
            }
        }));

        assertEquals(BigDecimal.valueOf(10), expression.evaluate());

        expression = Expression.ofBigDecimal("ifThen(0>1, 10, 20)").withVarArgsFunction("ifThen", (args -> {
            if (args.size() != 3) throw new IllegalArgumentException("ifThen function takes three arguments (condition, trueValue, falseValue)");

            BigDecimal condition = args.get(0);
            BigDecimal trueValue = args.get(1);
            BigDecimal falseValue = args.get(2);

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
        Expression<BigDecimal> expression = Expression.ofBigDecimal("sqrt(-1)").withFunction("sqrt", (a -> {
            double value = a.doubleValue();
            if (value < 0) throw new ArithmeticException("Cannot calculate square root of negative number");
            return BigDecimal.valueOf(Math.sqrt(value));
        }));

        assertThrows(ArithmeticException.class, expression::evaluate);
    }

    @Test
    @DisplayName("Instantiating a scientific notation conversion function")
    void instantiatingScientificNotationFunction() {
        Expression<BigDecimal> expression = Expression.ofBigDecimal("toScientific(1234.5678, 2)").withFunction("toScientific", ((a, b) -> {

            double number = a.doubleValue();
            int precision = b.intValue();

            int exponent = (int) Math.floor(Math.log10(Math.abs(number)));

            double mantissa = number / Math.pow(10, exponent);

            mantissa = Math.round(mantissa * Math.pow(10, precision)) / Math.pow(10, precision);

            return BigDecimal.valueOf(mantissa);
        }));

        assertEquals(1.23, expression.evaluate().doubleValue(), 0.0001);
    }
}
