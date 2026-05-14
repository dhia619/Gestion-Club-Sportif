package view.components;

import javax.swing.JButton;

import util.UIConstants;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

public class CustomButton extends JButton{
    
    public CustomButton(
        String text,
        Color Backgroundcolor,
        Color ForegroundColor
    ) {
        setText(text);
        setFont(UIConstants.buttonFont);
        setForeground(ForegroundColor);
        setBackground(Backgroundcolor);
        setOpaque(true);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setMinimumSize(new Dimension(120, 39));
    }

    public CustomButton(
        String text,
        Color backgroundColor
    ) {
        this(text, backgroundColor, Color.WHITE);
    }

    public CustomButton(
        String text
    ) {
        this(text, UIConstants.menuButtonBackgroundColor, Color.WHITE);
    }

}
