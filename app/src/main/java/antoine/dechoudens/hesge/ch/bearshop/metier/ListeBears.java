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
 * Created by antoine.dechoude on 16.11.2016.
 */
public class ListeBears {
    private static final String REF_BEAR = "Ref Serie";
    private static final String[] FROM = {};
    private static final int[] TO = {};

    private TreeSet<Bear> bears;
    private List<HashMap<String, Object>> dataAll, dataFiltre;
    private SimpleAdapter adapter;

    public interface Filtre{
        boolean isIn(Bear bear);
    }


    public ListeBears(Context context, TreeSet<Bear> series){
        this.bears = series;
        dataAll = new ArrayList<HashMap<String, Object>>(series.size());
        for (Bear b : series){
            HashMap<String, Object> map = new HashMap<>();
            map.put(FROM[0], b.getId());
            map.put(REF_BEAR, b);
            dataAll.add(map);
        }
        dataFiltre = new ArrayList<HashMap<String, Object>>(series.size());
        adapter = new SimpleAdapter(context, dataFiltre, R.layout.un_bear, FROM, TO);
    }

    public void doFiltre(Filtre filtre) {
        dataFiltre.clear();
        for (HashMap<String, Object> hm : dataAll){
            Bear b = (Bear)hm.get(REF_BEAR);

        }
        adapter.notifyDataSetChanged();
    }

    public SimpleAdapter getAdapter () {
        return adapter;
    }
}
