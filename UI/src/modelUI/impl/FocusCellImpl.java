package modelUI.impl;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelUI.api.focusCell;

import java.util.Collection;

public class FocusCellImpl implements focusCell {

    public StringProperty coordinate;
    public StringProperty originalValue;
    public StringProperty effectiveValue;
    //depandeOn
    //influenceOn


    public FocusCellImpl() {
        coordinate = new SimpleStringProperty("");
        originalValue = new SimpleStringProperty("");
        effectiveValue = new SimpleStringProperty("");
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

    @Override
    public void setDependOn(Collection<String> dependOn) {

    }

    @Override
    public void setInfluenceOn(Collection<String> influence) {

    }
}
