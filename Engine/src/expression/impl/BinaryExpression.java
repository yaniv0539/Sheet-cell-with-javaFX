package expression.impl;
import expression.api.Data;
import expression.api.Expression;

public abstract class BinaryExpression implements Expression {

    private Expression left;
    private Expression right;

    public BinaryExpression() {}

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Data evaluate() {
        return dynamicEvaluate(left.evaluate(), right.evaluate());
    }

//    @Override
//    public String toString()
//    {
//        return "(" + left.toString() + getOperationSign() + right.toString() +")";
//    }

    protected abstract Data dynamicEvaluate(Data left, Data right);
}
