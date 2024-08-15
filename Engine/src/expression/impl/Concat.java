package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;
import expression.api.StringExpression;

public class Concat extends BinaryExpression implements StringExpression {



    public Concat(StringExpression left, StringExpression right) {
        super(left, right);
     }

    @Override
    protected Data dynamicEvaluate(Data left, Data right) {

        String result = String.join("",(String) left.getValue(), (String) right.getValue());

        return new DataImpl(DataType.STRING, result);
    }
}




