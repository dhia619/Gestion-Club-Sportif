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
        membresTable = new JTable(model);
        membresTable.setRowHeight(36);
        membresTable.setFont(new Font("Arial", Font.PLAIN, 14));
        membresTable.setShowGrid(false);
        membresTable.setIntercellSpacing(new Dimension(0, 0));
        membresTable.setSelectionBackground(UIConstants.tableSelectionBackgroundColor);
        membresTable.setSelectionForeground(UIConstants.tableSelctionForegroundColor);
        membresTable.setForeground(UIConstants.tableForegroundColor);

        membresTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(
            JTable table, 
            Object value,
            boolean isSelected, 
            boolean hasFocus,
            int row, 
            int column) {

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

        JTableHeader header = membresTable.getTableHeader();
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

        return new JScrollPane(membresTable);
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
        panel.setBackground(UIConstants.secondaryBgColor);
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
        form.setBackground(UIConstants.secondaryBgColor);

        identifiantField = new JTextField();
        nomField = new JTextField();
        prenomField = new JTextField();
        dateNaissanceField = new JTextField();
        telField = new JTextField();
        adresseField = new JTextField();
        poidsField = new JTextField();
        passwordField = new JPasswordField();

        identifiantField.setFont(UIConstants.fieldFont);
        nomField.setFont(UIConstants.fieldFont);
        prenomField.setFont(UIConstants.fieldFont);
        dateNaissanceField.setFont(UIConstants.fieldFont);
        telField.setFont(UIConstants.fieldFont);
        adresseField.setFont(UIConstants.fieldFont);
        poidsField.setFont(UIConstants.fieldFont);
        passwordField.setFont(UIConstants.fieldFont);

        identifiantField.setToolTipText("Identifiant unique à utiliser lors de la connexion.");
        telField.setToolTipText("8 chiffres");
        dateNaissanceField.setToolTipText("format : jj-mm-aaaa");
        poidsField.setToolTipText("Poids en Kg");

        form.add(createLabel("Identifiant:", UIConstants.labelFont));
        form.add(identifiantField);

        form.add(createLabel("Nom:", UIConstants.labelFont));
        form.add(nomField);

        form.add(createLabel("Prénom:", UIConstants.labelFont));
        form.add(prenomField);

        form.add(createLabel("Date de naissance:", UIConstants.labelFont));
        form.add(dateNaissanceField);

        form.add(createLabel("Téléphone:", UIConstants.labelFont));
        form.add(telField);

        form.add(createLabel("Adresse:", UIConstants.labelFont));
        form.add(adresseField);

        form.add(createLabel("Poids:", UIConstants.labelFont));
        form.add(poidsField);

        form.add(createLabel("Mot de passe:", UIConstants.labelFont));
        form.add(passwordField);

        submitButton = new JButton();
        submitButton.setFont(UIConstants.buttonFont);
        submitButton.setFocusPainted(false);

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
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 50));
        topBar.setBackground(Color.WHITE);

        retourButton = new JButton("Retour");
        retourButton.addActionListener(e -> cardLayout.show(container, "list"));

        formTitle = new JLabel("Ajouter un membre");
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

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }
}