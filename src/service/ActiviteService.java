package service;

import java.util.ArrayList;

import dao.ActiviteDAO;
import dao.InscriptionDAO;
import model.Activite;
import model.ActiviteRow;
import model.enums.StatutActivite;
import util.Lang;

public class ActiviteService {

    private ActiviteDAO activiteDAO = new ActiviteDAO();
    private InscriptionDAO inscriptionDAO = new InscriptionDAO();
    private InscriptionService inscriptionService = new InscriptionService();

    public ArrayList<ActiviteRow> getActivitesDisponiblesPourMembre(int utilisateurId) {
        ArrayList<ActiviteRow> activitesDisponibles = new ArrayList<ActiviteRow>();
        ArrayList<Activite> activites = activiteDAO.findAll();
        for (Activite activite : activites) {
            boolean dejaInscri = false;
            if (inscriptionDAO.findByMembreAndActivite(utilisateurId, activite.getId()) != null) {
                dejaInscri = true;
            }
            int placeRestants = activite.getCapaciteMax() - inscriptionService.getNombreInscriptionsAccepteParActivite(activite.getId());
            String statut = "-";
            if (dejaInscri || placeRestants <= 0) {
                continue;
            } else {
                statut = StatutActivite.DISPONIBLE.toString();
            }
            activitesDisponibles.add(new ActiviteRow(activite, placeRestants, Lang.get(statut)));
        }
        return activitesDisponibles;
    }

    public ArrayList<ActiviteRow> getAllActivites() {
        ArrayList<ActiviteRow> activiteRows = new ArrayList<ActiviteRow>();
        ArrayList<Activite> activites = activiteDAO.findAll();
        for (Activite activite : activites) {
            int placeRestants = activite.getCapaciteMax() - inscriptionService.getNombreInscriptionsAccepteParActivite(activite.getId());
            String statut = "-";
            if (placeRestants <= 0) {statut = StatutActivite.COMPLET.toString();}
            else {statut = StatutActivite.DISPONIBLE.toString();}
            activiteRows.add(new ActiviteRow(activite, placeRestants, statut));
        }
        return activiteRows;
    }

    public ArrayList<ActiviteRow> getActivitesDisponibles() {
        ArrayList<ActiviteRow> activitesDisponibles = new ArrayList<ActiviteRow>();
        ArrayList<Activite> activites = activiteDAO.findAll();
        for (Activite activite : activites) {
            int placeRestants = activite.getCapaciteMax() - inscriptionService.getNombreInscriptionsAccepteParActivite(activite.getId());
            if (placeRestants > 0) activitesDisponibles.add(new ActiviteRow(activite, placeRestants, StatutActivite.DISPONIBLE.toString()));
        }
        return activitesDisponibles;
    }

    public ArrayList<ActiviteRow> getActivitesComplets() {
        ArrayList<ActiviteRow> activitesComplets = new ArrayList<ActiviteRow>();
        ArrayList<Activite> activites = activiteDAO.findAll();
        for (Activite activite : activites) {
            int placeRestants = activite.getCapaciteMax() - inscriptionService.getNombreInscriptionsAccepteParActivite(activite.getId());
            if (placeRestants <= 0) activitesComplets.add(new ActiviteRow(activite, placeRestants, StatutActivite.COMPLET.toString()));
        }
        return activitesComplets;
    }

    public ArrayList<ActiviteRow> getActivitesPopulaires(int n) {
        return inscriptionDAO.findActivitesPopulaires(n);
    }

    public int getNombreActivites() {
        return activiteDAO.countActivites();
    }

    public int getNombreActivitesCompletes() {
        return activiteDAO.countActivitesComplets();
    }

    public int getNombreActivitesDisponibles() {
        return activiteDAO.countActivitesDisponibles();
    }
}
