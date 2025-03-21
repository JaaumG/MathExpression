package dev.joao_guilherme.functions;

import java.math.BigDecimal;

public class SignFunction implements UnaryFunction {
    @Override
    public BigDecimal apply(BigDecimal a) {
        var value = a.stripTrailingZeros();
        if (value.compareTo(BigDecimal.ZERO) > 0) {
            return BigDecimal.ONE;
        } else if (value.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.valueOf(-1);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
