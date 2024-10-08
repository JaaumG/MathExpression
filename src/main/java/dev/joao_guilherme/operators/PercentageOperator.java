package dev.joao_guilherme.operators;

import java.math.BigDecimal;
import java.util.Deque;

public class PercentageOperator implements UnaryOperation {

    @Override
    public BigDecimal apply(BigDecimal a) {
        return a.multiply(BigDecimal.valueOf(0.01));
    }

    @Override
    public int getPrecedence() {
        return 4;
    }

    @Override
    public char getSymbol() {
        return '%';
    }

    public void applyImplicitPercentageOperator(Deque<BigDecimal> values, Deque<Operator> ops) {
        if (ops.isEmpty()) {
            if (values.peek().compareTo(BigDecimal.ZERO) > 0) {
                values.push(apply(values.pop()));
            } else {
                BigDecimal percentage = apply(values.pop());
                BigDecimal value = values.pop();
                BigDecimal percentageOf = value.multiply(percentage);
                values.push(value.add(percentageOf));
            }
        } else {
            switch (ops.peek()) {
                case AdditionOperator additionOperator -> {
                    BigDecimal percentage = values.pop();
                    BigDecimal value = values.pop();
                    values.push(additionOperator.apply(value, value.multiply(apply(percentage))));
                }
                case SubtractOperator subtractOperator -> {
                    BigDecimal percentage = values.pop();
                    BigDecimal value = values.pop();
                    values.push(subtractOperator.apply(value.multiply(apply(percentage)), value));
                    ops.pop();
                }
                case MultiplyOperator ignored -> values.push(apply(values.pop()));
                case DivideOperator divideOperator -> {
                    BigDecimal percentage = apply(values.pop());
                    BigDecimal lastValue = values.pop();
                    values.push(divideOperator.apply(percentage, lastValue));
                    ops.pop();
                }
                default -> throw new IllegalStateException("Unexpected value: " + ops.peek());
            }
        }
    }
}
