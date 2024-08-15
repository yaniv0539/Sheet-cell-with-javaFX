package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;
import expression.api.NumericExpression;

public class Abs extends UnaryExpression implements NumericExpression {

    public Abs(){}

    public Abs(NumericExpression input) {
        super(input);
    }

    @Override
    protected Data dynamicEvaluate(Data input) {

        return new DataImpl(DataType.NUMERIC, Math.abs((double)input.getValue()));
    }

//    @Override
//    public String getOperationSign() {
//        return "| |";
//    }
//
//    @Override
//    public String toString() {
//        return "|" + getInput().toString() + "|";
//    }
}
