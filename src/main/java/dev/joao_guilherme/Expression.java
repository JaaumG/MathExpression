package dev.joao_guilherme;

import java.math.BigDecimal;
import java.math.MathContext;

public class Expression {

    private final String expression;
    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    public Expression(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Expression cannot be null");
        }
        this.expression = expression.replaceAll("\\s+", "");
    }

    public String getExpression() {
        return expression;
    }
}
