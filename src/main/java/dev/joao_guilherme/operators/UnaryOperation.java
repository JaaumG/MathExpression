package dev.joao_guilherme.operators;

@FunctionalInterface
public interface UnaryOperation<T> extends Operator<T> {

    @Override
    T apply(T a);
}