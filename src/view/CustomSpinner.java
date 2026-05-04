package view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

import util.UIConstants;

public class CustomSpinner extends JSpinner{
    public CustomSpinner(
        SpinnerModel spinnerModel,
        String fontName,
        int fontSize
    ){
        super(spinnerModel);
        setFont(new Font(fontName, Font.PLAIN, fontSize));
        setForeground(UIConstants.navy);
        setBackground(UIConstants.textFieldBackgroundColor);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIConstants.borderColor, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
        setPreferredSize(new Dimension(300, 42));
        setMaximumSize(new Dimension(300, 42));
        setAlignmentX(LEFT_ALIGNMENT);
    }

    public CustomSpinner(SpinnerModel spinnerModel){
        this(spinnerModel, "banschrift", 15);
    }
}
