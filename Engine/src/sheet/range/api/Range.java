package sheet.range.api;

import sheet.range.boundaries.api.Boundaries;

public interface Range {
    String getName();
    Boundaries getBoundaries();
    void setBoundaries(Boundaries boundaries);
}
