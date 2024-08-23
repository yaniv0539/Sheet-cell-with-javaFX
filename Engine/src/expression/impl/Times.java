package expression.impl;
import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;

import java.util.Arrays;

public class Times extends BinaryExpression {

    public Times(Expression left, Expression right) {

        super(left, right);
        setDataType(DataType.NUMERIC);
        isValidArgs(left, right);
    }

    @Override
    protected Data dynamicEvaluate(Data left, Data right) {
        return new DataImpl(DataType.STRING, (double)left.GetValue() * (double)right.GetValue());
    }

    @Override
    public boolean isValidArgs(Object... args) {
        boolean value = Arrays
                .stream(args)
                .map(Expression.class::cast)
                .allMatch(arg -> arg.GetType() == DataType.NUMERIC);

        if (!value) {
            //need to throw our own exception.
            throw new IllegalArgumentException("arguments must be numeric in" + this.getClass().getSimpleName());
        }

        return true;
    }
}
