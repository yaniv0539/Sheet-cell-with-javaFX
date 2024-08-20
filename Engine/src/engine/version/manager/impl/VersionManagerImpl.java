package engine.version.manager.impl;

import engine.version.api.Version;

import java.util.ArrayList;
import java.util.List;

public class VersionManagerImpl {
    private final List<Version> versions;

    private VersionManagerImpl() {
        this.versions = new ArrayList<>();
    }

    public static VersionManagerImpl create() {
        return new VersionManagerImpl();
    }

    public List<Version> getVersions() {
        return this.versions;
    }

    public void addVersion(Version version) {
        this.versions.add(version);
    }
}
