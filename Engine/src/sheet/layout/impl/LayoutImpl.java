package sheet.layout.impl;

import sheet.layout.api.Layout;
import sheet.layout.size.api.Size;

public class LayoutImpl implements Layout {
    private Size size;
    private int rows;
    private int columns;

    private LayoutImpl(Size size, int rows, int columns) {
        SetSize(size);
        SetRows(rows);
        SetColumns(columns);
    }

    public static LayoutImpl create(Size size, int row, int column) {
        return new LayoutImpl(size, row, column);
    }

    @Override
    public Size GetSize() {
        return this.size;
    }

    @Override
    public int GetRows() {
        return this.rows;
    }

    @Override
    public int GetColumns() {
        return this.columns;
    }

    @Override
    public void SetSize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        this.size = size;
    }

    @Override
    public void SetRows(int rows) {
        if (!isValidRows(rows))
            throw new IllegalArgumentException("Rows must be a positive integer");
        this.rows = rows;
    }

    @Override
    public void SetColumns(int columns) {
        if (!isValidColumns(columns))
            throw new IllegalArgumentException("Columns must be a positive integer");
        this.columns = columns;
    }

    private static boolean isValidRows(int rows) {
        return rows > 0;
    }

    private static boolean isValidColumns(int columns) {
        return columns > 0;
    }
}
