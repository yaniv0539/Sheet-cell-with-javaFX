package expression.impl;

import expression.Expression;

public class Divide extends BinaryExpression {

    public Divide(){}
    public Divide(Expression left, Expression right) {
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
