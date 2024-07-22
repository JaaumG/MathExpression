package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.functions.*;
import dev.joao_guilherme.functions.trigonometric.CosFunction;
import dev.joao_guilherme.functions.trigonometric.SinFunction;
import dev.joao_guilherme.functions.trigonometric.TanFunction;
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

    protected static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
    protected static final Pattern FUNCTION_PATTERN = Pattern.compile("[a-zA-Z0-9]+");


    public ArithmeticExpressionEvaluator() {
        addFunction("sqrt", new SquareRootFunction());
        addFunction("nrt", new NthRootFunction());
        addFunction("ln", new NaturalLogarithmFunction());
        addFunction("log", new LogarithmFunction());
        addFunction("sin", new SinFunction());
        addFunction("cos", new CosFunction());
        addFunction("tan", new TanFunction());
        addFunction("abs", new AbsoluteFunction());
        addFunction("ceil", new CeilFunction());
        addFunction("floor", new FloorFunction());

        addOperator("+", new AdditionOperator());
        addOperator("-", new SubtractOperator());
        addOperator("/", new DivideOperator());
        addOperator("*", new MultiplyOperator());
        addOperator("^", new ExponentialOperator());
        addOperator("!", new FactorialOperator());
        addOperator("%", new PercentageOperator());
        addVariable("e", BigDecimalUtils.E);
        addVariable("pi", BigDecimalUtils.PI);
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

    protected static void checkForImplicitMultiplication(Stack<Operator> ops, int i, String expression) {
        if ((i > 0 && (isDigit(expression.charAt(i - 1)) || isClosingBracket(expression.charAt(i - 1)))) || (expression.length() > i + 1 && isOpeningBracket(expression.charAt(i + 1)))) {
            ops.push(new MultiplyOperator());
        }
    }
}
