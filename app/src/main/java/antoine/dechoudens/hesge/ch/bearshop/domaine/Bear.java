package antoine.dechoudens.hesge.ch.bearshop.domaine;

/**
 * Created by antoine.dechoude on 16.11.2016.
 */
public class Bear {
    private int id;
    private String nom;
    private int taille;
    private double prix;

    public Bear(int id, String nom, int taille, double prix) {
        this.id = id;
        this.nom = nom;
        this.taille = taille;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getTaille() {
        return taille;
    }

    public double getPrix() {
        return prix;
    }
}
