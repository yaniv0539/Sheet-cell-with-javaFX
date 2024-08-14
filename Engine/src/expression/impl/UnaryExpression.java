package expression.impl;
import expression.Expression;

public abstract class UnaryExpression<T> implements Expression<T> {

    private Expression<T> input;

    public  UnaryExpression(){}

    public UnaryExpression(Expression<T> input) {
        this.input = input;
    }
    public Expression<T> getInput() {
        return input;
    }
    @Override
    public T evaluate() {
        return dynamicEvaluate(input.evaluate());
    }

    protected abstract T dynamicEvaluate(T input);
}
