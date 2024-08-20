package sheet.coordinate.impl;

import sheet.cell.api.Cell;
import sheet.coordinate.api.Coordinate;

import java.util.Objects;

public class CoordinateImpl implements Coordinate {

    private final int row;
    private final int column;

    private CoordinateImpl(int row, int column) {

        if (!isValidRow(row)) {
            throw new IllegalArgumentException("Row must be a non-negative integer");
        }

        if (!isValidColumn(column)) {
            throw new IllegalArgumentException("Column must be a non-negative integer");
        }

        this.row = row;
        this.column = column;
    }

    public static CoordinateImpl create(int row, int column) {
        return new CoordinateImpl(row, column);
    }

    @Override
    public int getRow() {
        return this.row;
    }

    @Override
    public int getCol() {
        return this.column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoordinateImpl that = (CoordinateImpl) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    private static boolean isValidRow(int row)
    {
        return row >= 0;
    }

    private static boolean isValidColumn(int column)
    {
        return column >= 0;
    }

    public static Coordinate toCoordinate(String coordinateName) {

        if (!isValidCoordinateFormat(coordinateName))
            throw new IllegalArgumentException("Invalid cell name");

        int row = extractRow(coordinateName) - 1;
        int column = parseColumnToInt(extractColumn(coordinateName)) - 1;

        return CoordinateFactory.createCoordinate(row, column);
    }

    private static boolean isValidCoordinateFormat(String coordinateName) {

        if (coordinateName == null || coordinateName.isEmpty()) {
            return false;
        }

        return coordinateName.matches("^[A-Z]+[0-9]+$");
    }

    private static int extractRow(String coordinateName) {

        int index = 0;

        while (index < coordinateName.length() && !Character.isDigit(coordinateName.charAt(index))) {
            index++;
        }

        return Integer.parseInt(coordinateName.substring(index));
    }

    private static String extractColumn(String coordinateName) {

        int index = 0;
        while (index < coordinateName.length() && !Character.isDigit(coordinateName.charAt(index))) {
            index++;
        }

        return coordinateName.substring(0, index);
    }

    private static int parseColumnToInt(String column) {
        int result = 0;
        int length = column.length();

        for (int i = 0; i < length; i++) {
            char c = column.charAt(i);
            int value = c - 'A' + 1;
            result = result * 26 + value;
        }

        return result;
    }

}
