package expression;

public interface Expression<T> {

    T evaluate();
    String getOperationSign();
}
