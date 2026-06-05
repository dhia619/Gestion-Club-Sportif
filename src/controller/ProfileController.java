package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import model.Utilisateur;
import service.ServiceResult;
import service.UtilisateurService;
import util.Lang;
import view.components.PopUp;
import view.membre.ProfilePanel;

import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;

import org.openpdf.text.Chunk;
import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.Phrase;
import org.openpdf.text.Rectangle;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;
import org.openpdf.text.pdf.BaseFont;

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
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("carte_membre_" + membre.getId() + ".pdf"));

            int option = fileChooser.showSaveDialog(view);
            if (option != JFileChooser.APPROVE_OPTION) return;

            File file = fileChooser.getSelectedFile();

            Document document = new Document(PageSize.A6, 18, 18, 18, 18);
            PdfWriter.getInstance(document, new FileOutputStream(file));

            document.open();

            Color primaryColor = new Color(41, 128, 185);
            Color lightGray = new Color(245, 245, 245);
            Color darkGray = new Color(60, 60, 60);

            BaseFont baseFont = BaseFont.createFont(
                "resources/fonts/NotoSansArabic-VariableFont_wdth,wght.ttf",
                BaseFont.IDENTITY_H,
                BaseFont.EMBEDDED
            );

            Font titleFont = new Font(baseFont, 18, Font.BOLD, Color.WHITE);
            Font subtitleFont = new Font(baseFont, 10, Font.NORMAL, Color.WHITE);
            Font labelFont = new Font(baseFont, 9, Font.BOLD, darkGray);
            Font valueFont = new Font(baseFont, 9, Font.NORMAL, darkGray);
            Font footerFont = new Font(baseFont, 8, Font.ITALIC, Color.GRAY);

            PdfPTable card = new PdfPTable(1);
            card.setWidthPercentage(100);
            if (isArabic()) {
                card.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            }

            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(primaryColor);
            header.setPadding(12);
            header.setBorder(Rectangle.NO_BORDER);
            if (isArabic()) {
                header.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            }

            Paragraph title = new Paragraph(Lang.get("membership.card.title"), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);

            Paragraph subtitle = new Paragraph(Lang.get("membership.card.subtitle"), subtitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);

            header.addElement(title);
            header.addElement(subtitle);
            card.addCell(header);

            PdfPCell body = new PdfPCell();
            body.setPadding(12);
            body.setBackgroundColor(lightGray);
            body.setBorder(Rectangle.NO_BORDER);
            if (isArabic()) {
                body.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            }

            Paragraph id = new Paragraph(
                Lang.get("membership.card.id") + " " + membre.getId(),
                labelFont
            );
            id.setAlignment(Element.ALIGN_CENTER);
            body.addElement(id);
            body.addElement(Chunk.NEWLINE);

            PdfPTable infos = new PdfPTable(2);
            infos.setWidthPercentage(100);
            infos.setWidths(new float[]{35, 65});

            if (isArabic()) {
                infos.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            }

            addInfoRow(infos, Lang.get("user.lastname"), view.getNom(), labelFont, valueFont);
            addInfoRow(infos, Lang.get("user.firstname"), view.getPrenom(), labelFont, valueFont);
            addInfoRow(infos, Lang.get("user.username"), view.getIdentifiant(), labelFont, valueFont);
            addInfoRow(infos, Lang.get("user.tel"), view.getTelephone(), labelFont, valueFont);
            addInfoRow(infos, Lang.get("user.birthdate"), view.getDateNaissance(), labelFont, valueFont);
            addInfoRow(infos, Lang.get("user.address"), view.getAdresse(), labelFont, valueFont);

            body.addElement(infos);
            card.addCell(body);

            String today = String.valueOf(LocalDateTime.now());
            PdfPCell footer = new PdfPCell(
                new Phrase(Lang.get("generated_at") + " " + today.split("T")[0] + " " + today.split("T")[1].substring(0, 5), footerFont)
            );
            footer.setHorizontalAlignment(Element.ALIGN_CENTER);
            footer.setPadding(8);
            footer.setBorder(Rectangle.NO_BORDER);
            if (isArabic()) {
                footer.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            }

            card.addCell(footer);

            document.add(card);
            document.close();

            PopUp.showInfo(view, Lang.get("membership.card.generate.success"));

        } catch (Exception ex) {
            PopUp.showError(view, Lang.get("pdf.generate.error") + ex.getMessage());
        }
    }

    private void addInfoRow(
        PdfPTable table,
        String label,
        String value,
        Font labelFont,
        Font valueFont
    ) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label + " :", labelFont));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPadding(4);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, valueFont));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPadding(4);

        if (isArabic()) {
            labelCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            valueCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

            labelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        } else {
            labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
            valueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        }

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private boolean isArabic() {
        return Lang.getLocale().getLanguage().equals("ar");
    }

    public ProfilePanel getView() {
        return this.view;
    }
}
