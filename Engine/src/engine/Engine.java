package engine;

import engine.impl.EngineImpl;

public interface Engine {

    public EngineImpl CreateEngine();
    public void ReadXMLInitFile(String filename);
    public void ShowSheetStatus();
    public void ShowCellStatus(String cell);
    public void UpdateCellStatus(String cell, String status);
    public void ShowVersions();
    public void ShowVersion(String version);
}
