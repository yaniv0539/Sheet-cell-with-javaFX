package modelUI.impl;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import modelUI.api.EffectiveValuesPoolProperty;
import sheet.coordinate.api.Coordinate;

import java.util.HashMap;
import java.util.Map;

public class EffectiveValuesPoolPropertyImpl implements EffectiveValuesPoolProperty {

    Map<Coordinate,StringProperty> EffectiveValuesMap;

    public EffectiveValuesPoolPropertyImpl() {
        EffectiveValuesMap = new HashMap<>();
    }

    @Override
    public StringProperty getEffectiveValuePropertyAt(Coordinate coordinate) {
        return EffectiveValuesMap.get(coordinate);
    }

    @Override
    public boolean setEffectiveValuePropertyAt(Coordinate coordinate, String value) {
        if(EffectiveValuesMap.containsKey(coordinate)) {
            EffectiveValuesMap.get(coordinate).set(value);
            return true;
        }

        return false;
    }

    @Override
    public void addEffectiveValuePropertyAt(Coordinate coordinate, String value) {
        if(EffectiveValuesMap.containsKey(coordinate)) {
            setEffectiveValuePropertyAt(coordinate, value);
            return;
        }

        EffectiveValuesMap.put(coordinate, new SimpleStringProperty(value));
    }

    @Override
    public void bindPropertyTo(Coordinate coordinate, StringProperty ToBind) {
        EffectiveValuesMap.get(coordinate).bind(ToBind);
    }

    @Override
    public boolean isExcite(Coordinate coordinate) {
        return EffectiveValuesMap.containsKey(coordinate);
    }

    @Override
    public void clear() {
        EffectiveValuesMap.clear();
    }
}
