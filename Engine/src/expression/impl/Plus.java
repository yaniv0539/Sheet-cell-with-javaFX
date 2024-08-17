package expression.impl;

import expression.api.*;

import java.util.Arrays;

public class Plus extends BinaryExpression {

    public Plus(Expression left, Expression right) {

        super(left, right);
        this.isValidArgs(left, right);
        isValidArgs(left, right);
    }


    @Override
    protected Data dynamicEvaluate(Data left, Data right) {
        return new DataImpl(DataType.NUMERIC, (double)left.getValue() + (double)right.getValue());
    }

    @Override
    public boolean isValidArgs(Object... args) {
//         if (!Arrays.stream(args).allMatch(arg -> arg.getType() == DataType.NUMERIC))
//            throw new IllegalArgumentException("itay");;
            return true;
    }

//    @Override
//    public String getOperationSign() {
//        return "+";
//    }


}
