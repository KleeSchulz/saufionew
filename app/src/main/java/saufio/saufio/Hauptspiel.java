package saufio.saufio;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.reward.RewardedVideoAd;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Hauptspiel extends AppCompatActivity {

    //region Variablen
    //Werbung
    RewardedVideoAd ad;
    //Video
    //private AlphaMovieView alphaMovieView;
    private VideoView videoView;
    private MediaController mediaController;
    //Background
    ImageView img_bck, img_bck02;
    //Button
    Button btn_zufall;
    //ImageButton
    ImageButton btnimg_ton, btnimg_zahl, btnimg_kopf,btn_share, imgbtn_info;
    //TextView
    TextView tv_spieler1111, tv_aufgabe, tv_aufgabenzahl, tv_kopfzahl;
    //INT
    int zufallszahl, zufallspieler, max_shots, anzahl_kopf = 0, anzahl_zahl = 0, anzahl_aufgaben = 0, wahl, reihnfolge,runde,bgnzahl,bgnzufall, werbungzahlgebe, werbungwann, werbungmax=1;
    //Strings
    String spieler, aufgabetxt;
    //Boolean
    boolean folge, background, changezahl, timeruebergabe;
    //Array
    ArrayList<String> Spieler;
    ArrayList<String> aufgabelist;
    //Aufgabe
    Aufgabe a;
    //Zufallgenerator
    Random r = new Random();
    //TextToSpeech
    TextToSpeech textspeech;
    //endregion
    Werbung w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hauptspiel);
        tv_spieler1111 = (TextView) findViewById(R.id.tv_spieleranzeige);
        tv_kopfzahl = (TextView) findViewById(R.id.tv_kopfzahl);
        tv_aufgabe = (TextView) findViewById(R.id.tv_aufgabe);
        tv_aufgabenzahl = (TextView) findViewById(R.id.tv_aufgabenvorhanden);
        btnimg_ton = (ImageButton) findViewById(R.id.btn_ton);
        btnimg_zahl = (ImageButton) findViewById(R.id.btn_zahl);
        btnimg_kopf = (ImageButton) findViewById(R.id.btn_kopf);
        videoView = (VideoView) findViewById(R.id.videoBB);
      //  alphaMovieView = (AlphaMovieView) findViewById(R.id.video_player);
        btnimg_kopf = (ImageButton) findViewById(R.id.btn_kopf);
        btnimg_zahl = (ImageButton) findViewById(R.id.btn_zahl);
        imgbtn_info = (ImageButton) findViewById(R.id.img_info);
        btn_zufall = (Button) findViewById(R.id.btn_zufall);
        btn_share = (ImageButton)findViewById(R.id.imgBtn_share);
        img_bck=(ImageView)findViewById(R.id.img_background);
        img_bck02=(ImageView)findViewById(R.id.img_background02);
        Spieler = (ArrayList<String>) getIntent().getSerializableExtra("key");
        max_shots = getIntent().getIntExtra("key2", 1);
        background=true;
        bgnzahl=0;
        bgnzufall=r.nextInt(6)+3;
        //Aufgabe vorlesen
        textspeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textspeech.setLanguage(Locale.GERMANY);
                }
            }
        });
        aufgabelist = (ArrayList<String>) db.aufgabeKategorie(getIntent().getStringExtra("key3"));
        folge=getIntent().getBooleanExtra("key4",false);
        Spielerauswaehlen();
        tv_aufgabenzahl.setText(getResources().getString(R.string.aufgabevorhanden) + aufgabelist.size());
        reihnfolge=r.nextInt(Spieler.size());
        setzeTonbild(Allgemein.gebeTon(this));
        einblenden_k_o_z();
        //starteWerbetimer();
        videoView = findViewById(R.id.videoBB);
        videoView.setVisibility(View.INVISIBLE);
        mediaController = new MediaController(this);
        a = new Aufgabe(1,"0","J","0");
        buttonton();
        werbungwann=r.nextInt(werbungmax)+1;
        w = new Werbung(this,false);
        w.setAngeschaut(getIntent().getBooleanExtra("key5",false));
        ad = w.rewardwerbung();
        starteWerbetimer();
    }
    DatabaseHandler db = new DatabaseHandler(this);

    //Zurücktaste
    @Override
    public void onBackPressed() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        statiskUndEnde();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.alter_spielende_title));
        builder.setMessage(getResources().getString(R.string.alter_spielende_title)).setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no), dialogClickListener).show();
    }

    @Override
    public void onResume() {
        ad.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        ad.pause(this);
        super.onPause();
    }


    //region Buttons
    //Buttons Kopf u. Zahl
    public void btnimg_zahl(View view) { kopf_o_zahl(0); }
    public void btnimg_kopf(View view) {
        kopf_o_zahl(1);
    }

    //Zufallspieler
    public void btn_zufall(View view) {
        Spielerauswaehlen();
        einblenden_k_o_z();
        werbungYN_banner();
        //tODO: Farbwert + 1;
    }

    //Spieler löschen
    public void btn_spielerloeschen(View view) {
        Spieler.add(getResources().getString(R.string.spieleradd));
        CharSequence spieler[] = Spieler.toArray(new CharSequence[Spieler.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.alter_spielerfrage));
        builder.setItems(spieler, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Spieler.size() - 1 == which) {
                    Spieler.remove(Spieler.size() - 1);
                    Spieleradd();
                } else if (Spieler.size() > 3) {
                    Toast.makeText(getApplicationContext(), Spieler.get(which) + " " + getResources().getString(R.string.t_loschen), Toast.LENGTH_SHORT).show();
                    Spieler.remove(which);
                    Spieler.remove(Spieler.size() - 1);
                } else {
                    Spieler.remove(Spieler.size() - 1);
                    wenigSpieler();
                }
                tv_aufgabe.setText(String.valueOf(which));
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Spieler.remove(Spieler.size() - 1);
            }
        });
        builder.show();
    }

    //Button Share
    public void btnimg_share(View view) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_subject));
            String shareMessage = "\n" + getResources().getString(R.string.share_Text) + "\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_auswaehlen)));
        } catch (Exception e) {
        }
    }

    //Ton aktivieren
    public void btn_tonsetzen(View view) {
        setzeTonbild(Allgemein.tonaendern(this));
        buttonton();
    }

    public void btn_info(View view) {
        String txt = a.getKategorie();
        if (txt.equalsIgnoreCase(getResources().getString(R.string.kategroie1))) {
            Allgemein.alertOK(this, getResources().getString(R.string.alter_kategorie_title1), getResources().getString(R.string.alter_kategorie_message1), getResources().getString(R.string.verstanden));
        }
    }

    public void btn_werbung(View view) {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
        alterDialog.setTitle("Werbefrei spielen");
        alterDialog.setMessage("Möchtest dudie nächsten 145 Minuten ohne Werbung spielen");
        alterDialog.setPositiveButton("JA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Werbung abspielen
                starteWerbetimer();
                //w.rewardwerbung();

            }
        });
        alterDialog.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alterDialog.create();
        alterDialog.show();
    }
    //endregion

    //Eigene Methoden
    //region Nicht zuordbar
    //Suche einen Zufälligen  Spieler
    public void Spielerauswaehlen() {
        if (aufgabelist.size() == 0) {
            AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
            alterDialog.setTitle(getResources().getString(R.string.keineaufgabe_title));
            alterDialog.setMessage(getResources().getString(R.string.keineaufgabe_message));
            alterDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(com.example.saufio.Hauptspiel.this, MainActivity.class));
                }
            });
            alterDialog.create();
            alterDialog.show();
        }
        //a.setKategorie("0");
        if (folge){
            zufallspieler = r.nextInt(Spieler.size());
            spieler = Spieler.get(zufallspieler);
        } else {
            spieler=Spieler.get(reihnfolge);
            if (reihnfolge==Spieler.size()-1){
                reihnfolge=0;
                runde=runde+1;
            } else {
                reihnfolge=reihnfolge+1;
            }
        }
        tv_spieler1111.setText(spieler);
        tv_kopfzahl.setText(getResources().getString(R.string.entscheidung));
    }
    //ermittle welches Ergebnis
    public void kopf_o_zahl(int wert) {
        gebezufall();
        zufallszahl = r.nextInt(2);
        wahl=wert;
        if (zufallszahl == 0) {
            if (zufallszahl!=wahl){
                aufgabetxt = Aufgabe.getAufgabe(this, aufgabelist);
                aufgabetxt=Allgemein.ersetztenSchluck(this,aufgabetxt,max_shots);
            }
            tv_spieler1111.setText(spieler);
            tv_kopfzahl.setText(getResources().getString(R.string.wartenvid));
            playVideo(zufallszahl);
            Timer t = new Timer(false);
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (zufallszahl == wahl) {
                                tv_spieler1111.setText(spieler);
                                tv_kopfzahl.setText(getResources().getString(R.string.glueck_zahl));
                                anzahl_zahl = anzahl_zahl + 1;
                                btn_zufall.setVisibility(View.VISIBLE);
                                imgbtn_info.setVisibility(View.INVISIBLE);
                            } else {
                                tv_spieler1111.setText(spieler);
                                tv_kopfzahl.setText(getResources().getString(R.string.pech_kopf));
                                tv_aufgabenzahl.setText(getResources().getString(R.string.aufgabevorhanden) + aufgabelist.size());
                                tv_aufgabe.setText(aufgabetxt);
                                sprachausgabe(aufgabetxt);
                                anzahl_kopf = anzahl_kopf + 1;
                                anzahl_aufgaben = anzahl_aufgaben + 1;
                                btn_zufall.setVisibility(View.VISIBLE);
                                imgbtn_info.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }, 2300);     //DELAY
        } else {
            tv_kopfzahl.setText(getResources().getString(R.string.wartenvid));
            playVideo(zufallszahl);
            if (zufallszahl!=wahl){
                aufgabetxt = Aufgabe.getAufgabe(this, aufgabelist);
                aufgabetxt=Allgemein.ersetztenSchluck(this,aufgabetxt,max_shots);
            }
            Timer t = new Timer(false);
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (zufallszahl == wahl) {
                                tv_spieler1111.setText(spieler);
                                tv_kopfzahl.setText(getResources().getString(R.string.glueck_zahl));
                                anzahl_kopf = anzahl_kopf + 1;
                                btn_zufall.setVisibility(View.VISIBLE);
                                imgbtn_info.setVisibility(View.INVISIBLE);
                            } else {
                                tv_spieler1111.setText(spieler);
                                tv_kopfzahl.setText(getResources().getString(R.string.pech_kopf));
                                tv_aufgabenzahl.setText(getResources().getString(R.string.aufgabevorhanden) + aufgabelist.size());
                                tv_aufgabe.setText(aufgabetxt);
                                sprachausgabe(aufgabetxt);
                                anzahl_zahl = anzahl_zahl + 1;
                                anzahl_aufgaben = anzahl_aufgaben + 1;
                                btn_zufall.setVisibility(View.VISIBLE);
                                imgbtn_info.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            }, 2300);  //DELAY
        }
        ausblenden_k_o_z();
    }
    //Alert wenn zu Wenig Spieler bei löschen
    public void wenigSpieler() {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
        alterDialog.setTitle(getResources().getString(R.string.alter_spielerloeschen_title));
        alterDialog.setMessage(getResources().getString(R.string.alter_spieleerloeschen_message));
        alterDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onBackPressed();
            }
        });
        alterDialog.create();
        alterDialog.show();
    }
    //Endstatistik
    public void statiskUndEnde() {
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
        alterDialog.setTitle(getResources().getString(R.string.alter_statistik_title));
        String x= getResources().getString(R.string.alter_statistik_message_gesamt) + String.valueOf(anzahl_kopf + anzahl_zahl) + "\n" +
                getResources().getString(R.string.alter_statistik_message_kopf) + String.valueOf(anzahl_kopf) + "\n" +
                getResources().getString(R.string.alter_statistik_message_zahl) + String.valueOf(anzahl_zahl) + "\n" +
                getResources().getString(R.string.alter_statistik_message_runden) + String.valueOf(anzahl_aufgaben);
        if (!folge){
            x = x + "\n"+getResources().getString(R.string.alter_statistik_message_durchlauf)+String.valueOf(runde);
        }
        alterDialog.setMessage(x);
        alterDialog.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(com.example.saufio.Hauptspiel.this, MainActivity.class);
                startActivity(intent);
            }
        });
        alterDialog.create();
        alterDialog.show();
    }
    //Spieler nachträglich hinzufügen
    public void Spieleradd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.alter_spielerhinzufügen_title));
        builder.setMessage(getResources().getString(R.string.alter_spielerhinzufügen_message));
        final EditText input = new EditText(this);
        input.setSoundEffectsEnabled(Allgemein.gebeTon(this));
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_DATETIME_VARIATION_NORMAL);
        builder.setView(input);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Spieler.add(input.getText().toString());
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.abbrechen), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    //endregion

    //region einblenen/ausblenden
    //Ausblenden von Kopf und Zahl Buttons -> Zufallspieler wird angezeigt
    public void ausblenden_k_o_z() {
        btnimg_zahl.setVisibility(View.INVISIBLE);
        btnimg_kopf.setVisibility(View.INVISIBLE);

    }

    //Einblenden von Kopf und Zahl -> Zufallspieler wird nicht angezeigt
    public void einblenden_k_o_z() {
        btnimg_zahl.setVisibility(View.VISIBLE);
        btnimg_kopf.setVisibility(View.VISIBLE);
        imgbtn_info.setVisibility(View.INVISIBLE);
        btn_zufall.setVisibility(View.INVISIBLE);
        videoView.setVisibility(View.GONE);
        tv_aufgabe.setText("");
    }

    //endregion

    //region Video
    //Videoabspielen
    public void playVideo(int zufall) {

        Uri uri = zufallvideo(zufall);
        videoView.setVideoURI(uri);
        videoView.setVisibility(View.VISIBLE);
        videoView.start();
        /*alphaMovieView.setVideoFromAssets("video.mp4");
        alphaMovieView.setVisibility(View.VISIBLE);
        alphaMovieView.start();
        */
    }

    public Uri zufallvideo(int koz){
        Uri uri;
        boolean zufall = r.nextBoolean();
        //Zahl
        if (koz==0){
            if (zufall){
                uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.zahl_auf_zahl);
            } else {
                uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bier_auf_zahl);
            }
        } else {
            //Kopf
            if (koz==1){
                uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bier_auf_bier);
            } else {
                uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.zahl_auf_bier);
            }
        }
        return uri;
    }
    //endregion

    //region Ton-Ausgabe/Buttons
    // Text to Speech, abbrechen bzw. Ausgabe
    public void sprachausgabe(String aufgabe) {
        if (Allgemein.gebeTon(this)) {
            if (textspeech != null) {
                textspeech.stop();
            }
            textspeech.speak(aufgabe, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    //Abbrechen text2speech
    @Override
    public void onDestroy() {
//        ad.resume(this);
        super.onDestroy();
        /*if (textspeech != null) {
            textspeech.stop();
            textspeech.shutdown();
        }*/
    }

    //setzeTonBild
    public void setzeTonbild(boolean b) {
        if (b) {
            btnimg_ton.setImageResource(R.drawable.pixel_lautsprecher_an);
        } else {
            btnimg_ton.setImageResource(R.drawable.pixel_lautsprecher_aus);
        }
    }



    //Buttonston
    public void buttonton(){
        Boolean ton = Allgemein.gebeTon(this);
        btnimg_ton.setSoundEffectsEnabled(ton);
        btnimg_zahl.setSoundEffectsEnabled(ton);
        btnimg_kopf.setSoundEffectsEnabled(ton);
        btn_zufall.setSoundEffectsEnabled(ton);
        btn_share.setSoundEffectsEnabled(ton);
    }
    //endregion

    //region Hintergrund
    //Hitergrundwechsel Zufall ermitteln
    public void gebezufall(){
        if (bgnzufall==bgnzahl){
            changebground();
            bgnzufall=r.nextInt(6)+3;
            bgnzahl=0;
        } else {
            bgnzahl=bgnzahl+1;
        }
    }

    //Hintergrund wechsel
    public void changebground(){
        if (background){
            img_bck02.setAlpha(0f);
            img_bck02.setVisibility(View.VISIBLE);
            img_bck02.animate()
                    .alpha(0.7f)
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
                    .alpha(0.7f)
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
    //endregion

    //region Werbung
    //Werbung zufall
    public void werbungYN_banner()
    {
        if (!w.angeschaut) {
            if (werbungzahlgebe == werbungwann) {
                w.klick_werbung();
                werbungmax = r.nextInt(werbungmax) + 1;
                werbungzahlgebe = 0;
            } else {
                werbungzahlgebe++;
                Log.i("HUAN", String.valueOf(werbungmax) + " " + String.valueOf(werbungzahlgebe) + " " + werbungwann);
            }
        }
    }

    public void starteWerbetimer(){
        if (w.getAngeschaut()){
            Log.i("NUTTE!", String.valueOf(w.getAngeschaut()));
            w.setAngeschaut(true);
            //starte timer
            Timer t = new Timer(false);
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            w.setAngeschaut(false);
                            Log.i("HAUN", String.valueOf(w.angeschaut));
                        }
                    });
                }
            }, 12300);
        }
    }
    //endregion
}
