package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import model.Utilisateur;
import util.Lang;
import util.LanguageHandler;
import util.UIConstants;
import view.components.CustomButton;
import view.components.CustomLabel;
import view.components.CustomPasswordField;

public class ChangePasswordPanel extends JPanel {

    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private JButton submitButton;
    private Utilisateur utilisateur;

    public ChangePasswordPanel(Utilisateur utilisateur) {
        this.setBackground(UIConstants.secondaryBackgroundColor);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.utilisateur = utilisateur;
        JLabel titleLabel = new CustomLabel(
            Lang.get("password.change.title"),
            UIConstants.navy,
            UIConstants.titleFont,
            LanguageHandler.getLocale().equals("ar") ? Font.PLAIN : Font.ITALIC,
            25
        );

        JLabel descriptionLabel = new CustomLabel(
            Lang.get("password.change.description"),
            UIConstants.secondaryTextColor,
            "Arial",
            Font.PLAIN,
            14
        );

        JLabel oldPasswordLabel = new CustomLabel(Lang.get("password.old"), UIConstants.secondaryTextColor, "Arial", Font.PLAIN, 13);
        JLabel newPasswordLabel = new CustomLabel(Lang.get("password.new"), UIConstants.secondaryTextColor, "Arial", Font.PLAIN, 13);
        JLabel confirmPasswordLabel = new CustomLabel(Lang.get("password.confirm"), UIConstants.secondaryTextColor, "Arial", Font.PLAIN, 13);

        oldPasswordField = new CustomPasswordField();
        newPasswordField = new CustomPasswordField();
        confirmPasswordField = new CustomPasswordField();

        submitButton = new CustomButton(Lang.get("password.change.button"), UIConstants.belizeBlue);

        Dimension fieldSize = new Dimension(350, 40);

        oldPasswordField.setMaximumSize(fieldSize);
        newPasswordField.setMaximumSize(fieldSize);
        confirmPasswordField.setMaximumSize(fieldSize);
        submitButton.setMaximumSize(fieldSize);

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        oldPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        newPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        oldPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        newPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalGlue());

        this.add(titleLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(descriptionLabel);
        this.add(Box.createVerticalStrut(30));

        this.add(oldPasswordLabel);
        this.add(Box.createVerticalStrut(5));
        this.add(oldPasswordField);
        this.add(Box.createVerticalStrut(15));

        this.add(newPasswordLabel);
        this.add(Box.createVerticalStrut(5));
        this.add(newPasswordField);
        this.add(Box.createVerticalStrut(15));

        this.add(confirmPasswordLabel);
        this.add(Box.createVerticalStrut(5));
        this.add(confirmPasswordField);
        this.add(Box.createVerticalStrut(25));

        this.add(submitButton);

        this.add(Box.createVerticalGlue());
    }

    public String getOldPassword() {
        return new String(oldPasswordField.getPassword());
    }

    public String getNewPassword() {
        return new String(newPasswordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }
}