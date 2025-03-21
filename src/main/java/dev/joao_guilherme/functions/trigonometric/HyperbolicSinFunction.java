package dev.joao_guilherme.functions.trigonometric;

import dev.joao_guilherme.functions.UnaryFunction;
import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class HyperbolicSinFunction implements UnaryFunction {
    @Override
    public BigDecimal apply(BigDecimal a) {
        return BigDecimalUtils.sinh(a);
    }
}
