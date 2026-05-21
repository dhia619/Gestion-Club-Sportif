package view.membre;

import java.util.ArrayList;

import javax.swing.*;

import model.ActiviteRow;
import util.Lang;
import util.UIConstants;
import view.components.CustomButton;
import view.components.TableWithActionsPanel;

public class ConsulterActivitePanel extends TableWithActionsPanel<ActiviteRow> {

    private JButton inscriptionButton;
    private JButton refreshButton;

    public ConsulterActivitePanel(ArrayList<ActiviteRow> activitesDisponibles) {
        super(Lang.get("available.activities"), activitesDisponibles);
        inscriptionButton = new CustomButton(Lang.get("button.register"),UIConstants.emeraldGreen);    
        refreshButton = new CustomButton(Lang.get("button.refresh"),UIConstants.menuButtonBackgroundColor);
        addActionButton(inscriptionButton);
        addActionButton(refreshButton);
        hideColumn(7);
    }

    protected Object[] getTableColumns() {
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

    protected Object[][] getTableData(ArrayList<ActiviteRow> activitesDisponibles) {
        Object[][] data = new Object[activitesDisponibles.size()][8];

        for (int i = 0; i < activitesDisponibles.size(); i++) {
            ActiviteRow a = activitesDisponibles.get(i);

            data[i] = toTableRow(a);
        }
        return data;
    }

    private Object[] toTableRow(ActiviteRow a) {
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

    public JButton getInscriptionButton() {
        return inscriptionButton;
    }

    public JButton getRefreshButton() {
        return refreshButton;
    }

    public void refreshUIText() {

        titleLabel.setText(Lang.get("available.activities"));
        inscriptionButton.setText(Lang.get("button.register"));
        refreshButton.setText(Lang.get("button.refresh"));

        refreshTableHeaders();

        revalidate();
        repaint();
    }
}