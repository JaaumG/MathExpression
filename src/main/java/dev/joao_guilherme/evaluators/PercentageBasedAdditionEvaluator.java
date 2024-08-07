package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.operators.PercentageOperator;

public class PercentageBasedAdditionEvaluator extends ArithmeticExpressionEvaluator {

    @Override
    protected void percentageHandler(PercentageOperator pop, String expression) {
        if (values.isEmpty()) throw new IllegalArgumentException("Invalid expression: " + expression);
        if (currentIndex + 1 < expression.length() && (expression.charAt(currentIndex + 1) == '*' || expression.charAt(currentIndex + 1) == '/' || expression.charAt(currentIndex + 1) == '(')) {
            values.push(pop.apply(values.pop()));
        } else {
            pop.applyImplicitPercentageOperator(values, ops);
        }
    }
}
