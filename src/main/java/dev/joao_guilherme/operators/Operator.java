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
        return this.getPrecedence() > operator.getPrecedence();
    }

    default BigDecimal apply(BigDecimal a, BigDecimal b) {
        return BigDecimal.ZERO;
    }

    default BigDecimal apply(BigDecimal a) {
        return BigDecimal.ZERO;
    }
}
