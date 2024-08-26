package engine.impl;

import engine.api.Engine;
import engine.jaxb.parser.STLSheetToSheet;
import engine.version.manager.api.VersionManager;
import engine.version.manager.api.VersionManagerGetters;
import engine.version.manager.impl.VersionManagerImpl;
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
import sheet.layout.api.Layout;

public class EngineImpl implements Engine {

    private final static String JAXB_XML_GENERATED_PACKAGE_NAME = "engine.jaxb.generated";
    private final static int MAX_ROWS = 50;
    private final static int MAX_COLUMNS = 20;

    private Sheet sheet;
    private final VersionManager versionManager;

    private EngineImpl() {
        this.versionManager = VersionManagerImpl.create();
    }

    public static EngineImpl create() {
        return new EngineImpl();
    }

    @Override
    public void readXMLInitFile(String filename) {
        try {
            if (!filename.endsWith(".xml")) {
                throw new FileNotFoundException("File name has to end with '.xml'");
            }

            InputStream inputStream = new FileInputStream(new File(filename));
            STLSheet stlSheet = deserializeFrom(inputStream);
            Sheet sheet = STLSheetToSheet.generate(stlSheet);

            if (!isValidLayout(sheet.getLayout())) {
                throw new IndexOutOfBoundsException("Layout is invalid");
            }

            this.sheet = sheet;
            versionManager.clearVersions();
            versionManager.addVersion(this.sheet);

        } catch (JAXBException | FileNotFoundException e) {
            throw new RuntimeException("Failed to read XML file", e);
        }
    }

    @Override
    public SheetGetters getSheetStatus() { return this.sheet; }

    @Override
    public CellGetters getCellStatus(String cellName) { return getCell(cellName); }

    @Override
    public void updateCellStatus(String cellName, String value) {
        this.sheet.setCell(CoordinateFactory.toCoordinate(cellName), value);
        versionManager.addVersion(this.sheet);
    }

    @Override
    public VersionManagerGetters getVersionsManagerStatus() { return this.versionManager; }

    @Override
    public void exit() {}

    private static STLSheet deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GENERATED_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (STLSheet) unmarshaller.unmarshal(inputStream);
    }

    private static boolean isValidLayout(Layout layout) {
        return !(layout == null || layout.getRows() > MAX_ROWS || layout.getColumns() > MAX_COLUMNS);
    }

    private Cell getCell(String cellName) {

        if (!isValidCellFormat(cellName)) {
            throw new IllegalArgumentException(cellName + "is not valid cell format");
        }

        return this.sheet.getCell(CoordinateFactory.toCoordinate(cellName));
    }

    private static boolean isValidCellFormat(String cellName) {

        if (cellName == null || cellName.isEmpty()) {
            return false;
        }

        return cellName.matches("^[A-Z]+[0-9]+$");
    }
}
