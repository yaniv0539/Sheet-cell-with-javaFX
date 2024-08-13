package expression.impl;

import expression.Expression;
import expression.NumericExpression;

public class Plus extends BinaryExpression{

    public Plus() {}

    public Plus(NumericExpression left, NumericExpression right) {
        super(left, right);
    }

    @Override
    protected double dynamicEvaluate(double left, double right) {
        return left + right;
    }

    @Override
    public String getOperationSign() {
        return "+";
    }
}
