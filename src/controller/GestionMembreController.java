package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import dao.UtilisateurDAO;
import model.Utilisateur;
import util.HashUtil;
import util.Lang;
import view.admin.GestionMembrePanel;
import view.components.PopUp;

public class GestionMembreController {

    private UtilisateurDAO utilisateurDAO;
    private GestionMembrePanel view;

    private int membreModifieId;

    public GestionMembreController() {

        utilisateurDAO = new UtilisateurDAO();

        ArrayList<Utilisateur> membres = utilisateurDAO.findAll();
        view = new GestionMembrePanel(membres);

        view.getSubmitFormButton().addActionListener(new ActionListener() {
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
                int[] ids = view.getSelectedIds();

                if (ids.length == 1) {
                    membreModifieId = ids[0];
                    showEditForm(membreModifieId);
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

        String identifiant = view.getIdentifiant().trim();
        String prenom = view.getNom().trim();
        String nom = view.getPrenom().trim();
        String dateNaissance = view.getDateNaissance().trim();
        String tel = view.getTelephone().trim();
        String adresse = view.getAdresse().trim();
        String poids = view.getPoids().trim();
        String motDePasse = view.getMotDePasse().trim(); 
        LocalDate birthdate;

        if (identifiant.isEmpty()) {
            PopUp.showError(view, Lang.get("error.enter.username"));
            return false;
        }

        if (!identifiant.matches("[a-zA-ZÀ-ÿ0-9_]{3,20}")) {
            PopUp.showError(view, Lang.get("error.username"));
            return false;
        }

        if (nom.isEmpty()) {
            PopUp.showError(view, Lang.get("error.enter.lastname"));
            return false;
        }

        if (!nom.matches("[a-zA-ZÀ-ÿ]{3,30}")) {
            PopUp.showError(view, Lang.get("error.lastname"));
            return false;
        }

        if (prenom.isEmpty()) {
            PopUp.showError(view, Lang.get("error.enter.firstname"));
            return false;
        }

        if (!prenom.matches("[a-zA-ZÀ-ÿ]{3,30}")) {
            PopUp.showError(view, Lang.get("error.firstname"));
            return false;
        }

        if (dateNaissance.isEmpty()) {
            PopUp.showError(view, Lang.get("error.enter.birthdate"));
            return false;
        }

        if (!dateNaissance.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            PopUp.showError(view, Lang.get("error.birthdate"));
            return false;
        }

        try {
            birthdate = LocalDate.parse(view.getDateNaissance());
            if (birthdate.isAfter(LocalDate.now())) {
                PopUp.showError(view, Lang.get("error.future.birthdate"));
                return false;
            }
        } catch (DateTimeParseException e) {
            PopUp.showError(view, Lang.get("error.birthdate"));
            return false;
        }

        if (tel.isEmpty()) {
            PopUp.showError(view, Lang.get("error.enter.phone"));
            return false;
        }

        if (!tel.matches("^\\d{8}$")) {
            PopUp.showError(view, Lang.get("error.phone"));
            return false;
        }

        if (adresse.isEmpty()) {
            PopUp.showError(view, Lang.get("error.enter.address"));
            return false;
        }

        if (adresse.length() < 3) {
            PopUp.showError(view, Lang.get("error.address"));
            return false;
        }

        if (poids.isEmpty()) {
            PopUp.showError(view, Lang.get("error.enter.valid.weight"));
            return false;
        }

        try {
            double weight = Double.parseDouble(poids);

            if (weight <= 0) {
                PopUp.showError(view, Lang.get("error.weight"));
                return false;
            }

        } catch (NumberFormatException e) {
            PopUp.showError(view, Lang.get("error.weight"));
            return false;
        }

        if (!view.isEditMode()) {
            if (motDePasse.isBlank()) {
                PopUp.showError(view, Lang.get("error.enter.password"));
                return false;
            } else if (!motDePasse.matches("^[a-zA-Z0-9=#._]{6,}$")) {
                PopUp.showError(view, Lang.get("error.password"));
                return false;
            }
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
                HashUtil.hash(view.getMotDePasse()),
                "MEMBRE",
                true
            );

            if (utilisateurDAO.findByLogin(view.getIdentifiant()) != null) {
                PopUp.showError(view, Lang.get("error.username.exists"));
                return;
            }
            boolean success = utilisateurDAO.create(nouveauMembre);
            if (success) {
                
                refreshTable();
                PopUp.showInfo(view, Lang.get("member.add.success"));

            } else {
                PopUp.showError(view, Lang.get("member.add.error"));
            }
        }
    }

    public void supprimerMembres(){
        int [] ids = view.getSelectedIds();
        if (ids.length == 0){
            return;
        }
        if (PopUp.showConfirm(view, Lang.get("confirm.delete.members"))){
            for (int id: ids){
                utilisateurDAO.delete(id);
            }
            refreshTable();
            PopUp.showInfo(view, Lang.get("member.delete.success"));
        }
        return;
    }

    private void showEditForm(int id){
        Utilisateur membre = utilisateurDAO.findById(id);
        if (membre != null){
            view.showEditForm(membre);
        }
    }
    
    public void modifierMembre(){
        if (checkFields()) {
            Utilisateur ancienMembre = utilisateurDAO.findById(membreModifieId);
            String passwordToSave;
            if (view.getMotDePasse().isBlank()) {
                passwordToSave = ancienMembre.getMotDePasse();
            } else {
                passwordToSave = HashUtil.hash(view.getMotDePasse());
            }
            Utilisateur membreModifie = new Utilisateur(
                membreModifieId,
                view.getNom(),
                view.getPrenom(),
                LocalDate.parse(view.getDateNaissance()),
                view.getTelephone(),
                view.getAdresse(),
                Double.parseDouble(view.getPoids()),
                view.getIdentifiant(),
                passwordToSave,
                "MEMBRE",
                ancienMembre.getFirstLogin()
            );
            
            if (PopUp.showConfirm(view, Lang.get("confirm.save.changes"))) {
                if (utilisateurDAO.update(membreModifie)) {
                    refreshTable();
                    PopUp.showInfo(view, Lang.get("member.update.success"));
                }
                else {
                    PopUp.showError(view, Lang.get("member.update.error"));
                }
            }
        }
    }

    private void refreshTable() {
        ArrayList<Utilisateur> membres = utilisateurDAO.findAll();
        view.refreshTable(membres);
    }

    public GestionMembrePanel getView() {
        return view;
    }
}