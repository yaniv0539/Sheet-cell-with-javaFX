package expression.impl;
import expression.api.*;

public class Number extends ExpressionImpl {

    private double value;

    public Number(double value) {
        this.value = value;
        setDataType(DataType.NUMERIC);
        isValidArgs(value);
    }

    public double getValue() {
        return value;
    }

    @Override
    public Data evaluate() {
        return new DataImpl(DataType.NUMERIC, value);
    }

    @Override
    public boolean isValidArgs(Object... args) {
        return false;
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
