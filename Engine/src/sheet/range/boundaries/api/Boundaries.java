package sheet.range.boundaries.api;

import sheet.coordinate.api.Coordinate;

public interface Boundaries {
    Coordinate getFrom();
    void setFrom(Coordinate from);
    Coordinate getTo();
    void setTo(Coordinate to);
}
