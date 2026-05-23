package view.admin;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


import model.Utilisateur;
import util.Lang;
import util.UIConstants;
import view.components.CustomButton;
import view.components.CustomLabel;
import view.components.CustomPasswordField;
import view.components.CustomTextField;
import view.components.ManagementPanel;

public class GestionMembrePanel extends ManagementPanel<Utilisateur> {

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

    public GestionMembrePanel(ArrayList<Utilisateur> membres) {
        super("manage.members", membres);
    }

    protected Object[] getTableColumns() {
        return new Object[] {
            "ID",
            Lang.get("user.username"),
            Lang.get("user.lastname"),
            Lang.get("user.firstname"),
            Lang.get("user.tel"),
            Lang.get("user.birthdate"),
            Lang.get("user.address"),
            Lang.get("user.weight")
        };
    }

    protected Object[][] getTableData(ArrayList<Utilisateur> membres) {
        Object[][] data = new Object[membres.size()][8];

        for (int i = 0; i < membres.size(); i++) {
            Utilisateur membre = membres.get(i);

            data[i] = new Object[] {
                membre.getId(),
                membre.getLogin(),
                membre.getNom(),
                membre.getPrenom(),
                membre.getTelephone(),
                membre.getDateNaissance(),
                membre.getAdresse(),
                membre.getPoids()
            };
        }

        return data;
    }

    public void refreshTable(ArrayList<Utilisateur> membres) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        for (Utilisateur membre : membres) {
            model.addRow(new Object[] {
                membre.getId(),
                membre.getLogin(),
                membre.getNom(),
                membre.getPrenom(),
                membre.getTelephone(),
                membre.getDateNaissance(),
                membre.getAdresse(),
                membre.getPoids()
            });
        }
    }

    public void showEditForm(Utilisateur membre) {
        formMode = "EDIT";
        editedId = membre.getId();

        identifiantField.setText(membre.getLogin());
        nomField.setText(membre.getNom());
        prenomField.setText(membre.getPrenom());
        dateNaissanceField.setText(membre.getDateNaissance().toString());
        telField.setText(membre.getTelephone());
        adresseField.setText(membre.getAdresse());
        poidsField.setText(String.valueOf(membre.getPoids()));

        refreshUIText();

        cardLayout.show(container, "form");
    }

    protected JPanel createFormulaire() {
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

        form.add(nomLabel);
        form.add(nomField);

        form.add(prenomLabel);
        form.add(prenomField);

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

        submitButton = new CustomButton(Lang.get("button.create"),UIConstants.emeraldGreen);

        form.add(new JLabel());
        form.add(submitButton);

        refreshTooltips();

        return form;
    }

    public void refreshUIText() {
        titleLabel.setText(Lang.get("manage.members"));

        ajouterButton.setText(Lang.get("button.add"));
        retourButton.setText(Lang.get("button.back"));

        formTitle.setText(isEditMode() ? Lang.get("user.edit") : Lang.get("user.create"));

        submitButton.setText(isEditMode() ? Lang.get("button.save") : Lang.get("button.create"));

        identifiantLabel.setText(Lang.get("user.username"));
        nomLabel.setText(Lang.get("user.lastname"));
        prenomLabel.setText(Lang.get("user.firstname"));
        dateNaissanceLabel.setText(Lang.get("user.birthdate"));
        telLabel.setText(Lang.get("user.tel"));
        adresseLabel.setText(Lang.get("user.address"));
        poidsLabel.setText(Lang.get("user.weight"));
        passwordLabel.setText(Lang.get("password"));

        deleteItem.setText(Lang.get("button.delete"));
        editItem.setText(Lang.get("button.edit"));

        refreshTooltips();
        refreshTableHeaders();

        revalidate();
        repaint();
    }

    private void refreshTooltips() {
        identifiantField.setToolTipText(Lang.get("username.field.tooltip"));
        telField.setToolTipText(Lang.get("tel.field.tooltip"));
        dateNaissanceField.setToolTipText(Lang.get("date.field.tooltip"));
        poidsField.setToolTipText(Lang.get("weight.field.tooltip"));
    }

    protected void clearFormulaire() {
        identifiantField.setText("");
        nomField.setText("");
        prenomField.setText("");
        dateNaissanceField.setText("");
        telField.setText("");
        adresseField.setText("");
        poidsField.setText("");
        passwordField.setText("");
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

    public boolean isEditMode() {
        return "EDIT".equals(formMode);
    }
}