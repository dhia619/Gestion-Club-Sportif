package model;

import java.time.LocalDateTime;

public class Activite {
    private int id;
    private String nom;
    private String description;
    private int capaciteMax;
    private LocalDateTime horaire;

    public Activite (String nom, String description, int capaciteMax, LocalDateTime horaire) {
        this.nom = nom;
        this.description = description;
        this.capaciteMax = capaciteMax;
        this.horaire = horaire;
    }

    public Activite (int id, String nom, String description, int capaciteMax, LocalDateTime horaire) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.capaciteMax = capaciteMax;
        this.horaire = horaire;
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public String getDescription() {
        return this.description;
    }

    public int getCapaciteMax() {
        return this.capaciteMax;
    }

    public LocalDateTime getHoraire() {
        return this.horaire;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapaciteMax(int capaciteMax) {
        this.capaciteMax = capaciteMax;
    }

    public void setHoraire(LocalDateTime horaire) {
        this.horaire = horaire;
    }
}
