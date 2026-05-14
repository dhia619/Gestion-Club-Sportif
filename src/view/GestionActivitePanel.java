package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import view.components.CustomTable;
import view.components.CustomTextField;

public class GestionActivitePanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel container;

    private JTable activitesTable;

    private JButton ajouterButton;
    private JButton submitButton;
    private JButton retourButton;

    private JTextField nomField;
    private JTextField descriptionField;
    private JTextField capaciteMaxField;
    private JTextField dateField;
    private JSpinner timeSpinner;

    private JLabel titleLabel;
    private JLabel formTitle;

    private JLabel nomLabel;
    private JLabel descriptionLabel;
    private JLabel capaciteMaxLabel;
    private JLabel dateLabel;
    private JLabel timeLabel;

    private String formMode = "ADD";
    private int editedActiviteId = -1;

    private JMenuItem deleteItem;
    private JMenuItem editItem;

    public GestionActivitePanel(ArrayList<Activite> activites) {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        container.add(createListPage(activites), "list");
        container.add(createActiviteForm(), "form");

        titleLabel = new CustomLabel(Lang.get("manage.activities"), 24);

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);

        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JPanel createTopActions() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        ajouterButton = new CustomButton(
                Lang.get("button.add"),
                UIConstants.menuButtonBackgroundColor
        );

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showAddForm();
            }
        });

        panel.add(ajouterButton);
        return panel;
    }

    private JScrollPane createTablePanel(ArrayList<Activite> activites) {
        DefaultTableModel model = new DefaultTableModel(
                getTableData(activites),
                getTableColumns()
        );

        activitesTable = new CustomTable(model);

        JScrollPane scrollPane = new JScrollPane(activitesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1, true));
        scrollPane.setBackground(Color.WHITE);

        JPopupMenu popupMenu = new JPopupMenu();

        deleteItem = new JMenuItem(Lang.get("button.delete"));
        editItem = new JMenuItem(Lang.get("button.edit"));

        deleteItem.setIcon(new ImageIcon("./resources/images/delete.png"));
        editItem.setIcon(new ImageIcon("./resources/images/edit.png"));

        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        activitesTable.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e);
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e);
            }

            private void showMenu(MouseEvent e) {
                int row = activitesTable.rowAtPoint(e.getPoint());

                if (row >= 0 && !activitesTable.isRowSelected(row)) {
                    activitesTable.setRowSelectionInterval(row, row);
                }

                int selectedCount = activitesTable.getSelectedRowCount();

                editItem.setVisible(selectedCount == 1);
                deleteItem.setVisible(selectedCount >= 1);

                popupMenu.show(activitesTable, e.getX(), e.getY());
            }
        });

        return scrollPane;
    }

    private Object[] getTableColumns() {
        return new Object[] {
                "ID",
                Lang.get("activity.name"),
                Lang.get("activity.description"),
                Lang.get("activity.capacity"),
                Lang.get("activity.date"),
                Lang.get("activity.time")
        };
    }

    private Object[][] getTableData(ArrayList<Activite> activites) {
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
        DefaultTableModel model = (DefaultTableModel) activitesTable.getModel();

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

    private JPanel createListPage(ArrayList<Activite> activites) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIConstants.secondaryBackgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(createTopActions());
        panel.add(createTablePanel(activites));

        return panel;
    }

    public void showAddForm() {
        formMode = "ADD";
        editedActiviteId = -1;

        refreshUIText();
        clearForm();

        cardLayout.show(container, "form");
    }

    public void showEditForm(Activite activite) {
        formMode = "EDIT";
        editedActiviteId = activite.getId();

        nomField.setText(activite.getNom());
        descriptionField.setText(activite.getDescription());
        capaciteMaxField.setText(String.valueOf(activite.getCapaciteMax()));
        dateField.setText(activite.getHoraire().toLocalDate().toString());
        timeSpinner.setValue((Date) Timestamp.valueOf(activite.getHoraire()));

        refreshUIText();

        cardLayout.show(container, "form");
    }

    private JPanel createFormulaire() {
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

        submitButton = new CustomButton(
                Lang.get("button.create"),
                UIConstants.emeraldGreen
        );

        form.add(new JLabel());
        form.add(submitButton);

        return form;
    }

    private JPanel createActiviteForm() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 50));
        topBar.setBackground(Color.WHITE);

        retourButton = new CustomButton(
                Lang.get("button.back"),
                UIConstants.concreteGrey
        );

        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(container, "list");
            }
        });

        formTitle = new CustomLabel(
                Lang.get("activity.create"),
                UIConstants.primaryTextColor,
                20
        );

        topBar.add(retourButton);
        topBar.add(Box.createHorizontalStrut(20));
        topBar.add(formTitle);

        panel.add(topBar, BorderLayout.NORTH);
        panel.add(createFormulaire(), BorderLayout.CENTER);

        return panel;
    }

    public void refreshUIText() {
        titleLabel.setText(Lang.get("manage.activities"));

        ajouterButton.setText(Lang.get("button.add"));
        retourButton.setText(Lang.get("button.back"));

        formTitle.setText(
                isEditMode()
                        ? Lang.get("activity.edit")
                        : Lang.get("activity.create")
        );

        submitButton.setText(
                isEditMode()
                        ? Lang.get("button.save")
                        : Lang.get("button.create")
        );

        nomLabel.setText(Lang.get("activity.name"));
        descriptionLabel.setText(Lang.get("activity.description"));
        capaciteMaxLabel.setText(Lang.get("activity.capacity"));
        dateLabel.setText(Lang.get("activity.date"));
        timeLabel.setText(Lang.get("activity.time"));

        deleteItem.setText(Lang.get("button.delete"));
        editItem.setText(Lang.get("button.edit"));

        refreshTableHeaders();

        revalidate();
        repaint();
    }

    private void refreshTableHeaders() {
        DefaultTableModel model = (DefaultTableModel) activitesTable.getModel();
        model.setColumnIdentifiers(getTableColumns());
    }

    public void clearForm() {
        nomField.setText("");
        descriptionField.setText("");
        capaciteMaxField.setText("");
        dateField.setText("");
        timeSpinner.setValue(new Date());
    }

    public boolean isEditMode() {
        return formMode.equals("EDIT");
    }

    public int getEditedActiviteId() {
        return editedActiviteId;
    }

    public JButton getSubmitActiviteFormButton() {
        return submitButton;
    }

    public JTable getActivitesTable() {
        return activitesTable;
    }

    public JMenuItem getDeleteItem() {
        return deleteItem;
    }

    public JMenuItem getEditItem() {
        return editItem;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public int[] getSelectedActiviteIds() {
        int[] rows = activitesTable.getSelectedRows();
        int[] ids = new int[rows.length];

        for (int i = 0; i < rows.length; i++) {
            ids[i] = (int) activitesTable.getValueAt(rows[i], 0);
        }

        return ids;
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