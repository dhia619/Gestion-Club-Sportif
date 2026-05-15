package view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.Lang;
import util.LanguageHandler;
import util.UIConstants;
import view.components.CustomButton;
import view.components.CustomCheckBox;
import view.components.CustomLabel;
import view.components.CustomPasswordField;
import view.components.CustomTextField;
public class LoginPanel extends JPanel {

    private JTextField identifiantField;
    private JPasswordField motDePasseField;
    private JButton loginButton;
    private JCheckBox rememberMeCheckBox;

    public LoginPanel() {
        this.setLayout(new GridLayout(1, 2));

        JPanel leftChild = new JPanel();
        JPanel rightChild = new JPanel();

        leftChild.setBackground(UIConstants.primaryBackgroundColor);
        rightChild.setBackground(UIConstants.secondaryBackgroundColor);

        this.add(leftChild);
        this.add(rightChild);


        leftChild.setLayout(new GridBagLayout());

        JPanel centerPanel = new JPanel();

        centerPanel.setOpaque(false);

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel welcomeLabel = new CustomLabel(Lang.get("login.welcome"), UIConstants.navy, UIConstants.titleFont, Font.PLAIN, 31);
        JLabel clubNameLabel = new CustomLabel(
            Lang.get("app.title"), 
            UIConstants.primaryTextColor, 
            UIConstants.titleFont, 
            LanguageHandler.getLocale().equals("ar") ? Font.PLAIN : Font.ITALIC,
            37
        );
        JLabel sloganLabel = new CustomLabel(
            Lang.get("login.description"), 
            UIConstants.navy, 
            UIConstants.titleFont, 
            LanguageHandler.getLocale().equals("ar") ? Font.PLAIN : Font.ITALIC,
            25
        );

        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        clubNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sloganLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(clubNameLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(sloganLabel);

        leftChild.add(centerPanel);


        rightChild.setLayout(new GridBagLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(UIConstants.secondaryBackgroundColor);

        JLabel titleLabel = new CustomLabel(
            Lang.get("signin"), 
            UIConstants.navy, 
            UIConstants.titleFont, 
            LanguageHandler.getLocale().equals("ar") ? Font.PLAIN : Font.ITALIC, 
            25
        );
        JLabel identifiantLabel = new CustomLabel(Lang.get("username"), UIConstants.secondaryTextColor, "Arial", Font.PLAIN, 13);
        JLabel motDePasseLabel = new CustomLabel(Lang.get("password"), UIConstants.secondaryTextColor, "Arial", Font.PLAIN, 13);

        identifiantField = new CustomTextField();
        motDePasseField = new CustomPasswordField();

        loginButton = new CustomButton(Lang.get("signin"), UIConstants.belizeBlue);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, loginButton.getPreferredSize().height));

        rememberMeCheckBox = new CustomCheckBox(Lang.get("remember.me"));

        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(25));

        formPanel.add(identifiantLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(identifiantField);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(motDePasseLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(motDePasseField);
        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(rememberMeCheckBox);
        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(loginButton);

        rightChild.add(formPanel);

    }

    public String getIdentifiant() {
        return identifiantField.getText();
    }

    public String getMotDePasse() {
        return new String(motDePasseField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public boolean isRememberMeSelected() {
        return rememberMeCheckBox.isSelected();
    }
}