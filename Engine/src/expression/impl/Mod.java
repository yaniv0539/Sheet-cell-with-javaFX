package expression.impl;

import expression.Expression;

public class Mod extends BinaryExpression{

    public Mod(){}

    public Mod(Expression left, Expression right) {
        super(left, right);
    }
    @Override
    public double dynamicEvaluate(double left, double right) {
        return left % right;
    }

    @Override
    public String getOperationSign() {
        return "%";
    }
}
