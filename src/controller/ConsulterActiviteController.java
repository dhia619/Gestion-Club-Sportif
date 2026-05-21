package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.Utilisateur;
import service.ActiviteService;
import service.InscriptionService;
import service.ServiceResult;
import model.ActiviteRow;
import view.components.PopUp;
import view.membre.ConsulterActivitePanel;

public class ConsulterActiviteController {

    private ConsulterActivitePanel view;
    private ArrayList<ActiviteRow> activitesDisponibles = new ArrayList<ActiviteRow>();
    private InscriptionService inscriptionService = new InscriptionService();
    private ActiviteService activiteService = new ActiviteService();

    public ConsulterActiviteController(Utilisateur utilisateur) {

        activitesDisponibles = activiteService.getActivitesDisponiblesPourMembre(utilisateur.getId());

        view = new ConsulterActivitePanel(activitesDisponibles);

        view.getInscriptionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServiceResult result = inscriptionService.inscrire(utilisateur, view.getSelectedId());
                if (!result.getSuccess()) {
                    PopUp.showError(view, result.getMessage());
                }
                else {
                    refreshTable(utilisateur.getId());
                    PopUp.showInfo(view, result.getMessage());
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
        activitesDisponibles = activiteService.getActivitesDisponiblesPourMembre(utilisateurId);
        view.refreshTable(activitesDisponibles);
    }

    public ConsulterActivitePanel getView() {
        return view;
    }
}
