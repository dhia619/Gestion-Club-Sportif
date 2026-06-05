package view.admin;

import java.awt.*;

import javax.swing.*;

import controller.GestionActiviteController;
import controller.GestionInscriptionController;
import controller.GestionMembreController;
import controller.GestionSuiviController;
import controller.NotificationController;
import controller.AdminOverviewController;
import model.Utilisateur;
import util.Lang;
import util.LanguageHandler;

import view.components.DashboardLayoutPanel;

public class AdminDashboardPanel extends DashboardLayoutPanel {
    
    private JButton overviewButton;
    private JButton membersButton;
    private JButton activitiesButton;
    private JButton registrationsButton;
    private JButton monitoringButton;
    private GestionMembreController membreController;
    private GestionActiviteController activiteController;
    private GestionInscriptionController inscriptionController;
    private GestionSuiviController suiviController;
    private AdminOverviewController adminOverviewController;
    private NotificationController notificationController;

    private Utilisateur utilisateur;

    public AdminDashboardPanel(Utilisateur utilisateur) {
        super(Lang.get("admin.dashboard.title"));

        this.utilisateur = utilisateur;

        overviewButton = addMenuButton(Lang.get("overview"), "acceuil");
        membersButton = addMenuButton(Lang.get("members"), "membres");
        activitiesButton = addMenuButton(Lang.get("activities"), "activites");
        registrationsButton = addMenuButton(Lang.get("registrations"), "inscriptions");
        monitoringButton = addMenuButton(Lang.get("monitoring"), "suivi");

        addPage("acceuil", createOverviewPage());
        addPage("membres", gestionMembresPage());
        addPage("activites", gestionActivitesPage());
        addPage("inscriptions", gestionInscriptionsPage());
        addPage("suivi", gestionSuiviPage());
        addPage("notifications", notificationsPage());
    }

    private JPanel createOverviewPage() {
        JPanel panel = createBasePage();
        adminOverviewController = new AdminOverviewController();
        panel.add(adminOverviewController.getView());
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
        inscriptionController = new GestionInscriptionController(utilisateur);
        panel.add(inscriptionController.getView());
        return panel;
    }

    private JPanel gestionSuiviPage() {
        JPanel panel = createBasePage();
        suiviController = new GestionSuiviController();
        panel.add(suiviController.getView());
        return panel;
    }

    private JPanel notificationsPage() {
        JPanel panel = createBasePage();
        notificationController = new NotificationController(utilisateur);
        panel.add(notificationController.getView());
        return panel;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void refreshUIText() {
        
        titleLabel.setFont(new Font(LanguageHandler.getTitleFont(), Font.PLAIN, 22));
        titleLabel.setText(Lang.get("admin.dashboard.title") + " ");

        logoutButton.setText(Lang.get("disconnect"));
        notificationButton.setToolTipText(Lang.get("notifications"));
        languageBox.setToolTipText(Lang.get("language"));

        overviewButton.setText(Lang.get("overview"));
        membersButton.setText(Lang.get("members"));
        activitiesButton.setText(Lang.get("activities"));
        registrationsButton.setText(Lang.get("registrations"));
        monitoringButton.setText(Lang.get("monitoring"));

        membreController.getView().refreshUIText();
        activiteController.getView().refreshUIText();
        inscriptionController.getView().refreshUIText();
        suiviController.getView().refreshUIText();
        adminOverviewController.refreshUI();
        adminOverviewController.getView().refreshUIText();
        notificationController.getView().refreshUIText();

        revalidate();
        repaint();
    }

}