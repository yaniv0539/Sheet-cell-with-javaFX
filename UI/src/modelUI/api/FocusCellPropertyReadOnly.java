package modelUI.api;

import javafx.beans.property.StringProperty;

import java.util.Collection;

public interface FocusCellPropertyReadOnly {
    StringProperty getCoordinate();
    StringProperty getOriginalValue();
    StringProperty getEffectiveValue();
    StringProperty getLastUpdateVersion();
    Collection<String>  getDependOn();
    Collection<String> getInfluenceOn();
}
