package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;


public class Concat extends BinaryExpression {

    private Concat(Expression left, Expression right) {
        super(left, right);
        setDataType(DataType.STRING);
        isValidArgs(left, right);
     }

    @Override
    protected Data dynamicEvaluate(Data left, Data right) {

        String result = String.join("",(String) left.GetValue(), (String) right.GetValue());

        return new DataImpl(DataType.STRING, result);
    }
    @Override
    public boolean isValidArgs(Object... args) {
        return false;
    }
}




