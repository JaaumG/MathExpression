package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.Expression;
import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public abstract class DerivativeEvaluator {

    private DerivativeEvaluator() {
        throw new IllegalStateException("Utility class");
    }

    public static BigDecimal derivate(ExpressionEvaluator expressionEvaluator, String function, String variable, BigDecimal x) {
        BigDecimal h = BigDecimalUtils.valueOf("1E-18");
        BigDecimal fX = new Expression(function, expressionEvaluator).withVariable(variable, x).evaluate(); // f(x)
        BigDecimal fXPlusH = new Expression(function, expressionEvaluator).withVariable(variable, BigDecimalUtils.add(x, h)).evaluate(); // f(x+h)
        return BigDecimalUtils.divide(BigDecimalUtils.subtract(fXPlusH, fX), h); // (f(x+h) - f(x)) / h
    }
}
