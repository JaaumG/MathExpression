package dev.joao_guilherme.operators;

import java.math.BigDecimal;

public class AdditionOperator implements BinaryOperation {

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    @Override
    public int getPrecedence() {
        return 1;
    }
}
