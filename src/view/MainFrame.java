package view;

import javax.swing.JFrame;

import controller.AdminDashboardController;
import controller.LoginController;
import model.Utilisateur;

public class MainFrame extends JFrame{

    public MainFrame(int width, int height, String title){
        super();
        this.setSize(width, height);
        this.setTitle(title);
        
        showLoginPanel();

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showAdminDashboard(Utilisateur utilisateur) {
        AdminDashboardPanel adminDashboardPanel = new AdminDashboardPanel(utilisateur);
        setContentPane(adminDashboardPanel);
        new AdminDashboardController(adminDashboardPanel, this);
        revalidate();
        repaint();
    }

    public void showMemberDashboard(Utilisateur utilisateur) {
        setContentPane(new MembreDashboardPanel(utilisateur));
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
}
