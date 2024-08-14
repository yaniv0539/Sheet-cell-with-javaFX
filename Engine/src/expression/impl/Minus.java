package expression.impl;

import expression.Expression;
import expression.NumericExpression;

public class Minus extends BinaryExpression<Double> implements NumericExpression{

    public Minus(){}

    public Minus(Expression<Double> left, Expression<Double> right) {
        super(left, right);
    }

    @Override
    protected Double dynamicEvaluate(Double left, Double right) {
        return left - right;
    }

    @Override
    public String getOperationSign() {
        return "-";
    }

}
