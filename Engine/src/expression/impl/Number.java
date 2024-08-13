package expression.impl;
import expression.NumericExpression;

public class Number implements NumericExpression {

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
