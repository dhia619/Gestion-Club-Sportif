package service;

import java.util.Properties;
import java.util.UUID;

import dao.UtilisateurDAO;
import model.Utilisateur;
import util.HashUtil;

public class AuthService {

    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public Utilisateur authenticate(String login, String motDePasse) {

        Utilisateur utilisateur = utilisateurDAO.getUtilisateurByLogin(login);
        if (utilisateur != null) {

            String hashedMotDePasse = HashUtil.hash(motDePasse);
            if (utilisateur.getMotDePasse().equals(hashedMotDePasse)) {
                return utilisateur;
            }
        }

        return null;
    }

    public void rememberUser(Utilisateur utilisateur, Properties config) {
        String token = UUID.randomUUID().toString();
        String hashedToken = HashUtil.hash(token);

        utilisateurDAO.updateRememberMeToken(utilisateur.getId(), hashedToken);

        config.setProperty("remember_me", "true");
        config.setProperty("remember_me_token", token);
    }

    public Utilisateur authenticateByRememberToken(String token) {

        if (token == null || token.isBlank()) {
            return null;
        }

        return utilisateurDAO.getUtilisateurByRememberMeToken(HashUtil.hash(token));
    }

    public void logout(Utilisateur utilisateur) {
        utilisateurDAO.clearRememberMeToken(utilisateur.getId());
    }
}