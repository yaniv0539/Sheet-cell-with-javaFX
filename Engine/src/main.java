import engine.api.Engine;
import engine.impl.EngineImpl;
import sheet.api.Sheet;
import sheet.api.SheetGetters;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.coordinate.impl.CoordinateImpl;
import sheet.impl.SheetImpl;
import sheet.layout.api.Layout;
import sheet.layout.impl.LayoutImpl;
import sheet.layout.size.api.Size;
import sheet.layout.size.impl.SizeImpl;

import java.util.Map;

public class main
{
    private static final String BASIC_XML_RESOURCE = "Engine/src/engine/jaxb/resources/basic.xml";
    private static final String ERROR2_XML_RESOURCE = "Engine/src/engine/jaxb/resources/error-2.xml";
    private static final String ERROR4_XML_RESOURCE = "Engine/src/engine/jaxb/resources/error-4.xml";
    private static final String INSURANCE_XML_RESOURCE = "Engine/src/engine/jaxb/resources/insurance.xml";


    public static void main(String[] args) {

        int width = 5;
        int height = 5;

        Size size = SizeImpl.create(width, height);

        int column = 5;
        int row = 5;

        Layout layout = LayoutImpl.create(size, column, row);
        String name = "Itay & Yaniv inc.";

        Sheet sheet1 = SheetImpl.create(name, layout);

        try
        {
            sheet1.SetCell(CoordinateFactory.toCoordinate("A1"),"5");
            sheet1.SetCell(CoordinateFactory.toCoordinate("A2"),"5");
            sheet1.SetCell(CoordinateFactory.toCoordinate("A3"),"{PLUS,{REF, A2},{REF,A1}}");
            sheet1.SetCell(CoordinateFactory.toCoordinate("A4"),"{REF, A2}");

            Map<Coordinate, String> comeOn = Map.of(
                    CoordinateFactory.toCoordinate("B1"),"5",
                    CoordinateFactory.toCoordinate("B2"),"5",
                    CoordinateFactory.toCoordinate("B3"),"{PLUS,{REF, B2},{REF,B1}}",
                    CoordinateFactory.toCoordinate("B4"),"{REF, B2}",
                    CoordinateFactory.toCoordinate("B5"),"{PLUS,{REF, B2}, 4}"
            );

            sheet1.SetCells(comeOn);

            print(sheet1);

            Engine engine = EngineImpl.Create();

            engine.ReadXMLInitFile(BASIC_XML_RESOURCE);
            SheetGetters sheet2 = engine.ShowSheetStatus();
            engine.UpdateCellStatus("A2", "3");
            CellGetters cell2 = engine.ShowCellStatus("A2");

            System.out.println(engine);

            print(sheet2);

//            engine.ReadXMLInitFile(ERROR2_XML_RESOURCE);
//            SheetGetters sheet3 = engine.ShowSheetStatus();
//            engine.UpdateCellStatus("A2", "3");
//            CellGetters cell3 = engine.ShowCellStatus("A2");
//
//            print(sheet3);

//            engine.ReadXMLInitFile(ERROR4_XML_RESOURCE);
//            SheetGetters sheet4 = engine.ShowSheetStatus();
//            engine.UpdateCellStatus("A2", "3");
//            CellGetters cell4 = engine.ShowCellStatus("A2");
//
//            print(sheet4);

            engine.ReadXMLInitFile(INSURANCE_XML_RESOURCE);
            SheetGetters sheet5 = engine.ShowSheetStatus();
            engine.UpdateCellStatus("A2", "3");
            CellGetters cell5 = engine.ShowCellStatus("A2");

            print(sheet5);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void print(SheetGetters sheet)
    {
        System.out.println("Sheet name: " + sheet.GetName());
        sheet.GetActiveCells().forEach((coordinate, cell) ->
            System.out.println(
                    cell.GetCoordinate().toString()
                    + ": " + "\"" + cell.GetOriginalValue() + "\""
                    + " => " + cell.GetEffectiveValue().GetValue()));

        System.out.println();
    }
}
