package dev.joao_guilherme.operators;

import java.math.BigDecimal;

public class SubtractOperator implements BinaryOperation {

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return a.subtract(b);
    }

    @Override
    public int getPrecedence() {
        return 1;
    }
}
