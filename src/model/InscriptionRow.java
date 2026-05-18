package model;

public class InscriptionRow {

    private Utilisateur utilisateur;
    private Inscription inscription;
    private Activite activite;

    public InscriptionRow(
        Utilisateur utilisateur,
        Inscription inscription,
        Activite activite
    ) {
        this.utilisateur = utilisateur;
        this.inscription = inscription;
        this.activite = activite;
    }

    public InscriptionRow(
        Inscription inscription,
        Activite activite
    ) {
        this.inscription = inscription;
        this.activite = activite;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }
    
    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }
}