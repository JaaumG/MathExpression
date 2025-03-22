package dev.joao_guilherme.operators;

public interface Operator<T> {

    default int getPrecedence() {
        return 0;
    }

    default char getSymbol() {
        return ' ';
    }

    default boolean hasHigherPrecedence(Operator<T> operator) {
        return this.getPrecedence() >= operator.getPrecedence();
    }

    default T apply(T a, T b) {
        throw new IllegalArgumentException("This operator does not support binary operations");
    }

    default T apply(T a) {
        throw new IllegalArgumentException("This operator does not support unary operations");
    }
}