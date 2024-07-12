package dev.joao_guilherme.operators;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class DivideOperator extends Operator implements BinaryOperation {

    public DivideOperator() {
        super(2, '/');
    }

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return BigDecimalUtils.divide(b, a);
    }
}
