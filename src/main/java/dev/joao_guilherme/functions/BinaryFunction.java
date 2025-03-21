package dev.joao_guilherme.functions;

import java.math.BigDecimal;

@FunctionalInterface
public non-sealed interface BinaryFunction extends Function {

    @Override
    BigDecimal apply(BigDecimal a, BigDecimal b);
}
