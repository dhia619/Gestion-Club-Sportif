package view.membre;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Utilisateur;
import util.Lang;
import util.UIConstants;
import view.components.ActionsPanel;
import view.components.CustomButton;
import view.components.CustomLabel;
import view.components.CustomPasswordField;
import view.components.CustomTextField;

public class ProfilePanel extends JPanel{
    
    private JTextField identifiantField;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField dateNaissanceField;
    private JTextField telField;
    private JTextField adresseField;
    private JTextField poidsField;
    private JPasswordField passwordField;

    private JLabel identifiantLabel;
    private JLabel nomLabel;
    private JLabel prenomLabel;
    private JLabel dateNaissanceLabel;
    private JLabel telLabel;
    private JLabel adresseLabel;
    private JLabel poidsLabel;
    private JLabel passwordLabel;
    private JLabel titleLabel;

    private JButton saveButton;
    private JButton generateMemberCardButton;
    private JButton refreshButton;
    
    public ProfilePanel() {
        
        setLayout(new BorderLayout());

        titleLabel = new CustomLabel(Lang.get("profile"), 25);
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);

        ActionsPanel actionsPanel = new ActionsPanel();
        actionsPanel.setBackground(UIConstants.secondaryBackgroundColor);
        actionsPanel.setAlignmentX(LEFT_ALIGNMENT);
        
        saveButton = new CustomButton(Lang.get("button.save"), UIConstants.emeraldGreen);
        generateMemberCardButton = new CustomButton(Lang.get("membership.card.generate"), UIConstants.sunflowerYellow);
        refreshButton = new CustomButton(Lang.get("button.refresh"), UIConstants.belizeBlue);
        actionsPanel.addComponent(generateMemberCardButton);
        actionsPanel.addComponent(saveButton);
        actionsPanel.addComponent(refreshButton);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(UIConstants.secondaryBackgroundColor);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(actionsPanel);

        JPanel form = new JPanel(new GridLayout(9, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        form.setBackground(UIConstants.secondaryBackgroundColor);
        
        identifiantField = new CustomTextField();
        nomField = new CustomTextField();
        prenomField = new CustomTextField();
        dateNaissanceField = new CustomTextField();
        telField = new CustomTextField();
        adresseField = new CustomTextField();
        poidsField = new CustomTextField();
        passwordField = new CustomPasswordField();
        
        identifiantLabel = new CustomLabel(Lang.get("user.username"));
        nomLabel = new CustomLabel(Lang.get("user.lastname"));
        prenomLabel = new CustomLabel(Lang.get("user.firstname"));
        dateNaissanceLabel = new CustomLabel(Lang.get("user.birthdate"));
        telLabel = new CustomLabel(Lang.get("user.tel"));
        adresseLabel = new CustomLabel(Lang.get("user.address"));
        poidsLabel = new CustomLabel(Lang.get("user.weight"));
        passwordLabel = new CustomLabel(Lang.get("password"));
        
    
        form.add(identifiantLabel);
        form.add(identifiantField);
        form.add(prenomLabel);
        form.add(prenomField);
        form.add(nomLabel);
        form.add(nomField);
        form.add(dateNaissanceLabel);
        form.add(dateNaissanceField);
        form.add(telLabel);
        form.add(telField);
        form.add(adresseLabel);
        form.add(adresseField);
        form.add(poidsLabel);
        form.add(poidsField);
        form.add(passwordLabel);
        form.add(passwordField);
        
        add(topPanel, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
    }

    private void refreshTooltips() {
        identifiantField.setToolTipText(Lang.get("username.field.tooltip"));
        telField.setToolTipText(Lang.get("tel.field.tooltip"));
        dateNaissanceField.setToolTipText(Lang.get("date.field.tooltip"));
        poidsField.setToolTipText(Lang.get("weight.field.tooltip"));
    }

    public void clearFormulaire() {
        identifiantField.setText("");
        nomField.setText("");
        prenomField.setText("");
        dateNaissanceField.setText("");
        telField.setText("");
        adresseField.setText("");
        poidsField.setText("");
        passwordField.setText("");
    }

    public void fillForm(Utilisateur membre) {
        clearFormulaire();
        identifiantField.setText(membre.getLogin());
        nomField.setText(membre.getNom());
        prenomField.setText(membre.getPrenom());
        dateNaissanceField.setText(membre.getDateNaissance().toString());
        telField.setText(membre.getTelephone());
        adresseField.setText(membre.getAdresse());
        poidsField.setText(String.valueOf(membre.getPoids()));
    }

    public void refreshUIText() {
        titleLabel.setText(Lang.get("profile"));

        saveButton.setText(Lang.get("button.save"));
        generateMemberCardButton.setText(Lang.get("membership.card.generate"));

        identifiantLabel.setText(Lang.get("user.username"));
        nomLabel.setText(Lang.get("user.lastname"));
        prenomLabel.setText(Lang.get("user.firstname"));
        dateNaissanceLabel.setText(Lang.get("user.birthdate"));
        telLabel.setText(Lang.get("user.tel"));
        adresseLabel.setText(Lang.get("user.address"));
        poidsLabel.setText(Lang.get("user.weight"));
        passwordLabel.setText(Lang.get("password"));

        refreshTooltips();
    }

    public JButton getSaveButton() {
        return this.saveButton;
    }

    public JButton getRefreshButton() {
        return this.refreshButton;
    }

    public JButton getGenerateMemberCardButton() {
        return this.generateMemberCardButton;
    }

    public String getIdentifiant(){
        return identifiantField.getText();
    }

    public String getNom(){
        return nomField.getText();
    }

    public String getPrenom(){
        return prenomField.getText();
    }

    public String getTelephone(){
        return telField.getText();
    }

    public String getDateNaissance(){
        return dateNaissanceField.getText();
    }

    public String getAdresse(){
        return adresseField.getText();
    }

    public String getPoids(){
        return poidsField.getText();
    }

    public String getMotDePasse(){
        return new String(passwordField.getPassword());
    }
    
}
