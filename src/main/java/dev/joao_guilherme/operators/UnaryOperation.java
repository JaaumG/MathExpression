package dev.joao_guilherme.operators;

import java.math.BigDecimal;

@FunctionalInterface
public interface UnaryOperation {

    BigDecimal apply(BigDecimal a);
}
