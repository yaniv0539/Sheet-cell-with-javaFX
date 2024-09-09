package modelUI.api;

import java.util.Collection;

public interface focusCell {

    void setCoordinate(String coordinate );
    void setOriginalValue(String originalValue );
    void setEffectiveValue(String effectiveValue );
    void setDependOn(Collection<String> dependOn );
    void setInfluenceOn(Collection<String> influence );
}
