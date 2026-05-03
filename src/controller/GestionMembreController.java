package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import dao.UtilisateurDAO;
import model.Utilisateur;
import view.GestionMembrePanel;
import view.PopUpHandler;

public class GestionMembreController {

    private UtilisateurDAO utilisateurDAO;
    private GestionMembrePanel view;

    private int membreModifieId;

    public GestionMembreController() {

        utilisateurDAO = new UtilisateurDAO();

        ArrayList<Utilisateur> membres = utilisateurDAO.getAllUtilisateurs();
        view = new GestionMembrePanel(membres);

        view.getSubmitMembreFormButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.isEditMode()) {
                    modifierMembre();
                } else {
                    ajouterMembre();
                }
            }
        });

        view.getDeleteItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                supprimerMembres();
            }
        });

        view.getEditItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                int[] ids = view.getSelectedMemberIds();

                if (ids.length == 1) {
                    membreModifieId = ids[0];
                    showEditForm(membreModifieId);
                }
            }
        });
    }

    private boolean checkFields() {
         if (view.getIdentifiant().isBlank()){
            PopUpHandler.showError(view, "Veuillez entrer un identifiant.");
            return false;
        }
        else if (view.getNom().isBlank()){
            PopUpHandler.showError(view, "Veuillez entrer un nom.");
            return false;
        }
        else if (view.getPrenom().isBlank()){
            PopUpHandler.showError(view, "Veuillez entrer un prénom.");
            return false;
        }
        else if (view.getDateNaissance().isBlank()){
            PopUpHandler.showError(view, "Veuillez entrer une date de naissance.");
            return false;
        }
        else if (view.getTelephone().isBlank()){
            PopUpHandler.showError(view, "Veuillez entrer un numéro de téléphone.");
            return false;
        }
        else if (view.getAdresse().isBlank()){
            PopUpHandler.showError(view, "Veuillez entrer une adresse.");
            return false;
        }
        else if (view.getPoids().isBlank() || Double.parseDouble(view.getPoids()) <= 0){
            PopUpHandler.showError(view, "Veuillez entrer un poids valide.");
            return false;
        }
        else if (view.getMotDePasse().isBlank()){
            PopUpHandler.showError(view, "Veuillez entrer un mot de passe.");
            return false;
        }
        return true;
    }

    public void ajouterMembre() {
        if (checkFields()) {
            Utilisateur nouveauMembre = new Utilisateur(
                view.getNom(),
                view.getPrenom(),
                LocalDate.parse(view.getDateNaissance()),
                view.getTelephone(),
                view.getAdresse(),
                Double.parseDouble(view.getPoids()),
                view.getIdentifiant(),
                view.getMotDePasse(),
                "MEMBRE"
            );

            if (utilisateurDAO.getUtilisateurByLogin(view.getIdentifiant()) != null) {
                PopUpHandler.showError(view, "Cet identifiant est déjà utilisé. Veuillez en choisir un autre.");
                return;
            }
            boolean success = utilisateurDAO.addUtilisateur(nouveauMembre);
            if (success) {
                
                refreshTable();
                PopUpHandler.showInfo(view, "Membre ajouté avec succès !");

            } else {
                PopUpHandler.showError(view, "Erreur lors de l'ajout du membre. Veuillez réessayer.");
            }
        }
    }

    public void supprimerMembres(){
        int [] ids = view.getSelectedMemberIds();
        if (ids.length == 0){
            return;
        }
        if (PopUpHandler.showConfirm(view, "Voulez vous vraiment supprimer les membres sélectionnés ?")){
            for (int id: ids){
                utilisateurDAO.deleteUtilisateurById(id);
            }
            refreshTable();
            PopUpHandler.showInfo(view, "Membres supprimés avec succées!");
        }
        return;
    }

    private void showEditForm(int id){
        Utilisateur membre = utilisateurDAO.getUtilisateurById(id);
        if (membre != null){
            view.showEditForm(membre);
        }
    }
    
    public void modifierMembre(){
        if (checkFields()) {
            Utilisateur membreModifie = new Utilisateur(
                membreModifieId,
                view.getNom(),
                view.getPrenom(),
                LocalDate.parse(view.getDateNaissance()),
                view.getTelephone(),
                view.getAdresse(),
                Double.parseDouble(view.getPoids()),
                view.getIdentifiant(),
                view.getMotDePasse(),
                "MEMBRE"
            );
            
            if (PopUpHandler.showConfirm(view, "Voulez vous vraiment enregistrer les modifications ?")) {
                if (utilisateurDAO.updateUtilisateur(membreModifie)) {
                    refreshTable();
                    PopUpHandler.showInfo(view, "Modifications enregistrés avec succés!");
                }
                else {
                    PopUpHandler.showError(view, "Erreur lors de la modification, Veuillez réssayer.");
                }
            }
        }
    }

    private void refreshTable() {
        ArrayList<Utilisateur> membres = utilisateurDAO.getAllUtilisateurs();
        view.refreshTable(membres);
    }

    public GestionMembrePanel getView() {
        return view;
    }
}