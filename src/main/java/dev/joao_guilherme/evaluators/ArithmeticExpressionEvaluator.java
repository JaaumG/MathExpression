package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.factory.OperatorFactory;
import dev.joao_guilherme.operators.*;

import java.math.BigDecimal;
import java.util.Stack;

import static dev.joao_guilherme.utils.ExpressionUtils.*;

public class ArithmeticExpressionEvaluator extends ExpressionEvaluator {

    private static Stack<BigDecimal> VALUES = new Stack<>();
    private static Stack<Operator> OPS = new Stack<>();
    private static final OperatorFactory operatorFactory = OperatorFactory.getInstance();

    @Override
    public BigDecimal evaluate(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Expression cannot be null");
        }
        VALUES = new Stack<>();
        OPS = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (isOpeningBracket(c)) {
                int j = getIndexClosingBracket(expression, i);
                VALUES.push(evaluate(expression.substring(i + 1, j)));
                i = j;
            } else if (isDigit(c) || (c == '-' && isMinusSignNegation(expression, i))) {
                int start = i++;
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    i++;
                }
                VALUES.push(new BigDecimal(expression.substring(start, i--)));;
            } else if (OperatorFactory.getInstance().isOperator(c)) {
                Operator op = operatorFactory.createOperator(c);
                while (!OPS.empty() && OPS.peek().hasHigherPrecedence(op)) {
                    applyOperator();
                }
                OPS.push(op);
            }
        }

        while (!OPS.empty()) {
            applyOperator();
        }

        return VALUES.pop();
    }

    private void applyOperator() {
        Operator lastOp = OPS.pop();
        switch (lastOp) {
            case UnaryOperation uOp -> VALUES.push(uOp.apply(VALUES.pop()));
            case BinaryOperation bOp -> VALUES.push(bOp.apply(VALUES.pop(), VALUES.isEmpty() ? BigDecimal.ZERO : VALUES.pop()));
            default -> throw new IllegalStateException("Unexpected value: " + lastOp);
        }
    }
}
