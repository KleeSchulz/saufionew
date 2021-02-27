package saufio.saufio;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AC_bearbeiten extends AppCompatActivity {

Aufgabe aufgabe;
Button btn_add;
EditText et_Aufgabe;
Spinner spinner;
int id;

    DatabaseHandler db = new DatabaseHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_c_bearbeiten);
        btn_add = (Button) findViewById(R.id.btn_add);
        et_Aufgabe = (EditText) findViewById(R.id.et_Aufgabe);
        id = getIntent().getIntExtra("IDAufgabe", 0);
        //Spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter;
        List<String> spinnerlist;
        spinnerlist = db.getKategorien();
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, spinnerlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (id != 0) {
            aufgabe = db.getAufgabe(id);
            et_Aufgabe.setText(aufgabe.getAufgabe());
            btn_add.setText(getResources().getString(R.string.update));
            for (int i=0;i<spinnerlist.size();i++){
                if (aufgabe.getKategorie().equals(spinnerlist.get(i))){
                    spinner.setSelection(i);
                    break;
                }
            }
        }
        setzeTon();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(AC_bearbeiten.this, ac_aufgaben.class));
    }

    //Update Aufgabe
    public void aufgabe_update_add(View view) {
        if (id==0) {
            if (et_Aufgabe.getText().toString().equals("")){
                Toast x= Toast.makeText(getApplicationContext(),getResources().getString(R.string.aufgabeleer),Toast.LENGTH_SHORT);
                x.setGravity(Gravity.BOTTOM,0,0);
                x.show();
            } else {
                db.addAufgabe(new Aufgabe(1, et_Aufgabe.getText().toString(), "E", spinner.getSelectedItem().toString()));
                Toast x = Toast.makeText(getApplicationContext(), getResources().getString(R.string.aufgabeadded), Toast.LENGTH_SHORT);
                x.setGravity(Gravity.BOTTOM, 0, 0);
                x.show();
                et_Aufgabe.setText("");
                et_Aufgabe.setHint("Aufgabe eintragen");
            }
        } else if (id!=0) {
            if (et_Aufgabe.getText().toString().equals("")){
                Toast x= Toast.makeText(getApplicationContext(),"Aufgabe darf nicht leer sein",Toast.LENGTH_SHORT);
                x.setGravity(Gravity.BOTTOM,0,0);
                x.show();
            } else {
                aufgabe.setAufgabe(et_Aufgabe.getText().toString());
                aufgabe.setKategorie(spinner.getSelectedItem().toString());
                db.updateAufgabe(aufgabe);
                startActivity(new Intent(AC_bearbeiten.this, ac_aufgaben.class));
            }
        }
    }

    //setze Tonbild und Buttons
    public void setzeTon(){
        boolean ton = Allgemein.gebeTon(this);
        btn_add.setSoundEffectsEnabled(ton);
        et_Aufgabe.setSoundEffectsEnabled(ton);
        spinner.setSoundEffectsEnabled(ton);
    }
}