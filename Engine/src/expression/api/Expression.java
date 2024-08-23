package expression.api;

public interface Expression {

    Data Evaluate();
    boolean isValidArgs(Object...args);
    DataType GetType();
}
