package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
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

    LocalDateTime horaire;

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

        if (view.getNom().isBlank()) {
            PopUp.showError(view, Lang.get("error.enter.activity.name"));
            return false;
        }

        if (view.getNom().trim().length() < 3) {
            PopUp.showError(view, Lang.get("error.activity.name"));
            return false;
        }

        if (view.getCapaciteMax().isBlank()) {
            PopUp.showError(view, Lang.get("error.enter.activity.capacity"));
            return false;
        }

        try {
            int capacity = Integer.parseInt(view.getCapaciteMax().trim());

            if (capacity <= 0) {
                PopUp.showError(view, Lang.get("error.activity.capacity"));
                return false;
            }

        } catch (NumberFormatException e) {
            PopUp.showError(view, Lang.get("error.activity.capacity"));
            return false;
        }

        if (view.getDate().isBlank()) {
            PopUp.showError(view, Lang.get("error.enter.activity.date"));
            return false;
        }

        LocalDate date;
        try {
            date = LocalDate.parse(view.getDate());
        } catch (DateTimeParseException e) {
            PopUp.showError(view, Lang.get("error.invalid.date.format"));
            return false;
        }
        
        LocalTime time;
        try {
            Date spinnerDate = view.getHoraire();
            time = spinnerDate
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalTime();
        } catch (Exception e) {
            PopUp.showError(view, Lang.get("error.activity.time"));
            return false;
        }

        horaire = LocalDateTime.of(date, time);
        
        if (horaire.isBefore(LocalDateTime.now())) {
            PopUp.showError(view, Lang.get("error.activity.past"));
            return false;
        }

        return true;
    }

    public void ajouterActivite() {

        if (checkFields()) {

            try {
                Activite nouvelleActivite = new Activite(
                        view.getNom(),
                        view.getDescription(),
                        Integer.parseInt(view.getCapaciteMax()),
                        horaire
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
                Activite activiteModifiee =
                        new Activite(
                                activiteModifieeId,
                                view.getNom(),
                                view.getDescription(),
                                Integer.parseInt(view.getCapaciteMax()),
                                horaire
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