package expression.impl;
import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;
import expression.api.NumericExpression;

public class Times extends BinaryExpression implements NumericExpression {

    public Times(){}

    public Times(NumericExpression left, NumericExpression right) {
        super(left, right);
    }

    @Override
    protected Data dynamicEvaluate(Data left, Data right) {
        return new DataImpl(DataType.STRING, (double)left.getValue() * (double)right.getValue());
    }

//    @Override
//    public String getOperationSign() {
//        return "*";
//    }
}
