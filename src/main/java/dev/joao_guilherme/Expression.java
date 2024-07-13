package dev.joao_guilherme;


import dev.joao_guilherme.evaluators.ArithmeticExpressionEvaluator;
import dev.joao_guilherme.evaluators.ExpressionEvaluator;
import dev.joao_guilherme.functions.Function;
import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.UnaryOperation;

import java.math.BigDecimal;

public class Expression {

    private final String expression;
    private final ExpressionEvaluator evaluator;

    public Expression(String expression) {
        this(expression, new ArithmeticExpressionEvaluator());
    }

    public Expression(String expression, ExpressionEvaluator evaluator) {
        if (expression == null) throw new IllegalArgumentException("Expression cannot be null");
        this.expression = expression.replaceAll("\\s+", "");
        this.evaluator = evaluator;
    }

    public BigDecimal evaluate() {
        return this.evaluator.evaluate(expression);
    }

    public String getExpression() {
        return expression;
    }

    public Expression withFunction(String function, Function functionImpl) {
        evaluator.addFunction(function, functionImpl);
        return this;
    }

    public Expression withVariable(String variable, BigDecimal value) {
        evaluator.addVariable(variable, value);
        return this;
    }

    public Expression withVariable(String variable, String value) {
        return new Expression(expression.replaceAll(variable, value), evaluator);
    }

    public Expression withOperator(String operator, int precedence, BinaryOperation operation) {
        evaluator.addOperator(operator, precedence, operation);
        return this;
    }

    public Expression withOperator(String operator, int precedence, UnaryOperation operation) {
        evaluator.addOperator(operator, precedence, operation);
        return this;
    }

    public Expression withOperator(String operator, BinaryOperation operation) {
        evaluator.addOperator(operator, 1, operation);
        return this;
    }

    public Expression withOperator(String operator, UnaryOperation operation) {
        evaluator.addOperator(operator, 1, operation);
        return this;
    }
}
