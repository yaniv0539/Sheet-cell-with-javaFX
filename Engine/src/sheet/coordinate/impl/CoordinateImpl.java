package sheet.coordinate.impl;

import sheet.coordinate.api.Coordinate;

import java.util.Objects;

public class CoordinateImpl implements Coordinate {

    private final int row;
    private final int column;

    public CoordinateImpl(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.column;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CoordinateImpl that = (CoordinateImpl) o;
//        return row == that.row && column == that.column;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(row, column);
//    }

}
