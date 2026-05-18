package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import dao.ActiviteDAO;
import dao.InscriptionDAO;
import model.Activite;
import model.ActiviteDisponibleRow;
import model.Inscription;
import model.InscriptionRow;
import model.Utilisateur;
import model.enums.StatutActivite;
import model.enums.StatutInscription;
import util.Lang;

public class InscriptionService {
    
    private InscriptionDAO inscriptionDAO = new InscriptionDAO();
    private ActiviteDAO activiteDAO = new ActiviteDAO();
    private ArrayList<ActiviteDisponibleRow> activitesDisponibles;

    public ArrayList<ActiviteDisponibleRow> getActivitesDisponibles(int utilisateurId) {
        activitesDisponibles = new ArrayList<ActiviteDisponibleRow>();
        ArrayList<Activite> activites = activiteDAO.findAll();
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
            if (dejaInscri || placeRestants <= 0) {
                continue;
            } else {
                statut = StatutActivite.DISPONIBLE.toString();
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

    public ArrayList<InscriptionRow> getMesInscriptions(int utilisateurId) {
        ArrayList<InscriptionRow> inscriptionRows = new ArrayList<InscriptionRow>();
        ArrayList<Inscription> inscriptions = inscriptionDAO.findByMembre(utilisateurId);
        for (Inscription inscription : inscriptions) {
            inscriptionRows.add(new InscriptionRow(inscription, inscription.getActivite()));
        }
        return inscriptionRows;
    }

    public ServiceResult annuler(int inscriptionId) {
        if (inscriptionId < 0) {
            return new ServiceResult(false, Lang.get("error.choose.registration"));
        }
        if (!inscriptionDAO.delete(inscriptionId)) {
            return new ServiceResult(false, Lang.get("error.cancel.registration"));
        }
        return new ServiceResult(true, Lang.get("success.cancel.registration"));
    }

    public ArrayList<InscriptionRow> getAll() {
        ArrayList<InscriptionRow> inscriptionRows = new ArrayList<InscriptionRow>();
        ArrayList<Inscription> inscriptions = inscriptionDAO.findAll();
        for (Inscription inscription : inscriptions) {
            inscriptionRows.add(new InscriptionRow(inscription.getMembre(), inscription, inscription.getActivite()));
        }
        return inscriptionRows;
    }

    public ServiceResult accepter(int inscriptionId) {
        if (inscriptionId < 0) {
            return new ServiceResult(false, Lang.get("error.choose.registration"));
        }
        if (!inscriptionDAO.updateStatut(inscriptionId, StatutInscription.ACCEPTEE)){
            return new ServiceResult(false, Lang.get("error.accept.registration"));
        }
        return new ServiceResult(true, Lang.get("success.accept.registration"));
    }

    public ServiceResult refuser(int inscriptionId) {
        if (inscriptionId < 0) {
            return new ServiceResult(false, Lang.get("error.choose.registration"));
        }
        if (!inscriptionDAO.updateStatut(inscriptionId, StatutInscription.REFUSEE)){
            return new ServiceResult(false, Lang.get("error.reject.registration"));
        }
        return new ServiceResult(true, Lang.get("success.reject.registration"));
    }

}
