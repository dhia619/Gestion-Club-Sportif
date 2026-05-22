package view.components;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import util.UIConstants;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;

public class CustomButton extends JButton{
    
    public CustomButton(
        String text,
        Color Backgroundcolor,
        Color ForegroundColor,
        ImageIcon icon
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
        if (icon != null) setIcon(icon);
    }

    public CustomButton(
        String text,
        Color backgroundColor
    ) {
        this(text, backgroundColor, Color.WHITE, null);
    }

    public CustomButton(
        String text
    ) {
        this(text, UIConstants.menuButtonBackgroundColor, Color.WHITE, null);
    }

    public CustomButton(
        Color backgroundColor,
        ImageIcon icon
    ) {
        this("", backgroundColor, Color.WHITE, icon);
    }


    public CustomButton(
        ImageIcon icon
    ) {
        this("", UIConstants.menuButtonBackgroundColor, Color.WHITE, icon);
    }

}
