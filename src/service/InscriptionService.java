package service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import dao.ActiviteDAO;
import dao.InscriptionDAO;
import model.Activite;
import model.Inscription;
import model.InscriptionRow;
import model.MembreActifRow;
import model.Utilisateur;
import model.Notification;
import model.enums.StatutInscription;
import util.Lang;

public class InscriptionService {
    
    private InscriptionDAO inscriptionDAO = new InscriptionDAO();
    private ActiviteDAO activiteDAO = new ActiviteDAO();
    private NotificationService notificationService = new NotificationService();

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
        Inscription inscription = inscriptionDAO.findById(inscriptionId);
        if (!inscriptionDAO.delete(inscriptionId)) {
            return new ServiceResult(false, Lang.get("error.cancel.registration"));
        }
        notificationService.create(
            new Notification(
                inscription.getMembre(), 
                Lang.get("admin") + " " + Lang.get("notification.cancel.registration.for.activity") + " " + inscription.getActivite().getNom(),
                false,
                LocalDateTime.now()
            )
        );
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

    public int getNombreInscriptionsAccepteParActivite(int activiteId) {
        return inscriptionDAO.getNbInscriptionsAccepteParActivite(activiteId);
    }

    public ArrayList<MembreActifRow> getMembresPlusActifs(int n) {
        return inscriptionDAO.findMembresPlusActifs(n);
    }

    public ServiceResult accepter(int inscriptionId, Utilisateur admin) {
        if (inscriptionId < 0) {
            return new ServiceResult(false, Lang.get("error.choose.registration"));
        }
        Inscription inscription = inscriptionDAO.findById(inscriptionId);
        if (getNombreInscriptionsAccepteParActivite(inscription.getActivite().getId()) >= inscription.getActivite().getCapaciteMax()) {
            return new ServiceResult(false, Lang.get("error.activity.reached.max.capacity"));
        }
        if (!inscriptionDAO.updateStatut(inscriptionId, StatutInscription.ACCEPTEE)){
            return new ServiceResult(false, Lang.get("error.accept.registration"));
        }
        notificationService.create(
            new Notification(
                inscription.getMembre(), 
                Lang.get("admin") + " " + Lang.get("notification.accept.registration.for.activity") + " " + inscription.getActivite().getNom(),
                false,
                LocalDateTime.now()
            )
        );
        if (getNombreInscriptionsAccepteParActivite(inscription.getActivite().getId()) >= inscription.getActivite().getCapaciteMax()) {
            notificationService.create(
                new Notification(
                    admin, 
                    inscription.getActivite().getNom() + " : " + Lang.get("notification.activity.full"),
                    false,
                    LocalDateTime.now()
                )
            );
        }
        return new ServiceResult(true, Lang.get("success.accept.registration"));
    }

    public ServiceResult refuser(int inscriptionId) {
        if (inscriptionId < 0) {
            return new ServiceResult(false, Lang.get("error.choose.registration"));
        }
        Inscription inscription = inscriptionDAO.findById(inscriptionId);
        if (!inscriptionDAO.updateStatut(inscriptionId, StatutInscription.REFUSEE)){
            return new ServiceResult(false, Lang.get("error.reject.registration"));
        }
        notificationService.create(
            new Notification(
                inscription.getMembre(), 
                Lang.get("admin") + " " + Lang.get("notification.reject.registration.for.activity") + " " + inscription.getActivite().getNom(),
                false,
                LocalDateTime.now()
            )
        );
        return new ServiceResult(true, Lang.get("success.reject.registration"));
    }

    public int getNombreMesInscriptions(int membreId) {
        return inscriptionDAO.countMembreInscriptions(membreId);
    }

    public int getNombreInscriptions() {
        return inscriptionDAO.countInscriptions();
    }

    public int getNombreInscriptionsEnAttente() {
        return inscriptionDAO.countPendingInscriptions();
    }

    public int getNombreInscriptionsEnAttentePourMembre(int membreId) {
        return inscriptionDAO.countMembrePendingInscriptions(membreId);
    }

    public int getNombreInscriptionsAccepteePourMembre(int membreId) {
        return inscriptionDAO.countMembreAcceptedInscriptions(membreId);
    }

    public int getNombreInscriptionsRefuseePourMembre(int membreId) {
        return inscriptionDAO.countMembreRejectedInscriptions(membreId);
    }
}
