package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import model.InscriptionRow;
import model.Utilisateur;
import service.InscriptionService;
import service.ServiceResult;
import util.Lang;
import view.admin.GestionInscriptionPanel;
import view.components.PopUp;

public class GestionInscriptionController {

    private ArrayList<InscriptionRow> inscriptionRows;
    private InscriptionService inscriptionService = new InscriptionService();
    private GestionInscriptionPanel view;

    public GestionInscriptionController(Utilisateur admin) {

        inscriptionRows = inscriptionService.getAll();
        view = new GestionInscriptionPanel(inscriptionRows);

        view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });

        view.getAccepterInscriptionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PopUp.showConfirm(view, Lang.get("confirm.accept.registration"))) {
                    ServiceResult result = inscriptionService.accepter(view.getSelectedId(), admin);
                    if (!result.getSuccess()) {
                        PopUp.showError(view, result.getMessage());
                    } else {
                        PopUp.showInfo(view, result.getMessage());
                    }
                    refreshTable();
                }
            }
        });

        view.getRefuserInscriptionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PopUp.showConfirm(view, Lang.get("confirm.reject.registration"))) {
                    ServiceResult result = inscriptionService.refuser(view.getSelectedId());
                    if (!result.getSuccess()) {
                        PopUp.showError(view, result.getMessage());
                    } else {
                        PopUp.showInfo(view, result.getMessage());
                    }
                    refreshTable();
                }
            }
        });

        view.getSupprimerInscriptionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (PopUp.showConfirm(view, Lang.get("confirm.delete.registration"))) {
                    ServiceResult result = inscriptionService.annuler(view.getSelectedId());
                    if (!result.getSuccess()) {
                        PopUp.showError(view, result.getMessage());
                    } else {
                        PopUp.showInfo(view, result.getMessage());
                    }
                    refreshTable();
                }
            }
        });
    }

    private void refreshTable() {
        inscriptionRows = inscriptionService.getAll();
        view.refreshTable(inscriptionRows);
    }

    public GestionInscriptionPanel getView() {
        return view;
    }
}