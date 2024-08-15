package expression.impl;
import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;
import expression.api.NumericExpression;

public class Number implements Expression,NumericExpression {

    private double value;

    public Number() {}

    public Number(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public Data evaluate() {
        return new DataImpl(DataType.NUMERIC, value);
    }

//    @Override
//    public String getOperationSign() {
//        return "";
//    }

    @Override
    public String toString() {
        return value >= 0 ? Double.toString(value): "(" + Double.toString(value) + ")";
    }
}
