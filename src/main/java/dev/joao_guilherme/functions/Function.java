package dev.joao_guilherme.functions;

import java.util.List;

public interface Function<T> {

    default int minArgs() {
        return 0;
    }

    default int maxArgs() {
        return Integer.MAX_VALUE;
    }

    default T apply(T a) {
        throw new IllegalArgumentException("Invalid number of arguments: 1");
    }

    default T apply(T a, T b) {
        throw new IllegalArgumentException("Invalid number of arguments: 2");
    }

    default T apply(List<T> args) {
        if (args.size() == 1) {
            return apply(args.getFirst());
        } else if (args.size() == 2) {
            return apply(args.getFirst(), args.getLast());
        } else {
            throw new IllegalArgumentException("Invalid number of arguments: " + args.size());
        }
    }

    default boolean isValidArgsCount(int argsCount) {
        return argsCount >= minArgs() && argsCount <= maxArgs();
    }
}