package view.admin;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.MembreActifRow;
import util.Lang;
import util.UIConstants;
import view.components.CustomButton;
import view.components.TableWithActionsPanel;

public class MembreActifPanel extends TableWithActionsPanel<MembreActifRow>{
        
    private JButton refreshButton;
    
    public MembreActifPanel(ArrayList<MembreActifRow> membreActifRows) {
        
        super(Lang.get("members.active"), membreActifRows);
        titleLabel.setText("");
        
        refreshButton = new CustomButton(Lang.get("button.refresh"),UIConstants.menuButtonBackgroundColor);

        addActionButton(refreshButton);
    }

    protected Object[] getTableColumns() {
        return new Object[] {
            "ID",
            Lang.get("user.firstname"),
            Lang.get("user.lastname"),
            Lang.get("user.tel"),
            Lang.get("registrations.accepted.number")
        };
    }

    protected Object[][] getTableData(ArrayList<MembreActifRow> membreActifRows) {
        Object[][] data = new Object[membreActifRows.size()][5];

        for (int i = 0; i < membreActifRows.size(); i++) {
            MembreActifRow a = membreActifRows.get(i);

            data[i] = toTableRow(a);
        }
        return data;
    }

    public void refreshTable(ArrayList<MembreActifRow> membreActifRows) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Object[] row : getTableData(membreActifRows)) {
            model.addRow(row);
        }
    }

    private Object[] toTableRow(MembreActifRow membreActifRow) {
        return new Object[] {
            membreActifRow.getMembre().getId(),
            membreActifRow.getMembre().getNom(),
            membreActifRow.getMembre().getPrenom(),
            membreActifRow.getMembre().getTelephone(),
            membreActifRow.getNbInscriptionsAccepte()
        };
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JTable getTable() {
        return table;
    }

    public void refreshUIText() {

        refreshButton.setText(Lang.get("button.refresh"));

        refreshTableHeaders();
        revalidate();
        repaint();
    }
}
