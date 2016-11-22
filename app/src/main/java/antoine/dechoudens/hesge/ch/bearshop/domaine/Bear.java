package antoine.dechoudens.hesge.ch.bearshop.domaine;

import java.io.Serializable;

/**
 * Created by antoine.dechoude on 16.11.2016.
 */
public class Bear implements Comparable<Bear>, Serializable{
    private int id;
    private String nom;
    private int taille;
    private double prix;
    private int refPetiteImage;
    private int refGrandImage;

    public Bear(int id, String nom, int taille, double prix, int refPetiteImage, int refGrandImage) {
        this.id = id;
        this.nom = nom;
        this.taille = taille;
        this.prix = prix;
        this.refGrandImage = refGrandImage;
        this.refPetiteImage = refPetiteImage;
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

    public int getRefPetiteImage() {
        return refPetiteImage;
    }

    public int getRefGrandImage() {
        return refGrandImage;
    }

    @Override
    public int compareTo(Bear another) {
        return nom.compareTo(another.getNom());
    }
}
