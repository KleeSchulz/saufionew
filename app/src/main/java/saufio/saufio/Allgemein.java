package saufio.saufio;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;

import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class Allgemein {

    static String KEY_TON = "KEY_TON";
    SharedPreferences sharedpreferences;
    static SharedPreferences prefs;
    static SharedPreferences.Editor prefsedit;

    //Alert OK ohne weiteres auszuführen
    public static void alertOK(Context c, String title, String message, String button) {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(c);
        alterDialog.setTitle(title);
        alterDialog.setMessage(message);
        alterDialog.setPositiveButton(button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alterDialog.create();
        alterDialog.show();
    }

    //gebe Ton Variable
    public static boolean gebeTon(Context c){
        final SharedPreferences prefs;
        prefs = c.getSharedPreferences("Speicher", MODE_PRIVATE);
        return prefs.getBoolean(KEY_TON, true);
    }

    //setze Ton Überall
    public static boolean tonaendern(Context c) {
        prefs = c.getSharedPreferences("Speicher", MODE_PRIVATE);
        prefsedit = prefs.edit();
        if (prefs.getBoolean(KEY_TON, true)) {
            prefsedit.putBoolean(KEY_TON, false);
            prefsedit.commit();
        } else {
            prefsedit.putBoolean(KEY_TON, true);
            prefsedit.commit();
        }
        return prefs.getBoolean(KEY_TON, true);
    }

    //ersezten Schlucke
    public static String ersetztenSchluck(Context c, String aufgabe, int max_shots){
        int rdm = new Random().nextInt(max_shots)+1;
        String schlueck;
        if (rdm==1){
            schlueck=c.getResources().getString(R.string.einzelsschluck);
        }
        else {
            schlueck=rdm+" "+c.getResources().getString(R.string.mehrzahlschluck);
        }
        aufgabe=aufgabe.replace("$",schlueck);
        return aufgabe;
    }
}
