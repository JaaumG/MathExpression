package dev.joao_guilherme.utils;

import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.Operator;
import dev.joao_guilherme.operators.UnaryOperation;

import java.math.BigDecimal;
import java.util.Deque;

public abstract class OperationUtils {
    
    private static final StepHandler STEP_HANDLER = StepHandler.getInstance();

    private OperationUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static BigDecimal applyOperator(Operator lastOp, Deque<BigDecimal> values) {
        return switch (lastOp) {
            case UnaryOperation uOp -> {
                BigDecimal value = values.pop();
                STEP_HANDLER.addStep(uOp, value);
                yield uOp.apply(value);
            }
            case BinaryOperation bOp -> {
                BigDecimal value = values.pop();
                BigDecimal secondValue = values.isEmpty() ? BigDecimal.ZERO : values.pop();
                STEP_HANDLER.addStep(bOp, secondValue, value);
                yield bOp.apply(value, secondValue);
            }
            default -> throw new IllegalStateException("Unexpected value: " + lastOp);
        };
    }
}
