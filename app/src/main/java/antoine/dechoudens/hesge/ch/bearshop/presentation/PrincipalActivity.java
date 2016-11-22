package antoine.dechoudens.hesge.ch.bearshop.presentation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private List<HashMap<String, Object>> selection;
    private ListeBears.Filtre filtrePrix;

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
                CheckBox cb = (CheckBox) view.findViewById(R.id.ckSelectionPrincipal);
                HashMap<String, Object> bear = listeBears.getOneBear(position);
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

        imbPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), PanierActivity.class);
                intent.putExtra("selection", (Serializable) selection);
                startActivityForResult(intent, 1);
            }
        });
    }

    public void filtrer(View v){
        listeBears.doFiltre(filtrePrix);
        btnFiltrer.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                //je sais pas
            }
        }
    }

} // PrincipalActivity
