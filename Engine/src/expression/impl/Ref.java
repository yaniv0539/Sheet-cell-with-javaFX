package expression.impl;

import expression.api.Data;
import expression.api.DataType;
import expression.api.Expression;
import sheet.api.SheetGetters;
import sheet.cell.api.Cell;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;

import java.util.Arrays;

public class Ref extends ExpressionImpl {

    private Coordinate cellToReferTo;
    public static SheetGetters sheetView;

    public Ref(Expression cellId) {

        if (cellId.getClass() == RawString.class) {

            setDataType(DataType.UNKNOWN);
            cellToReferTo = CoordinateFactory.toCoordinate((String) cellId.evaluate().getValue());
        }
        else {
            throw new IllegalArgumentException("Ref argument must be a cell-id !");
        }
    }
    @Override
    public Data evaluate() {
        return sheetView.getCell(cellToReferTo).getEffectiveValue();
    }

    @Override
    public boolean isValidArgs(Object... args) {

        boolean value = Arrays
                 .stream(args)
                 .map(Expression.class::cast)
                 .allMatch(arg-> arg.getClass() == RawString.class);

        if(!value)
        {
            throw new IllegalArgumentException("arguments must be cell-id only in " + this.getClass().getSimpleName());
        }

        return true;
    }
}
