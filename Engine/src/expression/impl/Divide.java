package expression.impl;

public class Divide extends BinaryExpression{
    @Override
    public double dynamicEvaluate(double left, double right) {
        return 0;
    }

    @Override
    public String getOperationSign() {
        return "";
    }
}
