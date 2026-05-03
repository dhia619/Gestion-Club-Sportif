package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import java.awt.event.MouseEvent;
import java.sql.Timestamp;

import javax.swing.table.DefaultTableModel;

import model.Activite;
import util.UIConstants;

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

    private String formMode = "ADD";
    private int editedActiviteId = -1;
    private JLabel formTitle;

    JMenuItem deleteItem;
    JMenuItem editItem;

    public GestionActivitePanel(ArrayList<Activite> activites) {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        container.add(createListPage(activites), "list");
        container.add(createactiviteForm(), "form");

        setLayout(new BorderLayout());
        add(container, BorderLayout.CENTER);
        
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JPanel createTopActions() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        ajouterButton = new JButton("+ Ajouter");
        ajouterButton.setFont(UIConstants.labelFont);
        ajouterButton.setForeground(Color.WHITE);
        ajouterButton.setBackground(UIConstants.primaryBgColor);
        ajouterButton.setOpaque(true);
        ajouterButton.setBorderPainted(false);
        ajouterButton.setFocusPainted(false);
        ajouterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ajouterButton.setPreferredSize(new Dimension(120, 36));

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
        String[] columns = {
            "ID", "Nom", "Description", "Capacité maximale", "Date", "Heure"
        };

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

        DefaultTableModel model = new DefaultTableModel(data, columns);
        activitesTable = new JTable(model);
        activitesTable.setRowHeight(36);
        activitesTable.setFont(new Font("Arial", Font.PLAIN, 14));
        activitesTable.setShowGrid(false);
        activitesTable.setIntercellSpacing(new Dimension(0, 0));
        activitesTable.setSelectionBackground(UIConstants.tableSelectionBackgroundColor);
        activitesTable.setSelectionForeground(UIConstants.tableSelctionForegroundColor);
        activitesTable.setForeground(UIConstants.tableForegroundColor);

        activitesTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus,
                                                    int row, int column) {

            Component c = super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                c.setBackground(row % 2 == 0
                    ? UIConstants.tableRowEvenColor
                    : UIConstants.tableRowOddColor);
            }

            return c;
        }
    });

        JTableHeader header = activitesTable.getTableHeader();
        header.setBackground(UIConstants.tableHeaderBackgroundColor);
        header.setForeground(UIConstants.tableHeaderForegroundColor);
        header.setFont(UIConstants.fieldFont);


        JPopupMenu popupMenu = new JPopupMenu();

        deleteItem = new JMenuItem("Supprimer");
        editItem = new JMenuItem("Modifier");

        ImageIcon deleteIcon = new ImageIcon("./resources/images/delete.png");
        ImageIcon editIcon = new ImageIcon("./resources/images/edit.png");

        editItem.setIcon(editIcon);
        deleteItem.setIcon(deleteIcon);

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

        return new JScrollPane(activitesTable);
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

    public JMenuItem getDeleteItem() {
        return deleteItem;
    }

    public JMenuItem getEditItem() {
        return editItem;
    } 

    private JPanel createListPage(ArrayList<Activite> activites) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIConstants.secondaryBgColor);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(createTopActions());
        panel.add(createTablePanel(activites));

        return panel;
    }

    public void showAddForm() {
        formMode = "ADD";
        editedActiviteId = -1;
        formTitle.setText("Ajouter une activité");
        submitButton.setText("Ajouter");
        clearForm();
        cardLayout.show(container, "form");
    }

    public void showEditForm(Activite activite) {
        formMode = "EDIT";
        editedActiviteId = activite.getId();

        formTitle.setText("Modifier une activité");
        submitButton.setText("Enregistrer");

        nomField.setText(activite.getNom());
        descriptionField.setText(activite.getDescription());
        capaciteMaxField.setText(String.valueOf(activite.getCapaciteMax()));
        dateField.setText(activite.getHoraire().toLocalDate().toString());
        timeSpinner.setValue((Date) Timestamp.valueOf(activite.getHoraire()));

        cardLayout.show(container, "form");
    }

    public boolean isEditMode() {
        return formMode.equals("EDIT");
    }

    public int getEditedActiviteId() {
        return editedActiviteId;
    }

    public void clearForm() {
        nomField.setText("");
        descriptionField.setText("");
        capaciteMaxField.setText("");
        dateField.setText("");
        timeSpinner.setValue(new Date());
    }

    private JPanel createFormulaire() {
        JPanel form = new JPanel(new GridLayout(9, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        form.setBackground(UIConstants.secondaryBgColor);

        nomField = new JTextField();
        descriptionField = new JTextField();
        capaciteMaxField = new JTextField();
        dateField = new JTextField();
        timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));

        nomField.setFont(UIConstants.fieldFont);
        descriptionField.setFont(UIConstants.fieldFont);
        capaciteMaxField.setFont(UIConstants.fieldFont);
        dateField.setFont(UIConstants.fieldFont);
        timeSpinner.setFont(UIConstants.fieldFont);

        form.add(createLabel("Nom:", UIConstants.labelFont));
        form.add(nomField);

        form.add(createLabel("Description:", UIConstants.labelFont));
        form.add(descriptionField);

        form.add(createLabel("Capacité Maximale:", UIConstants.labelFont));
        form.add(capaciteMaxField);

        form.add(createLabel("Date:", UIConstants.labelFont));
        form.add(dateField);

        form.add(createLabel("Horaire:", UIConstants.labelFont));
        form.add(timeSpinner);

        submitButton = new JButton();
        submitButton.setFont(UIConstants.buttonFont);
        submitButton.setFocusPainted(false);

        form.add(new JLabel());
        form.add(submitButton);

        return form;
    }

    public JButton getSubmitActiviteFormButton() {
        return submitButton;
    }

    public JTable getActivitesTable() {
        return activitesTable;
    }

    public int[] getSelectedActiviteIds() {
        int[] rows = activitesTable.getSelectedRows();

        int[] ids = new int[rows.length];

        for (int i = 0; i < rows.length; i++) {
            ids[i] = (int) activitesTable.getValueAt(rows[i], 0);
        }

        return ids;
    }

    private JPanel createactiviteForm() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 50));
        topBar.setBackground(Color.WHITE);

        retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> cardLayout.show(container, "list"));

        formTitle = new JLabel("Ajouter un activite");
        formTitle.setFont(UIConstants.labelFont);

        topBar.add(retourButton);
        topBar.add(Box.createHorizontalStrut(20));
        topBar.add(formTitle);

        panel.add(topBar, BorderLayout.NORTH);
        panel.add(createFormulaire(), BorderLayout.CENTER);

        return panel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
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

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
}