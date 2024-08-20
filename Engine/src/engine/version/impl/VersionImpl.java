package engine.version.impl;

import engine.version.api.Version;
import sheet.cell.api.Cell;
import sheet.coordinate.api.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VersionImpl implements Version {

    private int id;
    private int numberOfCellsThatChangedFromLastVersion;
    private Map<Coordinate, Cell> activeCells;

    public interface Manager {

    }

    public VersionImpl(int id, int numberOfCellsThatChangedFromLastVersion, Map<Coordinate, Cell> activeCells) {
        this.id = id;
        this.numberOfCellsThatChangedFromLastVersion = numberOfCellsThatChangedFromLastVersion;
        this.activeCells = activeCells;
    }

    public static void main(String[] args) {

    }
}
