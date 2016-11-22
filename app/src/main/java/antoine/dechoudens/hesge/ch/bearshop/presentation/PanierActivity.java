package antoine.dechoudens.hesge.ch.bearshop.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    private List<Bear> selection;
    private TextView tvDetailNomPanier;
    private TextView tvDetailPrixPanier;
    private TextView tvDetailTaillePanier;
    private ImageView imBearGrandPanier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        initComponent();
        initialise();
        initListener();
    } // onCreate

    private void initComponent() {
        edNbPiece = (EditText) findViewById(R.id.edNbPiece);
        edSomme = (EditText)findViewById(R.id.edSomme);
        imbDelete = (ImageButton)findViewById(R.id.imbDelete);
        lvBearPanier = (ListView)findViewById(R.id.lvBearPanier);
        tvDetailNomPanier = (TextView)findViewById(R.id.tvDetailNomPanier);
        tvDetailPrixPanier = (TextView)findViewById(R.id.tvDetailPrixPanier);
        tvDetailTaillePanier = (TextView)findViewById(R.id.tvDetailTaillePanier);
        imBearGrandPanier = (ImageView) findViewById(R.id.imBearGrandPanier);
    }

    private void initialise() {
        Intent intent = getIntent();
        listePanier = new ListePanier(getApplicationContext(), (List)intent.getSerializableExtra("selection"));
        lvBearPanier.setAdapter(listePanier.getAdapter());
        selection = new ArrayList<>();
        afficherStat();
    }

    private void initListener() {
        lvBearPanier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.ckSelectionPanier);
                HashMap<String, Object> HMbear = listePanier.getOneBear(position);
                Bear bear = (Bear)HMbear.get(listePanier.REF_BEAR);
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
                for (Bear bear : selection){
                    listePanier.deleteOneBear(bear);
                }
                for(int i=0; i < lvBearPanier.getChildCount(); i++){
                    RelativeLayout itemLayout = (RelativeLayout)lvBearPanier.getChildAt(i);
                    CheckBox cb = (CheckBox)itemLayout.findViewById(R.id.ckSelectionPanier);
                    cb.setChecked(false);
                }

                afficherStat();
            }
        });

        lvBearPanier.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                Bear bear = listePanier.getBear(pos);
                imBearGrandPanier.setImageResource(bear.getRefGrandImage());
                tvDetailNomPanier.setText(bear.getNom().toString());
                tvDetailPrixPanier.setText(String.valueOf(bear.getTaille() + " " + getString(R.string.libTaille)));
                tvDetailTaillePanier.setText(String.valueOf(bear.getPrix() + " " + getString(R.string.libPrix)));
                return true;
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
        intent.putExtra("Retour", (Serializable)listePanier.getListePanier());
        setResult(RESULT_OK, intent);
        finish();
        //deleted.clear();
    }
} // PanierActivity
