package view;

import java.util.Properties;

import javax.swing.JFrame;

import controller.DashboardController;
import controller.ChangePasswordController;
import controller.LoginController;
import model.Utilisateur;
import service.AuthService;
import util.ConfigurationFileHandler;
import view.admin.AdminDashboardPanel;
import view.membre.ChangePasswordPanel;
import view.membre.MembreDashboardPanel;

public class MainFrame extends JFrame{

    public MainFrame(int width, int height, String title){
        super();
        this.setSize(width, height);
        this.setTitle(title);
        
        Properties config = ConfigurationFileHandler.getConfig();

        AuthService authService = new AuthService();


        if (config.getProperty("remember_me").equals("true")) {
            String token = config.getProperty("remember_me_token");
            Utilisateur utilisateur = authService.authenticateByRememberToken(token);
            if (utilisateur != null) {
                if (utilisateur.getRole().equals("ADMIN")) {
                    showAdminDashboard(utilisateur);
                } else {
                    if (utilisateur.getFirstLogin()) {
                        showChangePasswordPanel(utilisateur);
                    } else {
                        showMemberDashboard(utilisateur);
                    }
                }
            } else {
                showLoginPanel();
            }
        } else {
            showLoginPanel();
        }

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showAdminDashboard(Utilisateur utilisateur) {
        AdminDashboardPanel adminDashboardPanel = new AdminDashboardPanel(utilisateur);
        this.setContentPane(adminDashboardPanel);
        new DashboardController(adminDashboardPanel, this, utilisateur);
        revalidate();
        repaint();
    }

    public void showMemberDashboard(Utilisateur utilisateur) {
        MembreDashboardPanel membreDashboardPanel = new MembreDashboardPanel(utilisateur);
        new DashboardController(membreDashboardPanel, this, utilisateur);
        this.setContentPane(membreDashboardPanel);
        revalidate();
        repaint();
    }

    public void showLoginPanel() {
        LoginPanel loginPanel = new LoginPanel();
        new LoginController(loginPanel, this);
        this.setContentPane(loginPanel);
        revalidate();
        repaint();
    }

    public void showChangePasswordPanel(Utilisateur utilisateur) {
        ChangePasswordPanel changePasswordPanel = new ChangePasswordPanel(utilisateur);
        new ChangePasswordController(this, changePasswordPanel);
        this.setContentPane(changePasswordPanel);
        revalidate();
        repaint();
    }
}
