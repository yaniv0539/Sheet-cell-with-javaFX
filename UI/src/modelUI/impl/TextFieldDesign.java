package modelUI.impl;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class TextFieldDesign {

    Color backgroundColor;
    String textStyle;
    Font textFont;

    public TextFieldDesign() {}

    public TextFieldDesign(Color backgroundColor,String textStyle, Font textFont) {
        this.backgroundColor = backgroundColor;
        this.textStyle = textStyle;
        this.textFont = textFont;
    }

    public String getTextStyle() {
        return textStyle;
    }

    public Font getTextFont() {
        return textFont;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }
}
