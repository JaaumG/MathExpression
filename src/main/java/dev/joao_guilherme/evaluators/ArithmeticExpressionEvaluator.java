package dev.joao_guilherme.evaluators;

import dev.joao_guilherme.functions.*;
import dev.joao_guilherme.functions.trigonometric.*;
import dev.joao_guilherme.operators.*;
import dev.joao_guilherme.utils.BigDecimalUtils;
import dev.joao_guilherme.utils.FunctionUtils;
import dev.joao_guilherme.utils.StepHandler;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dev.joao_guilherme.utils.ExpressionUtils.*;
import static dev.joao_guilherme.utils.OperationUtils.applyOperator;

public class ArithmeticExpressionEvaluator extends ExpressionEvaluator {

    protected static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");
    protected static final Pattern FUNCTION_PATTERN = Pattern.compile("[a-zA-Z0-9]+");
    private static final StepHandler STEP_HANDLER = StepHandler.getInstance();
    protected Deque<BigDecimal> values = new ArrayDeque<>();
    protected Deque<Operator> ops = new ArrayDeque<>();
    protected int currentIndex = 0;
    protected String expression;


    public ArithmeticExpressionEvaluator() {
        addFunction("sqrt", new SquareRootFunction());
        addFunction("nrt", new NthRootFunction());
        addFunction("exp", new ExponentialFunction());
        addFunction("ln", new NaturalLogarithmFunction());
        addFunction("log", new LogarithmFunction());
        addFunction("sin", new SinFunction());
        addFunction("cos", new CosFunction());
        addFunction("tan", new TanFunction());
        addFunction("sinh", new HyperbolicSinFunction());
        addFunction("cosh", new HyperbolicCosFunction());
        addFunction("tanh", new HyperbolicTanFunction());
        addFunction("asin", new InverseSinFunction());
        addFunction("acos", new InverseCosFunction());
        addFunction("atan", new InverseTanFunction());
        addFunction("abs", new AbsoluteFunction());
        addFunction("ceil", new CeilFunction());
        addFunction("floor", new FloorFunction());
        addFunction("sign", new SignFunction());
        addFunction("max", new MaxFunction());
        addFunction("min", new MinFunction());
        addFunction("round", new RoundFunction());

        addOperator(new AdditionOperator());
        addOperator(new SubtractOperator());
        addOperator(new DivideOperator());
        addOperator(new MultiplyOperator());
        addOperator(new ExponentialOperator());
        addOperator(new FactorialOperator());
        addOperator(new PercentageOperator());
        addVariable("e", BigDecimalUtils.E);
        addVariable("pi", BigDecimalUtils.PI);
    }

    @Override
    public BigDecimal evaluate(final String expressionToEvaluate) {
        if (expressionToEvaluate == null || expressionToEvaluate.isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be null or empty");
        }
        STEP_HANDLER.addStep(expressionToEvaluate);
        validateAndInitializeStack(expressionToEvaluate);

        while (currentIndex < expression.length()) {
            char c = expression.charAt(currentIndex);

            if (isOpeningBracket(c)) {
                solveInnerExpression();
            } else if (isNumber(c)) {
                getNumber();
            } else if (isOperator(c)) {
                getAndApplyOperator(c);
            } else if (isCharacter(c)) {
                evaluateString();
            }
        }

        while (!ops.isEmpty()) {
            Operator nextOp = ops.pop();
            applyOperator(nextOp, values);
            STEP_HANDLER.addStep(values.peek());
        }
        BigDecimal bigDecimal = values.pop().stripTrailingZeros();
        STEP_HANDLER.addStep(bigDecimal);
        return bigDecimal;
    }

