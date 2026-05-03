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
import view.GestionActivitePanel;
import view.PopUpHandler;

public class GestionActiviteController {

    private ActiviteDAO activiteDAO;
    private GestionActivitePanel view;
    private int activiteModifieeId;

    public GestionActiviteController() {
        activiteDAO = new ActiviteDAO();

        ArrayList<Activite> activites = activiteDAO.getAllActivites();
        view = new GestionActivitePanel(activites);

        view.getSubmitActiviteFormButton().addActionListener(new ActionListener() {
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
                int[] ids = view.getSelectedActiviteIds();

                if (ids.length == 1) {
                    activiteModifieeId = ids[0];
                    showEditForm(activiteModifieeId);
                }
            }
        });
    }

    private boolean checkFields() {
        if (view.getNom().isBlank()){
            PopUpHandler.showError(view, "Veuillez entrer un nom.");
            return false;
        }
        else if (view.getDescription().isBlank()){
            PopUpHandler.showError(view, "Veuillez entrer une description.");
            return false;
        }
        else if (view.getCapaciteMax().isBlank()){
            PopUpHandler.showError(view, "Veuillez entrer une capacité maximale.");
            return false;
        }
        else if (String.valueOf(view.getHoraire()).equals("")){
            PopUpHandler.showError(view, "Veuillez entrer un horaire.");
            return false;
        }
        return true;
    }

    private LocalDateTime getHoraire() {
        LocalDate date = LocalDate.parse(view.getDate());

        Date spinnerDate = view.getHoraire();
        LocalTime time = spinnerDate.toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalTime();

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

                boolean success = activiteDAO.addActivite(nouvelleActivite);
                if (success) {
                    refreshTable();
                    PopUpHandler.showInfo(view, "Activité ajoutée avec succès !");
                } else {
                    PopUpHandler.showError(view, "Erreur lors de l'ajout de l'activité.");
                }
            } catch (Exception ex) {
                PopUpHandler.showError(view, "Format de date invalide (Utilisez YYYY-MM-DD)");
            }

        }
    }

    public void supprimerActivites(){
        int [] ids = view.getSelectedActiviteIds();
        if (ids.length == 0) return;

        if (PopUpHandler.showConfirm(view, "Voulez-vous vraiment supprimer les activités sélectionnées ?")){
            for (int id : ids){
                activiteDAO.deleteActiviteById(id);
            }
            refreshTable();
            PopUpHandler.showInfo(view, "Activités supprimées avec succès !");
        }
    }

    private void showEditForm(int id){
        Activite activite = activiteDAO.getActiviteById(id);
        if (activite != null){
            view.showEditForm(activite);
        }
    }
    
    public void modifierActivite(){
        if (checkFields()) {
            try {
                LocalDateTime fullDateTime = getHoraire();

                Activite activiteModifiee = new Activite(
                    activiteModifieeId,
                    view.getNom(),
                    view.getDescription(),
                    Integer.parseInt(view.getCapaciteMax()),
                    fullDateTime
                );
                
                if (PopUpHandler.showConfirm(view, "Voulez-vous vraiment enregistrer les modifications ?")) {
                    if (activiteDAO.updateActivite(activiteModifiee)) {
                        refreshTable();
                        PopUpHandler.showInfo(view, "Modifications enregistrées avec succès !");
                    } else {
                        PopUpHandler.showError(view, "Erreur lors de la modification.");
                    }
                }
            } catch (Exception ex) {
                PopUpHandler.showError(view, "Format de date invalide (Utilisez AAAA-MM-JJ)");
            }
        }
    }

    private void refreshTable() {
        ArrayList<Activite> activites = activiteDAO.getAllActivites();
        view.refreshTable(activites);
    }

    public GestionActivitePanel getView() {
        return view;
    }
}