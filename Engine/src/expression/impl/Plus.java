package expression.impl;

import expression.Expression;

public class Plus extends BinaryExpression{

    public Plus() {}

    public Plus(Expression left, Expression right) {
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
