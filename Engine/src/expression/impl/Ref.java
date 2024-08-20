package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;
import sheet.api.CellLookupService;
import sheet.cell.api.Cell;

import java.util.Arrays;
import java.util.Optional;

public class Ref extends ExpressionImpl {

    private Expression cellId;
    public static CellLookupService sheetView;

    public Ref(Expression cellId) {

        if (cellId.getClass() == RawString.class) {
            this.cellId = cellId;
            Data inCellData = sheetView.getCellData((String)cellId.evaluate().getValue());
            setDataType(inCellData.getType());
        }
        else {
            throw new IllegalArgumentException("Ref argument must be a cell id");
        }
    }
    @Override
    public Data evaluate() {
        return sheetView.getCellData((String)cellId.evaluate().getValue());
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
