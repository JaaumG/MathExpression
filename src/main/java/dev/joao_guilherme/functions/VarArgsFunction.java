package dev.joao_guilherme.functions;

import java.util.List;

@FunctionalInterface
public interface VarArgsFunction<T> extends Function<T> {

    @Override
    T apply(List<T> args);
}