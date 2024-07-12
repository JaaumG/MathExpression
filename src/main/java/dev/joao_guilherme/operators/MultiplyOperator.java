package dev.joao_guilherme.operators;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class MultiplyOperator extends Operator implements BinaryOperation {

    public MultiplyOperator() {
        super(2, '*');
    }

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return BigDecimalUtils.multiply(a, b);
    }
}
