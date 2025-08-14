package dev.joao_guilherme;


import dev.joao_guilherme.evaluators.*;
import dev.joao_guilherme.functions.BinaryFunction;
import dev.joao_guilherme.functions.UnaryFunction;
import dev.joao_guilherme.functions.VarArgsFunction;
import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.UnaryOperation;
import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

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
        return BigDecimalUtils.removeScientificNotation(this.evaluator.evaluate(expression).stripTrailingZeros());
    }

    public BigDecimal solveForX() {
        return solveFor("x");
    }

    public BigDecimal solveFor(String variable) {
        return BigDecimalUtils.removeScientificNotation(EquationEvaluator.solve(expression, variable, evaluator));
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
        return BigDecimalUtils.removeScientificNotation(DerivativeEvaluator.derivate(evaluator, expression, variable, BigDecimalUtils.valueOf(x)));
    }

    public BigDecimal derivate(String variable, long x) {
        return BigDecimalUtils.removeScientificNotation(DerivativeEvaluator.derivate(evaluator, expression, variable, BigDecimalUtils.valueOf(x)));
    }

    public BigDecimal derivate(String variable, double x) {
        return BigDecimalUtils.removeScientificNotation(DerivativeEvaluator.derivate(evaluator, expression, variable, BigDecimalUtils.valueOf(x)));
    }

    public BigDecimal integrateForX(BigDecimal lowerBound, BigDecimal upperBound) {
        return integrateForX(lowerBound, upperBound, 1000);
    }

    public BigDecimal integrateForX(BigDecimal lowerBound, BigDecimal upperBound, int segments) {
        return integrate("x", lowerBound, upperBound, segments);
    }

    public BigDecimal integrateForX(String lowerBound, String upperBound) {
        return integrate("x", BigDecimalUtils.valueOf(lowerBound), BigDecimalUtils.valueOf(upperBound));
    }

    public BigDecimal integrateForX(double lowerBound, double upperBound) {
        return integrate("x", BigDecimalUtils.valueOf(lowerBound), BigDecimalUtils.valueOf(upperBound));
    }

    public BigDecimal integrateForX(long lowerBound, long upperBound) {
        return integrate("x", BigDecimalUtils.valueOf(lowerBound), BigDecimalUtils.valueOf(upperBound));
    }

    public BigDecimal integrate(String variable, BigDecimal lowerBound, BigDecimal upperBound) {
        return integrate(variable, lowerBound, upperBound, 1000);
    }

    public BigDecimal integrate(String variable, BigDecimal lowerBound, BigDecimal upperBound, int segments) {
        return BigDecimalUtils.removeScientificNotation(IntegralEvaluator.integrate(evaluator, expression, variable, lowerBound, upperBound, segments));
    }

    public BigDecimal integrate(String variable, String lowerBound, String upperBound) {
        return integrate(variable, BigDecimalUtils.valueOf(lowerBound), BigDecimalUtils.valueOf(upperBound));
    }

    public BigDecimal integrate(String variable, double lowerBound, double upperBound) {
        return integrate(variable, BigDecimalUtils.valueOf(lowerBound), BigDecimalUtils.valueOf(upperBound));
    }

    public BigDecimal integrate(String variable, long lowerBound, long upperBound) {
        return integrate(variable, BigDecimalUtils.valueOf(lowerBound), BigDecimalUtils.valueOf(upperBound));
    }

    public String getExpression() {
        return expression;
    }

    public Expression withVarArgsFunction(String function, VarArgsFunction functionImpl) {
        evaluator.addFunction(function, functionImpl);
        return this;
    }

    public Expression withFunction(String function, UnaryFunction functionImpl) {
        evaluator.addFunction(function, functionImpl);
        return this;
    }

    public Expression withFunction(String function, BinaryFunction functionImpl) {
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

    public void steps(Consumer<String> consumer) {
        evaluator.evaluate(expression);
        evaluator.getSteps().forEach(consumer);
    }

    public List<String> steps() {
        evaluator.evaluate(expression);
        return evaluator.getSteps();
    }

    @Override
    public String toString() {
        return expression + " = " + evaluator.evaluate(expression);
    }
}
