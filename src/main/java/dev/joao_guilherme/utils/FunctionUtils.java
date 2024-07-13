package dev.joao_guilherme.utils;

import dev.joao_guilherme.evaluators.ExpressionEvaluator;
import dev.joao_guilherme.functions.Function;

import java.math.BigDecimal;
import java.util.Arrays;

import static dev.joao_guilherme.utils.ExpressionUtils.getIndexClosingBracket;

public abstract class FunctionUtils {

    public static BigDecimal evaluateFunction(ExpressionEvaluator evaluator, String expression, int start, int i) {
        int j = getIndexClosingBracket(expression, i);
        String innerExpression = expression.substring(i + 1, j);
        Function func = evaluator.getFunction(expression.substring(start, i));
        BigDecimal result;

        if (innerExpression.contains(",")) {
            String[] parts = innerExpression.split(",");
            BigDecimal[] args = Arrays.stream(parts).skip(1).map(BigDecimal::new).toArray(BigDecimal[]::new);
            result = applyFunction(func, new BigDecimal(parts[0]), args);
        } else {
            result = applyFunction(func, evaluator.evaluate(innerExpression));
        }
        return result;
    }

    public static BigDecimal applyFunction(Function function, BigDecimal value, BigDecimal... args) {
        return function.apply(value, args);
    }
}
