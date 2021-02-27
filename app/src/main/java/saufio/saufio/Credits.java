package saufio.saufio;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Credits extends AppCompatActivity {

    ImageButton btn_insta, btn_email, btn_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        btn_email=(ImageButton)findViewById(R.id.imgbtn_email);
        btn_insta=(ImageButton)findViewById(R.id.imgBtn_instagram);
        btn_share=(ImageButton)findViewById(R.id.imgBtn_share);
        setzeTon();
    }

    //Button Instagra
    public void btnimg_instagram(View view) {
        Uri uri = Uri.parse("https://www.instagram.com/saufio_newdrinkinggame/");
        Intent insta = new Intent(Intent.ACTION_VIEW, uri);
        insta.setPackage("com.instagram.android");
        try {
            startActivity(insta);
        } catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.instagram.com/saufio_newdrinkinggame/")));
        }
    }

    //Button Email-Report Btn
    public void btnimg_email(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:schulz.schulzwitzel.sw.code@gmail.com?subject=" + Uri.encode(getResources().getString(R.string.Bug_good)) + "&body=" + Uri.encode(""));
        intent.setData(data);
        startActivity(intent);
    }

    //BTN share
    public void btnimg_share(View view) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.share_subject));
            String shareMessage= "\n"+getResources().getString(R.string.share_Text)+"\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_auswaehlen)));
        } catch(Exception e)
        {

        }
    }

    public void setzeTon(){
        boolean ton = Allgemein.gebeTon(this);
        btn_insta.setSoundEffectsEnabled(ton);
        btn_email.setSoundEffectsEnabled(ton);
        //btn_share.setSoundEffectsEnabled(ton);
    }
}