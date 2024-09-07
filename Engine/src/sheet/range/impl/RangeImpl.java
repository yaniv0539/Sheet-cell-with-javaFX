package sheet.range.impl;

import sheet.range.api.Range;
import sheet.range.boundaries.api.Boundaries;

import java.util.Objects;

public class RangeImpl implements Range {

    String name;
    Boundaries boundaries;

    private RangeImpl(String name, Boundaries boundaries) {
        this.name = name;
        this.boundaries = boundaries;
    }

    public static RangeImpl create(String name, Boundaries boundaries) {
        return new RangeImpl(name, boundaries);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Boundaries getBoundaries() {
        return this.boundaries;
    }

    @Override
    public void setBoundaries(Boundaries boundaries) {
        this.boundaries = boundaries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RangeImpl range = (RangeImpl) o;
        return Objects.equals(name, range.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
