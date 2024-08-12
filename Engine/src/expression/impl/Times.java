package expression.impl;

import expression.Expression;

public class Times extends BinaryExpression {
    @Override
    public double dynamicEvaluate(double left, double right) {
        return 0;
    }

    @Override
    public String getOperationSign() {
        return "";
    }
}
