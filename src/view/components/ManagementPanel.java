package view.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.MouseEvent;

import util.Lang;
import util.UIConstants;

public abstract class ManagementPanel<T> extends JPanel {

    protected CardLayout cardLayout;
    protected JPanel container;
    protected ActionsPanel actionsPanel;
    protected JTable table;

    protected JButton ajouterButton;
    protected JButton submitButton;
    protected JButton retourButton;
    protected JButton refreshButton;

    protected JLabel titleLabel;
    protected JLabel formTitle;

    protected String formMode = "ADD";
    protected int editedId = -1;

    protected JMenuItem deleteItem;
    protected JMenuItem editItem;

    public ManagementPanel(String titleKey, ArrayList<T> items) {
        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        container.add(createTablePage(items), "list");
        container.add(createFormPage(), "form");

        titleLabel = new CustomLabel(Lang.get(titleKey), 24);

        setLayout(new BorderLayout());
        add(titleLabel, BorderLayout.NORTH);
        add(container, BorderLayout.CENTER);
    }

    private JPanel createTablePage(ArrayList<T> items) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIConstants.secondaryBackgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        actionsPanel = new ActionsPanel();

        ajouterButton = new CustomButton(Lang.get("button.add"), UIConstants.emeraldGreen);
        refreshButton = new CustomButton(Lang.get("button.refresh"), UIConstants.menuButtonBackgroundColor);

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddForm();
            }
        });

        actionsPanel.addComponent(ajouterButton);
        actionsPanel.addComponent(refreshButton);
        panel.add(actionsPanel);
        panel.add(createTablePanel(items));

        return panel;
    }

    private JScrollPane createTablePanel(ArrayList<T> items) {
        DefaultTableModel model = new DefaultTableModel(
            getTableData(items),
            getTableColumns()
        );

        table = new CustomTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPopupMenu popupMenu = new JPopupMenu();

        deleteItem = new JMenuItem(Lang.get("button.delete"));
        editItem = new JMenuItem(Lang.get("button.edit"));
        deleteItem.setIcon(new ImageIcon("./resources/images/delete.png"));
        editItem.setIcon(new ImageIcon("./resources/images/edit.png"));
        popupMenu.add(editItem);
        popupMenu.add(deleteItem);

        table.addMouseListener(new MouseAdapter() {
            private void showMenu(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());

                if (row >= 0 && !table.isRowSelected(row)) {
                    table.setRowSelectionInterval(row, row);
                }

                editItem.setVisible(table.getSelectedRowCount() == 1);
                deleteItem.setVisible(table.getSelectedRowCount() >= 1);

                popupMenu.show(table, e.getX(), e.getY());
            }

            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e);
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showMenu(e);
            }
        });

        return scrollPane;
    }

    private JPanel createFormPage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JPanel topBar = new JPanel();
        topBar.setLayout(new BoxLayout(topBar, BoxLayout.X_AXIS));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        topBar.setBackground(Color.WHITE);

        retourButton = new CustomButton(Lang.get("button.back"), UIConstants.concreteGrey);
        retourButton.addActionListener(e -> cardLayout.show(container, "list"));

        formTitle = new CustomLabel("", UIConstants.primaryTextColor, 20);

        topBar.add(retourButton);
        topBar.add(Box.createHorizontalStrut(20));
        topBar.add(formTitle);

        panel.add(topBar, BorderLayout.NORTH);
        panel.add(createFormulaire(), BorderLayout.CENTER);

        return panel;
    }

    public void showAddForm() {
        formMode = "ADD";
        editedId = -1;
        clearFormulaire();
        refreshUIText();
        cardLayout.show(container, "form");
    }

    public boolean isEditMode() {
        return formMode.equals("EDIT");
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

    public int getEditedId() {
        return editedId;
    }

    public JButton getSubmitFormButton() {
        return submitButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JTable getTable() {
        return table;
    }

    public int[] getSelectedIds() {
        int[] rows = table.getSelectedRows();
        int[] ids = new int[rows.length];

        for (int i = 0; i < rows.length; i++) {
            ids[i] = (int) table.getValueAt(rows[i], 0);
        }

        return ids;
    }

    protected void refreshTableHeaders() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setColumnIdentifiers(getTableColumns());
    }

    protected abstract Object[] getTableColumns();
    protected abstract Object[][] getTableData(ArrayList<T> items);
    protected abstract JPanel createFormulaire();
    protected abstract void clearFormulaire();
    public abstract void refreshTable(ArrayList<T> items);
    public abstract void refreshUIText();
}