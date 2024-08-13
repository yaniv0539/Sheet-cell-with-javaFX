package expression.impl;

import expression.Expression;
import expression.NumericExpression;

public class Minus extends BinaryExpression{

    public Minus(){}

    public Minus(NumericExpression left, NumericExpression right) {
        super(left, right);
    }

    @Override
    public String getOperationSign() {
        return "-";
    }

    @Override
    protected double dynamicEvaluate(double left, double right) {
        return left - right;
    }
}
