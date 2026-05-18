package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.Utilisateur;
import service.InscriptionService;
import service.ServiceResult;
import model.ActiviteDisponibleRow;
import view.components.PopUpHandler;
import view.membre.ConsulterActivitePanel;

public class ConsulterActiviteController {
    private ConsulterActivitePanel view;
    private ArrayList<ActiviteDisponibleRow> activitesDisponibles = new ArrayList<ActiviteDisponibleRow>();
    private InscriptionService inscriptionService = new InscriptionService();

    public ConsulterActiviteController(Utilisateur utilisateur) {

        activitesDisponibles = inscriptionService.getActivitesDisponibles(utilisateur.getId());

        view = new ConsulterActivitePanel(activitesDisponibles);

        view.getInscriptionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServiceResult result = inscriptionService.inscrire(utilisateur, view.getSelectedId());
                if (!result.getSuccess()) {
                    PopUpHandler.showError(view, result.getMessage());
                }
                else {
                    refreshTable(utilisateur.getId());
                    PopUpHandler.showInfo(view, result.getMessage());
                }
            }
        });

        view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(utilisateur.getId());
            }
        });
    }

    public void refreshTable(int utilisateurId) {
        activitesDisponibles = inscriptionService.getActivitesDisponibles(utilisateurId);
        view.refreshTable(activitesDisponibles);
    }

    public ConsulterActivitePanel getView() {
        return view;
    }
}
