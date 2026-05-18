package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.InscriptionRow;
import model.Utilisateur;
import service.InscriptionService;
import service.ServiceResult;
import util.Lang;
import view.components.PopUpHandler;
import view.membre.ConsulterInscriptionPanel;

public class ConsulterInscriptionController {
    
    private ConsulterInscriptionPanel view;
    private ArrayList<InscriptionRow> inscriptionRows;
    private InscriptionService inscriptionService = new InscriptionService();

    public ConsulterInscriptionController(Utilisateur utilisateur) {
        inscriptionRows = inscriptionService.getMesInscriptions(utilisateur.getId());
        view = new ConsulterInscriptionPanel(inscriptionRows);

        view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(utilisateur.getId());
            }
        });

        view.getAnnulerInscriptionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getSelectedId() < 0) {
                    PopUpHandler.showError(view, Lang.get("error.choose.activity"));
                    return;
                } 
                if (PopUpHandler.showConfirm(view, Lang.get("confirm.cancel.registration"))) {
                    ServiceResult result = inscriptionService.annuler(view.getSelectedId());
                    if (!result.getSuccess()) {
                        PopUpHandler.showError(view, result.getMessage());
                        return;
                    }
                    PopUpHandler.showInfo(view, result.getMessage());
                    refreshTable(utilisateur.getId());
                }
            }
        });
    }

    public void refreshTable(int utilisateurId) {
        inscriptionRows = inscriptionService.getMesInscriptions(utilisateurId);
        view.refreshTable(inscriptionRows);
    }

    public ConsulterInscriptionPanel getView() {
        return view;
    }
}
