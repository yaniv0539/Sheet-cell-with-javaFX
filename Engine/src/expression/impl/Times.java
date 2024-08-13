package expression.impl;

import expression.Expression;

public class Times extends BinaryExpression {

    public Times(){}

    public Times(Expression left, Expression right) {
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
