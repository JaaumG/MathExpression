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
    protected static final Stack<BigDecimal> values = new Stack<>();
    protected static final Stack<Operator> ops = new Stack<>();
    protected static int currentIndex = 0;


    public ArithmeticExpressionEvaluator() {
        addFunction("sqrt", new SquareRootFunction());
        addFunction("nrt", new NthRootFunction());
        addFunction("exp", new ExponentialFunction());
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

        while (currentIndex < expression.length()) {
            char c = expression.charAt(currentIndex);

            if (isOpeningBracket(c)) {
                int j = getIndexClosingBracket(expression, currentIndex);
                values.push(evaluate(expression.substring(currentIndex + 1, j)));
                currentIndex = j + 1;
            } else if (isDigit(c) || (c == '-' && isMinusSignNegation(expression, currentIndex, this))) {
                checkForImplicitMultiplication(expression);
                Matcher matcher = NUMBER_PATTERN.matcher(expression.substring(currentIndex));
                if (matcher.find()) {
                    String number = matcher.group();
                    values.push(new BigDecimal(number));
                    currentIndex += number.length();
                }
            } else if (isOperator(String.valueOf(c))) {
                Operator op = getOperator(String.valueOf(c));
                if (op instanceof PercentageOperator pop) {
                    this.percentageHandler(pop, expression);
                    currentIndex++;
                    continue;
                }
                while (!ops.empty() && ops.peek().hasHigherPrecedence(op)) applyOperator(ops.pop(), values);
                ops.push(op);
                currentIndex++;
            } else if (isCharacter(c)) {
                Matcher matcher = FUNCTION_PATTERN.matcher(expression.substring(currentIndex));
                if (matcher.find()) {
                    String string = matcher.group();
                    if (isFunction(string)) {
                        checkForImplicitMultiplication(expression);
                        currentIndex += string.length();
                        if (currentIndex < expression.length() && isOpeningBracket(expression.charAt(currentIndex))) {
                            BigDecimal result = FunctionUtils.evaluateFunction(this, expression, currentIndex - string.length(), currentIndex);
                            values.push(result);
                            currentIndex = getIndexClosingBracket(expression, currentIndex) + 1; // Move to the closing bracket index
                        } else {
                            throw new IllegalArgumentException("Invalid function: " + string);
                        }
                    } else if (isVariable(string)) {
                        checkForImplicitMultiplication(expression);
                        currentIndex += string.length();
                        values.push(getVariable(string));
                    } else {
                        throw new IllegalArgumentException("Invalid function: " + string);
                    }
                }
            } else {
                currentIndex++;
            }
        }

        while (!ops.empty()) applyOperator(ops.pop(), values);

        return values.pop().stripTrailingZeros();
    }

    protected static void checkForImplicitMultiplication(String expression) {
        if ((currentIndex > 0 && (isDigit(expression.charAt(currentIndex - 1)) || isClosingBracket(expression.charAt(currentIndex - 1)))) || (expression.length() > currentIndex + 1 && isOpeningBracket(expression.charAt(currentIndex + 1)))) {
            ops.push(new MultiplyOperator());
        }
    }

    protected void percentageHandler(PercentageOperator pop, String expression) {
        values.push(pop.apply(values.pop()));
    }
}
