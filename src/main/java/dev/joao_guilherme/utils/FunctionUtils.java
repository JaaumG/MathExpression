package dev.joao_guilherme.utils;

import dev.joao_guilherme.evaluators.ExpressionEvaluator;
import dev.joao_guilherme.functions.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static dev.joao_guilherme.utils.ExpressionUtils.getIndexClosingBracket;

public abstract class FunctionUtils {

    private FunctionUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T evaluateFunction(ExpressionEvaluator<T> evaluator, String expression, int start, int i) {
        int j = getIndexClosingBracket(expression, i);
        String innerExpression = expression.substring(i + 1, j);
        Function<T> func = evaluator.getFunction(expression.substring(start, i));

        if (innerExpression.contains(",")) {
            String[] parts = splitPreservingGroups(innerExpression);
            List<T> args = Arrays.stream(parts).map(evaluator.newInstance()::evaluate).toList();
            return applyFunction(func, args);
        } else {
            return applyFunction(func, List.of(evaluator.newInstance().evaluate(innerExpression)));
        }
    }

    public static String[] splitPreservingGroups(String str) {
        List<String> result = new ArrayList<>();
        int openParentheses = 0;
        StringBuilder currentSegment = new StringBuilder();

        for (char ch : str.toCharArray()) {
            switch (ch) {
                case ',' -> {
                    if (openParentheses == 0) {
                        result.add(currentSegment.toString().trim());
                        currentSegment.setLength(0);
                    } else {
                        currentSegment.append(ch);
                    }
                }
                case '(', '[', '{' -> {
                    openParentheses++;
                    currentSegment.append(ch);
                }
                case ')', ']', '}' -> {
                    openParentheses--;
                    currentSegment.append(ch);
                }
                default -> currentSegment.append(ch);
            }
        }
        result.add(currentSegment.toString().trim());
        return result.toArray(new String[0]);
    }

    public static <T> T applyFunction(Function<T> function, List<T> args) {
        return function.apply(args);
    }
}
