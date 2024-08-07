package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.functions.Function;
import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.Operator;
import dev.joao_guilherme.operators.UnaryOperation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public abstract class ExpressionEvaluator implements Cloneable {

    Map<Character, Operator> operators = new HashMap<>();
    Map<String, Function> functions = new HashMap<>();
    Map<String, BigDecimal> variables = new HashMap<>();

    public abstract BigDecimal evaluate(String expression);

    public void addFunction(String function, Function functionImpl) {
        functions.put(function, functionImpl);
    }

    public boolean isOperator(char operator) {
        return operators.containsKey(operator);
    }

    public Operator getOperator(char operator) {
        return operators.get(operator);
    }

    public boolean isFunction(String function) {
        return functions.containsKey(function);
    }

    public Function getFunction(String function) {
        return functions.get(function);
    }

    public boolean isVariable(String variable) {
        return variables.containsKey(variable);
    }

    public BigDecimal getVariable(String variable) {
        return variables.get(variable);
    }

    public void addVariable(String variable, BigDecimal value) {
        variables.put(variable, value);
    }

    public void addOperator(char operator, int precedence, UnaryOperation operation) {
        addOperator(new UnaryOperation() {
            @Override
            public BigDecimal apply(BigDecimal a) {
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

    public void addOperator(char operator, int precedence, BinaryOperation operation) {
        addOperator(new BinaryOperation() {
            @Override
            public BigDecimal apply(BigDecimal b, BigDecimal a) {
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

    protected void addOperator(Operator operator) {
        operators.put(operator.getSymbol(), operator);
    }

    public ExpressionEvaluator newInstance() {
        try {
            return (ExpressionEvaluator) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException("Failed to clone ExpressionEvaluator");
        }
    }
}
