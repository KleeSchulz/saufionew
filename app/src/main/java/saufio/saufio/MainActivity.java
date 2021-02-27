package saufio.saufio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static int minvalueshots=1;
    public static int maxvalueshots=5;
    Button btn_start, btn_add;
    //EditText
    EditText tp_spieler;
    //TextView
    TextView tv_spieler1;
    //ArrayListe
    ArrayList<String> Spieler = new ArrayList<>();
    //Strings
    String Spielertxt;
    //int
    int numberpicker = 1;
    //boolean
    boolean rewardw;
    //erstellen Objekte
    DatabaseHandler db = new DatabaseHandler(this);
    ListView lv_kategorie;
    Switch swch_zufall;

    //Options Menü (3 Punkte)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    //Menü welcher Button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Einstellungen:
                startActivity(new Intent(com.example.saufio.MainActivity.this, com.example.saufio.Einstellungen.class));
                return true;
            case R.id.Credits:
                startActivity(new Intent(com.example.saufio.MainActivity.this, com.example.saufio.Credits.class));
                return true;
            case R.id.Aufgabe:
                startActivity(new Intent(com.example.saufio.MainActivity.this, com.example.saufio.ac_aufgaben.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Beenden der App mit Zurücktaste
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.finishAndRemoveTask();
        }
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //todo: spieler löschen - Testdaten
        Spieler.add("ICH BIN DER TEST");
        Spieler.add("ICH 2TER");
        btn_start=(Button)findViewById(R.id.btn_zumSpiel);
        btn_add=(Button)findViewById(R.id.btn_add);
        tp_spieler = (EditText) findViewById(R.id.tp_spieler);
        tv_spieler1 = (TextView) findViewById(R.id.tv_Spieler);
        lv_kategorie = (ListView) findViewById(R.id.lv_kategorie);
        Spielertxt = getResources().getString(R.string.spielernamen);
        swch_zufall = (Switch)findViewById(R.id.swch_zufall);
        //erstellen Numberpicker
        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(minvalueshots);
        np.setMaxValue(maxvalueshots);
        np.setOnValueChangedListener(onValueChangeListener);
        //ListView erstellen
        lv_kategorie.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv_kategorie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView v = (CheckedTextView) view;
                boolean currentCheck = v.isChecked();
                String user = (String) lv_kategorie.getItemAtPosition(position);

            }
        });
        buttonton();
        DatabaseHandler d = new DatabaseHandler(this);

        //Aufgabe hinzufügen bei ersten start
        if (db.getLastID() == 0) {
            Aufgabe.getAddGameaufgabe(this);
        }
        //Kategorien anzeigen lassen
        initListViewKategorie();
    }

    //Button zum Spiel
    public void btn_zumSpiel(View view) {
        if (Spieler.size() >= 2) {
            Intent i = new Intent(com.example.saufio.MainActivity.this, com.example.saufio.Hauptspiel.class);
            i.putExtra("key", Spieler);
            i.putExtra("key2", numberpicker);
            i.putExtra("key4",swch_zufall.isChecked());
            i.putExtra("key5",true);
            String s = gebeKategorie();//.substring(0,gebeKategorie().length()-1);
            if (s !="0"){
                i.putExtra("key3",s);
                if(s.matches(".*"+getResources().getString(R.string.kategroie1)+".*")){
                    alertRegeln(i,this, getResources().getString(R.string.alter_kategorie_title1), getResources().getString(R.string.alter_kategorie_message1), getResources().getString(R.string.verstanden));
                } else {
                startActivity(i);
                }
            }
            else {
                Allgemein.alertOK(this,getResources().getString(R.string.alter_kategorie_title),getResources().getString(R.string.alter_kategorie_message),getResources().getString(R.string.ok));
            }
        }
        //wenn zu wenig Spieler
        else {
            Allgemein.alertOK(this, getResources().getString(R.string.alter_spieler_title), getResources().getString(R.string.alter_spieler_message), getResources().getString(R.string.ok));
        }
    }

    //Spieler hinzufügen
    public void btn_add(View view) throws InterruptedException {
        if (tp_spieler.getText().length() != 0) {
            String spielerstring = tp_spieler.getText().toString();
            if (Spieler.contains(spielerstring)){
                Toast x = Toast.makeText(getApplicationContext(), tp_spieler.getText().toString() + getResources().getString(R.string.add_spieler_fehler), Toast.LENGTH_SHORT);
                x.setGravity(Gravity.BOTTOM, 0, 0);
                x.show();
            } else {
                Spieler.add(spielerstring);
                Spielertxt = Spielertxt + " " + tp_spieler.getText().toString() + ",";
                tv_spieler1.setText(Spielertxt.toString().substring(0, Spielertxt.length() - 1));
                tp_spieler.setText("");
                // Verstecke Keybord
                InputMethodManager keybord = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                keybord.hideSoftInputFromWindow(tp_spieler.getWindowToken(), 0);
                Toast x = Toast.makeText(getApplicationContext(), tp_spieler.getText().toString() + " " + getResources().getString(R.string.t_add), Toast.LENGTH_SHORT);
                x.setGravity(Gravity.BOTTOM, 0, 0);
                x.show();
            }
        } else {
            Allgemein.alertOK(this, getResources().getString(R.string.alter_spielername_title), getResources().getString(R.string.alter_spielername_message), getResources().getString(R.string.ok));
        }
    }

    //Numberpiocker Change
    NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
            numberpicker = numberPicker.getValue();
        }
    };

    //Listview(Kategorien) initialsieren
    private void initListViewKategorie() {
        String[] users = db.getKategorien().toArray(new String[0]);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, users);
        lv_kategorie.setAdapter(arrayAdapter);
        for (int i = 0; i < users.length; i++) {
            lv_kategorie.setItemChecked(i, false);
        }
    }

    //Übergabe für next Activity
    public String gebeKategorie(){
    SparseBooleanArray sp = lv_kategorie.getCheckedItemPositions();
    StringBuilder sb= new StringBuilder();
        for(int i=0;i<sp.size();i++){
        if(sp.valueAt(i)==true){
            sb.append("'");
            String s = ((CheckedTextView) lv_kategorie.getChildAt(i)).getText().toString();
            sb = sb.append(s+"',");
            }
        }
        if (sb.length()==0){
            return "0";
        }
        else{
            return sb.toString().substring(0, sb.length() - 1);
        }
    }

    //setzte Ton
    public void buttonton(){
        Boolean ton = Allgemein.gebeTon(this);
        btn_start.setSoundEffectsEnabled(ton);
        btn_add.setSoundEffectsEnabled(ton);
        tp_spieler.setSoundEffectsEnabled(ton);
        lv_kategorie.setSoundEffectsEnabled(ton);
        swch_zufall.setSoundEffectsEnabled(ton);
    }

    //Alert Regeln
    public void alertRegeln(final Intent y, Context c, String title, String message, String button) {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(c);
        alterDialog.setTitle(title);
        alterDialog.setMessage(message);
        alterDialog.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(y);
            }
        });
        alterDialog.create();
        alterDialog.show();
    }



}