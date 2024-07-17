package dev.joao_guilherme.operators;

import java.math.BigDecimal;
import java.util.Stack;

public class PercentageOperator implements UnaryOperation {

    @Override
    public BigDecimal apply(BigDecimal a) {
        return a.multiply(BigDecimal.valueOf(0.01));
    }

    @Override
    public int getPrecedence() {
        return 4;
    }

    public void applyImplicitPercentageOperator(Stack<BigDecimal> values, Stack<Operator> ops) {
        if (ops.empty()) {
            values.push(apply(values.pop()));
        } else {
            switch (ops.peek()) {
                case AdditionOperator ignored -> {
                    BigDecimal percentage = values.pop();
                    BigDecimal value = values.pop();
                    values.push(value.add(value.multiply(apply(percentage))));
                }
                case SubtractOperator ignored -> {
                    BigDecimal percentage = values.pop();
                    BigDecimal value = values.pop();
                    values.push(value.subtract(value.multiply(apply(percentage))));
                    ops.pop();
                }
                case MultiplyOperator ignored -> values.push(apply(values.pop()));
                case DivideOperator ignored -> values.push(apply(values.pop()));
                default -> throw new IllegalStateException("Unexpected value: " + ops.peek());
            }
        }
    }
}
