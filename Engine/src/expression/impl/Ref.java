package expression.impl;

import expression.api.Data;
import expression.api.Expression;
import sheet.cell.api.Cell;

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
        return false;
    }
}
