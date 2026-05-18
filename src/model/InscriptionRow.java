package model;

public class InscriptionRow {

    private Inscription inscription;
    private Activite activite;

    public InscriptionRow(
            Inscription inscription,
            Activite activite
    ) {
        this.inscription = inscription;
        this.activite = activite;
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