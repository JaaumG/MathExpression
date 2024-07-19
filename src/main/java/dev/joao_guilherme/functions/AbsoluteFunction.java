package dev.joao_guilherme.functions;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class AbsoluteFunction implements Function {

    @Override
    public BigDecimal apply(BigDecimal... args) {
        return BigDecimalUtils.abs(args[0]);
    }
}
