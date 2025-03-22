package dev.joao_guilherme.operators;

@FunctionalInterface
public interface BinaryOperation<T> extends Operator<T> {

    @Override
    T apply(T a, T b);
}