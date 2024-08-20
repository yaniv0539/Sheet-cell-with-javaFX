package sheet.layout.api;

import sheet.layout.size.api.Size;

public interface Layout {
    public Size GetSize();
    public int GetRows();
    public int GetColumns();
    public void SetSize(Size size);
    public void SetRows(int rows);
    public void SetColumns(int columns);
}
