package dev.joao_guilherme.operators;

import java.math.BigDecimal;

public class SubtractOperator implements BinaryOperation {

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return b.subtract(a);
    }

    @Override
    public int getPrecedence() {
        return 1;
    }
}
