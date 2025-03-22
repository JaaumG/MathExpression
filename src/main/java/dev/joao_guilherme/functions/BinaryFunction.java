package dev.joao_guilherme.functions;

@FunctionalInterface
public interface BinaryFunction<T> extends Function<T> {

    @Override
    T apply(T a, T b);

    @Override
    default int minArgs() {
        return 2;
    }

    @Override
    default int maxArgs() {
        return 2;
    }
}