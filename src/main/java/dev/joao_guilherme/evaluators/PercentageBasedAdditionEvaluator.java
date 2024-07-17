package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.operators.Operator;
import dev.joao_guilherme.operators.PercentageOperator;
import dev.joao_guilherme.utils.FunctionUtils;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.regex.Matcher;

import static dev.joao_guilherme.utils.ExpressionUtils.*;
import static dev.joao_guilherme.utils.OperationUtils.applyOperator;

public class PercentageBasedAdditionEvaluator extends ArithmeticExpressionEvaluator {

    @Override
    public BigDecimal evaluate(String expression) {
        if (expression == null) throw new IllegalArgumentException("Expression cannot be null");

        Stack<BigDecimal> values = new Stack<>();
        Stack<Operator> ops = new Stack<>();
        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);

            if (isOpeningBracket(c)) {
                int j = getIndexClosingBracket(expression, i);
                values.push(evaluate(expression.substring(i + 1, j)));
                i = j + 1;
            } else if (isDigit(c) || (c == '-' && isMinusSignNegation(expression, i, this))) {
                checkForImplicitMultiplication(ops, i, expression);
                Matcher matcher = NUMBER_PATTERN.matcher(expression.substring(i));
                if (matcher.find()) {
                    String number = matcher.group();
                    values.push(new BigDecimal(number));
                    i += number.length();
                }
            } else if (isOperator(String.valueOf(c))) {
                Operator op = getOperator(String.valueOf(c));
                if (op instanceof PercentageOperator pop) {
                    if (values.isEmpty()) throw new IllegalArgumentException("Invalid expression: " + expression);
                    if (i + 1 < expression.length() && (expression.charAt(i + 1) == '*' || expression.charAt(i + 1) == '/' || expression.charAt(i + 1) == '(')){
                        i++;
                        values.push(pop.apply(values.pop()));
                        continue;
                    } else
                        pop.applyImplicitPercentageOperator(values, ops);
                    i++;
                    continue;
                }
                while (!ops.empty() && ops.peek().hasHigherPrecedence(op)) applyOperator(ops.pop(), values);
                ops.push(op);
                i++;
            } else if (isCharacter(c)) {
                Matcher matcher = FUNCTION_PATTERN.matcher(expression.substring(i));
                if (matcher.find()) {
                    String string = matcher.group();
                    if (isFunction(string)) {
                        checkForImplicitMultiplication(ops, i, expression);
                        i += string.length();
                        if (i < expression.length() && isOpeningBracket(expression.charAt(i))) {
                            BigDecimal result = FunctionUtils.evaluateFunction(this, expression, i - string.length(), i);
                            values.push(result);
                            i = getIndexClosingBracket(expression, i) + 1; // Move to the closing bracket index
                        } else {
                            throw new IllegalArgumentException("Invalid function: " + string);
                        }
                    } else if (isVariable(string)) {
                        checkForImplicitMultiplication(ops, i, expression);
                        i += string.length();
                        values.push(getVariable(string));
                    } else {
                        throw new IllegalArgumentException("Invalid function: " + string);
                    }
                }
            } else {
                i++;
            }
        }

        while (!ops.empty()) applyOperator(ops.pop(), values);

        return values.pop().stripTrailingZeros();
    }
}
