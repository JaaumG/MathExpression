package dev.joao_guilherme.operators;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class FactorialOperator implements UnaryOperation {

    @Override
    public BigDecimal apply(BigDecimal a) {
        return BigDecimalUtils.factorial(a);
    }

    @Override
    public int getPrecedence() {
        return 4;
    }

    @Override
    public char getSymbol() {
        return '!';
    }
}
