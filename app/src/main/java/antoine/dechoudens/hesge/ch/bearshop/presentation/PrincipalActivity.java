package antoine.dechoudens.hesge.ch.bearshop.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import antoine.dechoudens.hesge.ch.bearshop.R;
import antoine.dechoudens.hesge.ch.bearshop.base.Data;
import antoine.dechoudens.hesge.ch.bearshop.domaine.Bear;
import antoine.dechoudens.hesge.ch.bearshop.metier.ListeBears;

/**
 * Module 635.1 - Programmation
 * Activité principale
 *
 * @author ??? - HEG-Genève
 * @version Version 1.0
 */
public class PrincipalActivity extends AppCompatActivity {
    private final int F1 = 1, F2 = 2,F3 =3, F4 = 4;
    private int currentFiltre;
    private RadioGroup rgFiltrePrix;
    private RadioButton rb1, rb2, rb3, rb4;
    private ImageButton imbPanier;
    private Button btnFiltrer;
    private ListView lvBears;
    private ListeBears listeBears;
    private EditText edPanier;
    private List<HashMap<String, Object>> selection;
    private ListeBears.Filtre filtrePrix;

    private static final String ID_ROT_ACTUEL = "idRotActuel";
    private static final String POS_FIRST_VISIBLE = "posFirstVisible";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_port);
        initComponent();
        initialise();
        initListener();
    } // onCreate

    private void initComponent() {
        rgFiltrePrix = (RadioGroup) findViewById(R.id.rgFiltrePrix);
        imbPanier = (ImageButton) findViewById(R.id.imbPanier);
        btnFiltrer = (Button) findViewById(R.id.btnFiltrer);
        lvBears = (ListView) findViewById(R.id.lvBearPrincipal);
        rb1 = (RadioButton) findViewById(R.id.rb1); rb1.setTag(F1);
        rb2 = (RadioButton) findViewById(R.id.rb2); rb2.setTag(F2);
        rb3 = (RadioButton) findViewById(R.id.rb3); rb3.setTag(F3);
        rb4 = (RadioButton) findViewById(R.id.rb4); rb4.setTag(F4);
        edPanier = (EditText) findViewById(R.id.edPanier);
    }

    private void initialise() {
        listeBears = new ListeBears(getApplicationContext(), Data.getBears(getResources()));
        lvBears.setAdapter(listeBears.getAdapter());
        selection = new ArrayList<>();
        btnFiltrer.setEnabled(false);
        imbPanier.setEnabled(true);
        filtrePrix = new ListeBears.Filtre() {
            @Override
            public boolean isIn(Bear bear) {
                RadioButton rb = (RadioButton) findViewById(rgFiltrePrix.getCheckedRadioButtonId());
                int tag = (Integer) rb.getTag();
                currentFiltre = tag;
                double prix = bear.getPrix();
                switch (tag){
                    case 1:
                        if (prix < 30){
                            return true;
                        }
                        break;
                    case 2:
                        if (prix >= 30 && prix <= 50){
                            return true;
                        }
                        break;
                    case 3:
                        if (prix > 50){
                            return true;
                        }
                        break;
                    default:
                        return true;
                }
                return false;
            }
        };

        listeBears.doFiltre(filtrePrix);
    }

    private void initListener() {
        rgFiltrePrix.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) findViewById(checkedId);
                int tag = (Integer) rb.getTag();
                if (currentFiltre != tag){
                    btnFiltrer.setEnabled(true);
                }
                else{
                    btnFiltrer.setEnabled(false);
                }
            }
        });

        lvBears.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                @SuppressWarnings("unchecked")
                HashMap<String, Object> HMbear = (HashMap<String, Object>) parent.getItemAtPosition(position);
                CheckBox cb = (CheckBox) view.findViewById(R.id.ckSelectionPrincipal);
                cb.setChecked(!cb.isChecked());
                HMbear.put("checkbox", cb.isChecked());
                Bear bear = (Bear)HMbear.get("Ref Bear");
                if(bear.isChecked()) {
                    selection.remove(HMbear);
                }
                else{
                    selection.add(HMbear);
                }

                majInfo();

            }
        });

        imbPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), PanierActivity.class);
                intent.putExtra("selection", (Serializable) selection);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void majInfo() {
        edPanier.setText(String.valueOf(selection.size()));
        ((SimpleAdapter)lvBears.getAdapter()).notifyDataSetChanged();
    }

    public void filtrer(View v){
        listeBears.doFiltre(filtrePrix);
        btnFiltrer.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            selection = (List) intent.getSerializableExtra("liste");
            majAdapter();
        }
    }

    public void majAdapter(){
        for (int i = 0; i< listeBears.getSize();i++) {
            System.out.println("");
            View vi = (View) lvBears.getAdapter().getView(i, null, lvBears);

            CheckBox cb = (CheckBox) vi.findViewById(R.id.ckSelectionPrincipal);
            System.out.println(cb.toString());
            System.out.println(listeBears.getOneBear(i));
            cb.setChecked(selection.contains(listeBears.getOneBear(i)));
            HashMap<String, Object> hm = (HashMap<String, Object>)lvBears.getItemAtPosition(i);
            hm.put("checkbox", cb.isChecked());
        }
        majInfo();
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ID_ROT_ACTUEL, rgFiltrePrix.indexOfChild(findViewById(rgFiltrePrix.getCheckedRadioButtonId())));
        outState.putInt(POS_FIRST_VISIBLE, lvBears.getFirstVisiblePosition());
        outState.putSerializable("selec",(Serializable)selection);
    } // onSaveInstanceState

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (rgFiltrePrix.indexOfChild(findViewById(rgFiltrePrix.getCheckedRadioButtonId())) != -1) {
            selection = (List) savedInstanceState.getSerializable("selec");
            majAdapter();
            lvBears.setSelection(savedInstanceState.getInt(POS_FIRST_VISIBLE));
        }
    }

} // PrincipalActivity
