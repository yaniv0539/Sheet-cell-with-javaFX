package expression.impl;

import expression.api.Data;
import expression.api.DataType;

import java.io.Serializable;

public class DataImpl implements Data, Serializable {

    public static final String undefiled = "!UNDEIFINED!";
    public static final String empty = "";

    private DataType type;
    private Object value;

    public DataImpl() {}

    public DataImpl(DataType type, Object value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public DataType getType() {
        return type;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
