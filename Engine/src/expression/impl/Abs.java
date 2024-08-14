package expression.impl;

import expression.Expression;

public class Abs extends UnaryExpression<Double>{

    public Abs(){}

    public Abs(Expression<Double> input) {
        super(input);
    }

    @Override
    protected Double dynamicEvaluate(Double input) {
        return Math.abs(input);
    }

    @Override
    public String getOperationSign() {
        return "| |";
    }

    @Override
    public String toString() {
        return "|" + getInput().toString() + "|";
    }
}
