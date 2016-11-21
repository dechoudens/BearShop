package antoine.dechoudens.hesge.ch.bearshop.base;

import android.content.res.Resources;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.TreeSet;

import antoine.dechoudens.hesge.ch.bearshop.R;
import antoine.dechoudens.hesge.ch.bearshop.domaine.Bear;


/**
 * Module 635.1 - Programmation - TP S05
 *
 * Accès aux données.
 *
 * Format des fichiers:
 * - genre.txt: identifiant;libellé du genre
 * - series.txt: identifiant;titre;audience;synopsis;<liste des identifiants de genre>
 *
 * Remarques:
 * - La <liste des identifiants de genre> est de longueur variable.
 * - L'affiche correspondant à une série est "affiche" suivi de l'identifiant de la série
 *
 * @author Antoine de Choudens
 * @version Version 1.0
 */
public class Data {

    private static final int EOF = -1;                   /* Marque de fin de fichier */

    private static int refImage (Resources res, String nomImage){
        return res.getIdentifier(nomImage.toLowerCase(), "drawable", R.class.getPackage().getName());
    }

    /* Lecture de l'InputStream inStream et retour de la séquence de charactères dans un String */
    private static String lireStr (InputStream inStream) {
        BufferedInputStream in = new BufferedInputStream(inStream);
        StringBuilder b = new StringBuilder();
        try {
            b.ensureCapacity(in.available() + 10);
            int c = in.read();
            while (c != EOF) {b.append((char)c); c = in.read();}
            in.close();
        }
        catch (IOException e) {e.printStackTrace();}
        return b.toString();
    } // lireStr

    public static TreeSet<Bear> getBears(Resources res){
        TreeSet<Bear> bears = new TreeSet<>();
        String[] lstBear = lireStr(res.openRawResource(R.raw.bears)).split("\r\n");
        for (int i = 0; i < lstBear.length; i++){
            String[] val = lstBear[i].split(";");
            String id = val[0];
            String nom = val[1];
            String taille = val[2];
            String prix = val[3];
            int imgPetite = refImage(res, nom + 0);
            int imgGrande = refImage(res, nom + 1);
            Bear serie = new Bear(Integer.parseInt(id), nom, Integer.parseInt(taille), Double.parseDouble(prix), imgPetite, imgGrande);
            bears.add(serie);
        }
        return bears;
    }

} // Data
