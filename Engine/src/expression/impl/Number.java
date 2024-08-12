package expression.impl;

import expression.Expression;

public class Number implements Expression {

    private double value;

    public Number() {};

    public Number(double value) {
        this.value = value;
    }
    @Override
    public double evaluate() {
        return value;
    }

    @Override
    public String getOperationSign() {
        return "";
    }
}
