package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;
import expression.api.NumericExpression;

public class Divide extends BinaryExpression implements NumericExpression{

    public Divide(){}

    public Divide(NumericExpression left, NumericExpression right) {

        super(left, right);
    }

    @Override
    public Data dynamicEvaluate(Data left, Data right) {

        return (double)right.getValue() == 0 ? new DataImpl(DataType.NUMERIC,Double.NaN) :
                new DataImpl(DataType.NUMERIC,(double)left.getValue() / (double)right.getValue());
    }

//    @Override
//    public String getOperationSign() {
//        return "/";
//    }
}
