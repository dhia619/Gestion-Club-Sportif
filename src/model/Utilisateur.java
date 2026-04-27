package model;

import java.time.LocalDate;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String telephone;
    private String adresse;
    private double poids;
    private String login;
    private String motDePasse;
    private String role;

    public Utilisateur(
        String nom, 
        String prenom, 
        LocalDate dateNaissance, 
        String telephone, 
        String adresse, 
        double poids, 
        String login, 
        String motDePasse,
        String role
    ) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.adresse = adresse;
        this.poids = poids;
        this.login = login;
        this.motDePasse = motDePasse;
        this.role = role;
    }

    public String getNom(){
        return this.nom;
    }

    public String getPrenom(){
        return this.prenom;
    }

    public LocalDate getDateNaissance(){
        return this.dateNaissance;
    }

    public String getAdresse(){
        return this.adresse;
    }

    public String getTelephone(){
        return this.telephone;
    }

    public double getPoids(){
        return this.poids;
    }

    public String getLogin(){
        return this.login;
    }

    public String getMotDePasse(){
        return this.motDePasse;
    }

    public String getRole(){
        return this.role;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setPrenom(String prenom){
        this.prenom = prenom;
    }

    public void setDateNaissance(LocalDate dateNaissance){
        this.dateNaissance = dateNaissance;
    }

    public void setAdresse(String adresse){
        this.adresse = adresse;
    }

    public void setTelephone(String telephone){
        this.telephone = telephone;
    }

    public void setPoids(double poids){
        this.poids = poids;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void setMotDePasse(String motDePasse){
        this.motDePasse = motDePasse;
    }

    public void setRole(String role){
        this.role = role;
    }
}
