package view.admin;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.ActiviteRow;
import util.Lang;
import util.UIConstants;
import view.components.CustomButton;
import view.components.TableWithActionsPanel;

public class SuiviActivitePanel extends TableWithActionsPanel<ActiviteRow>{
        
    private JButton refreshButton;
    private JComboBox<String> filterActivityStatusComboBox;
    
    public SuiviActivitePanel(ArrayList<ActiviteRow> activiteRows) {
        
        super(Lang.get("monitor.activities"), activiteRows);
        titleLabel.setText("");
        filterActivityStatusComboBox = new JComboBox<>(new String[] {
            Lang.get("filter.all"),
            Lang.get("COMPLET"),
            Lang.get("DISPONIBLE")
        });
        filterActivityStatusComboBox.setPreferredSize(new Dimension(90, 35));
        
        refreshButton = new CustomButton(Lang.get("button.refresh"), UIConstants.menuButtonBackgroundColor);

        addActionButton(refreshButton);
        actionsPanel.addComponent(filterActivityStatusComboBox);
    }

    protected Object[] getTableColumns() {
        return new Object[] {
            "ID",
            Lang.get("activity.name"),
            Lang.get("activity.date"),
            Lang.get("activity.time"),
            Lang.get("activity.capacity"),
            Lang.get("activity.participants"),
            Lang.get("activity.remaining.places"),
            Lang.get("activity.status")
        };
    }

    protected Object[][] getTableData(ArrayList<ActiviteRow> activiteRows) {
        Object[][] data = new Object[activiteRows.size()][8];

        for (int i = 0; i < activiteRows.size(); i++) {
            ActiviteRow a = activiteRows.get(i);

            data[i] = toTableRow(a);
        }
        return data;
    }

    public void refreshTable(ArrayList<ActiviteRow> activiteRows) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Object[] row : getTableData(activiteRows)) {
            model.addRow(row);
        }
    }

    private Object[] toTableRow(ActiviteRow activiteRow) {
        return new Object[] {
            activiteRow.getActivite().getId(),
            activiteRow.getActivite().getNom(),
            activiteRow.getActivite().getHoraire().toLocalDate().toString(),
            activiteRow.getActivite().getHoraire().toLocalTime().toString().substring(0, 5),
            activiteRow.getActivite().getCapaciteMax(),
            activiteRow.getActivite().getCapaciteMax() - activiteRow.getPlacesRestantes(),
            activiteRow.getPlacesRestantes(),
            Lang.get(activiteRow.getStatut())
        };
    }

    public JComboBox<String> getFilterActivityStatusComboBox() {
        return filterActivityStatusComboBox;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JTable getTable() {
        return table;
    }

    public void refreshUIText() {

        filterActivityStatusComboBox.removeAllItems();
        filterActivityStatusComboBox.addItem(Lang.get("filter.all"));
        filterActivityStatusComboBox.addItem(Lang.get("COMPLET"));
        filterActivityStatusComboBox.addItem(Lang.get("DISPONIBLE"));

        refreshButton.setText(Lang.get("button.refresh"));

        refreshTableHeaders();
        revalidate();
        repaint();
    }
}
