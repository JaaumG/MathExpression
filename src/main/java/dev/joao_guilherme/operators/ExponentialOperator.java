package dev.joao_guilherme.operators;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class ExponentialOperator extends Operator implements BinaryOperation {

    public ExponentialOperator() {
        super(3, '^');
    }

    @Override
    public BigDecimal apply(BigDecimal a, BigDecimal b) {
        return BigDecimalUtils.pow(b, a);
    }
}
