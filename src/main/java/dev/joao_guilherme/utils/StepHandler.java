package dev.joao_guilherme.utils;


import dev.joao_guilherme.evaluators.ArithmeticExpressionEvaluator;
import dev.joao_guilherme.evaluators.ExpressionEvaluator;
import dev.joao_guilherme.operators.BinaryOperation;
import dev.joao_guilherme.operators.Operator;
import dev.joao_guilherme.operators.UnaryOperation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class StepHandler {

    private final List<String> steps;
    private static volatile StepHandler instance;
    private static ExpressionEvaluator evaluator = new ArithmeticExpressionEvaluator(); //Base evaluator for steps

    private StepHandler(final List<String> steps) {
        this.steps = steps;
    }

    public static void setEvaluator(ExpressionEvaluator evaluator) {
        StepHandler.evaluator = evaluator;
    }
    public static StepHandler getInstance() {
        if (instance == null) {
            instance = new StepHandler(new ArrayList<>());
        }
        return instance;
    }

    public static void clear() {
        getInstance().steps.clear();
    }

    public void addStep(UnaryOperation operation, BigDecimal value) {
        addStep(operation.getSymbol() + " " + value.stripTrailingZeros().toPlainString());
    }

    public void addStep(BinaryOperation operation, BigDecimal first, BigDecimal second) {
        addStep(first.stripTrailingZeros().toPlainString() + " " + operation.getSymbol() + " " + second.stripTrailingZeros().toPlainString());
    }

    public void addStep(BigDecimal value) {
        addStep(value.stripTrailingZeros().toPlainString());
    }

    public void addStep(String step) {
        String removedWhiteSpace = step.replace(" ", "");
        boolean isLastStepFunction = !steps.isEmpty() && evaluator.isFunction(getFunctionNameFromStep(steps.getLast()));
        boolean isCurrentStepFunctionParameter = isLastStepFunction && steps.getLast().substring(steps.getLast().indexOf('(') + 1, steps.getLast().indexOf(')')).equals(removedWhiteSpace);
        boolean isCurrentStepSameAsLast = removedWhiteSpace.equals(steps.isEmpty() ? "" : steps.getLast());
        if (!isCurrentStepSameAsLast && !isCurrentStepFunctionParameter) {
            steps.add(removedWhiteSpace);
        }
    }

    private String getFunctionNameFromStep(String step) {
        return step.substring(0, steps.getLast().indexOf('(') == -1 ? steps.getLast().length() : steps.getLast().indexOf('('));
    }

    public List<String> getSteps() {
        return steps;
    }
}
