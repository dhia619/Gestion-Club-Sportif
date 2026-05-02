package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Utilisateur;
import service.AuthService;
import view.LoginPanel;
import view.MainFrame;

public class LoginController {

    private LoginPanel view;
    private MainFrame mainFrame;
    private AuthService authService = new AuthService();

    public LoginController(LoginPanel view, MainFrame mainFrame) {
        this.view = view;
        this.mainFrame = mainFrame;

        this.view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {

        String identifiant = this.view.getIdentifiant();
        String motDePasse = this.view.getMotDePasse();
        Utilisateur utilisateur;

        if (identifiant.isBlank()){
            this.view.showErrorMessage("Veuillez entrer votre identifiant.");
        }
        else if (motDePasse.isBlank()){
            this.view.showErrorMessage("Veuillez entrer votre mot de passe.");
        }
        else {
            utilisateur = authService.authenticate(identifiant, motDePasse);
            if (utilisateur != null){
                if (utilisateur.getRole().equals("ADMIN")){
                    mainFrame.showAdminDashboard(utilisateur);
                } else {
                    mainFrame.showMemberDashboard(utilisateur);
                }
            } else {
                this.view.showErrorMessage("Identifiant ou mot de passe incorrect.");
            }
        }
    }
}
