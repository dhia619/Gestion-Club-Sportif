package view.membre;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.ConsulterActiviteController;
import model.Utilisateur;
import util.Lang;
import util.LanguageHandler;
import view.components.DashboardLayoutPanel;

public class MembreDashboardPanel extends DashboardLayoutPanel{

    private JButton overviewButton;
    private JButton activitiesButton;
    private JButton registrationsButton;
    private JButton profileButton;
    private ConsulterActiviteController consulterActiviteController;
    private Utilisateur utilisateur;
    
    public MembreDashboardPanel(Utilisateur utilisateur){
        super(Lang.get("user.dashboard.title") + " " + utilisateur.getPrenom() + " " + utilisateur.getNom());
        this.utilisateur = utilisateur;

        overviewButton = addMenuButton(Lang.get("overview"), "acceuil");
        activitiesButton = addMenuButton(Lang.get("activities"), "activites");
        registrationsButton = addMenuButton(Lang.get("registrations"), "inscriptions");
        profileButton = addMenuButton(Lang.get("profile"), "profile");

        addPage("acceuil", overviewPage());
        addPage("activites", consulterActivitesPage());
        addPage("inscriptions", consulterInscriptionsPage());
        addPage("profile", profilePage());
    }


    private JPanel overviewPage() {
        JPanel panel = createBasePage();

        return panel;
    }

    private JPanel consulterActivitesPage() {
        JPanel panel = createBasePage();
        consulterActiviteController = new ConsulterActiviteController(utilisateur);
        panel.add(consulterActiviteController.getView());
        return panel;
    }

    private JPanel consulterInscriptionsPage() {
        JPanel panel = createBasePage();

        return panel;
    }

    private JPanel profilePage() {
        JPanel panel = createBasePage();

        return panel;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void refreshUIText() {
        
        titleLabel.setFont(new Font(LanguageHandler.getTitleFont(), Font.PLAIN, 22));
        titleLabel.setText(Lang.get("user.dashboard.title") + " " + utilisateur.getPrenom() + " " + utilisateur.getNom());

        logoutButton.setText(Lang.get("disconnect"));

        overviewButton.setText(Lang.get("overview"));
        activitiesButton.setText(Lang.get("activities"));
        registrationsButton.setText(Lang.get("registrations"));
        profileButton.setText(Lang.get("profile"));

        consulterActiviteController.getView().refreshUIText();

        revalidate();
        repaint();
    }

}
