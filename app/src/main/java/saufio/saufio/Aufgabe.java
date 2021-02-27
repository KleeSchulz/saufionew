package saufio.saufio;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;


public class Aufgabe extends AppCompatActivity {


    int id;
    String aufgabe;
    String aenderung;
    String kategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("HUANSAN","HUANSOOOOON");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_aufgaben);
    }


    public Aufgabe(){}
    public Aufgabe(int id, String aufgabe, String aenderung,String kategorie){
        this.id=id;
        this.aufgabe=aufgabe;
        this.aenderung=aenderung;
        this.kategorie=kategorie;
    }

    //Getter und Setter ID
    public int getId(){return this.id;}
    public void setId(int id){this.id=id;}

    //Getter und Setter aufgabe
    public String getAufgabe(){return this.aufgabe;}
    public void  setAufgabe(String aufgabe){this.aufgabe=aufgabe;
    }

    //Getter und Setter aenderung
    public String getAenderung(){return this.aenderung;}
    public void setAenderung(String aenderung){this.aenderung=aenderung;}

    //Getter und Setter Kategorie
    public String getKategorie(){return this.kategorie;}
    public void setKategorie(String kategorie){this.kategorie=kategorie;}

    //RDM aufgabetxt
    public static String getAufgabe(Context c,ArrayList<String> aufgabelist){
        String aufgabe;
        int rdm = new Random().nextInt(aufgabelist.size());
        aufgabe = aufgabelist.get(rdm);
        aufgabelist.remove(rdm);
        return aufgabe;
    }

    //funkt net - muss noch gemacht werden
    public static Aufgabe gebekatsuch(Context c, String aufgabe){
        DatabaseHandler d = new DatabaseHandler(c);
        Aufgabe a = d.sucheUeberTxt(aufgabe);
        return a;
    }

    //Adde  Aufgaben
    public static void getAddGameaufgabe(Context c) {
        DatabaseHandler d = new DatabaseHandler(c);
        Aufgabe a = new Aufgabe(1,"","J",c.getResources().getString(R.string.kategroie1));
        a.setAufgabe(c.getResources().getString(R.string.aufgabe1));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe2));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe3));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe4));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe5));                d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe6));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe7));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe8));                      d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe9));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe10));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe11));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe12));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe13));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe14));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe15));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe16));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe17));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe18));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe19));              d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe20));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe21));           d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe22));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe23));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe24));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe25));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe26));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe27));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe28));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe29));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe30));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe31));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe32));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe33));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe34));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe35));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe36));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe37));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe38));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe39));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe40));          d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe41));                       d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe42));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe43));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe44));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe45));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe46));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe47));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe48));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe50));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe51));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe52));              d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe53));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe54));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe55));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe56));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe57));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe58));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe59));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe60));          d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe61));            d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe62));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe63));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe64));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe65));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe66));         d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe67));          d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe68));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe69));             d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe70));         d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe71));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe72));         d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe73));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe74));            d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe75));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe76));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe77));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe78));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe79));                  d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe80));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe81));         d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe82));          d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe83));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe84));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe85));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe86));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe87));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe88));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe89));          d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe90));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe91));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe92));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe93));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe94));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe95));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe96));         d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe97));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe98));            d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe99));                 d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe100));        d.addAufgabe(a);
        a.setAufgabe(c.getResources().getString(R.string.aufgabe101));        d.addAufgabe(a);
        d.addAufgabe(a);
    }
}











