package dev.joao_guilherme.operators;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class AdditionOperator implements BinaryOperation {

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return BigDecimalUtils.add(a,b);
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public char getSymbol() {
        return '+';
    }
}
