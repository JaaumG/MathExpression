package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.functions.LogarithmFunction;
import dev.joao_guilherme.functions.NaturalLogarithmFunction;
import dev.joao_guilherme.functions.NthRootFunction;
import dev.joao_guilherme.functions.SquareRootFunction;
import dev.joao_guilherme.operators.*;
import dev.joao_guilherme.utils.BigDecimalUtils;
import dev.joao_guilherme.utils.FunctionUtils;

import java.math.BigDecimal;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dev.joao_guilherme.utils.ExpressionUtils.*;
import static dev.joao_guilherme.utils.OperationUtils.applyOperator;

public class ArithmeticExpressionEvaluator implements ExpressionEvaluator {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static final Pattern FUNCTION_PATTERN = Pattern.compile("[a-zA-Z]+");


    public ArithmeticExpressionEvaluator() {
        this.functions.put("sqrt", new SquareRootFunction());
        this.functions.put("nrt", new NthRootFunction());
        this.functions.put("ln" ,new NaturalLogarithmFunction());
        this.functions.put("log", new LogarithmFunction());
        this.operator.put("+", new AdditionOperator());
        this.operator.put("-", new SubtractOperator());
        this.operator.put("/", new DivideOperator());
        this.operator.put("*", new MultiplyOperator());
        this.operator.put("^", new ExponentialOperator());
        this.operator.put("!", new FactorialOperator());
        this.variables.put("e", BigDecimalUtils.E);
        this.variables.put("pi", BigDecimalUtils.PI);
    }

    @Override
    public BigDecimal evaluate(final String expression) {
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

    private static void checkForImplicitMultiplication(Stack<Operator> ops, int i, String expression) {
        if ((i > 0 && (isDigit(expression.charAt(i - 1)) || isClosingBracket(expression.charAt(i - 1)))) || (expression.length() > i + 1 && isOpeningBracket(expression.charAt(i + 1)))) {
            ops.push(new MultiplyOperator());
        }
    }
}
