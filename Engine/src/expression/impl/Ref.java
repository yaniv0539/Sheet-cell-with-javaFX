package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;
import sheet.cell.api.Cell;

import java.util.Arrays;
import java.util.Optional;

public class Ref extends ExpressionImpl {

    Cell cellToRefer;

    public Ref(Cell cell) {
        this.cellToRefer = cell;
        setDataType(cellToRefer.getEffectiveValue().getType());
        isValidArgs(cell);
    }
    @Override
    public Data evaluate() {
        return cellToRefer.getEffectiveValue();
    }

    @Override
    public boolean isValidArgs(Object... args) {

        try{
            Arrays
                    .stream(args)
                    .map(Cell.class::cast);
        }
        catch(ClassCastException e) {
            throw new IllegalArgumentException("arguments must be cell-id in " + this.getClass().getSimpleName());
        }

        return true;
    }
}
