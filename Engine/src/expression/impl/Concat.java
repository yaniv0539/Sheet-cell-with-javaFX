package expression.impl;

import expression.Expression;
import expression.StringExpression;

public class Concat extends BinaryExpression<String> implements StringExpression {

    public Concat(Expression<String> left, Expression<String> right) {
        super(left, right);
    }

    @Override
    protected String dynamicEvaluate(String left, String right) {
        return String.join("", left, right);
    }

    @Override
    public String getOperationSign() {
        return "+";
    }
}
