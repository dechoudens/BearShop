package antoine.dechoudens.hesge.ch.bearshop.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import antoine.dechoudens.hesge.ch.bearshop.R;
import antoine.dechoudens.hesge.ch.bearshop.domaine.Bear;
import antoine.dechoudens.hesge.ch.bearshop.metier.ListePanier;

/**
 * Module 635.1 - Programmation
 * Activité principale
 *
 * @author ??? - HEG-Genève
 * @version Version 1.0
 */
public class PanierActivity extends AppCompatActivity {

    private EditText edNbPiece;
    private EditText edSomme;
    private ImageButton imbDelete;
    private ListView lvBearPanier;
    private ListePanier listePanier;
    private List<HashMap<String, Object>> selection, deleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier_port);
        initComponent();
        initialise();
        initListener();
    } // onCreate

    private void initComponent() {
        edNbPiece = (EditText) findViewById(R.id.edNbPiece);
        edSomme = (EditText)findViewById(R.id.edSomme);
        imbDelete = (ImageButton)findViewById(R.id.imbDelete);
        lvBearPanier = (ListView)findViewById(R.id.lvBearPanier);
    }

    private void initialise() {
        Intent intent = getIntent();
        listePanier = new ListePanier(getApplicationContext(), (List)intent.getSerializableExtra("selection"));
        lvBearPanier.setAdapter(listePanier.getAdapter());
        selection = new ArrayList<>();
        afficherStat();
        deleted = new ArrayList<>();
    }

    private void initListener() {
        lvBearPanier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.ckSelectionPanier);
                HashMap<String, Object> bear = listePanier.getOneBear(position);
                if(cb.isChecked()) {
                    cb.setChecked(false);
                    selection.remove(bear);
                }
                else{
                    cb.setChecked(true);
                    selection.add(bear);
                }
            }
        });

        imbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (HashMap<String, Object> HMbear : selection){
                    deleted.add(HMbear);
                    System.out.println((Bear)HMbear.get("Ref Bear"));
                    listePanier.deleteOneBear(HMbear);
                }
                for(int i=0; i < lvBearPanier.getChildCount(); i++){
                    RelativeLayout itemLayout = (RelativeLayout)lvBearPanier.getChildAt(i);
                    CheckBox cb = (CheckBox)itemLayout.findViewById(R.id.ckSelectionPanier);
                    cb.setChecked(false);
                }

                afficherStat();
            }
        });
    }

    private void afficherStat() {
        edSomme.setText(""+listePanier.getPrixTot());
        edNbPiece.setText(""+listePanier.getNbPiece());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        System.out.println(deleted.size() + "lol");
        intent.putExtra("deleted", (Serializable)deleted);
        setResult(RESULT_OK, intent);
        finish();
        //deleted.clear();
    }
} // PanierActivity
