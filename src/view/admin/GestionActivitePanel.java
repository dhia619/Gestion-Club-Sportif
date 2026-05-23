package view.admin;

import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Activite;
import util.Lang;
import util.UIConstants;
import view.components.CustomButton;
import view.components.CustomLabel;
import view.components.CustomSpinner;
import view.components.CustomTextField;
import view.components.ManagementPanel;

public class GestionActivitePanel extends ManagementPanel<Activite> {

    private JTextField nomField;
    private JTextField descriptionField;
    private JTextField capaciteMaxField;
    private JTextField dateField;
    private JSpinner timeSpinner;

    private JLabel nomLabel;
    private JLabel descriptionLabel;
    private JLabel capaciteMaxLabel;
    private JLabel dateLabel;
    private JLabel timeLabel;

    public GestionActivitePanel(ArrayList<Activite> activites) {
        super("manage.activities", activites);
    }

    protected Object[] getTableColumns() {
        return new Object[] {
            "ID",
            Lang.get("activity.name"),
            Lang.get("activity.description"),
            Lang.get("activity.capacity"),
            Lang.get("activity.date"),
            Lang.get("activity.time")
        };
    }

    protected Object[][] getTableData(ArrayList<Activite> activites) {
        Object[][] data = new Object[activites.size()][6];

        for (int i = 0; i < activites.size(); i++) {
            Activite activite = activites.get(i);

            data[i] = new Object[] {
                activite.getId(),
                activite.getNom(),
                activite.getDescription(),
                activite.getCapaciteMax(),
                activite.getHoraire().toLocalDate().toString(),
                activite.getHoraire().toLocalTime().toString().substring(0, 5)
            };
        }

        return data;
    }

    public void refreshTable(ArrayList<Activite> activites) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        for (Activite activite : activites) {
            model.addRow(new Object[] {
                activite.getId(),
                activite.getNom(),
                activite.getDescription(),
                activite.getCapaciteMax(),
                activite.getHoraire().toLocalDate().toString(),
                activite.getHoraire().toLocalTime().toString().substring(0, 5)
            });
        }
    }

    public void showEditForm(Activite activite) {
        formMode = "EDIT";
        editedId = activite.getId();

        nomField.setText(activite.getNom());
        descriptionField.setText(activite.getDescription());
        capaciteMaxField.setText(String.valueOf(activite.getCapaciteMax()));
        dateField.setText(activite.getHoraire().toLocalDate().toString());
        timeSpinner.setValue((Date) Timestamp.valueOf(activite.getHoraire()));

        refreshUIText();

        cardLayout.show(container, "form");
    }

    protected JPanel createFormulaire() {
        JPanel form = new JPanel(new GridLayout(9, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        form.setBackground(UIConstants.secondaryBackgroundColor);

        nomField = new CustomTextField();
        descriptionField = new CustomTextField();
        capaciteMaxField = new CustomTextField();
        dateField = new CustomTextField();

        timeSpinner = new CustomSpinner(new SpinnerDateModel());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));

        nomLabel = new CustomLabel(Lang.get("activity.name"));
        descriptionLabel = new CustomLabel(Lang.get("activity.description"));
        capaciteMaxLabel = new CustomLabel(Lang.get("activity.capacity"));
        dateLabel = new CustomLabel(Lang.get("activity.date"));
        timeLabel = new CustomLabel(Lang.get("activity.time"));

        form.add(nomLabel);
        form.add(nomField);

        form.add(descriptionLabel);
        form.add(descriptionField);

        form.add(capaciteMaxLabel);
        form.add(capaciteMaxField);

        form.add(dateLabel);
        form.add(dateField);

        form.add(timeLabel);
        form.add(timeSpinner);

        submitButton = new CustomButton(Lang.get("button.create"), UIConstants.emeraldGreen);

        form.add(new JLabel());
        form.add(submitButton);

        return form;
    }

    public void refreshUIText() {
        titleLabel.setText(Lang.get("manage.activities"));

        ajouterButton.setText(Lang.get("button.add"));
        retourButton.setText(Lang.get("button.back"));

        formTitle.setText(isEditMode() ? Lang.get("activity.edit") : Lang.get("activity.create"));

        submitButton.setText(isEditMode() ? Lang.get("button.save") : Lang.get("button.create"));

        nomLabel.setText(Lang.get("activity.name"));
        descriptionLabel.setText(Lang.get("activity.description"));
        capaciteMaxLabel.setText(Lang.get("activity.capacity"));
        dateLabel.setText(Lang.get("activity.date"));
        timeLabel.setText(Lang.get("activity.time"));

        deleteItem.setText(Lang.get("button.delete"));
        editItem.setText(Lang.get("button.edit"));

        refreshTooltips();
        refreshTableHeaders();

        revalidate();
        repaint();
    }

    private void refreshTooltips() {
        dateField.setToolTipText(Lang.get("date.field.tooltip"));
        timeSpinner.setToolTipText(Lang.get("time.field.tooltip"));
    }

    protected void clearFormulaire() {
        nomField.setText("");
        descriptionField.setText("");
        capaciteMaxField.setText("");
        dateField.setText("");
        timeSpinner.setValue(new Date());
    }

    public String getNom(){
        return nomField.getText();
    }

    public String getDescription(){
        return descriptionField.getText();
    }

    public String getCapaciteMax(){
        return capaciteMaxField.getText();
    }

    public String getDate(){
        return dateField.getText();
    }

    public Date getHoraire() {
        return (Date) timeSpinner.getValue();
    }
}