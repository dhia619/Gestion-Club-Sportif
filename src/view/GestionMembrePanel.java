package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;

import model.Utilisateur;
import util.Lang;
import util.UIConstants;
import view.components.CustomButton;
import view.components.CustomLabel;
import view.components.CustomPasswordField;
import view.components.CustomTable;
import view.components.CustomTextField;

public class GestionMembrePanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel container;

    private JTable membresTable;

    private JButton ajouterButton;
    private JButton submitButton;
    private JButton retourButton;

    private JTextField identifiantField;
    private JTextField nomField;
    private JTextField prenomField;
    private JTextField dateNaissanceField;
    private JTextField telField;
    private JTextField adresseField;
    private JTextField poidsField;
    private JPasswordField passwordField;

    private JLabel titleLabel;
    private JLabel formTitle;

    private JLabel identifiantLabel;
    private JLabel nomLabel;
    private JLabel prenomLabel;
    private JLabel dateNaissanceLabel;
    private JLabel telLabel;
    private JLabel adresseLabel;
    private JLabel poidsLabel;
    private JLabel passwordLabel;

    private String formMode = "ADD";
    private int editedMemberId = -1;

    private JMenuItem deleteItem;
    private JMenuItem editItem;

    public GestionMembrePanel(ArrayList<Utilisateur> membres) {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        container.add(createListPage(membres), "list");
        container.add(createMembreForm(), "form");

        titleLabel = new CustomLabel(Lang.get("manage.members"), 24);

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
                UIConstants.menuButtonBackgroundColor,
                Color.WHITE
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

    private JScrollPane createTablePanel(ArrayList<Utilisateur> membres) {
        DefaultTableModel model = new DefaultTableModel(
                getTableData(membres),
                getTableColumns()
        );

        membresTable = new CustomTable(model);

        JScrollPane scrollPane = new JScrollPane(membresTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1, true));
        scrollPane.setBackground(Color.WHITE);

        JPopupMenu popupMenu = new JPopupMenu();

        deleteItem = new JMenuItem(Lang.get("button.delete"));
        editItem = new JMenuItem(Lang.get("button.edit"));

        deleteItem.setIcon(new ImageIcon("./resources/images/delete.png"));
        editItem.setIcon(new ImageIcon("./resources/images/edit.png"));

        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        membresTable.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e);
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e);
            }

            private void showMenu(MouseEvent e) {
                int row = membresTable.rowAtPoint(e.getPoint());

                if (row >= 0 && !membresTable.isRowSelected(row)) {
                    membresTable.setRowSelectionInterval(row, row);
                }

                int selectedCount = membresTable.getSelectedRowCount();

                editItem.setVisible(selectedCount == 1);
                deleteItem.setVisible(selectedCount >= 1);

                popupMenu.show(membresTable, e.getX(), e.getY());
            }
        });

        return scrollPane;
    }

    private Object[] getTableColumns() {
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

    private Object[][] getTableData(ArrayList<Utilisateur> membres) {
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
        DefaultTableModel model = (DefaultTableModel) membresTable.getModel();

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

    private JPanel createListPage(ArrayList<Utilisateur> membres) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIConstants.secondaryBackgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(createTopActions());
        panel.add(createTablePanel(membres));

        return panel;
    }

    public void showAddForm() {
        formMode = "ADD";
        editedMemberId = -1;

        refreshUIText();
        clearForm();

        cardLayout.show(container, "form");
    }

    public void showEditForm(Utilisateur membre) {
        formMode = "EDIT";
        editedMemberId = membre.getId();

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

    private JPanel createFormulaire() {
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

    private JPanel createMembreForm() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));
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
                Lang.get("user.create"),
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
        titleLabel.setText(Lang.get("manage.members"));

        ajouterButton.setText(Lang.get("button.add"));
        retourButton.setText(Lang.get("button.back"));

        formTitle.setText(
                isEditMode()
                        ? Lang.get("user.edit")
                        : Lang.get("user.create")
        );

        submitButton.setText(
                isEditMode()
                        ? Lang.get("button.save")
                        : Lang.get("button.create")
        );

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
        dateNaissanceField.setToolTipText(Lang.get("birthdate.field.tooltip"));
        poidsField.setToolTipText(Lang.get("weight.field.tooltip"));
    }

    private void refreshTableHeaders() {
        DefaultTableModel model = (DefaultTableModel) membresTable.getModel();
        model.setColumnIdentifiers(getTableColumns());
    }

    public void clearForm() {
        identifiantField.setText("");
        nomField.setText("");
        prenomField.setText("");
        dateNaissanceField.setText("");
        telField.setText("");
        adresseField.setText("");
        poidsField.setText("");
        passwordField.setText("");
    }

    public boolean isEditMode() {
        return formMode.equals("EDIT");
    }

    public int getEditedMemberId() {
        return editedMemberId;
    }

    public JButton getSubmitMembreFormButton() {
        return submitButton;
    }

    public JTable getMembresTable() {
        return membresTable;
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

    public int[] getSelectedMemberIds() {
        int[] rows = membresTable.getSelectedRows();
        int[] ids = new int[rows.length];

        for (int i = 0; i < rows.length; i++) {
            ids[i] = (int) membresTable.getValueAt(rows[i], 0);
        }

        return ids;
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