package expression.impl;

import expression.Expression;
import expression.NumericExpression;

public class Divide extends BinaryExpression {

    public Divide(){}
    public Divide(NumericExpression left, NumericExpression right) {
        super(left, right);
    }
    @Override
    public double dynamicEvaluate(double left, double right) {
        return left / right;
    }

    @Override
    public String getOperationSign() {
        return "/";
    }
}
