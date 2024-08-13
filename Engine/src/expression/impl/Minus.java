package expression.impl;

import expression.Expression;

public class Minus extends BinaryExpression{

    public Minus(){}

    public Minus(Expression left, Expression right) {
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
