package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import java.awt.event.MouseEvent;

import javax.swing.table.DefaultTableModel;

import model.Utilisateur;
import util.UIConstants;

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

    private String formMode = "ADD";
    private int editedMemberId = -1;
    private JLabel formTitle;

    JMenuItem deleteItem;
    JMenuItem editItem;

    public GestionMembrePanel(ArrayList<Utilisateur> membres) {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        container.add(createListPage(membres), "list");
        container.add(createMembreForm(), "form");

        setLayout(new BorderLayout());
        add(container, BorderLayout.CENTER);
        
        setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JPanel createTopActions() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 16, 0));

        ajouterButton = new CustomButton("Ajouter", UIConstants.menuButtonBackgroundColor, Color.WHITE);

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
        String[] columns = {
            "ID", "Identifiant", "Nom", "Prénom", "Téléphone", "Date de naissance", "Adresse", "Poids"
        };

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

        DefaultTableModel model = new DefaultTableModel(data, columns);
        membresTable = new CustomTable(model);

        JScrollPane scrollPane = new JScrollPane(membresTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 224, 230), 1, true));
        scrollPane.setBackground(Color.WHITE);

        JPopupMenu popupMenu = new JPopupMenu();

        deleteItem = new JMenuItem("Supprimer");
        editItem = new JMenuItem("Modifier");

        ImageIcon deleteIcon = new ImageIcon("./resources/images/delete.png");
        ImageIcon editIcon = new ImageIcon("./resources/images/edit.png");

        editItem.setIcon(editIcon);
        deleteItem.setIcon(deleteIcon);

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

    public JMenuItem getDeleteItem() {
        return deleteItem;
    }

    public JMenuItem getEditItem() {
        return editItem;
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
        formTitle.setText("Ajouter un membre");
        submitButton.setText("Ajouter");
        clearForm();
        cardLayout.show(container, "form");
    }

    public void showEditForm(Utilisateur membre) {
        formMode = "EDIT";
        editedMemberId = membre.getId();

        formTitle.setText("Modifier un membre");
        submitButton.setText("Enregistrer");

        identifiantField.setText(membre.getLogin());
        nomField.setText(membre.getNom());
        prenomField.setText(membre.getPrenom());
        dateNaissanceField.setText(membre.getDateNaissance().toString());
        telField.setText(membre.getTelephone());
        adresseField.setText(membre.getAdresse());
        poidsField.setText(String.valueOf(membre.getPoids()));
        passwordField.setText(membre.getMotDePasse());

        cardLayout.show(container, "form");
    }

    public boolean isEditMode() {
        return formMode.equals("EDIT");
    }

    public int getEditedMemberId() {
        return editedMemberId;
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

        identifiantField.setToolTipText("Identifiant unique à utiliser lors de la connexion.");
        telField.setToolTipText("8 chiffres");
        dateNaissanceField.setToolTipText("format : AAAA-MM-JJ");
        poidsField.setToolTipText("Poids en Kg");

        form.add(new CustomLabel("Identifiant:"));
        form.add(identifiantField);

        form.add(new CustomLabel("Nom:"));
        form.add(nomField);

        form.add(new CustomLabel("Prénom:"));
        form.add(prenomField);

        form.add(new CustomLabel("Date de naissance:"));
        form.add(dateNaissanceField);

        form.add(new CustomLabel("Téléphone:"));
        form.add(telField);

        form.add(new CustomLabel("Adresse:"));
        form.add(adresseField);

        form.add(new CustomLabel("Poids:"));
        form.add(poidsField);

        form.add(new CustomLabel("Mot de passe:"));
        form.add(passwordField);

        submitButton = new CustomButton("Ajouter", UIConstants.emeraldGreen);

        form.add(new JLabel());
        form.add(submitButton);

        return form;
    }

    public JButton getSubmitMembreFormButton() {
        return submitButton;
    }

    public JTable getMembresTable() {
        return membresTable;
    }

    public int[] getSelectedMemberIds() {
        int[] rows = membresTable.getSelectedRows();

        int[] ids = new int[rows.length];

        for (int i = 0; i < rows.length; i++) {
            ids[i] = (int) membresTable.getValueAt(rows[i], 0);
        }

        return ids;
    }

    private JPanel createMembreForm() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 50));
        topBar.setBackground(Color.WHITE);

        retourButton = new CustomButton("Retour", UIConstants.concreteGrey);
        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(container, "list");
            }
        });

        formTitle = new CustomLabel("Ajouter un membre", UIConstants.primaryTextColor, 20);

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
        return  poidsField.getText();
    }

    public String getMotDePasse(){
        return new String(passwordField.getPassword());
    }

}