package dev.joao_guilherme.functions;

import java.math.BigDecimal;

public class NaturalLogarithmFunction implements Function {

    @Override
    public BigDecimal apply(BigDecimal value, BigDecimal... args) {
        return BigDecimal.valueOf(Math.log(value.doubleValue()));
    }
}
