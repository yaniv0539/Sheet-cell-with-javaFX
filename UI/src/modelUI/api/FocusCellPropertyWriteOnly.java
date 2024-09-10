package modelUI.api;

import java.util.Collection;

public interface FocusCellPropertyWriteOnly {

    void setCoordinate(String coordinate );
    void setOriginalValue(String originalValue );
    void setEffectiveValue(String effectiveValue );
    void setLastUpdateVersion(String lastUpdateVersion );
    void setDependOn(Collection<String> dependOn );
    void setInfluenceOn(Collection<String> influence );
}
