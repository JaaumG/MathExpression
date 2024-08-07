package dev.joao_guilherme.operators;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class ExponentialOperator implements BinaryOperation {

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return BigDecimalUtils.pow(b, a);
    }

    @Override
    public int getPrecedence() {
        return 3;
    }

    @Override
    public char getSymbol() {
        return '^';
    }
}
