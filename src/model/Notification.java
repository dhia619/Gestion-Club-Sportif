package model;

import java.time.LocalDateTime;

public class Notification {

    private int id;

    private Utilisateur utilisateur;

    private String message;

    private boolean lu;

    private LocalDateTime dateCreation;

    public Notification() {
    }

    public Notification(
        Utilisateur utilisateur,
        String message,
        boolean lu,
        LocalDateTime dateCreation
    ) {
        this.utilisateur = utilisateur;
        this.message = message;
        this.lu = lu;
        this.dateCreation = dateCreation;
    }

    public Notification(
        int id,
        Utilisateur utilisateur,
        String message,
        boolean lu,
        LocalDateTime dateCreation
    ) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.message = message;
        this.lu = lu;
        this.dateCreation = dateCreation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}