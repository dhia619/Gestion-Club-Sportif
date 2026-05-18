package view.membre;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.InscriptionRow;
import util.Lang;
import util.UIConstants;
import view.components.CustomButton;
import view.components.TableWithActionsPanel;

public class ConsulterInscriptionPanel extends TableWithActionsPanel<InscriptionRow> {

    private JButton annulerInscriptionButton;
    private JButton refreshButton;

    public ConsulterInscriptionPanel(ArrayList<InscriptionRow> inscriptionRows) {
        super(Lang.get("my.registrations"), inscriptionRows);

        annulerInscriptionButton = new CustomButton(Lang.get("button.cancel"),UIConstants.alizarinRed);    
        refreshButton = new CustomButton(Lang.get("button.refresh"),UIConstants.menuButtonBackgroundColor);

        hideColumn(0);

        addActionButton(annulerInscriptionButton);
        addActionButton(refreshButton);
    }

    protected Object[] getTableColumns() {
        return new Object[] {
            "ID",
            Lang.get("activity.name"),
            Lang.get("activity.description"),
            Lang.get("activity.date"),
            Lang.get("activity.time"),
            Lang.get("activity.status")
        };
    }

    protected Object[][] getTableData(ArrayList<InscriptionRow> inscriptionRows) {
        Object[][] data = new Object[inscriptionRows.size()][6];

        for (int i = 0; i < inscriptionRows.size(); i++) {
            InscriptionRow a = inscriptionRows.get(i);

            data[i] = toTableRow(a);
        }
        return data;
    }

    public void refreshTable(ArrayList<InscriptionRow> inscriptionRows) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        for (Object[] row : getTableData(inscriptionRows)) {
            model.addRow(row);
        }
    }

    private Object[] toTableRow(InscriptionRow inscriptionRow) {
        return new Object[] {
            inscriptionRow.getInscription().getId(),
            inscriptionRow.getActivite().getNom(),
            inscriptionRow.getActivite().getDescription(),
            inscriptionRow.getInscription().getDateInscription().toLocalDate().toString(),
            inscriptionRow.getInscription().getDateInscription().toLocalTime().toString().substring(0, 5),
            Lang.get(inscriptionRow.getInscription().getStatut().toString())
        };
    }

    public int getSelectedActiviteId() {

        int viewRow = table.getSelectedRow();

        if (viewRow == -1) {
            return -1;
        }

        int modelRow = table.convertRowIndexToModel(viewRow);

        return (int) table.getModel().getValueAt(modelRow, 0);
    }

    public JButton getAnnulerInscriptionButton() {
        return annulerInscriptionButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JTable getTable() {
        return table;
    }

    public void refreshUIText() {

        titleLabel.setText(Lang.get("my.registrations"));
        annulerInscriptionButton.setText(Lang.get("button.cancel"));
        refreshButton.setText(Lang.get("button.refresh"));

        refreshTableHeaders();
        revalidate();
        repaint();
    }
}