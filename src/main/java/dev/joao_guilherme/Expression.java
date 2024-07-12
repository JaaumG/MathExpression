package dev.joao_guilherme;


import dev.joao_guilherme.evaluators.ArithmeticExpressionEvaluator;
import dev.joao_guilherme.evaluators.ExpressionEvaluator;

import java.math.BigDecimal;

public class Expression {

    private final String expression;
    private final ExpressionEvaluator evaluator;

    public Expression(String expression) {
        this(expression, new ArithmeticExpressionEvaluator());
    }

    public Expression(String expression, ExpressionEvaluator evaluator) {
        if (expression == null) {
            throw new IllegalArgumentException("Expression cannot be null");
        }
        this.expression = expression.replaceAll("\\s+", "");
        this.evaluator = evaluator;
    }

    public BigDecimal evaluate() {
        return this.evaluator.evaluate(expression);
    }

    public String getExpression() {
        return expression;
    }
}
