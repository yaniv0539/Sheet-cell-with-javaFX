package expression.impl;
import expression.NumericExpression;

public abstract class BinaryExpression implements NumericExpression {

    private NumericExpression left;
    private NumericExpression right;

    public BinaryExpression() {}
    public BinaryExpression(NumericExpression left, NumericExpression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate() {
        return dynamicEvaluate(left.evaluate(), right.evaluate());
    }

    @Override
    public String toString()
    {
        return "(" + left.toString() + getOperationSign() + right.toString() +")";
    }

    protected abstract double dynamicEvaluate(double left, double right);
}
