package controller;

import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ActiviteRow;
import model.MembreActifRow;
import service.ActiviteService;
import service.InscriptionService;
import util.Lang;
import view.admin.GestionSuiviPanel;

public class GestionSuiviController {
    
    private GestionSuiviPanel view;
    private ArrayList<ActiviteRow> activiteRows = new ArrayList<ActiviteRow>();
    private ArrayList<MembreActifRow> membreActifRows = new ArrayList<MembreActifRow>();
    private ActiviteService activiteService = new ActiviteService();
    private InscriptionService inscriptionService = new InscriptionService();

    public GestionSuiviController() {
        activiteRows = activiteService.getAllActivites();
        membreActifRows = inscriptionService.getMembresPlusActifs();
        view = new GestionSuiviPanel(activiteRows, membreActifRows);

        view.getSuiviActivitePanel().getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStatutFilter();
            }
        });

        view.getSuiviActivitePanel().getFilterActivityStatusComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleStatutFilter();
            }
        });

        view.getMembreActifPanel().getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                membreActifRows = inscriptionService.getMembresPlusActifs();
                refreshMembreActifTable(membreActifRows);
            }
        });
    }

    public void refreshSuiviActiviteTable(ArrayList<ActiviteRow> activiteRows) {
        view.getSuiviActivitePanel().refreshTable(activiteRows);
    }
    
    private void handleStatutFilter() {
        String statut = (String) view.getSuiviActivitePanel().getFilterActivityStatusComboBox().getSelectedItem();
        if (statut == null) {
            return;
        }
        if (statut.equals(Lang.get("filter.all"))) {
            activiteRows = activiteService.getAllActivites();
        } else if (statut.equals(Lang.get("COMPLET"))) {
            activiteRows = activiteService.getActivitesComplets();
        } else if (statut.equals(Lang.get("DISPONIBLE"))) {
            activiteRows = activiteService.getActivitesDisponibles();
        }
        refreshSuiviActiviteTable(activiteRows);
    }

    public void refreshMembreActifTable(ArrayList<MembreActifRow> membreActifRows) {
        view.getMembreActifPanel().refreshTable(membreActifRows);
    }

    public GestionSuiviPanel getView() {
        return view;
    }
}
