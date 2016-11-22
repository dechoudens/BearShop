package com.prog.patrick.bears.presentation;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.prog.patrick.bears.R;
import com.prog.patrick.bears.R;
import com.prog.patrick.bears.base.Data;
import com.prog.patrick.bears.domaine.Peluche;
import com.prog.patrick.bears.metier.ListePeluche;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Module 635.1 - Programmation
 * Activité principale
 *
 * @author Adrien Dallinge
 * @version Version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private ListePeluche listePeluches;
    private ListePeluche.Filtre filtrePeluches;
    private ListView lvPeluches;
    private Button btnFiltrer;
    private ImageButton btnPanier;
    private EditText etPanier;
    private ImageView im_peluche;
    private TextView tvNom;
    private TextView tvTaille;
    private TextView tvPrix;
    private CheckBox ckPel;
    private RadioGroup rbGrp;
    private static final String ID_RB_ACTUEL = "idRbActuel";
    private static final String POS_FIRST_VISIBLE = "posFirstVisible";
    private TreeSet<Peluche> tsPeluche = new TreeSet<>();

    private void definirVariables () {
        lvPeluches = (ListView)findViewById(R.id.lv_Peluche);
        etPanier = (EditText)findViewById(R.id.et_Panier);
        btnFiltrer = (Button) findViewById(R.id.btn_Filtrer);
        btnPanier = (ImageButton) findViewById(R.id.im_Panier);
        rbGrp=(RadioGroup)findViewById(R.id.rgGrp);
        ckPel=(CheckBox)findViewById(R.id.ck_Pel);
        im_peluche = (ImageView)findViewById(R.id.im_peluche);
        tvNom = (TextView)findViewById(R.id.tvNom);
        tvTaille = (TextView)findViewById(R.id.tvTaille);
        tvPrix = (TextView)findViewById(R.id.tvPrix);

    } // definirVariables

    private void definirListener(){
        btnFiltrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtrePeluches = new ListePeluche.Filtre() {
                    @Override public boolean isIn (Peluche p) {
                        int rb = rbGrp.indexOfChild(findViewById(rbGrp.getCheckedRadioButtonId()));
                        switch (rb) {
                            case 0: if (p.getPrix() < 30) return true;
                                break;
                            case 1: if (p.getPrix() >= 30 && p.getPrix() <= 50) return true;
                                break;
                            case 2: if (p.getPrix() > 50) return true;
                                break;
                            case 3: return true;
                        }
                        return false;
                    }
                };
                listePeluches.doFiltre(filtrePeluches);
                lvPeluches.setAdapter(listePeluches.getAdapter(filtrePeluches));
                btnFiltrer.setEnabled(false);
            }
        });

        rbGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                btnFiltrer.setEnabled(true);
            }
        });

        lvPeluches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?>  parent, View view, int position, long id) {
                if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
                    Peluche p = listePeluches.getPeluche(position);
                    im_peluche.setImageResource(p.getImgGr());
                    tvNom.setText(p.getNom());
                    tvTaille.setText(Integer.toString(p.getTaille())+" [cm]");
                    tvPrix.setText(Double.toString(p.getPrix())+ " CHF");
                    setEtat(true);
                }
                return true;
            }
        });

       lvPeluches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                @SuppressWarnings("unchecked")
                HashMap<String, Object> hm = (HashMap<String, Object>)parent.getItemAtPosition(position);
                CheckBox cb = (CheckBox)view.findViewById(R.id.ck_Pel);
                cb.setChecked(!cb.isChecked());
                hm.put("checkbox", cb.isChecked());
                Peluche p = listePeluches.getPeluche(position);
                if (cb.isChecked()) {
                    tsPeluche.add(p);
                }else {
                    tsPeluche.remove(p);
                }
                etPanier.setText(String.valueOf(tsPeluche.size()));
                ((SimpleAdapter)lvPeluches.getAdapter()).notifyDataSetChanged();

            } // onItemClick
        });

        btnPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Panier.class);
                    intent.putExtra("liste", tsPeluche);
                    startActivityForResult(intent, 1);
            }
        });
    }

    public void setEtat(boolean visible) {
        if (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE) {
            if (visible) {
                im_peluche.setVisibility(VISIBLE);
                tvNom.setVisibility(VISIBLE);
                tvTaille.setVisibility(VISIBLE);
                tvPrix.setVisibility(VISIBLE);
            } else {
                im_peluche.setVisibility(INVISIBLE);
                tvNom.setVisibility(INVISIBLE);
                tvTaille.setVisibility(INVISIBLE);
                tvPrix.setVisibility(INVISIBLE);
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK) {
            tsPeluche = (TreeSet<Peluche>) data.getSerializableExtra("liste");
            majAdapter();
        }
    }

    public void majAdapter() {
        for (int i = 0; i< listePeluches.getItems();i++) {
            View vi = (View) lvPeluches.getAdapter().getView(i, null, lvPeluches);
            CheckBox cb = (CheckBox) vi.findViewById(R.id.ck_Pel);
            cb.setChecked(tsPeluche.contains(listePeluches.getPeluche(i)));
            HashMap<String, Object> hm = (HashMap<String, Object>)lvPeluches.getItemAtPosition(i);
            hm.put("checkbox", cb.isChecked());
        }
        ((SimpleAdapter)lvPeluches.getAdapter()).notifyDataSetChanged();
        etPanier.setText(String.valueOf(tsPeluche.size()));
    }

    private void initialise () {
        listePeluches = new ListePeluche(MainActivity.this, Data.getPeluche(getResources()),false);
        filtrePeluches = new ListePeluche.Filtre() {
            @Override public boolean isIn (Peluche p) {return true;}
        };
        listePeluches.doFiltre(filtrePeluches);
        lvPeluches.setAdapter(listePeluches.getAdapter(filtrePeluches));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        definirVariables();
        initialise();
        setEtat(false);
        definirListener();
    } // onCreate

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState); // Nécessaire si on veut que le système sauve l'état des contrôles ainsi que le focus.
        outState.putInt(ID_RB_ACTUEL, rbGrp.indexOfChild(findViewById(rbGrp.getCheckedRadioButtonId())));
        outState.putInt(POS_FIRST_VISIBLE, lvPeluches.getFirstVisiblePosition());
        outState.putSerializable("ts",tsPeluche);
    } // onSaveInstanceState

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState); // Nécessaire si on veut que le système restaure l'état sauvé par super.onSaveInstanceState().
        if (rbGrp.indexOfChild(findViewById(rbGrp.getCheckedRadioButtonId())) != -1) {
            tsPeluche = (TreeSet<Peluche>) savedInstanceState.getSerializable("ts");
            majAdapter();
            lvPeluches.setSelection(savedInstanceState.getInt(POS_FIRST_VISIBLE));
        }
    } // onRestoreInstanceState

} // MainActivity
