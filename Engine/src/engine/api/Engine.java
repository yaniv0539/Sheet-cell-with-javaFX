package engine.api;

import engine.dto.sheet.cell.api.DCell;
import engine.dto.sheet.impl.DSheet;
import engine.dto.version.api.DVersions;

public interface Engine {

    void ReadXMLInitFile(String filename);
    DSheet ShowSheetStatus();
    DCell ShowCellStatus(String cellName);
    void UpdateCellStatus(String cellName, String value);
    DVersions ShowVersions();
    DSheet ShowVersion(int version);
}
