package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;
import expression.api.NumericExpression;

public class Pow extends BinaryExpression implements NumericExpression{

    public Pow(){}

    public Pow(NumericExpression left, NumericExpression right){
        super(left, right);
    }

    @Override
    protected Data dynamicEvaluate(Data left, Data right) {
        return new DataImpl(DataType.NUMERIC, Math.pow((double)left.getValue(), (double)right.getValue()));
    }

//    @Override
//    public String getOperationSign() {
//        return "^";
//    }
}
