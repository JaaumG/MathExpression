package dev.joao_guilherme.functions;

import java.math.BigDecimal;

@FunctionalInterface
public interface Function {

    BigDecimal apply(BigDecimal value, BigDecimal... args);
}
