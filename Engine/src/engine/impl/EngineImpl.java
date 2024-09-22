package engine.impl;

import engine.api.Engine;
import engine.jaxb.parser.STLSheetToSheet;
import engine.version.manager.api.VersionManager;
import engine.version.manager.api.VersionManagerGetters;
import engine.version.manager.impl.VersionManagerImpl;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javafx.concurrent.Task;
import sheet.api.Sheet;

import java.io.*;
import java.util.*;

import engine.jaxb.generated.STLSheet;
import sheet.api.SheetGetters;
import sheet.cell.api.Cell;
import sheet.cell.api.CellGetters;
import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.impl.SheetImpl;
import sheet.layout.api.Layout;
import sheet.layout.api.LayoutGetters;
import sheet.layout.impl.LayoutImpl;
import sheet.range.api.RangeGetters;
import sheet.range.boundaries.api.Boundaries;
import sheet.range.boundaries.impl.BoundariesFactory;

public class EngineImpl implements Engine, Serializable {

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
            //versionManager.clearVersions();
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
    public CellGetters getCellStatus(SheetGetters sheet, String cellName) {
        return sheet.getCell(CoordinateFactory.toCoordinate(cellName.toUpperCase()));
    }

    @Override
    public CellGetters getCellStatus(String cellName) {
        return getCellStatus(this.sheet, cellName);
    }

    @Override
    public CellGetters getCellStatus(int row, int col) {
        return getCellStatus(this.sheet, row, col);
    }

    @Override
    public CellGetters getCellStatus(SheetGetters sheet, int row, int col) {
        return getCellStatus(sheet, CoordinateFactory.createCoordinate(row, col).toString());
    }

    @Override
    public void updateCellStatus(String cellName, String value) {
        versionManager.increaseVersion(sheet);
        // this.sheet.setVersion(sheet.getVersion() + 1);
        try {
            this.sheet.setCell(CoordinateFactory.toCoordinate(cellName.toUpperCase()), value);
            versionManager.addVersion(this.sheet);
        } catch (Exception e) {
            versionManager.decreaseVersion(sheet);
            throw e;
        }
    }

    @Override
    public VersionManagerGetters getVersionsManagerStatus() { return this.versionManager; }

    @Override
    public Task<Boolean> loadFileTask(String path) {

        return new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {

                for (int i = 0; i < 50; i++) {
                    Thread.sleep(50);
                    updateProgress(i,50);
                    if(i == 10){
                        updateMessage("Fetching...");
                    }
                    if(i == 40){
                        updateMessage("Preparing data...");
                        readXMLInitFile(path);
                    }
                }
                return true;
            }
        };
    }

    @Override
    public SheetGetters filter(Boundaries boundaries, String column, List<String> values,int version) {

        Coordinate to = boundaries.getTo();
        Coordinate from = boundaries.getFrom();

        SheetGetters sheetToFilter = versionManager.getVersion(version);

        Sheet newSheet = SheetImpl.create(copyLayout(sheetToFilter.getLayout())); //here need to bring the version we now look at.

        int columnInt = CoordinateFactory.parseColumnToInt(column) - 1;

        Map<Integer, Integer> oldRowToNewRow = new HashMap<>();

        int liftUpCellsCounter = 0;
        int liftDownCellsCounter = 0;

        for (int i = from.getRow(); i <= to.getRow(); i++) {
            Cell cell = sheetToFilter.getCell(CoordinateFactory.createCoordinate(i, columnInt));
            String effectiveValueStr;
            if (cell == null) {
                effectiveValueStr = "";
            } else {
                effectiveValueStr = cell.getEffectiveValue().toString();
            }

            if (values.contains(effectiveValueStr)) {
                oldRowToNewRow.put(i, from.getRow() + liftDownCellsCounter);
                liftDownCellsCounter++;
            } else {
                liftUpCellsCounter++;
            }
        }

        this.sheet
                .getActiveCells()
                .keySet()
                .stream()
                .filter(coordinate -> coordinate.getRow() < from.getRow())
                .forEach(oldCoordinate -> newSheet.setCell(oldCoordinate, sheetToFilter.getCell(oldCoordinate).getEffectiveValue().toString()));

        this.sheet
                .getActiveCells()
                .keySet()
                .stream()
                .filter(coordinate -> coordinate.getRow() >= from.getRow() && coordinate.getRow() <= to.getRow())
                .forEach(oldCoordinate -> {
                    if (oldRowToNewRow.containsKey(oldCoordinate.getRow())) {
                        Coordinate newCoordinate =
                                CoordinateFactory.createCoordinate(
                                        oldRowToNewRow.get(oldCoordinate.getRow()),
                                        oldCoordinate.getCol());
                        newSheet.setCell(newCoordinate, sheetToFilter.getCell(oldCoordinate).getEffectiveValue().toString());
                    }
                });

        int finalLiftUpCellsCounter = liftUpCellsCounter;
        this.sheet
                .getActiveCells()
                .keySet()
                .stream()
                .filter(coordinate -> coordinate.getRow() > to.getRow())
                .forEach(oldCoordinate -> {
                    Coordinate newCoordinate =
                            CoordinateFactory.createCoordinate(
                                    oldCoordinate.getRow() - finalLiftUpCellsCounter,
                                         oldCoordinate.getCol());
                    newSheet.setCell(newCoordinate, sheetToFilter.getCell(oldCoordinate).getEffectiveValue().toString());
                });

        return newSheet;
    }

    @Override
    public void addRange(String name, String boundariesString) {
        sheet.addRange(name, BoundariesFactory.toBoundaries(boundariesString));
    }

    @Override
    public RangeGetters getRange(String name) {
        return sheet.getRange(name);
    }

    @Override
    public Set<RangeGetters> getRanges() {
        return Collections.unmodifiableSet(sheet.getRanges());
    }

    @Override
    public void deleteRange(String rangeName) {
        this.sheet.deleteRange(this.sheet.getRange(rangeName));
    }

    @Override
    public void exit() {}

    private static STLSheet deserializeFrom(InputStream inputStream) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GENERATED_PACKAGE_NAME);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        return (STLSheet) unmarshaller.unmarshal(inputStream);
    }

    private static boolean isValidLayout(LayoutGetters layout) {
        return !(layout == null || layout.getRows() > MAX_ROWS || layout.getColumns() > MAX_COLUMNS);
    }

    private Layout copyLayout(LayoutGetters layout) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(layout);
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            return (LayoutImpl) ois.readObject();
        } catch (Exception e) {
            // deal with the runtime error that was discovered as part of invocation
            throw new RuntimeException(e);
        }
    }

}
