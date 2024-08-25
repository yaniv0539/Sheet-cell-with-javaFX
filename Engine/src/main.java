import engine.api.Engine;
import engine.impl.EngineImpl;
import engine.version.api.VersionGetters;
import engine.version.impl.VersionImpl;
import engine.version.manager.api.VersionManagerGetters;
import sheet.api.Sheet;
import sheet.api.SheetGetters;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
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
              sheet1.setCell(CoordinateFactory.toCoordinate("A1"),"{DIVIDE,2,0}");
            sheet1.setCell(CoordinateFactory.toCoordinate("A2"),"{REF,A1}");
            sheet1.setCell(CoordinateFactory.toCoordinate("A3"),"{REF,A2}");
            sheet1.setCell(CoordinateFactory.toCoordinate("A4"),"{PLUS,{REF,A2},{REF,A1}}");
            printSheet(sheet1);

            sheet1.setCell(CoordinateFactory.toCoordinate("A1"),"{DIVIDE,2,5}");
            printSheet(sheet1);




            Map<Coordinate, String> comeOn = Map.of(
                    CoordinateFactory.toCoordinate("B1"),"3",
                    CoordinateFactory.toCoordinate("B2"),"5",
                    CoordinateFactory.toCoordinate("B3"),"{PLUS,{REF,B2},{REF,B1}}",
                    CoordinateFactory.toCoordinate("B4"),"{REF,B3}",
                    CoordinateFactory.toCoordinate("B5"),"{PLUS,{REF,B3},{REF,B3}}"
            );

            sheet1.setCells(comeOn);

            Map<Coordinate, String> comeOn2 = Map.of(
                    CoordinateFactory.toCoordinate("B1"),"Hello"
            );

            sheet1.setCells(comeOn2);
//
            printSheet(sheet1);
//
//            Engine engine = EngineImpl.create();

//            engine.readXMLInitFile(BASIC_XML_RESOURCE);
//            SheetGetters sheet2 = engine.getSheetStatus();
//            engine.updateCellStatus("A2", "3");
//            engine.updateCellStatus("A2", "4");
//            engine.updateCellStatus("A2", "5");
//            CellGetters cell2 = engine.getCellStatus("A2");
//
//            printSheet(sheet2);
//            printVersionManager(engine.getVersionsManagerStatus());

//            engine.readXMLInitFile(ERROR2_XML_RESOURCE);
//            SheetGetters sheet3 = engine.getSheetStatus();
//            engine.updateCellStatus("A2", "3");
//            CellGetters cell3 = engine.getCellStatus("A2");
//
//            printSheet(sheet3);
//
//            engine.readXMLInitFile(ERROR4_XML_RESOURCE);
//            SheetGetters sheet4 = engine.getSheetStatus();
//            engine.updateCellStatus("A2", "3");
//            CellGetters cell4 = engine.getCellStatus("A2");
//
//            printSheet(sheet4);
//
//            engine.readXMLInitFile(INSURANCE_XML_RESOURCE);
//            SheetGetters sheet5 = engine.getSheetStatus();
//            engine.updateCellStatus("A2", "3");
//            CellGetters cell5 = engine.getCellStatus("A2");
//
//            printSheet(sheet5);

        } catch(Exception e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
            printSheet(sheet1);
        }
    }

    public static void printSheet(SheetGetters sheet)
    {
        System.out.println("Sheet name: " + sheet.getName());
        sheet.getActiveCells().forEach((coordinate, cell) ->
            System.out.println(
                    cell.getCoordinate().toString()
                    + ": " + "\"" + cell.getOriginalValue() + "\""
                    + " => " + cell.getEffectiveValue().getValue()));

        System.out.println();
    }

//    public static void printVersionManager(VersionManagerGetters versionManager) {
//        System.out.println("Version manager:");
//        int versionNumber = 1;
//        int previous = 0;
//
//        for (VersionGetters version : versionManager.getVersions()) {
//            int current = version.getSheet().getNumberOfCellsThatChangedSinceCreated();
//            String sheetName = version.getSheet().getName();
//            System.out.println(
//                    new StringBuilder()
//                            .append("[Version ")
//                            .append(versionNumber++)
//                            .append("] Sheet name: ")
//                            .append(sheetName)
//                            .append(" - ")
//                            .append("number of cells that changed since last version: ")
//                            .append(current - previous)
//                            .toString()
//            );
//            previous = current;
//        }
//
//        System.out.println();
//    }
}
