package dev.joao_guilherme;


import dev.joao_guilherme.evaluators.ArithmeticExpressionEvaluator;
import dev.joao_guilherme.evaluators.DerivativeEvaluator;
import dev.joao_guilherme.evaluators.EquationEvaluator;
import dev.joao_guilherme.evaluators.ExpressionEvaluator;
import dev.joao_guilherme.functions.Function;
import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.UnaryOperation;
import dev.joao_guilherme.utils.BigDecimalUtils;

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

    public BigDecimal solveForX() {
        return solveFor("x");
    }

    public BigDecimal solveFor(String variable) {
        return EquationEvaluator.solve(expression, variable, evaluator);
    }

    public BigDecimal derivativeForX(String x) {
        return derivate("x", x);
    }

    public BigDecimal derivativeForX(long x) {
        return derivate("x", x);
    }

    public BigDecimal derivativeForX(double x) {
        return derivate("x", x);
    }

    public BigDecimal derivate(String variable, String x) {
        return DerivativeEvaluator.derivate(evaluator, expression, variable, BigDecimalUtils.valueOf(x));
    }

    public BigDecimal derivate(String variable, long x) {
        return DerivativeEvaluator.derivate(evaluator, expression, variable, BigDecimalUtils.valueOf(x));
    }

    public BigDecimal derivate(String variable, double x) {
        return DerivativeEvaluator.derivate(evaluator, expression, variable, BigDecimalUtils.valueOf(x));
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
        evaluator.addVariable(variable, BigDecimalUtils.valueOf(value));
        return this;
    }

    public Expression withVariable(String variable, long value) {
        evaluator.addVariable(variable, BigDecimalUtils.valueOf(value));
        return this;
    }

    public Expression withVariable(String variable, double value) {
        evaluator.addVariable(variable, BigDecimalUtils.valueOf(value));
        return this;
    }

    public Expression withOperator(char operator, int precedence, BinaryOperation operation) {
        evaluator.addOperator(operator, precedence, operation);
        return this;
    }

    public Expression withOperator(char operator, int precedence, UnaryOperation operation) {
        evaluator.addOperator(operator, precedence, operation);
        return this;
    }

    public Expression withOperator(char operator, BinaryOperation operation) {
        evaluator.addOperator(operator, 1, operation);
        return this;
    }

    public Expression withOperator(char operator, UnaryOperation operation) {
        evaluator.addOperator(operator, 1, operation);
        return this;
    }
}
