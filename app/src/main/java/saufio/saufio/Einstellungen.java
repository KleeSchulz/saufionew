package saufio.saufio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Einstellungen extends AppCompatActivity {

    ImageButton ton;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(com.example.saufio.Einstellungen.this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);
        ton =(ImageButton)findViewById(R.id.btn_ton);
        setzeTonbild();

    }

    //setze TonBild
    public void setzeTonbild() {
        if (Allgemein.gebeTon(this)) {
            ton.setImageResource(R.drawable.pixel_lautsprecher_an);
        } else {
            ton.setImageResource(R.drawable.pixel_lautsprecher_aus);
        }
    }

    public void btn_einstellung_ton(View view) {
        Allgemein.tonaendern(this);
        setzeTonbild();
    }
}