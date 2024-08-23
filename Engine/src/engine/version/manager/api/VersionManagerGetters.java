package engine.version.manager.api;

import sheet.api.Sheet;

import java.util.List;

public interface VersionManagerGetters {
    //List<VersionGetters> GetSheets();
    Sheet GetSheetByVersion(int version);
}
