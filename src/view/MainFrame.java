package view;

import javax.swing.JFrame;

import controller.LoginController;
import view.MembreDashboardPanel;
import view.AdminDashboardPanel;
import model.Utilisateur;

public class MainFrame extends JFrame{

    public MainFrame(int width, int height, String title){
        super();
        this.setSize(width, height);
        this.setTitle(title);
        
        LoginPanel loginPanel = new LoginPanel();
        LoginController loginController = new LoginController(loginPanel, this);
        this.setContentPane(loginPanel);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void showAdminDashboard(Utilisateur utilisateur) {
        setContentPane(new AdminDashboardPanel(utilisateur));
        revalidate();
        repaint();
    }

    public void showMemberDashboard(Utilisateur utilisateur) {
        setContentPane(new MembreDashboardPanel(utilisateur));
        revalidate();
        repaint();
    }
}
