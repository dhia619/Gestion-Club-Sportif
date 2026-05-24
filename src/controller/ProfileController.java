package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.Utilisateur;
import service.ServiceResult;
import service.UtilisateurService;
import util.Lang;
import view.components.PopUp;
import view.membre.ProfilePanel;

public class ProfileController {

    private ProfilePanel view;
    private UtilisateurService utilisateurService = new UtilisateurService();

    public ProfileController(Utilisateur membre) {
        this.view = new ProfilePanel();
        this.view.fillForm(membre);
        this.view.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                modifierMembre(membre);
            }
        });

        this.view.getRefreshButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                view.fillForm(utilisateurService.getUtilisateurById(membre.getId()));
            }
        });
        
        this.view.getGenerateMemberCardButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                genererCarteMembre(utilisateurService.getUtilisateurById(membre.getId()));
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

        if (!motDePasse.isBlank() && !motDePasse.matches("^[a-zA-Z0-9=#._]{6,}$")) {
            PopUp.showError(view, Lang.get("error.password"));
            return false;
        }

        return true;
    }

    public void modifierMembre(Utilisateur membre){
        if (checkFields()) {
            if (PopUp.showConfirm(view, Lang.get("confirm.save.changes"))) {
                Utilisateur membreModifie = new Utilisateur(
                    membre.getId(),
                    view.getNom(),
                    view.getPrenom(),
                    LocalDate.parse(view.getDateNaissance()),
                    view.getTelephone(),
                    view.getAdresse(),
                    Double.parseDouble(view.getPoids()),
                    view.getIdentifiant(),
                    view.getMotDePasse(),
                    "MEMBRE",
                    false
                );
                ServiceResult result = utilisateurService.modifierMembre(membreModifie);
                if (result.getSuccess()) PopUp.showInfo(view, result.getMessage());
                else PopUp.showError(view, result.getMessage());
            }
        }
    }

    private void genererCarteMembre(Utilisateur membre) {
        try {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("carte_membre_" + membre.getId() + ".pdf"));

            int option = fileChooser.showSaveDialog(view);
            if (option != javax.swing.JFileChooser.APPROVE_OPTION) {
                return;
            }

            java.io.File file = fileChooser.getSelectedFile();

            org.openpdf.text.Document document =
                    new org.openpdf.text.Document(org.openpdf.text.PageSize.A6);

            org.openpdf.text.pdf.PdfWriter.getInstance(
                    document,
                    new java.io.FileOutputStream(file)
            );

            document.open();

            org.openpdf.text.Font titleFont =
                    new org.openpdf.text.Font(org.openpdf.text.Font.HELVETICA, 18, org.openpdf.text.Font.BOLD);

            org.openpdf.text.Font normalFont =
                    new org.openpdf.text.Font(org.openpdf.text.Font.HELVETICA, 12);

            org.openpdf.text.Paragraph title =
                    new org.openpdf.text.Paragraph("CARTE MEMBRE", titleFont);
            title.setAlignment(org.openpdf.text.Element.ALIGN_CENTER);
            document.add(title);

            document.add(new org.openpdf.text.Paragraph(" "));
            document.add(new org.openpdf.text.Paragraph("ID: " + membre.getId(), normalFont));
            document.add(new org.openpdf.text.Paragraph("Nom: " + view.getNom(), normalFont));
            document.add(new org.openpdf.text.Paragraph("Prénom: " + view.getPrenom(), normalFont));
            document.add(new org.openpdf.text.Paragraph("Login: " + view.getIdentifiant(), normalFont));
            document.add(new org.openpdf.text.Paragraph("Téléphone: " + view.getTelephone(), normalFont));
            document.add(new org.openpdf.text.Paragraph("Date naissance: " + view.getDateNaissance(), normalFont));
            document.add(new org.openpdf.text.Paragraph("Adresse: " + view.getAdresse(), normalFont));

            document.close();

            PopUp.showInfo(view, "Carte membre générée avec succès.");

        } catch (Exception ex) {
            PopUp.showError(view, "Erreur génération PDF: " + ex.getMessage());
        }
    }

    public ProfilePanel getView() {
        return this.view;
    }
}
