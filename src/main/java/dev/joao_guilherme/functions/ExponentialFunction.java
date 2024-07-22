package dev.joao_guilherme.functions;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class ExponentialFunction implements Function {

    @Override
    public BigDecimal apply(BigDecimal... args) {
        return BigDecimalUtils.exp(args[0]);
    }
}
