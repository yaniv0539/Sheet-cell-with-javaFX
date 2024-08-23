package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;

import java.util.Arrays;

public class Abs extends UnaryExpression {

    public Abs(Expression input) {

        super(input);
        setDataType(DataType.NUMERIC);
        isValidArgs(input);
    }

    @Override
    protected Data dynamicEvaluate(Data input) {

        return new DataImpl(DataType.NUMERIC, Math.abs((double)input.GetValue()));
    }

    @Override
    public boolean isValidArgs(Object... args) {

        boolean value = Arrays
                .stream(args)
                .map(Expression.class::cast)
                .allMatch(arg -> arg.GetType() == DataType.NUMERIC);

        if (!value) {
            //need to throw our own exception.
            throw new IllegalArgumentException("arguments must be numeric in " + this.getClass().getSimpleName());
        }

        return true;
    }
}
