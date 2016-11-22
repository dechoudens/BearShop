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
 * Created by antoine.dechoude on 16.11.2016.
 */
public class ListeBears implements Serializable{
    public static final String REF_BEAR = "Ref bear";
    private static final String[] FROM = {"Image", "Nom", "Taille", "Prix"};
    private static final int[] TO = {R.id.ivBear, R.id.tvNomPrincipal, R.id.tvTaillePrincipal, R.id.tvPrixPrincipal};

    private TreeSet<Bear> bears;
    private List<HashMap<String, Object>> dataAll, dataFiltre;
    private SimpleAdapter adapter;



    public interface Filtre{
        boolean isIn(Bear bear);
    }


    public ListeBears(Context context, TreeSet<Bear> bears){
        this.bears = bears;
        dataAll = new ArrayList<HashMap<String, Object>>(bears.size());
        for (Bear b : bears){
            HashMap<String, Object> map = new HashMap<>();
            map.put(FROM[0], b.getRefPetiteImage());
            map.put(FROM[1], b.getNom());
            map.put(FROM[2], b.getTaille());
            map.put(FROM[3], b.getPrix());
            map.put("checkbox", false);
            map.put(REF_BEAR, b);
            dataAll.add(map);
        }
        dataFiltre = new ArrayList<HashMap<String, Object>>(bears.size());
        adapter = new SimpleAdapter(context, dataFiltre, R.layout.un_bear_main, FROM, TO);
    }

    public void doFiltre(Filtre filtre) {
        dataFiltre.clear();
        for (HashMap<String, Object> hm : dataAll){
            Bear b = (Bear)hm.get(REF_BEAR);
            if (filtre.isIn(b)){
                dataFiltre.add(hm);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public HashMap<String, Object> getOneBear(int id){
        return dataFiltre.get(id);
    }

    public Bear getBear(int i){
        List<Bear> list = new ArrayList<Bear> (bears);
        return list.get(i);
    }

    public SimpleAdapter getAdapter () {
        return adapter;
    }

    public List<HashMap<String,Object>> getListeBears() {
        return dataFiltre;
    }

    public int getSize() {
        return dataFiltre.size();
    }

}
