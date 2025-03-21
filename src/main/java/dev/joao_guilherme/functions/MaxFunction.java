package dev.joao_guilherme.functions;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class MaxFunction implements VarArgsFunction {

    @Override
    public BigDecimal apply(BigDecimal... args) {
        return BigDecimalUtils.max(args);
    }
}