    protected void checkForImplicitMultiplication() {
        int nextCharacterIndex = currentIndex + 1;
        int previousCharacterIndex = currentIndex - 1;

        boolean isNotFirstCharacter = currentIndex > 0;
        boolean isNotLastCharacter = expression.length() > nextCharacterIndex;

        boolean isPreviousCharacterDigit = false;
        boolean isPreviousCharacterClosingBracket = false;

        if (isNotFirstCharacter) {
            isPreviousCharacterDigit = isDigit(expression.charAt(previousCharacterIndex));
            isPreviousCharacterClosingBracket = isClosingBracket(expression.charAt(previousCharacterIndex));
        }


        boolean isNextCharacterOpeningBracket = false;
        boolean isNextCharacterDigit = false;

        if (isNotLastCharacter) {
            isNextCharacterOpeningBracket = isOpeningBracket(expression.charAt(nextCharacterIndex));
            isNextCharacterDigit = isDigit(expression.charAt(nextCharacterIndex));
        }

        if (isNextCharacterDigit) {
            Matcher matcher = NUMBER_PATTERN.matcher(expression.substring(currentIndex));
            if (matcher.find()) {
                String number = matcher.group();
                boolean isWithinString = currentIndex + number.length() < expression.length();
                isNextCharacterOpeningBracket = isWithinString && isOpeningBracket(expression.charAt(currentIndex + number.length()));
            }
        }
        if (isPreviousCharacterDigit || isPreviousCharacterClosingBracket || isNextCharacterOpeningBracket) {
            ops.push(new MultiplyOperator());
        }
    }

    protected void percentageHandler(PercentageOperator pop) {
        if (pop == null) {
            throw new IllegalArgumentException("Invalid percentage operator");
        }
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Invalid expression: " + expression);
        }
        values.push(pop.apply(values.pop()));
    }

    private boolean isNumber(char c) {
        return isDigit(c) || (c == '-' && isMinusSignNegation(expression, currentIndex, this));
    }

    private void validateAndInitializeStack(String expressionToEvaluate) {
        if (expressionToEvaluate == null) {
            throw new IllegalArgumentException("Expression cannot be null");
        }
        currentIndex = 0;
        ops = new ArrayDeque<>();
        values = new ArrayDeque<>();
        this.expression = expressionToEvaluate;
    }

    private void solveInnerExpression() {
        int j = getIndexClosingBracket(expression, currentIndex);
        BigDecimal value = newInstance().evaluate(expression.substring(currentIndex + 1, j));
        values.push(value);
        STEP_HANDLER.addStep(value);
        currentIndex = j + 1;
    }

    private void getNumber() {
        checkForImplicitMultiplication();
        Matcher matcher = NUMBER_PATTERN.matcher(expression.substring(currentIndex));
        if (matcher.find()) {
            String number = matcher.group();
            values.push(new BigDecimal(number));
            currentIndex += number.length();
        }
    }

    private void getAndApplyOperator(char c) {
        Operator op = getOperator(c);
        if (op instanceof PercentageOperator pop) {
            this.percentageHandler(pop);
        } else {
            while (!ops.isEmpty() && ops.peek().hasHigherPrecedence(op)) {
                Operator nextOp = ops.pop();
                applyOperator(nextOp, values);
                STEP_HANDLER.addStep(values.peek());
            }
            ops.push(op);
        }
        currentIndex++;
    }

    private void evaluateString() {
        Matcher matcher = FUNCTION_PATTERN.matcher(expression.substring(currentIndex));
        if (matcher.find()) {
            String string = matcher.group();
            if (isFunction(string)) {
                applyFunction(string);
            } else if (isVariable(string)) {
                getVariableValue(string);
            } else {
                throw new IllegalArgumentException("Invalid String: " + string);
            }
        }
    }

    private void getVariableValue(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Invalid variable: " + string);
        }
        checkForImplicitMultiplication();
        currentIndex += string.length();
        values.push(getVariable(string));
    }

    private void applyFunction(String string) {
        if (string == null) {
            throw new IllegalArgumentException("Invalid function: " + string);
        }
        checkForImplicitMultiplication();
        currentIndex += string.length();
        if (currentIndex < expression.length() && isOpeningBracket(expression.charAt(currentIndex))) {
            BigDecimal result = FunctionUtils.evaluateFunction(this, expression, currentIndex - string.length(), currentIndex);
            values.push(result);
            currentIndex = getIndexClosingBracket(expression, currentIndex) + 1; // Move to the closing bracket index
        } else {
            throw new IllegalArgumentException("Invalid function: " + string);
        }
    }
}
