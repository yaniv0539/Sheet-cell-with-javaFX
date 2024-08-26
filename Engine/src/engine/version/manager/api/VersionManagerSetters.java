package engine.version.manager.api;

import sheet.api.Sheet;
import sheet.api.SheetGetters;

public interface VersionManagerSetters {
    void addVersion(Sheet sheet);
    void clearVersions();
}
