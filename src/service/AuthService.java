package service;

import dao.UtilisateurDAO;
import model.Utilisateur;

public class AuthService {
    
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public Utilisateur authenticate(String login, String motDePasse){
        Utilisateur utilisateur = utilisateurDAO.getUtilisateurByLogin(login);
        if (utilisateur != null && utilisateur.getMotDePasse().equals(motDePasse)) {
            return utilisateur;
        }
        return null;
    }
}
