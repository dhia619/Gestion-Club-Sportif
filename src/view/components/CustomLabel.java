package view.components;

import util.UIConstants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class CustomLabel extends JLabel{
    public CustomLabel(
        String text,
        Color foregroundColor,
        String fontName,
        int fontWeight,
        int fontSize
    ) {
        setText(text);
        setForeground(foregroundColor);
        setOpaque(false);

        Font labelFont = new Font(fontName, fontWeight, fontSize);
        setFont(labelFont);
    }

    public CustomLabel(
        String text,
        Color foregroundColor,
        int fontSize
    ) {
        this(text, foregroundColor, UIConstants.labelFont, Font.BOLD ,fontSize);
    }

    public CustomLabel(
        String text,
        String fontName,
        int fontSize
    ) {
        this(text, Color.BLACK, fontName,  Font.BOLD ,fontSize);
    }

    public CustomLabel(
        String text,
        int fontSize
    ) {
        this(text, Color.BLACK, UIConstants.labelFont,  Font.BOLD ,fontSize);
    }

    public CustomLabel(
        String text
    ) {
        this(text, Color.BLACK, UIConstants.labelFont,  Font.BOLD , 15);
    }
}
