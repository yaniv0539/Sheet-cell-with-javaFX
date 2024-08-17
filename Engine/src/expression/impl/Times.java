package expression.impl;
import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;

public class Times extends BinaryExpression {

    public Times(Expression left, Expression right) {

        super(left, right);
        setDataType(DataType.NUMERIC);
        isValidArgs(left, right);
    }

    @Override
    protected Data dynamicEvaluate(Data left, Data right) {
        return new DataImpl(DataType.STRING, (double)left.getValue() * (double)right.getValue());
    }

    @Override
    public boolean isValidArgs(Object... args) {
        return false;
    }
}
