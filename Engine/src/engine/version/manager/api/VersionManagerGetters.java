package engine.version.manager.api;

import engine.version.api.VersionGetters;
import sheet.api.SheetGetters;

import java.util.List;

public interface VersionManagerGetters {
    List<VersionGetters> getVersions();
}
