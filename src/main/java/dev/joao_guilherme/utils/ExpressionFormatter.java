package dev.joao_guilherme.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class ExpressionFormatter {

    private static final Map<Character, Character> OPERATOR_REPLACEMENTS = new HashMap<>();
    private static final Map<Character, String> SUPERSCRIPT_DIGITS = new HashMap<>();

    static {
        OPERATOR_REPLACEMENTS.put('*', '×');
        OPERATOR_REPLACEMENTS.put('/', '÷');
        SUPERSCRIPT_DIGITS.put('0', "⁰");
        SUPERSCRIPT_DIGITS.put('1', "¹");
        SUPERSCRIPT_DIGITS.put('2', "²");
        SUPERSCRIPT_DIGITS.put('3', "³");
        SUPERSCRIPT_DIGITS.put('4', "⁴");
        SUPERSCRIPT_DIGITS.put('5', "⁵");
        SUPERSCRIPT_DIGITS.put('6', "⁶");
        SUPERSCRIPT_DIGITS.put('7', "⁷");
        SUPERSCRIPT_DIGITS.put('8', "⁸");
        SUPERSCRIPT_DIGITS.put('9', "⁹");
    }

    public static String format(String expression) {
        if (expression == null || expression.isEmpty()) {
            return expression;
        }
        String formattedExpression = replaceOperators(expression);
        return formatExponents(formattedExpression);
    }

    private static String replaceOperators(String expression) {
        StringBuilder result = new StringBuilder();

        for (char c : expression.toCharArray()) {
            if (OPERATOR_REPLACEMENTS.containsKey(c)) {
                result.append(OPERATOR_REPLACEMENTS.get(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    private static String formatExponents(String expression) {
        Pattern pattern = Pattern.compile("\\^(\\d+)");
        Matcher matcher = pattern.matcher(expression);

        StringBuilder result = new StringBuilder();

        while (matcher.find()) {
            String exponent = matcher.group(1);
            StringBuilder superscriptExponent = new StringBuilder();

            for (char digit : exponent.toCharArray()) {
                superscriptExponent.append(SUPERSCRIPT_DIGITS.get(digit));
            }

            matcher.appendReplacement(result, superscriptExponent.toString());
        }

        matcher.appendTail(result);
        return result.toString();
    }
}
