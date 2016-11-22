package antoine.dechoudens.hesge.ch.bearshop.metier;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import antoine.dechoudens.hesge.ch.bearshop.R;
import antoine.dechoudens.hesge.ch.bearshop.domaine.Bear;

/**
 * Created by Meckanik on 21.11.2016.
 */
public class ListePanier {
    private static final String REF_BEAR = "Ref Bear";
    private static final String[] FROM = {"Nom", "Taille", "Prix"};
    private static final int[] TO = {R.id.tvNomPanier, R.id.tvTaillePanier, R.id.tvPrixPanier};

    private List<HashMap<String, Object>> dataAll;

    private SimpleAdapter adapter;

    public interface Filtre{
        boolean isIn(Bear bear);
    }

    public ListePanier(Context context, List<HashMap<String, Object>>  bears){
        dataAll = bears;
        adapter = new SimpleAdapter(context, dataAll, R.layout.un_bear_panier, FROM, TO);
    }

    public SimpleAdapter getAdapter () {
        adapter.notifyDataSetChanged();
        return adapter;
    }

    public HashMap<String, Object> getOneBear(int id){
        return dataAll.get(id);
    }

    public void deleteOneBear(HashMap<String, Object> bear){
        dataAll.remove(bear);
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
}
