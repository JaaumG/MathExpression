package dev.joao_guilherme.evaluators;

import java.math.BigDecimal;

public abstract class ExpressionEvaluator {

    public abstract BigDecimal evaluate(String expression);
}
