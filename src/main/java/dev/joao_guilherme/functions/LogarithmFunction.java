package dev.joao_guilherme.functions;

import java.math.BigDecimal;

public class LogarithmFunction implements Function {

    @Override
    public BigDecimal apply(BigDecimal value, BigDecimal... args) {
        BigDecimal base = args[0];
        return BigDecimal.valueOf(Math.log(value.doubleValue()) / Math.log(base.doubleValue()));
    }
}
