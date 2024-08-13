package expression.impl;

import expression.Expression;

public class Pow extends BinaryExpression{

    public Pow(){}

    public Pow(Expression left, Expression right){
        super(left, right);
    }

    @Override
    public double dynamicEvaluate(double left, double right) {
        return Math.pow(left, right);
    }

    @Override
    public String getOperationSign() {
        return "^";
    }
}
