package engine.version.impl;

import engine.version.api.Version;
import sheet.api.Sheet;
import sheet.api.SheetGetters;
import sheet.cell.api.Cell;
import sheet.coordinate.api.Coordinate;
import sheet.impl.SheetImpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VersionImpl implements Version {

    private SheetGetters sheet;

    private VersionImpl(Sheet sheet) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(sheet);
            oos.close();

            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            this.sheet = (SheetImpl)ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static VersionImpl create(Sheet sheet) {
        return new VersionImpl(sheet);
    }

    @Override
    public SheetGetters getSheet() {
        return this.sheet;
    }
}
