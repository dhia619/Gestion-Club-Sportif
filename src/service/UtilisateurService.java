package service;

import dao.UtilisateurDAO;

public class UtilisateurService {
    
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public int getNombreMembres() {
        return utilisateurDAO.countMembres();
    }
}
