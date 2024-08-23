package engine.impl;

import engine.api.Engine;
import engine.jaxb.parser.STLSheetToSheet;
import engine.version.manager.api.VersionManagerGetters;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import sheet.api.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import engine.jaxb.generated.STLSheet;
import sheet.api.SheetGetters;
import sheet.cell.api.Cell;
import sheet.cell.api.CellGetters;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.coordinate.impl.CoordinateImpl;
import sheet.layout.api.Layout;

public class EngineImpl implements Engine {

    private final static String JAXB_XML_GENERATED_PACKAGE_NAME = "engine.jaxb.generated";
    private final static int MAX_ROWS = 50;
    private final static int MAX_COLUMNS = 20;

    private Sheet sheet;

    private EngineImpl() {}

    public static EngineImpl Create() {
        return new EngineImpl();
    }

    @Override
    public void ReadXMLInitFile(String filename) {
        try {
            if (!filename.endsWith(".xml")) {
                throw new FileNotFoundException("File name has to end with '.xml'");
            }

            InputStream inputStream = new FileInputStream(new File(filename));
            STLSheet stlSheet = deserializeFrom(inputStream);
            Sheet sheet = STLSheetToSheet.generate(stlSheet);

            if (!isValidLayout(sheet.GetLayout())) {
                throw new IndexOutOfBoundsException("Layout is invalid");
            }

            this.sheet = sheet;
        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException("Failed to read XML file", e);
        }
    }

    @Override
    public SheetGetters ShowSheetStatus() { return this.sheet; }

    @Override
    public CellGetters ShowCellStatus(String cellName) { return getCell(cellName); }

    @Override
    public void UpdateCellStatus(String cellName, String value) { this.sheet.SetCell(CoordinateFactory.toCoordinate(cellName), value); }

    @Override
    public VersionManagerGetters ShowVersions() {
        // Todo: Make DVersions from Versions.
        return null;
    }

    @Override
    public SheetGetters ShowVersion(int version) {

        if (!isVersionExists(version)) {
            throw new IndexOutOfBoundsException("Version is invalid");
        }

        // Todo: Make DSheet from sheet.
        return null;
    }

    private static STLSheet deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GENERATED_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (STLSheet) unmarshaller.unmarshal(inputStream);
    }

    private boolean isVersionExists(int version) {
        // TODO: version validation.
        return false;
    }

    private static boolean isValidLayout(Layout layout) {
        return !(layout == null || layout.GetRows() > MAX_ROWS || layout.GetColumns() > MAX_COLUMNS);
    }

    private Cell getCell(String cellName) {

        if (!isValidCellFormat(cellName)) {
            throw new IllegalArgumentException("Invalid cell name");
        }

        return this.sheet.GetCell(CoordinateFactory.toCoordinate(cellName));
    }

    private static boolean isValidCellFormat(String cellName) {

        if (cellName == null || cellName.isEmpty()) {
            return false;
        }

        return cellName.matches("^[A-Z]+[0-9]+$");
    }

    @Override
    public String toString() {
        return "EngineImpl{" +
                "sheet=" + sheet +
                '}';
    }
}
