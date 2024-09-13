package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;

public class If extends ExpressionImpl {

    Expression condition;
    Expression thenExpression;
    Expression elseExpression;

    public If(Expression condition, Expression thenExpression, Expression elseExpression) {
        this.condition = condition;
        this.thenExpression = thenExpression;
        this.elseExpression = elseExpression;

    }

    @Override
    public Data evaluate() {
        Data data;

        if(condition.getType() == DataType.BOOLEAN && thenExpression.getType() == elseExpression.getType()) {

            if((Boolean)condition.evaluate().getValue()) {

            }
            else {

            }
        }
        else {

        }

        return data;
    }

    @Override
    public boolean isValidArgs(Object... args) {
        return false;
    }
}
