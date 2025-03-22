package dev.joao_guilherme.functions;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;
import java.util.List;

public class MaxFunction implements VarArgsFunction<BigDecimal> {

    @Override
    public BigDecimal apply(List<BigDecimal> args) {
        return BigDecimalUtils.max(args);
    }
}
