package sheet.cell.impl;

import expression.api.Data;
import expression.api.Expression;
import expression.parser.OrignalValueUtilis;
import sheet.cell.api.Cell;
import sheet.coordinate.api.Coordinate;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CellImpl implements Cell, Serializable {

    private final Coordinate coordinate;
    private int version;
    private String originalValue;
    private Expression expression;
    private Data effectiveValue;
    private Set<Cell> influenceFrom;
    private Set<Cell> influenceOn;

    private CellImpl(Coordinate coordinate, int version, String originalValue) {

        if (coordinate == null) {
            throw new IllegalArgumentException("Coordinate cannot be null");
        }

        this.coordinate = coordinate;
        setVersion(version);
        setOriginalValue(originalValue);
        this.influenceFrom = new HashSet<>();
        this.influenceOn = new HashSet<>();
    }

    public static CellImpl create(Coordinate coordinate, int version, String originalValue) {
        return new CellImpl(coordinate, version, originalValue);
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public String getOriginalValue() {
        return this.originalValue;
    }

    @Override
    public Data getEffectiveValue() {
        return this.effectiveValue;
    }

    @Override
    public Set<Cell> getInfluenceFrom() {
        return this.influenceFrom;
    }

    @Override
    public Set<Cell> getInfluenceOn() {
        return this.influenceOn;
    }

    @Override
    public void setInfluenceOn(Set<Cell> influenceOn) {
        this.influenceOn = influenceOn;
    }

    @Override
    public void setInfluenceFrom(Set<Cell> influenceFrom) {
        this.influenceFrom = influenceFrom;
    }

    @Override
    public void addInfluenceOn(Cell effectOn) {
        influenceOn.add(effectOn);
    }

    @Override
    public void addInfluenceFrom(Cell AffectedFrom) { influenceFrom.add(AffectedFrom); }

    @Override
    public void setOriginalValue(String originalValue) {

//        Data effectiveValue = OrignalValueUtilis.toExpression(originalValue).evaluate();
        //getting data, if pass this line value is valid to this specific cell.
        //the sheet need to check if this is ok for all cells that depend on this cell data.
        //get the cell that "this" influence from

//        this.originalValue = originalValue;
//        setEffectiveValue(effectiveValue);

       expression = OrignalValueUtilis.toExpression(originalValue);
        this.originalValue = originalValue;
    }

    private void setEffectiveValue(Data effectiveValue) {
        this.effectiveValue = effectiveValue;
    }

    @Override
    public void setVersion(int changeInVersion) {
        version = changeInVersion;
    }

    @Override
    public void computeEffectiveValue() {
       // setOriginalValue(this.originalValue);
        version++;
        setEffectiveValue(expression.evaluate());
    }

    private static boolean isValidVersion(int version) {
        return version >= 1;
    }

}
