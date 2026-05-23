package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import model.Utilisateur;
import service.AuthService;
import util.ConfigurationFileHandler;
import util.Lang;
import view.LoginPanel;
import view.MainFrame;
import view.components.PopUp;

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
            PopUp.showError(this.view, Lang.get("error.empty_username"));
        } else if (motDePasse.isBlank()){
            PopUp.showError(this.view, Lang.get("error.empty_password"));
        } else {
            utilisateur = authService.authenticate(identifiant, motDePasse);
            if (utilisateur != null){

                Properties config = ConfigurationFileHandler.getConfig();
                if (view.isRememberMeSelected()) {
                    authService.rememberUser(utilisateur, config);
                } else {
                    config.setProperty("remember_me", "false");
                    config.remove("remember_me_token");
                }
                ConfigurationFileHandler.saveConfig(config);

                if (utilisateur.getRole().equals("ADMIN")){
                    mainFrame.showAdminDashboard(utilisateur);
                } else {
                    if (utilisateur.getFirstLogin()) {
                        mainFrame.showChangePasswordPanel(utilisateur);
                    } else {
                        mainFrame.showMemberDashboard(utilisateur);
                    }
                }
            } else {
                PopUp.showError(this.view, Lang.get("error.invalid_credentials"));
            }
        }
    }
}
