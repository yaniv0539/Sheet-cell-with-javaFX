package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;

import java.util.Arrays;


public class Concat extends BinaryExpression {

    private Concat(Expression left, Expression right) {
        super(left, right);
        setDataType(DataType.STRING);
        isValidArgs(left, right);
     }

    @Override
    protected Data dynamicEvaluate(Data left, Data right) {

        String result = String.join("",(String) left.getValue(), (String) right.getValue());

        return new DataImpl(DataType.STRING, result);
    }
    @Override
    public boolean isValidArgs(Object... args) {
        boolean value = Arrays
                .stream(args)
                .map(Expression.class::cast)
                .allMatch(arg -> arg.getType() == DataType.STRING);
//        in " + this.getClass().getSimpleName()
        if (!value) {
            //need to throw our own exception.
            throw new IllegalArgumentException("arguments must be string !\n");
        }

        return true;
    }
}




