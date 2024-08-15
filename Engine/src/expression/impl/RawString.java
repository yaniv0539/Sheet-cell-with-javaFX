package expression.impl;
import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;
import expression.api.StringExpression;

public class RawString implements Expression,StringExpression {

    private String value;

    public RawString() {}
    public RawString(String value) {
        this.value = value;
    }

    @Override
    public Data evaluate() {

        return new DataImpl(DataType.STRING, (String)this.value);
    }

//    @Override
//    public String getOperationSign() {
//        return "";
//    }
//
//    @Override
//    public String toString() {
//        return value;
//    }
}
