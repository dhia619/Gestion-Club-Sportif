package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import dao.ActiviteDAO;
import dao.InscriptionDAO;
import model.Activite;
import model.ActiviteDisponibleRow;
import model.Inscription;
import model.Utilisateur;
import model.enums.StatutActivite;
import model.enums.StatutInscription;
import util.Lang;

public class InscriptionService {
    
    private InscriptionDAO inscriptionDAO = new InscriptionDAO();
    private ActiviteDAO activiteDAO = new ActiviteDAO();
    private ArrayList<Activite> activites;
    private ArrayList<ActiviteDisponibleRow> activitesDisponibles;

    public ArrayList<ActiviteDisponibleRow> getActivitesDisponibles(int utilisateurId) {
        activitesDisponibles = new ArrayList<ActiviteDisponibleRow>();
        activites = activiteDAO.findAll();
        for (Activite activite : activites) {
            ArrayList<Inscription> inscriptions = inscriptionDAO.findByActivite(activite.getId());
            boolean dejaInscri = false;
            for (Inscription inscription : inscriptions) {
                if (inscription.getMembre().getId() == utilisateurId) {
                    dejaInscri = true;
                }
            }
            int placeRestants = activite.getCapaciteMax() - inscriptions.size();
            String statut = "-";
            if (dejaInscri) {
                statut = StatutActivite.DEJA_INSCRIT.toString();
            } else {
                if (placeRestants <= 0) continue;
                else statut = StatutActivite.DISPONIBLE.toString();
            }
            activitesDisponibles.add(new ActiviteDisponibleRow(activite, placeRestants, Lang.get(statut)));
        }

        return activitesDisponibles;
    }

    public ServiceResult inscrire(Utilisateur utilisateur, int activiteId) {
        if (activiteId <= 0) {
            return new ServiceResult(false, Lang.get("error.choose.activity"));
        }
        Activite activite = activiteDAO.findById(activiteId);
        if (activite != null) {
            if (inscriptionDAO.findByMembreAndActivite(utilisateur.getId(), activiteId) != null) {
                return new ServiceResult(false, Lang.get("error.already.registred.activity"));
            }
            if (!inscriptionDAO.create(new Inscription(utilisateur, activite, LocalDateTime.now(), StatutInscription.EN_ATTENTE))) {
                return new ServiceResult(false, Lang.get(""));
            }
        }
        return new ServiceResult(true, Lang.get("success.request.register.activity"));
    }

}
