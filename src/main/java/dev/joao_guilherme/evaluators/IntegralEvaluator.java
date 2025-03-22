package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public abstract class IntegralEvaluator {

    private IntegralEvaluator() {
        throw new IllegalStateException("Utility class");
    }

    public static BigDecimal integrate(ExpressionEvaluator<BigDecimal> expressionEvaluator, String function, String variable, BigDecimal lowerBound, BigDecimal upperBound, int segments) {
        if (segments <= 0) throw new IllegalArgumentException("Number of segments must be positive");
        BigDecimal range = BigDecimalUtils.subtract(upperBound, lowerBound);
        BigDecimal stepSize = BigDecimalUtils.divide(range, BigDecimalUtils.valueOf(segments));
        BigDecimal sum = new Expression<>(function, expressionEvaluator).withVariable(variable, lowerBound).evaluate();
        BigDecimal lastValue = new Expression<>(function, expressionEvaluator).withVariable(variable, upperBound).evaluate();
        sum = BigDecimalUtils.add(sum, lastValue);
        for (int i = 1; i < segments; i++) {
            BigDecimal x = BigDecimalUtils.add(lowerBound, BigDecimalUtils.multiply(BigDecimalUtils.valueOf(i), stepSize));
            BigDecimal fx = new Expression<>(function, expressionEvaluator).withVariable(variable, x).evaluate();
            sum = BigDecimalUtils.add(sum, BigDecimalUtils.multiply(fx, BigDecimalUtils.valueOf(2)));
        }
        return BigDecimalUtils.multiply(BigDecimalUtils.divide(stepSize, BigDecimalUtils.valueOf(2)), sum);
    }
}
