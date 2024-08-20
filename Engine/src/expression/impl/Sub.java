package expression.impl;

import expression.api.*;

import java.util.Arrays;
import java.util.stream.Stream;

public class Sub extends ExpressionImpl {
    private Expression source;
    private Expression left;
    private Expression right;

    public Sub(Expression source, Expression left, Expression right) {

        this.source = source;
        this.left = left;
        this.right = right;
        setDataType(DataType.STRING);
        isValidArgs(source, left, right);
    }

    @Override
    public Data evaluate() {

        String str = (String)source.evaluate().getValue();
        Double first = (Double)left.evaluate().getValue();
        Double last = (Double)right.evaluate().getValue();

        return new DataImpl(DataType.STRING,str.substring(first.intValue(), last.intValue()));
    }

    @Override
    public boolean isValidArgs(Object... args) {

        boolean condition1 = Arrays.stream(args)
                .map(Expression.class::cast)
                .limit(1)
                .allMatch(arg -> arg.getType() == DataType.STRING);

        boolean condition2 = Arrays.stream(args)
                        .map(Expression.class::cast)
                        .skip(1)
                        .allMatch(arg -> arg.getType() == DataType.NUMERIC);

        if (!(condition1 && condition2)) {
            //need to throw our own exception.
            throw new IllegalArgumentException("first argument must be string and other numeric in " + this.getClass().getSimpleName());
        }

        return true;
    }
}
