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
    private boolean checked;

    public Bear(int id, String nom, int taille, double prix, int refPetiteImage, int refGrandImage) {
        this.id = id;
        this.nom = nom;
        this.taille = taille;
        this.prix = prix;
        this.refGrandImage = refGrandImage;
        this.refPetiteImage = refPetiteImage;
        this.checked = false;
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

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {

        return checked;
    }

    @Override
    public int compareTo(Bear another) {
        return nom.compareTo(another.getNom());
    }

    @Override
    public boolean equals(Object o) {
        Bear bear = (Bear) o;
        return nom != null ? nom.equals(bear.nom) : bear.nom == null;
    }
}
