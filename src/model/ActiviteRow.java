package model;

public class ActiviteRow {
    private Activite activite;
    private int placesRestantes;
    private String statut;

    public ActiviteRow(Activite activite, int placesRestantes, String statut) {
        this.activite = activite;
        this.placesRestantes = placesRestantes;
        this.statut = statut;
    }

    public ActiviteRow(Activite activite, int placesRestantes) {
        this.activite = activite;
        this.placesRestantes = placesRestantes;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public int getPlacesRestantes() {
        return placesRestantes;
    }

    public void setPlacesRestantes(int placesRestantes) {
        this.placesRestantes = placesRestantes;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}