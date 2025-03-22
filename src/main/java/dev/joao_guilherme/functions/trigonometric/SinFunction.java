package dev.joao_guilherme.functions.trigonometric;

import dev.joao_guilherme.functions.UnaryFunction;
import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class SinFunction implements UnaryFunction<BigDecimal> {

    @Override
    public BigDecimal apply(BigDecimal a) {
        return BigDecimalUtils.sin(a);
    }
}
