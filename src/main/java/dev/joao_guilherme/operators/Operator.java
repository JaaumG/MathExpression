package dev.joao_guilherme.operators;

public abstract class Operator {

    protected final int precedence;
    protected final char symbol;

    public Operator(int precedence, char symbol) {
        this.precedence = precedence;
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public final boolean hasHigherPrecedence(Operator other) {
        return precedence > other.precedence;
    }
}
