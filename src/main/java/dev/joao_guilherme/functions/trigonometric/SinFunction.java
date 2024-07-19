package dev.joao_guilherme.functions.trigonometric;

import dev.joao_guilherme.functions.Function;
import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class SinFunction implements Function {
    @Override
    public BigDecimal apply(BigDecimal... args) {
        return BigDecimalUtils.sin(args[0]);
    }
}
