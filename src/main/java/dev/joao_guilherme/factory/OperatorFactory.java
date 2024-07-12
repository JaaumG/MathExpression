package dev.joao_guilherme.factory;

import dev.joao_guilherme.operators.*;
import org.reflections.Reflections;

import java.util.Set;

public abstract class OperatorFactory {

    public abstract Operator createOperator(char symbol);

    public abstract boolean isOperator(char symbol);

    public static OperatorFactory getInstance() {
        return new OperatorFactoryImpl();
    }

    private static class OperatorFactoryImpl extends OperatorFactory {

        @Override
        public Operator createOperator(char symbol) {
            Reflections reflections = new Reflections("dev.joao_guilherme.operators");
            Set<Class<? extends Operator>> subclasses = reflections.getSubTypesOf(Operator.class);
            for (Class<? extends Operator> aClass : subclasses.stream().toList()) {
                try {
                    Operator operator = aClass.getDeclaredConstructor().newInstance();
                    if (operator.getSymbol() == symbol) {
                        return operator;
                    }
                } catch (Exception ignore) {}
            }
            return null;
        }

        @Override
        public boolean isOperator(char symbol) {
            Reflections reflections = new Reflections("dev.joao_guilherme.operators");
            Set<Class<? extends Operator>> subclasses = reflections.getSubTypesOf(Operator.class);
            for (Class<? extends Operator> aClass : subclasses.stream().toList()) {
                try {
                    Operator operator = aClass.getDeclaredConstructor().newInstance();
                    if (operator.getSymbol() == symbol) {
                        return true;
                    }
                } catch (Exception ignore) {}
            }
            return false;
        }
    }
}
