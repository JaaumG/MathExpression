package dev.joao_guilherme.operators;

public interface Operator {

    default int getPrecedence() {
        return 0;
    }

    default boolean hasHigherPrecedence(Operator operator) {
        return this.getPrecedence() > operator.getPrecedence();
    }
}
