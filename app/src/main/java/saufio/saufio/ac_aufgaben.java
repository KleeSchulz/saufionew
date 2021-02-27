package saufio.saufio;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ac_aufgaben extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String menu_loeschen;
    String menu_bearbeiten;
    Spinner spinner;
    ListView listView;
    ArrayList<String> aufgabe;
    ArrayAdapter<String> arrayAdapter;
    List<String> kategorien;
    int auswahl;
    ImageView img_bck, img_bck02;
    Timer timer;
    boolean background;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_aufgaben);
        //Listview adapter
        listView = (ListView) findViewById(R.id.listview);
        DatabaseHandler db = new DatabaseHandler(this);
        aufgabe = (ArrayList<String>) db.getAllAufgabeString();
        aufgabe.add(0,getResources().getString(R.string.Aufgabeadd));
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aufgabe);
        listView.setAdapter(arrayAdapter);
        registerForContextMenu(listView);
        menu_bearbeiten=getResources().getString(R.string.change);
        menu_loeschen=getResources().getString(R.string.delete);
        spinner=(Spinner)findViewById(R.id.spinner_filter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                int selectedFromList =(int) (listView.getItemIdAtPosition(myItemInt));
            }
        });
        //Spinner
        kategorien=db.getKategorien();
        kategorien.add(0,getResources().getString(R.string.alleaufgaben));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kategorien);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        setzeTon();
        img_bck=(ImageView)findViewById(R.id.img_background);
        img_bck02=(ImageView)findViewById(R.id.img_background02);
        zeitBisChange();
    }





    @Override
    public void onBackPressed() {
        startActivity(new Intent(ac_aufgaben.this, MainActivity.class));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        ListView lv = (ListView) listView;
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        auswahl = (int) lv.getItemIdAtPosition(acmi.position);
        if (auswahl != 0) {
            menu.setHeaderTitle(getResources().getString(R.string.record));
            menu.add(0, v.getId(), 0, menu_bearbeiten);
            menu.add(0, v.getId(), 0, menu_loeschen);
        } else {
            Intent intent = new Intent(ac_aufgaben.this,AC_bearbeiten.class);
            startActivity(intent);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String  ersetzen = aufgabe.get(auswahl);
        ersetzen=ersetzen.replace(getResources().getString(R.string.einzelsschluck),"$");
        for (int i=0;i<MainActivity.maxvalueshots+1;i++){
            ersetzen = ersetzen.replace(String.valueOf(i) +" "+getResources().getString(R.string.mehrzahlschluck),"$");
        }
        Aufgabe a= db.sucheUeberTxt(ersetzen);
        if (menu_loeschen==item.getTitle()) {
            if (a.getAenderung()=="N"){
            } else {
                db.deleteAufgabe(a);
                listviewRefresh();
            }
        } else if (menu_bearbeiten==item.getTitle()){
            Intent intent = new Intent(ac_aufgaben.this,AC_bearbeiten.class);
            intent.putExtra("IDAufgabe",a.getId());
            startActivity(intent);
        }
        return true;
    }

    public void listviewRefresh(){
        aufgabe.clear();
        aufgabe= (ArrayList<String>) db.getAllAufgabeString();
        arrayAdapter.clear();
        aufgabe.add(0,getResources().getString(R.string.Aufgabeadd));
        arrayAdapter.addAll(aufgabe);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 0) {
            aufgabe.clear();
            aufgabe= (ArrayList<String>) db.getAllAufgabeString();
            arrayAdapter.clear();
            arrayAdapter.addAll(replaceListe(aufgabe));
            listView.setAdapter(arrayAdapter);
        }
        else {
            setzeFilter(kategorien.get(i));
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void setzeFilter(String kategorie){
        aufgabe.clear();
        aufgabe= (ArrayList<String>) db.aufgabeKategorie("'"+kategorie+"'");
        arrayAdapter.clear();
        arrayAdapter.addAll(replaceListe(aufgabe));
        listView.setAdapter(arrayAdapter);
    }

    public ArrayList<String> replaceListe(ArrayList<String> ersetzen){
        for (int j=0;j<ersetzen.size();j++){
            String aufgabetxt = ersetzen.get(j);
            int zufall=new Random().nextInt(MainActivity.maxvalueshots)+MainActivity.minvalueshots;
            String schluck;
            if (zufall==1){
                schluck =getResources().getString(R.string.einzelsschluck);
            } else {
                schluck=String.valueOf(zufall) +" "+getResources().getString(R.string.mehrzahlschluck);
            }
            aufgabetxt=aufgabetxt.replace("$",schluck);
            aufgabe.set(j, aufgabetxt);
        }
        aufgabe.add(0,getResources().getString(R.string.Aufgabeadd));
        return aufgabe;
    }

    public void setzeTon(){
        boolean ton = Allgemein.gebeTon(this);
        spinner.setSoundEffectsEnabled(ton);
        listView.setSoundEffectsEnabled(ton);
    }


    public void zeitBisChange() {

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        change();
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 10000);
    }

    public void change(){
        if (background){
            img_bck02.setAlpha(0f);
            img_bck02.setVisibility(View.VISIBLE);
            img_bck02.animate()
                    .alpha(0.9f)
                    .setDuration(2500)
                    .setListener(null);
            img_bck.animate()
                    .alpha(0f)
                    .setDuration(2500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            img_bck.setVisibility(View.GONE);
                        }
                    });

            background=false;
        } else {
            img_bck.setAlpha(0f);
            img_bck.setVisibility(View.VISIBLE);
            img_bck.animate()
                    .alpha(0.9f)
                    .setDuration(2500)
                    .setListener(null);
            img_bck02.animate()
                    .alpha(0f)
                    .setDuration(2500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            img_bck02.setVisibility(View.GONE);
                        }
                    });

            background=true;
        }
    }
}