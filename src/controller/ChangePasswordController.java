package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Utilisateur;
import service.AuthService;
import service.ServiceResult;
import util.Lang;
import view.ChangePasswordPanel;
import view.MainFrame;
import view.components.PopUpHandler;

public class ChangePasswordController {

    private MainFrame mainFrame;
    private ChangePasswordPanel view;
    private AuthService authService = new AuthService();

    public ChangePasswordController(MainFrame mainFrame, ChangePasswordPanel view) {
        this.view = view;
        this.mainFrame = mainFrame;
        this.view.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        String ancienMotDePasse = this.view.getOldPassword();
        String nouveauMotDePasse = this.view.getNewPassword();
        String confirmMotDePasse = this.view.getConfirmPassword();
        Utilisateur utilisateur = this.view.getUtilisateur();
        if (ancienMotDePasse.isBlank()) {
            PopUpHandler.showError(this.view, Lang.get("error.enter.old.password"));
        }
        else if (nouveauMotDePasse.isBlank()){
            PopUpHandler.showError(this.view, Lang.get("error.enter.new.password"));
        }
        else if (confirmMotDePasse.isBlank()){
            PopUpHandler.showError(this.view, Lang.get("error.enter.confirm.password"));
        }
        else if (!nouveauMotDePasse.equals(confirmMotDePasse)) {
            PopUpHandler.showError(this.view, Lang.get("error.password.not.match"));
        } else {
            if (PopUpHandler.showConfirm(this.view, Lang.get("confirm.change.password"))) {
                ServiceResult result = authService.changePassword(utilisateur, ancienMotDePasse, nouveauMotDePasse);
                if (result.getSuccess()) {
                        PopUpHandler.showInfo(this.view, Lang.get("success.password.change"));
                        mainFrame.showMemberDashboard(utilisateur);
                } else {
                    PopUpHandler.showError(this.view, result.getMessage());
                }
            }
        }
    }
}
