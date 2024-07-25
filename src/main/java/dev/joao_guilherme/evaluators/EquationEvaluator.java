package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.utils.BigDecimalUtils;

import java.math.BigDecimal;

public abstract class EquationEvaluator {

    private static String variable;

    public static BigDecimal solve(String equation, String variable, ExpressionEvaluator evaluator) {
        EquationEvaluator.variable = variable;
        String[] sides = equation.replaceAll("\\s+", "").split("=");
        if (sides.length != 2) throw new IllegalArgumentException("Equation must contain exactly one '=' character");

        return solveEquation(sides[0], sides[1], evaluator);
    }

    private static BigDecimal solveEquation(String left, String right, ExpressionEvaluator evaluator) {
        return findRoot(left + "-(" + right + ")", BigDecimalUtils.valueOf("1E-25"), evaluator);
    }

    private static BigDecimal findRoot(String expression, BigDecimal tolerance, ExpressionEvaluator evaluator) {
        BigDecimal x = BigDecimal.ONE;
        for (int i = 0; i < 1000; i++) {
            BigDecimal fValue = evaluator.evaluate(expression.replace(variable, x.toPlainString()));
            BigDecimal fDerivative = evaluateDerivative(expression, variable, x, evaluator);
            if (fDerivative.compareTo(BigDecimal.ZERO) == 0) break;
            BigDecimal nextX = BigDecimalUtils.subtract(x, BigDecimalUtils.divide(fValue, fDerivative));
            if (BigDecimalUtils.abs(BigDecimalUtils.subtract(nextX, x)).compareTo(tolerance) < 0) return nextX.stripTrailingZeros();
            x = nextX;
        }
        throw new ArithmeticException("Unable to find root within tolerance");
    }
}
