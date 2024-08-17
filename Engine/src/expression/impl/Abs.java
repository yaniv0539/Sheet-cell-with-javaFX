package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;

public class Abs extends UnaryExpression {

    public Abs(Expression input) {

        super(input);
        setDataType(DataType.NUMERIC);
        isValidArgs(input);
    }

    @Override
    protected Data dynamicEvaluate(Data input) {

        return new DataImpl(DataType.NUMERIC, Math.abs((double)input.getValue()));
    }

    @Override
    public boolean isValidArgs(Object... args) {
        return false;
    }

}
