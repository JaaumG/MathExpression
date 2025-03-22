package dev.joao_guilherme;

import dev.joao_guilherme.evaluators.*;
import dev.joao_guilherme.functions.BinaryFunction;
import dev.joao_guilherme.functions.UnaryFunction;
import dev.joao_guilherme.functions.VarArgsFunction;
import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.UnaryOperation;
import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class Expression<T> {

    private final String expression;
    private final ExpressionEvaluator<T> evaluator;

    public Expression(String expression, ExpressionEvaluator<T> evaluator) {
        if (expression == null) throw new IllegalArgumentException("Expression cannot be null");
        this.expression = expression.replaceAll("\\s+", "");
        this.evaluator = evaluator;
    }

    public static Expression<BigDecimal> ofBigDecimal(String expression) {
        return new Expression<>(expression, new ArithmeticExpressionEvaluator());
    }

    public T evaluate() {
        return evaluator.evaluate(expression);
    }

    public BigDecimal solveForX() {
        return solveFor("x");
    }

    public BigDecimal solveFor(String variable) {
        return BigDecimalUtils.removeScientificNotation(EquationEvaluator.solve(expression, variable, (ExpressionEvaluator<BigDecimal>) evaluator));
    }

    public BigDecimal derivativeForX(BigDecimal x) {
        return derivate("x", x);
    }

    public BigDecimal derivativeForX(long x) {
        return derivate("x", BigDecimal.valueOf(x));
    }

    public BigDecimal derivativeForX(double x) {
        return derivate("x", BigDecimal.valueOf(x));
    }

    public BigDecimal derivate(String variable, BigDecimal x) {
        return BigDecimalUtils.removeScientificNotation(DerivativeEvaluator.derivate((ExpressionEvaluator<BigDecimal>) evaluator, expression, variable, x));
    }

    public BigDecimal integrateForX(BigDecimal lowerBound, BigDecimal upperBound) {
        return integrateForX(lowerBound, upperBound, 1000);
    }

    public BigDecimal integrateForX(long lowerBound, long upperBound) {
        return integrateForX(BigDecimal.valueOf(lowerBound), BigDecimal.valueOf(upperBound), 1000);
    }

    public BigDecimal integrateForX(double lowerBound, double upperBound) {
        return integrateForX(BigDecimal.valueOf(lowerBound), BigDecimal.valueOf(upperBound), 1000);
    }

    public BigDecimal integrateForX(BigDecimal lowerBound, BigDecimal upperBound, int segments) {
        return integrate("x", lowerBound, upperBound, segments);
    }

    public BigDecimal integrate(String variable, BigDecimal lowerBound, BigDecimal upperBound, int segments) {
        return BigDecimalUtils.removeScientificNotation(IntegralEvaluator.integrate((ExpressionEvaluator<BigDecimal>) evaluator, expression, variable, lowerBound, upperBound, segments).stripTrailingZeros());
    }

    public String getExpression() {
        return expression;
    }

    public Expression<T> withVariable(String variable,T value) {
        evaluator.addVariable(variable, value);
        return this;
    }

    public Expression<T> withVarArgsFunction(String function, VarArgsFunction<T> functionImpl) {
        evaluator.addFunction(function, functionImpl);
        return this;
    }

    public Expression<T> withFunction(String function, UnaryFunction<T> functionImpl) {
        evaluator.addFunction(function, functionImpl);
        return this;
    }

    public Expression<T> withFunction(String function, BinaryFunction<T> functionImpl) {
        evaluator.addFunction(function, functionImpl);
        return this;
    }

    public Expression<T> withOperator(char operator, int precedence, BinaryOperation<T> operation) {
        evaluator.addOperator(operator, precedence, operation);
        return this;
    }

    public Expression<T> withOperator(char operator, int precedence, UnaryOperation<T> operation) {
        evaluator.addOperator(operator, precedence, operation);
        return this;
    }

    public Expression<T> withOperator(char operator, BinaryOperation<T> operation) {
        evaluator.addOperator(operator, 1, operation);
        return this;
    }

    public Expression<T> withOperator(char operator, UnaryOperation<T> operation) {
        evaluator.addOperator(operator, 1, operation);
        return this;
    }
}
