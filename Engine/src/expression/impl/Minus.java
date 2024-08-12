package expression.impl;

public class Minus extends BinaryExpression{
    @Override
    public String getOperationSign() {
        return "";
    }

    @Override
    public double dynamicEvaluate(double left, double right) {
        return 0;
    }
}
