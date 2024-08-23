package sheet.cell.api;

import java.util.Set;

public interface CellSetters {
    void ComputeEffectiveValue();
    void SetOriginalValue(String originalValue);
    void SetInfluenceOn(Set<Cell> influenceOn);
    void SetInfluenceFrom(Set<Cell> influenceFrom);
    void AddInfluenceOn(Cell influenceOn);
    void AddInfluenceFrom(Cell influenceFrom);
}
