package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;

public class Mod extends BinaryExpression {

    public Mod(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    protected Data dynamicEvaluate(Data left, Data right) {

        return new DataImpl(DataType.NUMERIC,(double)left.getValue() % (double)right.getValue() );
    }

    @Override
    public boolean isValidArgs(Object... args) {
        return false;
    }
    //    @Override
//    public String getOperationSign() {
//        return "%";
//    }
}
