package service;

import dao.UtilisateurDAO;
import model.Utilisateur;
import util.HashUtil;
import util.Lang;

public class UtilisateurService {
    
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public int getNombreMembres() {
        return utilisateurDAO.countMembres();
    }

    public ServiceResult modifierMembre(Utilisateur membre) {
        Utilisateur ancienMembre = utilisateurDAO.findById(membre.getId());
        if (ancienMembre == null) {
            return new ServiceResult(false, Lang.get("member.not.exist"));
        }
        
        if (membre.getMotDePasse().equals("")) membre.setMotDePasse(ancienMembre.getMotDePasse());
        else membre.setMotDePasse(HashUtil.hash(membre.getMotDePasse()));
        membre.setFirstLogin(ancienMembre.getFirstLogin());

        if (!utilisateurDAO.update(membre)) {
            return new ServiceResult(false, Lang.get("account.update.error"));
        }
        return new ServiceResult(true, Lang.get("account.update.success"));
    }

    public Utilisateur getUtilisateurById(int utilisateurId) {
        return utilisateurDAO.findById(utilisateurId);
    }
}
