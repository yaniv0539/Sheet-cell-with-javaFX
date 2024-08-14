package expression.impl;
import expression.Expression;
import expression.NumericExpression;

public abstract class BinaryExpression<T> implements Expression<T> {

    private Expression<T> left;
    private Expression<T> right;

    public BinaryExpression() {}
    public BinaryExpression(Expression<T> left, Expression<T> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public T evaluate() {
        return dynamicEvaluate(left.evaluate(), right.evaluate());
    }

    @Override
    public String toString()
    {
        return "(" + left.toString() + getOperationSign() + right.toString() +")";
    }

    protected abstract T dynamicEvaluate(T left, T right);
}
