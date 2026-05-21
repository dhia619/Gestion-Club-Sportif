package model;

public class MembreActifRow {
    
    private Utilisateur membre;
    private int nbInscriptionsAccepte;

    public MembreActifRow(Utilisateur membre, int nbInscriptionAccepte) {
        this.membre = membre;
        this.nbInscriptionsAccepte = nbInscriptionAccepte;
    }

    public Utilisateur getMembre() {
        return membre;
    }

    public void setMembre(Utilisateur membre) {
        this.membre = membre;
    }

    public int getNbInscriptionsAccepte() {
        return nbInscriptionsAccepte;
    }

    public void setNbInscriptionsAccepte(int nbInscriptionsAccepte) {
        this.nbInscriptionsAccepte = nbInscriptionsAccepte;
    }
}
