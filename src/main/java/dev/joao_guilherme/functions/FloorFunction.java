package dev.joao_guilherme.functions;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class FloorFunction implements UnaryFunction<BigDecimal> {

    @Override
    public BigDecimal apply(BigDecimal a) {
        return BigDecimalUtils.floor(a);
    }
}
