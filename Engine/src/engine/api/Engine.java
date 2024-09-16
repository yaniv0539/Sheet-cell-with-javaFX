package engine.api;

import engine.version.manager.api.VersionManagerGetters;
import sheet.api.SheetGetters;
import sheet.cell.api.CellGetters;

public interface Engine {

    void readXMLInitFile(String filename);
    SheetGetters getSheetStatus();
    CellGetters getCellStatus(SheetGetters sheet, String cellName);
    CellGetters getCellStatus(String cellName);
    CellGetters getCellStatus(int row, int col);
    CellGetters getCellStatus(SheetGetters sheet, int row, int col);
    void updateCellStatus(String cellName, String value);
    VersionManagerGetters getVersionsManagerStatus();
    void exit();

    javafx.concurrent.Task<Boolean> loadFileTask(String path);
}
