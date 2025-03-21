package dev.joao_guilherme.functions;

import java.math.BigDecimal;

@FunctionalInterface
public non-sealed interface VarArgsFunction extends Function {

    @Override
    BigDecimal apply(BigDecimal... args);

}
