package sheet.coordinate.impl;

import sheet.coordinate.api.Coordinate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CoordinateFactory {

    private static final Map<String, Coordinate> coordinateMap = new HashMap<String, Coordinate>();

    public static Coordinate createCoordinate(int row, int col) {
        String key = row + "," + col;

        Coordinate coordinate = coordinateMap.get(key);

        if (coordinate == null) {
            coordinate = CoordinateImpl.create(row, col);
            coordinateMap.put(key, coordinate);
        }
        return coordinate;
    }
}
