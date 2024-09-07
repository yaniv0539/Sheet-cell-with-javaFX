package sheet.range.boundaries.impl;

import sheet.range.boundaries.api.Boundaries;

public class BoundariesImpl implements Boundaries {
    private String from;
    private String to;

    private BoundariesImpl(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public static Boundaries create(String from, String to) {
        return new BoundariesImpl(from, to);
    }

    @Override
    public String getFrom() {
        return this.from;
    }

    @Override
    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String getTo() {
        return this.to;
    }

    @Override
    public void setTo(String to) {
        this.to = to;
    }
}
