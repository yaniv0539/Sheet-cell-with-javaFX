package expression.impl;

import expression.api.*;

public class Sub implements Expression, StringExpression {
    private Expression source;
    private Expression left;
    private Expression right;

    public Sub(StringExpression source, NumericExpression left, NumericExpression right) {
        this.source = source;
        this.left = left;
        this.right = right;
    }

//    @Override
//    public java.lang.String getOperationSign() {
//        return "-";
//    }

    @Override
    public Data evaluate() {

        String str = (String)source.evaluate().getValue();
        int first = (int)left.evaluate().getValue();
        int last = (int)right.evaluate().getValue();

        return new DataImpl(DataType.STRING,str.substring(first, last));
    }

//    @Override
//    public java.lang.String toString() {
//        return  "(" + source.toString() + " " + left.toString() + getOperationSign() + right.toString() + ")";
//    }
}
