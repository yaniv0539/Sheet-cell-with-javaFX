package expression.impl;
import expression.api.Data;
import expression.api.Expression;

public abstract class BinaryExpression extends ExpressionImpl {

    public static final int numberOfArgs  = 2;
    private Expression left;
    private Expression right;

    public BinaryExpression(Expression left, Expression right) {

        this.left = left;
        this.right = right;
    }

    @Override
    public Data Evaluate() {
        return dynamicEvaluate(left.Evaluate(), right.Evaluate());
    }

    protected abstract Data dynamicEvaluate(Data left, Data right);
}
