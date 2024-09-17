package sheet.range.boundaries.impl;

import sheet.coordinate.api.Coordinate;
import sheet.coordinate.impl.CoordinateFactory;
import sheet.coordinate.impl.CoordinateImpl;
import sheet.range.boundaries.api.Boundaries;
import sheet.range.boundaries.api.BoundariesGetters;

import java.util.HashMap;
import java.util.Map;

public class BoundariesFactory {

    private static final String CELL_RANGE_REGEX = "^[a-zA-Z]+\\d+\\.\\.[a-zA-Z]+\\d+$";

    private static final Map<String, Boundaries> boundariesMap = new HashMap<String, Boundaries>();

    public static Boundaries createBoundaries(Coordinate from, Coordinate to) {
        String key = from.toString() + ".." + to.toString();
        return boundariesMap.computeIfAbsent(key, k -> BoundariesImpl.create(from, to));
    }

    public static Boundaries toBoundaries(String boundariesName) {

        boundariesName = boundariesName.toUpperCase();

        if (boundariesName.trim().isEmpty()) {
            throw new IllegalArgumentException("Range name cannot be empty");
        }

        if (!boundariesName.matches(CELL_RANGE_REGEX)) {
            throw new NumberFormatException("Invalid range boundaries");
        }

        String[] cells = boundariesName.split("\\.\\.");

        Coordinate from = CoordinateFactory.toCoordinate(cells[0]);
        Coordinate to = CoordinateFactory.toCoordinate(cells[1]);

        return BoundariesFactory.createBoundaries(from, to);
    }
}
