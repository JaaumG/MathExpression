package dev.joao_guilherme.operators;

import java.math.BigDecimal;

@FunctionalInterface
public interface BinaryOperation extends Operator {

    @Override
    BigDecimal apply(BigDecimal a, BigDecimal b);
}
