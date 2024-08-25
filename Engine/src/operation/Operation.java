package operation;

import exception.InvalidFunctionArgument;
import expression.api.Expression;
import expression.impl.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public enum Operation {


    PLUS(BinaryExpression.numberOfArgs)
        {
            @Override
           public Expression create(Object... args)
            {
                return (Expression) createInstance(Plus.class, getNumberOfArguments(),args);
            }
        },

    MINUS(BinaryExpression.numberOfArgs)
        {
            @Override
            public Expression create(Object... args)
            {
                return (Expression) createInstance(Minus.class, getNumberOfArguments(),args);
            }
        },

    TIMES(BinaryExpression.numberOfArgs){
        @Override
        public Expression create(Object... args) {
            return  (Expression) createInstance(Minus.class, getNumberOfArguments(),args);
        }
    },

    DIVIDE(BinaryExpression.numberOfArgs){
        @Override
        public Expression create(Object... args) {
            return (Expression) createInstance(Divide.class, getNumberOfArguments(),args);
        }
    },
    CONCAT(BinaryExpression.numberOfArgs){
        @Override
        public Expression create(Object... args) {
            return (Expression) createInstance(Concat.class, getNumberOfArguments(),args);
        }
    },
    POW(BinaryExpression.numberOfArgs){
        @Override
        public Expression create(Object... args) {
            return (Expression) createInstance(Pow.class, getNumberOfArguments(),args);
        }
    },
    MOD(BinaryExpression.numberOfArgs){
        @Override
        public Expression create(Object... args) {
            return (Expression) createInstance(Mod.class, getNumberOfArguments(),args);
        }
    },
    SUB(3){
        @Override
        public Expression create(Object... args) {
            return (Expression) createInstance(Sub.class, getNumberOfArguments(),args);
        }
    },
    ABS(UnaryExpression.numberOfArgs){
        @Override
        public Expression create(Object... args) {
            return (Expression) createInstance(Abs.class, getNumberOfArguments(),args);
        }
    },
    REF(UnaryExpression.numberOfArgs){
        @Override
        public Expression create(Object... args) {
            return (Expression) createInstance(Ref.class, getNumberOfArguments(),args);
        }
    };

    private int numberOfArguments;

    Operation (int args) {
        this.numberOfArguments = args;
    }

    private static Object createInstance(Class<?> clazz, int numberOfArgs, Object... args) {

        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(constructor -> constructor.getParameterCount() == numberOfArgs)
                .findFirst()
                .map(constructor -> {
                    try {
                        constructor.setAccessible(true);
                        return constructor.newInstance(args);
                    } catch (InstantiationException e) {
                        InvalidFunctionArgument funcError =  new InvalidFunctionArgument(valueOf(clazz.getSimpleName().toUpperCase()), List.of(args));
                        funcError.initCause(e);
                        throw funcError;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        InvalidFunctionArgument funcError =  new InvalidFunctionArgument(valueOf(clazz.getSimpleName().toUpperCase()), List.of(args));
                        funcError.initCause(e);
                        throw funcError;
                    }
                })
                .get();

    }

    public int getNumberOfArguments() {
        return numberOfArguments;
    }

    public abstract Expression create(Object... args);
}
