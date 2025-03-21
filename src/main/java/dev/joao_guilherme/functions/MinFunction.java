package dev.joao_guilherme.functions;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class MinFunction implements Function {

    @Override
    public BigDecimal apply(BigDecimal... args) {
        return BigDecimalUtils.min(args);
    }
}
