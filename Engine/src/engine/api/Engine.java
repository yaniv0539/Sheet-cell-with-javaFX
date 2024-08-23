package engine.api;

import engine.version.manager.api.VersionManagerGetters;
import sheet.api.SheetGetters;
import sheet.cell.api.CellGetters;

public interface Engine {

    void ReadXMLInitFile(String filename);
    SheetGetters ShowSheetStatus();
    CellGetters ShowCellStatus(String cellName);
    void UpdateCellStatus(String cellName, String value);
    VersionManagerGetters ShowVersions();
    SheetGetters ShowVersion(int version);
}
