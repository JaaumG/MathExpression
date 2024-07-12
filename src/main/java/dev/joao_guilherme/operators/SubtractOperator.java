package dev.joao_guilherme.operators;

import java.math.BigDecimal;

public class SubtractOperator extends Operator implements BinaryOperation{

    public SubtractOperator() {
        super(1, '-');
    }

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }
}
