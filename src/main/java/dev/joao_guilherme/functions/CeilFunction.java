package dev.joao_guilherme.functions;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class CeilFunction implements Function {

    @Override
    public BigDecimal apply(BigDecimal... args) {
        return BigDecimalUtils.ceil(args[0]);
    }
}
