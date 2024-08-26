package engine.api;

import engine.version.manager.api.VersionManagerGetters;
import sheet.api.SheetGetters;
import sheet.cell.api.CellGetters;

public interface Engine {

    void readXMLInitFile(String filename);
    SheetGetters getSheetStatus();
    CellGetters getCellStatus(String cellName);
    void updateCellStatus(String cellName, String value);
    VersionManagerGetters getVersionsManagerStatus();
    void exit();
}
