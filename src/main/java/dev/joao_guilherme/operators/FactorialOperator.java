package dev.joao_guilherme.operators;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class FactorialOperator extends Operator implements UnaryOperation{

    public FactorialOperator() {
        super(4, '!');
    }

    @Override
    public BigDecimal apply(BigDecimal a) {
        return BigDecimalUtils.factorial(a);
    }
}
