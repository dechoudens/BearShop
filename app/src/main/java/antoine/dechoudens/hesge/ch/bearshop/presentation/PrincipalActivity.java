package antoine.dechoudens.hesge.ch.bearshop.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    private RadioGroup rgFiltrePrix;
    private RadioButton rb1, rb2, rb3, rb4;
    private ImageButton imbPanier;
    private Button btnFiltrer;
    private ListView lvBears;
    private ListeBears listeBears;
    private ListeBears.Filtre filtrePrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        initComponent();
        initialise();
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

        filtrePrix = new ListeBears.Filtre() {
            @Override
            public boolean isIn(Bear bear) {
                RadioButton rb = (RadioButton) findViewById(rgFiltrePrix.getCheckedRadioButtonId());
                double prix = bear.getPrix();
                switch ((Integer)rb.getTag()){
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

} // PrincipalActivity
