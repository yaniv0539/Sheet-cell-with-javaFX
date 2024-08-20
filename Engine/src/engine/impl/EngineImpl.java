package engine.impl;

import engine.dto.sheet.cell.api.DCell;
import engine.dto.sheet.impl.DSheet;
import engine.dto.version.api.DVersions;
import engine.api.Engine;
import engine.jaxb.parser.STLSheetToSheet;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import sheet.api.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import engine.jaxb.generated.STLSheet;
import sheet.cell.api.Cell;
import sheet.layout.api.Layout;

public class EngineImpl implements Engine {

    private final static String JAXB_XML_GENERATED_PACKAGE_NAME = "engine.jaxb.generated";
    private final static int MAX_ROWS = 50;
    private final static int MAX_COLUMNS = 20;

    private Sheet sheet;

    private EngineImpl() {}

    public static EngineImpl CreateEngine() {
        return new EngineImpl();
    }

    @Override
    public void ReadXMLInitFile(String filename) {
        // TODO: Handle with exceptions...
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
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DSheet ShowSheetStatus() {
        // TODO: Make DSheet from sheet and return it.
        return null;
    }

    @Override
    public DCell ShowCellStatus(String cellName) {
        Cell cell = getCell(cellName);
        // TODO: Make DCell from cell and return it.
        return null;
    }

    @Override
    public void UpdateCellStatus(String cellName, String value) {
        Cell cell = getCell(cellName);
        cell.setOriginalValue(value);
    }

    @Override
    public DVersions ShowVersions() {
        // Todo: Make DVersions from Versions.
        return null;
    }

    @Override
    public DSheet ShowVersion(int version) {

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

        if (!isValidCellFormat(cellName))
            throw new IllegalArgumentException("Invalid cell name");

        int row = extractRow(cellName) - 1;
        int column = parseColumnToInt(extractColumn(cellName)) - 1;

        return this.sheet.getCell(row, column);
    }

    private static boolean isValidCellFormat(String cellName) {

        if (cellName == null || cellName.isEmpty()) {
            return false;
        }

        return cellName.matches("^[A-Z]+[0-9]+$");
    }

    private static int extractRow(String cellName) {

        int index = 0;

        while (index < cellName.length() && !Character.isDigit(cellName.charAt(index))) {
            index++;
        }

        return Integer.parseInt(cellName.substring(index));
    }

    private static String extractColumn(String cellName) {

        int index = 0;
        while (index < cellName.length() && !Character.isDigit(cellName.charAt(index))) {
            index++;
        }

        return cellName.substring(0, index);
    }

    private static int parseColumnToInt(String column) {
        int result = 0;
        int length = column.length();

        for (int i = 0; i < length; i++) {
            char c = column.charAt(i);
            int value = c - 'A' + 1;
            result = result * 26 + value;
        }

        return result;
    }

}
