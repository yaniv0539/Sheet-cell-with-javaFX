package modelUI.impl;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelUI.api.FocusCellProperty;
import modelUI.api.FocusCellPropertyReadOnly;
import modelUI.api.FocusCellPropertyWriteOnly;

import java.util.Collection;
import java.util.List;

public class FocusCellPropertyImpl implements FocusCellProperty {

    public StringProperty coordinate;
    public StringProperty originalValue;
    public StringProperty effectiveValue;
    public StringProperty lastUpdateVersion;
    //depandeOn
    //influenceOn

    public FocusCellPropertyImpl() {
        coordinate = new SimpleStringProperty("");
        originalValue = new SimpleStringProperty("");
        effectiveValue = new SimpleStringProperty("");
        lastUpdateVersion = new SimpleStringProperty("");
    }

    @Override
    public StringProperty getCoordinate() {
        return coordinate;
    }

    @Override
    public StringProperty getOriginalValue() {
        return originalValue;
    }

    @Override
    public StringProperty getEffectiveValue() {
        return effectiveValue;
    }

    @Override
    public StringProperty getLastUpdateVersion() {
        return lastUpdateVersion;
    }

    @Override
    public Collection<String> getDependOn() {
        return List.of();
    }

    @Override
    public Collection<String> getInfluenceOn() {
        return List.of();
    }

    @Override
    public void setCoordinate(String coordinate) {
        this.coordinate.set(coordinate);
    }

    @Override
    public void setOriginalValue(String originalValue) {
        this.originalValue.set(originalValue);
    }

    @Override
    public void setEffectiveValue(String effectiveValue) {
        this.effectiveValue.set(effectiveValue);
    }

    public void setLastUpdateVersion(String lastUpdateVersion) {
        this.lastUpdateVersion.set(lastUpdateVersion);
    }

    @Override
    public void setDependOn(Collection<String> dependOn) {

    }
    @Override
    public void setInfluenceOn(Collection<String> influence) {

    }
}
