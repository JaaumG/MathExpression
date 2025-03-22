package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.functions.Function;
import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.Operator;
import dev.joao_guilherme.operators.UnaryOperation;

import java.util.HashMap;
import java.util.Map;

public abstract class ExpressionEvaluator<T> implements Cloneable {

    private final Map<Character, Operator<T>> operators = new HashMap<>();
    private final Map<String, Function<T>> functions = new HashMap<>();
    private final Map<String, T> variables = new HashMap<>();

    public abstract T evaluate(String expression);

    public void addFunction(String name, Function<T> function) {
        functions.put(name, function);
    }

    public boolean isOperator(char operator) {
        return operators.containsKey(operator);
    }

    public Operator<T> getOperator(char operator) {
        return operators.get(operator);
    }

    public boolean isFunction(String function) {
        return functions.containsKey(function);
    }

    public Function<T> getFunction(String function) {
        return functions.get(function);
    }

    public boolean isVariable(String variable) {
        return variables.containsKey(variable);
    }

    public T getVariable(String variable) {
        return variables.get(variable);
    }

    public <E extends T> void addVariable(String variable, E value) {
        variables.put(variable, value);
    }

    public void addOperator(char operator, int precedence, UnaryOperation<T> operation) {
        addOperator(new UnaryOperation<>() {
            @Override
            public T apply(T a) {
                return operation.apply(a);
            }

            @Override
            public int getPrecedence() {
                return precedence;
            }

            @Override
            public char getSymbol() {
                return operator;
            }
        });
    }

    public void addOperator(char operator, int precedence, BinaryOperation<T> operation) {
        addOperator(new BinaryOperation<>() {
            @Override
            public T apply(T b, T a) {
                return operation.apply(b, a);
            }

            @Override
            public int getPrecedence() {
                return precedence;
            }

            @Override
            public char getSymbol() {
                return operator;
            }
        });
    }

    protected void addOperator(Operator<T> operator) {
        operators.put(operator.getSymbol(), operator);
    }

    @SuppressWarnings("unchecked")
    public ExpressionEvaluator<T> newInstance() {
        try {
            return (ExpressionEvaluator<T>) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Failed to clone ExpressionEvaluator", e);
        }
    }
}