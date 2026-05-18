package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import dao.ActiviteDAO;
import model.Activite;
import util.Lang;
import view.admin.GestionActivitePanel;
import view.components.PopUp;

public class GestionActiviteController {

    private ActiviteDAO activiteDAO;
    private GestionActivitePanel view;
    private int activiteModifieeId;

    public GestionActiviteController() {

        activiteDAO = new ActiviteDAO();

        ArrayList<Activite> activites = activiteDAO.findAll();
        view = new GestionActivitePanel(activites);

        view.getSubmitFormButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.isEditMode()) {
                    modifierActivite();
                } else {
                    ajouterActivite();
                }
            }
        });

        view.getDeleteItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                supprimerActivites();
            }
        });

        view.getEditItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int[] ids = view.getSelectedIds();

                if (ids.length == 1) {
                    activiteModifieeId = ids[0];
                    showEditForm(activiteModifieeId);
                }
            }
        });

        view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
    }

    private boolean checkFields() {

        if (view.getNom().isBlank()){
            PopUp.showError(view, Lang.get("error.enter.activity.name"));
            return false;
        }
        else if (view.getDescription().isBlank()){
            PopUp.showError(view, Lang.get("error.enter.activity.description"));
            return false;
        }
        else if (view.getCapaciteMax().isBlank()){
            PopUp.showError(view, Lang.get("error.enter.activity.capacity"));
            return false;
        }
        else if (String.valueOf(view.getHoraire()).equals("")){
            PopUp.showError(view, Lang.get("error.enter.activity.schedule"));
            return false;
        }

        return true;
    }

    private LocalDateTime getHoraire() {

        LocalDate date = LocalDate.parse(view.getDate());

        Date spinnerDate = view.getHoraire();

        LocalTime time = spinnerDate.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        LocalDateTime fullDateTime = LocalDateTime.of(date, time);

        return fullDateTime;
    }

    public void ajouterActivite() {

        if (checkFields()) {

            try {

                LocalDateTime fullDateTime = getHoraire();

                Activite nouvelleActivite = new Activite(
                        view.getNom(),
                        view.getDescription(),
                        Integer.parseInt(view.getCapaciteMax()),
                        fullDateTime
                );
                boolean success = activiteDAO.create(nouvelleActivite);
                if (success) {
                    refreshTable();
                    PopUp.showInfo(view, Lang.get("activity.add.success"));

                } else {
                    PopUp.showError(view, Lang.get("activity.add.error"));
                }

            } catch (Exception ex) {
                PopUp.showError(view, Lang.get("error.invalid.date.format"));
            }
        }
    }

    public void supprimerActivites(){

        int [] ids = view.getSelectedIds();

        if (ids.length == 0){
            return;
        }

        if (PopUp.showConfirm(view, Lang.get("confirm.delete.activities"))) {
            for (int id : ids){
                activiteDAO.delete(id);
            }
            refreshTable();
            PopUp.showInfo(view, Lang.get("activity.delete.success"));
        }
    }

    private void showEditForm(int id){

        Activite activite = activiteDAO.findById(id);

        if (activite != null){
            view.showEditForm(activite);
        }
    }

    public void modifierActivite(){

        if (checkFields()) {

            try {

                LocalDateTime fullDateTime = getHoraire();

                Activite activiteModifiee =
                        new Activite(
                                activiteModifieeId,
                                view.getNom(),
                                view.getDescription(),
                                Integer.parseInt(view.getCapaciteMax()),
                                fullDateTime
                        );

                if (PopUp.showConfirm(view, Lang.get("confirm.save.activity.changes"))) {

                    if (activiteDAO.update(activiteModifiee)) {
                        refreshTable();
                        PopUp.showInfo(view, Lang.get("activity.update.success"));
                    } else {
                        PopUp.showError(view, Lang.get("activity.update.error"));
                    }
                }

            } catch (Exception ex) {

                PopUp.showError(view, Lang.get("error.invalid.date.format"));
            }
        }
    }

    private void refreshTable() {
        ArrayList<Activite> activites = activiteDAO.findAll();
        view.refreshTable(activites);
    }

    public GestionActivitePanel getView() {
        return view;
    }
}