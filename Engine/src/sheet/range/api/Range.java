package sheet.range.api;

import expression.api.DataType;
import sheet.coordinate.api.Coordinate;
import sheet.range.boundaries.api.Boundaries;

import java.util.Arrays;
import java.util.Collection;

public interface Range {
    String getName();
    Boundaries getBoundaries();
    void setBoundaries(Boundaries boundaries);
    Collection<Coordinate> toCoordinateCollection();
}
