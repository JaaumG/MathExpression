package dev.joao_guilherme.operators;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class MultiplyOperator implements BinaryOperation {

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return BigDecimalUtils.multiply(a, b);
    }

    @Override
    public int getPrecedence() {
        return 2;
    }

    @Override
    public char getSymbol() {
        return '*';
    }
}
