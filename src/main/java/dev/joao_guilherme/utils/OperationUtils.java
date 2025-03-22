package dev.joao_guilherme.utils;

import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.Operator;
import dev.joao_guilherme.operators.UnaryOperation;

import java.util.Deque;

public abstract class OperationUtils {

    private OperationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> void applyOperator(Operator<T> lastOp, Deque<T> values, T defaultValue) {
        switch (lastOp) {
            case UnaryOperation<T> uOp -> values.push(uOp.apply(values.pop()));
            case BinaryOperation<T> bOp -> values.push(bOp.apply(values.pop(), values.isEmpty() ? defaultValue : values.pop()));
            default -> throw new IllegalStateException("Unexpected value: " + lastOp);
        }
    }
}
