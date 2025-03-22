package dev.joao_guilherme.functions;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;
import java.util.List;

public class LogarithmFunction implements VarArgsFunction<BigDecimal> {

    @Override
    public int maxArgs() {
        return 2;
    }

    @Override
    public BigDecimal apply(List<BigDecimal> args) {
        if (args.size() == 1) {
            return BigDecimalUtils.log(args.getFirst(), BigDecimal.TEN);
        }
        return BigDecimalUtils.log(args.getFirst(), args.getLast());
    }
}
