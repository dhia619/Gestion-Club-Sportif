package view.admin;

import java.awt.*;

import javax.swing.*;

import controller.GestionActiviteController;
import controller.GestionMembreController;
import model.Utilisateur;
import util.Lang;
import util.LanguageHandler;
import view.components.CustomLabel;
import view.components.DashboardLayoutPanel;

public class AdminDashboardPanel extends DashboardLayoutPanel {
    
    private JButton overviewButton;
    private JButton membersButton;
    private JButton activitiesButton;
    private JButton registrationsButton;
    private JButton monitoringButton;
    private GestionMembreController membreController;
    private GestionActiviteController activiteController;

    private Utilisateur utilisateur;

    public AdminDashboardPanel(Utilisateur utilisateur) {
        super(Lang.get("admin.dashboard.title"));

        this.utilisateur = utilisateur;

        overviewButton = addMenuButton(Lang.get("overview"), "acceuil");
        membersButton = addMenuButton(Lang.get("members"), "membres");
        activitiesButton = addMenuButton(Lang.get("activities"), "activites");
        registrationsButton = addMenuButton(Lang.get("registrations"), "inscriptions");
        monitoringButton = addMenuButton(Lang.get("monitoring"), "suivi");

        addPage("acceuil", createDashboardPage());
        addPage("membres", gestionMembresPage());
        addPage("activites", gestionActivitesPage());
        addPage("inscriptions", gestionInscriptionsPage());
        addPage("suivi", gestionSuiviPage());
    }

    private JPanel createDashboardPage() {
        JPanel panel = createBasePage();

        return panel;
    }

    private JPanel gestionMembresPage() {
        JPanel panel = createBasePage();
        membreController = new GestionMembreController();
        panel.add(membreController.getView());
        return panel;
    }

    private JPanel gestionActivitesPage() {
        JPanel panel = createBasePage();
        activiteController = new GestionActiviteController();
        panel.add(activiteController.getView());

        return panel;
    }

    private JPanel gestionInscriptionsPage() {
        JPanel panel = createBasePage();

        return panel;
    }

    private JPanel gestionSuiviPage() {
        JPanel panel = createBasePage();

        JLabel complete = new CustomLabel("Activités complètes : Musculation, Natation");
        JLabel active = new CustomLabel("Membres les plus actifs : Sami, Lina");

        panel.add(complete);
        panel.add(active);

        return panel;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void refreshUIText() {
        
        titleLabel.setFont(new Font(LanguageHandler.getTitleFont(), Font.PLAIN, 22));
        titleLabel.setText(Lang.get("admin.dashboard.title") + " ");

        logoutButton.setText(Lang.get("disconnect"));

        overviewButton.setText(Lang.get("overview"));
        membersButton.setText(Lang.get("members"));
        activitiesButton.setText(Lang.get("activities"));
        registrationsButton.setText(Lang.get("registrations"));
        monitoringButton.setText(Lang.get("monitoring"));

        membreController.getView().refreshUIText();
        activiteController.getView().refreshUIText();

        revalidate();
        repaint();
    }

}