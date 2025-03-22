package dev.joao_guilherme.functions;


@FunctionalInterface
public interface UnaryFunction<T> extends Function<T> {

    @Override
    T apply(T a);

    @Override
    default int minArgs() {
        return 1;
    }

    @Override
    default int maxArgs() {
        return 1;
    }
}