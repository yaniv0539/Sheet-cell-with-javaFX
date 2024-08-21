package engine.jaxb.parser;

import engine.jaxb.generated.*;
import sheet.api.Sheet;
import sheet.cell.api.Cell;
import sheet.cell.impl.CellImpl;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.impl.SheetImpl;
import sheet.layout.api.Layout;
import sheet.layout.impl.LayoutImpl;
import sheet.layout.size.api.Size;
import sheet.layout.size.impl.SizeImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class STLSheetToSheet {

    private STLSheetToSheet() {}

    public static Sheet generate(STLSheet stlSheet) {

        STLLayout stlLayout = stlSheet.getSTLLayout();
        STLSize stlSize = stlLayout.getSTLSize();

        int width = stlSize.getColumnWidthUnits();
        int height = stlSize.getRowsHeightUnits();

        Size size = SizeImpl.create(width, height);

        int column = stlLayout.getColumns();
        int row = stlLayout.getRows();

        Layout layout = LayoutImpl.create(size, column, row);

        String name = stlSheet.getName();

        STLCells stlCells = stlSheet.getSTLCells();

        List<STLCell> stlCellsList = stlCells.getSTLCell();

        Map<Coordinate, Cell> coordinateCellMap = stlCellsList.stream()
                .collect(Collectors.toMap(
                        stlCell -> CoordinateFactory.createCoordinate(stlCell.getRow(), Integer.parseInt(stlCell.getColumn())),
                        stlCell -> CellImpl.create(CoordinateFactory.createCoordinate(stlCell.getRow(), Integer.parseInt(stlCell.getColumn()) - 'A' + 1), 1, stlCell.getSTLOriginalValue())
                ));

        return SheetImpl.create(name, layout);
    }
}
