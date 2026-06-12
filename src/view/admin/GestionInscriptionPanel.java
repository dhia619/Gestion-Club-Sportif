package view.admin;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.InscriptionRow;
import util.Lang;
import util.UIConstants;
import view.components.CustomButton;
import view.components.TableWithActionsPanel;

public class GestionInscriptionPanel extends TableWithActionsPanel<InscriptionRow>{
    
    private JButton accepterInscriptionButton;
    private JButton refuserInscriptionButton;
    private JButton supprimerInscriptionButton;
    private JButton refreshButton;

    public GestionInscriptionPanel(ArrayList<InscriptionRow> inscriptionRows) {
        super(Lang.get("manage.registrations"), inscriptionRows);
        accepterInscriptionButton = new CustomButton(Lang.get("button.accept"),UIConstants.emeraldGreen); 
        refuserInscriptionButton = new CustomButton(Lang.get("button.reject"),UIConstants.alizarinRed); 
        supprimerInscriptionButton = new CustomButton(Lang.get("button.delete"),UIConstants.terracotta);    
        refreshButton = new CustomButton(Lang.get("button.refresh"),UIConstants.menuButtonBackgroundColor);

        addActionButton(accepterInscriptionButton);
        addActionButton(refuserInscriptionButton);
        addActionButton(supprimerInscriptionButton);
        addActionButton(refreshButton);
    }

    protected Object[] getTableColumns() {
        return new Object[] {
            "ID",
            Lang.get("activity.name"),
            Lang.get("user.username"),
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
        String memberName = (inscriptionRow.getUtilisateur() != null) ? inscriptionRow.getUtilisateur().getLogin() : "N/A";
        String activityName = (inscriptionRow.getActivite() != null) ? inscriptionRow.getActivite().getNom() : "N/A";
        String activityDate = "N/A";
        String activityTime = "N/A";
        
        if (inscriptionRow.getActivite() != null && inscriptionRow.getActivite().getHoraire() != null) {
            activityDate = inscriptionRow.getActivite().getHoraire().toLocalDate().toString();
            activityTime = inscriptionRow.getActivite().getHoraire().toLocalTime().toString().substring(0, 5);
        }
        
        return new Object[] {
            inscriptionRow.getInscription().getId(),
            activityName,
            memberName,
            activityDate,
            activityTime,
            Lang.get(inscriptionRow.getInscription().getStatut().toString())
        };
    }
    public JButton getRefreshButton() {
        return refreshButton;
    }

    public JButton getAccepterInscriptionButton() {
        return accepterInscriptionButton;
    }

    public JButton getRefuserInscriptionButton() {
        return refuserInscriptionButton;
    }

    public JButton getSupprimerInscriptionButton() {
        return supprimerInscriptionButton;
    }

    public JTable getTable() {
        return table;
    }

    public void refreshUIText() {

        titleLabel.setText(Lang.get("manage.registrations"));
        accepterInscriptionButton.setText(Lang.get("button.accept"));
        refuserInscriptionButton.setText(Lang.get("button.reject"));
        supprimerInscriptionButton.setText(Lang.get("button.delete"));
        refreshButton.setText(Lang.get("button.refresh"));

        refreshTableHeaders();
        revalidate();
        repaint();
    }
}
