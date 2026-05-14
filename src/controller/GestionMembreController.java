package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

import dao.UtilisateurDAO;
import model.Utilisateur;
import util.Lang;
import view.GestionMembrePanel;
import view.components.PopUpHandler;

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
            PopUpHandler.showError(view, Lang.get("error.enter.username"));
            return false;
        }
        else if (view.getNom().isBlank()){
            PopUpHandler.showError(view, Lang.get("error.enter.lastname"));
            return false;
        }
        else if (view.getPrenom().isBlank()){
            PopUpHandler.showError(view, Lang.get("error.enter.firstname"));
            return false;
        }
        else if (view.getDateNaissance().isBlank()){
            PopUpHandler.showError(view, Lang.get("error.enter.birthdate"));
            return false;
        }
        else if (view.getTelephone().isBlank()){
            PopUpHandler.showError(view, Lang.get("error.enter.phone"));
            return false;
        }
        else if (view.getAdresse().isBlank()){
            PopUpHandler.showError(view, Lang.get("error.enter.address"));
            return false;
        }
        else if (view.getPoids().isBlank() || Double.parseDouble(view.getPoids()) <= 0){
            PopUpHandler.showError(view, Lang.get("error.enter.valid.weight"));
            return false;
        }
        else if (view.getMotDePasse().isBlank()){
            PopUpHandler.showError(view, Lang.get("error.enter.password"));
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
                PopUpHandler.showError(view, Lang.get("error.username.exists"));
                return;
            }
            boolean success = utilisateurDAO.addUtilisateur(nouveauMembre);
            if (success) {
                
                refreshTable();
                PopUpHandler.showInfo(view, Lang.get("member.add.success"));

            } else {
                PopUpHandler.showError(view, Lang.get("member.add.error"));
            }
        }
    }

    public void supprimerMembres(){
        int [] ids = view.getSelectedMemberIds();
        if (ids.length == 0){
            return;
        }
        if (PopUpHandler.showConfirm(view, Lang.get("confirm.delete.members"))){
            for (int id: ids){
                utilisateurDAO.deleteUtilisateurById(id);
            }
            refreshTable();
            PopUpHandler.showInfo(view, Lang.get("members.delete.success"));
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
            
            if (PopUpHandler.showConfirm(view, Lang.get("confirm.save.changes"))) {
                if (utilisateurDAO.updateUtilisateur(membreModifie)) {
                    refreshTable();
                    PopUpHandler.showInfo(view, Lang.get("member.update.success"));
                }
                else {
                    PopUpHandler.showError(view, Lang.get("member.update.error"));
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