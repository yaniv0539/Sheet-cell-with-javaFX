package expression.impl;

import expression.Expression;
import expression.NumericExpression;

public class Pow extends BinaryExpression{

    public Pow(){}

    public Pow(NumericExpression left, NumericExpression right){
        super(left, right);
    }

    @Override
    public double dynamicEvaluate(double left, double right) {
        return Math.pow(left, right);
    }

    @Override
    public String getOperationSign() {
        return "^";
    }
}
