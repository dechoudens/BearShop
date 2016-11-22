package antoine.dechoudens.hesge.ch.bearshop.metier;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import antoine.dechoudens.hesge.ch.bearshop.R;
import antoine.dechoudens.hesge.ch.bearshop.domaine.Bear;

/**
 * Created by Meckanik on 21.11.2016.
 */
public class ListePanier implements Serializable{
    public static final String REF_BEAR = "Ref bear";
    private static final String[] FROM = {"Nom", "Taille", "Prix"};
    private static final int[] TO = {R.id.tvNomPanier, R.id.tvTaillePanier, R.id.tvPrixPanier};

    private List<HashMap<String, Object>> dataAll;

    private SimpleAdapter adapter;
    private List<Bear> bears;

    public interface Filtre{
        boolean isIn(Bear bear);
    }

    public ListePanier(Context context, List<Bear> bears){
        this.bears = bears;
        dataAll = new ArrayList<HashMap<String, Object>>();
        for (Bear b : bears){
            HashMap<String, Object> map = new HashMap<>();
            map.put(FROM[0], b.getNom());
            map.put(FROM[1], b.getTaille());
            map.put(FROM[2], b.getPrix());
            map.put("checkbox", false);
            map.put(REF_BEAR, b);
            dataAll.add(map);
        }
        adapter = new SimpleAdapter(context, dataAll, R.layout.un_bear_panier, FROM, TO);
    }

    public SimpleAdapter getAdapter () {
        adapter.notifyDataSetChanged();
        return adapter;
    }

    public Bear getBear(int i){
        List<Bear> list = new ArrayList<Bear> (bears);
        return list.get(i);
    }

    public HashMap<String, Object> getOneBear(int id){
        return dataAll.get(id);
    }

    public void deleteOneBear(Bear bear){
        for (HashMap<String, Object> hm: dataAll) {
            if (((Bear)hm.get("Ref bear")).equals(bear)){
                dataAll.remove(hm);
                bears.remove(bear);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    public double getPrixTot(){
        double prixTot = 0.0;
        for (HashMap<String, Object> hm: dataAll) {
            prixTot += Double.parseDouble(hm.get("Prix").toString());
        }
        return prixTot;
    }

    public int getNbPiece(){
        return dataAll.size();
    }

    public List<Bear> getListePanier() {
        return bears;
    }
}
