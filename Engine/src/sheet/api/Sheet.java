package sheet.api;

import sheet.cell.api.Cell;

public interface Sheet {
    Cell getCell(int row, int column);
    void setCell(int row, int column, String value);
}
