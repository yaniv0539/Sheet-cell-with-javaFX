package engine.api;

import engine.impl.EngineImpl;

public interface Engine {

    void ReadXMLInitFile(String filename);
    void ShowSheetStatus();
    void ShowCellStatus(String cell);
    void UpdateCellStatus(String cell, String status);
    void ShowVersions();
    void ShowVersion(String version);
}
