package engine.version.manager.impl;

import engine.version.manager.api.VersionManager;
import sheet.api.Sheet;
import sheet.api.SheetGetters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VersionManagerImpl implements VersionManager {

    private static int currentVersion = 1;

    private final List<SheetGetters> versions;

    private VersionManagerImpl() {
        this.versions = new ArrayList<>();
    }

    public static VersionManagerImpl create() {
        return new VersionManagerImpl();
    }

    public List<SheetGetters> getVersions() {
        return Collections.unmodifiableList(this.versions);
    }

    @Override
    public SheetGetters getVersion(int version) {
        
        for (SheetGetters sheetGetters : this.versions) {
            if (sheetGetters.getVersion() == version) {
                return sheetGetters;
            }
        }

        throw new IllegalArgumentException("Version " + version + " not found");
    }

    public static int getCurrentVersion() {
        return currentVersion;
    }

    @Override
    public void addVersion(Sheet sheet) {
        sheet.setVersion(++currentVersion);
        this.versions.add(sheet);
    }

    @Override
    public void clearVersions() {
        currentVersion = 1;
        this.versions.clear();
    }


}
