package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.operators.Operator;
import dev.joao_guilherme.operators.PercentageOperator;

import java.math.BigDecimal;
import java.util.Stack;

public class PercentageBasedAdditionEvaluator extends ArithmeticExpressionEvaluator {

    @Override
    protected void percentageHandler(PercentageOperator pop, Stack<Operator> ops, Stack<BigDecimal> values, String expression, int i) {
        if (values.isEmpty()) throw new IllegalArgumentException("Invalid expression: " + expression);
        if (i + 1 < expression.length() && (expression.charAt(i + 1) == '*' || expression.charAt(i + 1) == '/' || expression.charAt(i + 1) == '(')){
            values.push(pop.apply(values.pop()));
        } else {
            pop.applyImplicitPercentageOperator(values, ops);
        }
    }
}
