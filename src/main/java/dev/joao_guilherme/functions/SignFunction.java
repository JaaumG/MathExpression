package dev.joao_guilherme.functions;

import java.math.BigDecimal;

public class SignFunction implements Function {
    @Override
    public BigDecimal apply(BigDecimal... args) {
        var value = args[0].stripTrailingZeros();
        if (value.compareTo(BigDecimal.ZERO) > 0) {
            return BigDecimal.ONE;
        } else if (value.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.valueOf(-1);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
