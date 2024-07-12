package dev.joao_guilherme.operators;

import java.math.BigDecimal;

public class AdditionOperator extends Operator implements BinaryOperation {

    public AdditionOperator() {
        super(1, '+');
    }

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }
}
