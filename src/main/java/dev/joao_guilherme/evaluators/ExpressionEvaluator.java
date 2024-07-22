package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.functions.Function;
import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.Operator;
import dev.joao_guilherme.operators.UnaryOperation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public interface ExpressionEvaluator {

    Map<String, Operator> operator = new HashMap<>();
    Map<String, Function> functions = new HashMap<>();
    Map<String, BigDecimal> variables = new HashMap<>();

    BigDecimal evaluate(String expression);

    default void addFunction(String function, Function functionImpl) {
        this.functions.put(function, functionImpl);
    }

    default boolean isOperator(String operator) {
        return this.operator.containsKey(operator);
    }

    default Operator getOperator(String operator) {
        return this.operator.get(operator);
    }

    default boolean isFunction(String function) {
        return this.functions.containsKey(function);
    }

    default Function getFunction(String function) {
        return functions.get(function);
    }

    default boolean isVariable(String variable) {
        return variables.containsKey(variable);
    }

    default BigDecimal getVariable(String variable) {
        return variables.get(variable);
    }

    default void addVariable(String variable, BigDecimal value) {
        variables.put(variable, value);
    }

    default void addOperator(String operator, int precedence, UnaryOperation operation) {
        this.operator.put(operator, new UnaryOperation() {
            @Override
            public BigDecimal apply(BigDecimal a) {
                return operation.apply(a);
            }

            @Override
            public int getPrecedence() {
                return precedence;
            }
        });
    }

    default void addOperator(String operator, int precedence, BinaryOperation operation) {
        this.operator.put(operator, new BinaryOperation() {
            @Override
            public BigDecimal apply(BigDecimal b, BigDecimal a) {
                return operation.apply(b, a);
            }

            @Override
            public int getPrecedence() {
                return precedence;
            }
        });
    }

    default <T extends Operator> void addOperator(String operator, T operatorImpl) {
        this.operator.put(operator, operatorImpl);
    }
}
