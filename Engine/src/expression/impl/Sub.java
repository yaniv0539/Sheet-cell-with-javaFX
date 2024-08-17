package expression.impl;

import expression.api.*;

public class Sub extends ExpressionImpl {
    private Expression source;
    private Expression left;
    private Expression right;

    public Sub(Expression source, Expression left, Expression right) {

        this.source = source;
        this.left = left;
        this.right = right;
        setDataType(DataType.STRING);
        isValidArgs(source, left, right);
    }

    @Override
    public Data evaluate() {

        String str = (String)source.evaluate().getValue();
        Double first = (Double)left.evaluate().getValue();
        Double last = (Double)right.evaluate().getValue();

        return new DataImpl(DataType.STRING,str.substring(first.intValue(), last.intValue()));
    }

    @Override
    public boolean isValidArgs(Object... args) {
        return false;
    }
}
