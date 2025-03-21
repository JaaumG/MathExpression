package dev.joao_guilherme.operators;

import java.math.BigDecimal;

public interface Operator {

    default int getPrecedence() {
        return 0;
    }

    default char getSymbol() {
        return ' ';
    }

    default boolean hasHigherPrecedence(Operator operator) {
        return this.getPrecedence() >= operator.getPrecedence();
    }

    default BigDecimal apply(BigDecimal a, BigDecimal b) {
        throw new IllegalArgumentException("This operator does not support binary operations");
    }

    default BigDecimal apply(BigDecimal a) {
        throw new IllegalArgumentException("This operator does not support unary operations");
    }
}
