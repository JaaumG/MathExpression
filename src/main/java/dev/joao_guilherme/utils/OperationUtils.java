package dev.joao_guilherme.utils;

import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.Operator;
import dev.joao_guilherme.operators.UnaryOperation;

import java.math.BigDecimal;
import java.util.Deque;

public abstract class OperationUtils {

    public static void applyOperator(Operator lastOp, Deque<BigDecimal> values) {
        switch (lastOp) {
            case UnaryOperation uOp -> values.push(uOp.apply(values.pop()));
            case BinaryOperation bOp -> values.push(bOp.apply(values.pop(), values.isEmpty() ? BigDecimal.ZERO : values.pop()));
            default -> throw new IllegalStateException("Unexpected value: " + lastOp);
        }
    }
}
