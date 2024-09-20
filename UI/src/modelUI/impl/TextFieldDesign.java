package modelUI.impl;

import javafx.scene.text.Font;

public class TextFieldDesign {

    String style;
    Font textFont;

    public TextFieldDesign() {}

    public TextFieldDesign(String style, Font textFont) {
        this.style = style;
        this.textFont = textFont;
    }

    public String getStyle() {
        return style;
    }

    public Font getTextFont() {
        return textFont;
    }
}
