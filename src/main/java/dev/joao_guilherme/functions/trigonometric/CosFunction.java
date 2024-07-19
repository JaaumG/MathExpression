package dev.joao_guilherme.functions.trigonometric;

import dev.joao_guilherme.functions.Function;
import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class CosFunction implements Function {

    @Override
    public BigDecimal apply(BigDecimal... args) {
        return BigDecimalUtils.cos(args[0]);
    }
}
