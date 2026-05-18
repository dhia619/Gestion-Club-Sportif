package view.membre;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.ActiviteDisponibleRow;
import util.Lang;
import util.UIConstants;
import view.components.CustomButton;
import view.components.CustomTable;
import view.components.ActionsPanel;

public class ConsulterActivitePanel extends JPanel {

    private JTable activitesTable;
    private JButton inscriptionButton;
    private JButton refreshButton;

    private ActionsPanel actionsPanel;

    public ConsulterActivitePanel(ArrayList<ActiviteDisponibleRow> activitesDisponibles) {
        setLayout(new BorderLayout());
        setBackground(UIConstants.secondaryBackgroundColor);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        actionsPanel = new ActionsPanel();

        inscriptionButton = new CustomButton(Lang.get("button.register"),UIConstants.emeraldGreen);    
        refreshButton = new CustomButton(Lang.get("button.refresh"),UIConstants.menuButtonBackgroundColor);

        actionsPanel.addComponent(inscriptionButton);
        actionsPanel.addComponent(refreshButton);
        
        add(actionsPanel, BorderLayout.NORTH);
        add(createTablePanel(activitesDisponibles), BorderLayout.CENTER);
    }

    private JScrollPane createTablePanel(ArrayList<ActiviteDisponibleRow> activitesDisponibles) {
        DefaultTableModel model = new DefaultTableModel(
                getTableData(activitesDisponibles),
                getTableColumns()
        );

        activitesTable = new CustomTable(model);
        activitesTable.removeColumn(activitesTable.getColumnModel().getColumn(0));

        JScrollPane scrollPane = new JScrollPane(activitesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(UIConstants.secondaryBackgroundColor, 1, true));

        return scrollPane;
    }

    private Object[] getTableColumns() {
        return new Object[] {
                "ID",
                Lang.get("activity.name"),
                Lang.get("activity.description"),
                Lang.get("activity.capacity"),
                Lang.get("activity.remaining.places"),
                Lang.get("activity.date"),
                Lang.get("activity.time"),
                Lang.get("activity.status")
        };
    }

    protected Object[][] getTableData(ArrayList<ActiviteDisponibleRow> activitesDisponibles) {
        Object[][] data = new Object[activitesDisponibles.size()][8];

        for (int i = 0; i < activitesDisponibles.size(); i++) {
            ActiviteDisponibleRow a = activitesDisponibles.get(i);

            data[i] = toTableRow(a);
        }
        return data;
    }

    public void refreshTable(ArrayList<ActiviteDisponibleRow> activitesDisponibles) {
        DefaultTableModel model = (DefaultTableModel) activitesTable.getModel();
        model.setRowCount(0);

        for (Object[] row : getTableData(activitesDisponibles)) {
            model.addRow(row);
        }
    }

    private Object[] toTableRow(ActiviteDisponibleRow a) {
        return new Object[] {
            a.getActivite().getId(),
            a.getActivite().getNom(),
            a.getActivite().getDescription(),
            a.getActivite().getCapaciteMax(),
            a.getPlacesRestantes(),
            a.getActivite().getHoraire().toLocalDate().toString(),
            a.getActivite().getHoraire().toLocalTime().toString().substring(0, 5),
            a.getStatut()
        };
    }

    public int getSelectedActiviteId() {

        int viewRow = activitesTable.getSelectedRow();

        if (viewRow == -1) {
            return -1;
        }

        int modelRow = activitesTable.convertRowIndexToModel(viewRow);

        return (int) activitesTable.getModel().getValueAt(modelRow, 0);
    }

    public JButton getInscriptionButton() {
        return inscriptionButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JTable getActivitesTable() {
        return activitesTable;
    }

    protected void refreshTableHeaders() {
        DefaultTableModel model = (DefaultTableModel) activitesTable.getModel();
        model.setColumnIdentifiers(getTableColumns());
        activitesTable.removeColumn(activitesTable.getColumnModel().getColumn(0));
    }

    public void refreshUIText() {

        inscriptionButton.setText(Lang.get("button.register"));
        refreshButton.setText(Lang.get("button.refresh"));

        refreshTableHeaders();

        revalidate();
        repaint();
    }
}