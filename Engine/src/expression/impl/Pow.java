package expression.impl;

import expression.Expression;
import expression.NumericExpression;

public class Pow extends BinaryExpression<Double> implements NumericExpression{

    public Pow(){}

    public Pow(Expression<Double> left, Expression<Double> right){
        super(left, right);
    }

    @Override
    protected Double dynamicEvaluate(Double left, Double right) {
        return Math.pow(left, right);
    }

    @Override
    public String getOperationSign() {
        return "^";
    }
}
