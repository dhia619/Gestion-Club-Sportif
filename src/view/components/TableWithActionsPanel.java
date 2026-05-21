package view.components;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import util.UIConstants;

public abstract class TableWithActionsPanel<T> extends JPanel {

    protected JTable table;
    protected JLabel titleLabel;
    protected ActionsPanel actionsPanel;

    public TableWithActionsPanel(String title, ArrayList<T> items) {

        setLayout(new BorderLayout());
        setBackground(UIConstants.secondaryBackgroundColor);

        titleLabel = new CustomLabel(title, 24);
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);

        JPanel mainContainer = new JPanel();
        mainContainer.setOpaque(false);
        mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
        mainContainer.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        actionsPanel = new ActionsPanel();
        
        mainContainer.add(actionsPanel);
        mainContainer.add(createTablePanel(items));

        add(titleLabel, BorderLayout.NORTH);
        add(mainContainer, BorderLayout.CENTER);
    }

    private JScrollPane createTablePanel(ArrayList<T> items) {

        DefaultTableModel model = new DefaultTableModel(
            getTableData(items),
            getTableColumns()
        );

        table = new CustomTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.secondaryBackgroundColor, 1, true));

        return scrollPane;
    }

    protected void addActionButton(JButton button) {
        actionsPanel.addComponent(button);
    }

    public void refreshTable(ArrayList<T> items) {

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        model.setRowCount(0);

        for (Object[] row : getTableData(items)) {
            model.addRow(row);
        }
    }

    public int getSelectedId() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            return -1;
        }
        int modelRow = table.convertRowIndexToModel(viewRow);
        return (int) table.getModel().getValueAt(modelRow, 0);
    }

    protected void refreshTableHeaders() {
        Object[] columns = getTableColumns();
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            int modelIndex = table.getColumnModel().getColumn(i).getModelIndex();
            table.getColumnModel().getColumn(i).setHeaderValue(columns[modelIndex]);
        }
    }

    protected void hideColumn(int modelIndex) {
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            if (table.getColumnModel().getColumn(i).getModelIndex() == modelIndex) {
                table.removeColumn(table.getColumnModel().getColumn(i));
                return;
            }
        }
    }

    public JTable getTable() {
        return table;
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    protected abstract Object[] getTableColumns();

    protected abstract Object[][] getTableData(ArrayList<T> items);
}