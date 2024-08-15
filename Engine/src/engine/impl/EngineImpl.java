package engine.impl;

import engine.Engine;

public class EngineImpl implements Engine {

    private EngineImpl() {

    }

    @Override
    public EngineImpl CreateEngine() {
        return new EngineImpl();
    }

    @Override
    public void ReadXMLInitFile(String filename) {
        //TODO
    }

    @Override
    public void ShowSheetStatus() {

    }

    @Override
    public void ShowCellStatus(String cell) {

    }

    @Override
    public void UpdateCellStatus(String cell, String status) {

    }

    @Override
    public void ShowVersions() {

    }

    @Override
    public void ShowVersion(String version) {

    }
}
