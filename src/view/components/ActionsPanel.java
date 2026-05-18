package view.components;

import java.awt.FlowLayout;

import javax.swing.*;

public class ActionsPanel extends JPanel {
    
    public ActionsPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));
    }

    public void addComponent(JComponent c) {
        add(c);
        add(Box.createHorizontalStrut(10));
    }
}
