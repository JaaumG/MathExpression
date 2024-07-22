package dev.joao_guilherme.operators;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class SubtractOperator implements BinaryOperation {

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return BigDecimalUtils.subtract(b,a);
    }

    @Override
    public int getPrecedence() {
        return 1;
    }
}
