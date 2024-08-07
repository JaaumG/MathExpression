package dev.joao_guilherme.utils;

import dev.joao_guilherme.evaluators.ExpressionEvaluator;

public abstract class ExpressionUtils {

    public static boolean isMinusSignNegation(String expression, int index, ExpressionEvaluator evaluator) {
        if (index == 0) {
            return true;
        }
        char c = expression.charAt(index - 1);
        if (c == '%') return false; //In case of the last char is a percentage operator then in every way it should consider the minus sign as operator
        return c == '(' || c == '[' || c == '{' || c == ',' || evaluator.isOperator(c);
    }

    public static boolean isCharacter(char c) {
        return Character.isLetter(c);
    }

    public static boolean isDigit(char c) {
        return Character.isDigit(c) || c == '.';
    }

    public static int getIndexClosingBracket(String expression, int indexOpeningBracket) {
        if (indexOpeningBracket < 0 || indexOpeningBracket >= expression.length()) {
            return -1;
        }
        int count = 1;
        for (int i = indexOpeningBracket + 1; i < expression.length(); i++) {
            if (isOpeningBracket(expression.charAt(i))) {
                count++;
            } else if (isClosingBracket(expression.charAt(i))) {
                count--;
            }
            if (count == 0) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isOpeningBracket(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    public static boolean isClosingBracket(char c) {
        return c == ')' || c == ']' || c == '}';
    }
}
