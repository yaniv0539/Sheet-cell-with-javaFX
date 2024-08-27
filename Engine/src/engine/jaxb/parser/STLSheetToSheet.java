package engine.jaxb.parser;

import engine.jaxb.generated.*;
import engine.version.manager.impl.VersionManagerImpl;
import sheet.api.Sheet;
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

        Layout layout = LayoutImpl.create(size, row, column);

        String name = stlSheet.getName();

        Sheet sheet = SheetImpl.create(name, layout);

//        sheet.setVersion(VersionManagerImpl.firstInit);

        STLCells stlCells = stlSheet.getSTLCells();

        List<STLCell> stlCellsList = stlCells.getSTLCell();

        Map<Coordinate, String> originalValuesMap = stlCellsList.stream()
                .collect(Collectors.toMap(
                        stlCell -> CoordinateFactory.createCoordinate(stlCell.getRow() - 1, CoordinateFactory.parseColumnToInt(stlCell.getColumn()) - 1),
                        STLCell::getSTLOriginalValue
                ));

        sheet.setCells(originalValuesMap);

        return sheet;
    }
}
