package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.Properties;

import service.AuthService;
import util.ConfigurationFileHandler;
import util.LanguageHandler;
import view.AdminDashboardPanel;
import view.MainFrame;

public class AdminDashboardController {

    private AdminDashboardPanel view;

    public AdminDashboardController(AdminDashboardPanel view, MainFrame mainFrame) {
        this.view = view;

        this.view.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                AuthService authService = new AuthService();
                authService.logout(view.getUtilisateur());
                Properties config = ConfigurationFileHandler.getConfig();
                config.setProperty("remember_me", "false");
                config.remove("remember_me_token");
                ConfigurationFileHandler.saveConfig(config);
                mainFrame.showLoginPanel();
            }
        });

        this.view.getLanguageComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selected = (String) view.getLanguageComboBox().getSelectedItem();
                Locale locale;

                switch (selected) {
                    case "English":
                        locale = Locale.ENGLISH;
                        break;

                    case "Français":
                        locale = Locale.FRENCH;
                        break;

                    case "العربية":
                        locale = new Locale("ar");
                        break;

                    default:
                        locale = Locale.FRENCH;
                }
                LanguageHandler.setLocale(locale.getLanguage());
                LanguageHandler.saveLanguagePreference(locale.getLanguage());
                view.refreshUIText();
            }

        });
    }
}