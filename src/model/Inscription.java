package model;

import model.enums.StatutInscription;
import java.time.LocalDateTime;

public class Inscription {

    private int id;

    private Utilisateur membre;
    private Activite activite;

    private LocalDateTime dateInscription;

    private StatutInscription statut;

    public Inscription() {
    }

    public Inscription(
            int id,
            Utilisateur membre,
            Activite activite,
            LocalDateTime dateInscription,
            StatutInscription statut
    ) {
        this.id = id;
        this.membre = membre;
        this.activite = activite;
        this.dateInscription = dateInscription;
        this.statut = statut;
    }

    public Inscription(
            Utilisateur membre,
            Activite activite,
            LocalDateTime dateInscription,
            StatutInscription statut
    ) {
        this.membre = membre;
        this.activite = activite;
        this.dateInscription = dateInscription;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getMembre() {
        return membre;
    }

    public void setMembre(Utilisateur membre) {
        this.membre = membre;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public StatutInscription getStatut() {
        return statut;
    }

    public void setStatut(StatutInscription statut) {
        this.statut = statut;
    }
}