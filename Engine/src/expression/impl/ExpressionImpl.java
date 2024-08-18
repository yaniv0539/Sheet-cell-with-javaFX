package expression.impl;

import expression.api.DataType;
import expression.api.Expression;

public abstract class ExpressionImpl implements Expression {

    protected DataType type;

    void setDataType(DataType type) {
        this.type = type;
    }
    @Override
    public DataType getType() {
        return type;
    }
}
