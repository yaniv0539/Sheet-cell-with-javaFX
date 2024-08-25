package engine.version.manager.impl;

import engine.version.api.Version;
import engine.version.api.VersionGetters;
import engine.version.manager.api.VersionManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VersionManagerImpl implements VersionManager {
    private final List<Version> versions;

    private VersionManagerImpl() {
        this.versions = new ArrayList<>();
    }

    public static VersionManagerImpl create() {
        return new VersionManagerImpl();
    }

    public List<VersionGetters> getVersions() {
        return Collections.unmodifiableList(this.versions);
    }

    @Override
    public void addVersion(Version version) {
        this.versions.add(version);
    }
}
