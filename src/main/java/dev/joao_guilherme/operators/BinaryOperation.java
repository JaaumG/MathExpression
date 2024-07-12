package dev.joao_guilherme.operators;

import java.math.BigDecimal;

@FunctionalInterface
public interface BinaryOperation {

    BigDecimal apply(BigDecimal a, BigDecimal b);
}
