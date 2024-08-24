package sheet.layout.api;

import sheet.layout.size.api.Size;

public interface Layout {
    public Size getSize();
    public int getRows();
    public int getColumns();
    public void setSize(Size size);
    public void setRows(int rows);
    public void setColumns(int columns);
}
