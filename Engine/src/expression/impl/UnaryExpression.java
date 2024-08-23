package expression.impl;
import expression.api.Data;
import expression.api.Expression;


public abstract class UnaryExpression extends ExpressionImpl{

    public static final int numberOfArgs  = 1;
    private Expression input;

    public UnaryExpression(Expression input) {
        this.input = input;
    }
    public Expression getInput() {
        return input;
    }
    @Override
    public Data Evaluate() {
        return dynamicEvaluate(input.Evaluate());
    }


    protected abstract Data dynamicEvaluate(Data input);
}
