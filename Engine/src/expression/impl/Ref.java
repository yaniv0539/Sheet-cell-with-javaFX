package expression.impl;

import expression.api.Data;
import expression.api.Expression;
import sheet.api.SheetGetters;
import sheet.cell.api.Cell;

import java.util.Arrays;

public class Ref extends ExpressionImpl {

    private Expression cellId;
    public static SheetGetters sheetView;

    public Ref(Expression cellId) {

        if (cellId.getClass() == RawString.class) {
            this.cellId = cellId;
            Data inCellData = sheetView.GetCellData((String)cellId.Evaluate().GetValue());
            setDataType(inCellData.GetType());
        }
        else {
            throw new IllegalArgumentException("Ref argument must be a cell-id");
        }
    }
    @Override
    public Data Evaluate() {
        return sheetView.GetCellData((String)cellId.Evaluate().GetValue());
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
