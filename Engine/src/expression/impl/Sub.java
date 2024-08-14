package expression.impl;

import expression.Expression;
import expression.NumericExpression;

public class Sub implements Expression<String> {
    private Expression<String> source;
    private Expression<Double> left;
    private Expression<Double> right;

    public Sub(Expression<String> source, Expression<Double> left, Expression<Double> right) {
        this.source = source;
        this.left = left;
        this.right = right;
    }

    @Override
    public java.lang.String getOperationSign() {
        return "-";
    }

    @Override
    public String evaluate() {

        return source.evaluate().substring(left.evaluate().intValue(), right.evaluate().intValue());
    }

    @Override
    public java.lang.String toString() {
        return  "(" + source.toString() + " " + left.toString() + getOperationSign() + right.toString() + ")";
    }
}
