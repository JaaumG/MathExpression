package dev.joao_guilherme.functions;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public class LogarithmFunction implements VarArgsFunction {

    @Override
    public int maxArgs() {
        return 2;
    }

    @Override
    public BigDecimal apply(BigDecimal... args) {
        if (args.length == 1) {
            return BigDecimalUtils.log(args[0], BigDecimal.TEN);
        }
        return BigDecimalUtils.log(args[0], args[1]);
    }
}
