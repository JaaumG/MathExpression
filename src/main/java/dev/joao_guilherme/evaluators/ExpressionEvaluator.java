package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.functions.Function;
import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.Operator;
import dev.joao_guilherme.operators.UnaryOperation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public interface ExpressionEvaluator {

    Map<Character, Operator> operators = new HashMap<>();
    Map<String, Function> functions = new HashMap<>();
    Map<String, BigDecimal> variables = new HashMap<>();

    BigDecimal evaluate(String expression);

    default void addFunction(String function, Function functionImpl) {
        functions.put(function, functionImpl);
    }

    default boolean isOperator(char operator) {
        return operators.containsKey(operator);
    }

    default Operator getOperator(char operator) {
        return operators.get(operator);
    }

    default boolean isFunction(String function) {
        return functions.containsKey(function);
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

    default void addOperator(char operator, int precedence, UnaryOperation operation) {
        operators.put(operator, new UnaryOperation() {
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

    default void addOperator(char operator, int precedence, BinaryOperation operation) {
        operators.put(operator, new BinaryOperation() {
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

    default <T extends Operator> void addOperator(char operator, T operatorImpl) {
        operators.put(operator, operatorImpl);
    }
}
