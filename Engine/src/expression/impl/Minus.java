package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;

import java.util.Arrays;

public class Minus extends BinaryExpression  {

    public Minus(Expression left, Expression right) {
        super(left, right);
        setDataType(DataType.NUMERIC);
        isValidArgs(left, right);
    }

    @Override
    protected Data dynamicEvaluate(Data left, Data right) {
        return new DataImpl(DataType.NUMERIC,(double)left.getValue() - (double)right.getValue() );
    }

    @Override
    public boolean isValidArgs(Object... args) {
        boolean value = Arrays
                .stream(args)
                .map(Expression.class::cast)
                .allMatch(arg -> arg.getType() == DataType.NUMERIC);

        if (!value) {
            //need to throw our own exception.
            throw new IllegalArgumentException("arguments must be numeric in " + this.getClass().getSimpleName());
        }

        return true;
    }

    //    @Override
//    public String getOperationSign() {
//        return "-";
//    }

}
