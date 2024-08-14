package expression.impl;
import expression.Expression;
import expression.StringExpression;

public class RawString implements Expression<String>,StringExpression {

    private String value;

    public RawString() {}
    public RawString(String value) {
        this.value = value;
    }

    @Override
    public String evaluate() {
        return this.value;
    }

    @Override
    public String getOperationSign() {
        return "";
    }

    @Override
    public String toString() {
        return value;
    }
}
