package view.components;

import javax.swing.JCheckBox;
import util.UIConstants;
import java.awt.Component;
import java.awt.Font;

public class CustomCheckBox extends JCheckBox{

    public CustomCheckBox(String text) {
        setText(text);
        setOpaque(false);
        setFont(new Font("Arial", Font.PLAIN, 13));
        setForeground(UIConstants.secondaryTextColor);
        setAlignmentX(Component.LEFT_ALIGNMENT);
        setFocusPainted(false);
    }
}
