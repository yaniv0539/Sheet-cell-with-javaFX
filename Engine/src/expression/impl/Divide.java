package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;

public class Divide extends BinaryExpression {


    public Divide(Expression left, Expression right) {
        super(left, right);
        setDataType(DataType.NUMERIC);
        isValidArgs(left, right);
    }

    @Override
    public Data dynamicEvaluate(Data left, Data right) {

        return (double)right.getValue() == 0 ? new DataImpl(DataType.NUMERIC,Double.NaN) :
                new DataImpl(DataType.NUMERIC,(double)left.getValue() / (double)right.getValue());
    }

    @Override
    public boolean isValidArgs(Object... args) {
        return false;
    }

    //    @Override
//    public String getOperationSign() {
//        return "/";
//    }
}
