package dev.joao_guilherme.functions;

import java.math.BigDecimal;

public sealed interface Function permits UnaryFunction, BinaryFunction, VarArgsFunction {

    default int minArgs() {
        return 0;
    }

    default int maxArgs() {
        return Integer.MAX_VALUE;
    }

    default BigDecimal apply(BigDecimal a) {
        throw new IllegalArgumentException("Invalid number of arguments: 1");
    }

    default BigDecimal apply(BigDecimal a, BigDecimal b) {
        throw new IllegalArgumentException("Invalid number of arguments: 2");
    }

    default BigDecimal apply(BigDecimal... args) {
        if (args.length == 1) {
            return apply(args[0]);
        } else if (args.length == 2) {
            return apply(args[0], args[1]);
        } else {
            throw new IllegalArgumentException("Invalid number of arguments: " + args.length);
        }
    }

    default boolean isValidArgsCount(int argsCount) {
        return argsCount >= minArgs() && argsCount <= maxArgs();
    }
}
