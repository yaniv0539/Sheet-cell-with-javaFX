package expression.impl;

import expression.Expression;
import expression.NumericExpression;

public class Times extends BinaryExpression {

    public Times(){}

    public Times(NumericExpression left, NumericExpression right) {
        super(left, right);
    }
    @Override
    public double dynamicEvaluate(double left, double right) {
        return left * right;
    }

    @Override
    public String getOperationSign() {
        return "*";
    }
}
