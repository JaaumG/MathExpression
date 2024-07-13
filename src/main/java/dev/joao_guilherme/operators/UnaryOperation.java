package dev.joao_guilherme.operators;

import java.math.BigDecimal;

@FunctionalInterface
public interface UnaryOperation extends Operator {

    BigDecimal apply(BigDecimal a);
}
