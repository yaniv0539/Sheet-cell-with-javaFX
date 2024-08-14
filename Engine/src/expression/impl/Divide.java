package expression.impl;

import expression.Expression;
import expression.NumericExpression;

public class Divide extends BinaryExpression<Double> implements NumericExpression{

    public Divide(){}

    public Divide(Expression<Double> left, Expression<Double> right) {

        super(left, right);
    }
    @Override
    public Double dynamicEvaluate(Double left, Double right) {

        return right == 0 ? Double.NaN : left / right;
    }

    @Override
    public String getOperationSign() {
        return "/";
    }
}
