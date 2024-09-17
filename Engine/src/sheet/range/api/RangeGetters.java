package sheet.range.api;

import sheet.coordinate.api.Coordinate;
import sheet.range.boundaries.api.Boundaries;
import sheet.range.boundaries.api.BoundariesGetters;

import java.util.Collection;

public interface RangeGetters {
    String getName();
    BoundariesGetters getBoundaries();
    Collection<Coordinate> toCoordinateCollection();
}
