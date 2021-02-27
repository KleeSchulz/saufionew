package saufio.saufio;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class Werbung {

    private RewardedVideoAd mRewardedVideoAd;
    private InterstitialAd interstitialAd;
    Context c;
    public boolean angeschaut=true;
    public int minuten = 0;

    //todo: Anpassen admob
    String interstalize = "ca-app-pub-4761500786576152~8215465788";
    String unitid = "ca-app-pub-3940256099942544/1033173712";

    public Werbung (Context c,boolean angeschaut){
        this.c = c;
        this.angeschaut = angeschaut;
    }

    public void setAngeschaut(boolean hier){
        this.angeschaut=hier;
    }
    public boolean getAngeschaut(){
        return angeschaut;
    }

    public void klick_werbung() {
        MobileAds.initialize(c, interstalize);
        interstitialAd = new InterstitialAd(c);
        interstitialAd.setAdUnitId(unitid);
        AdRequest request = new AdRequest.Builder().build();
        interstitialAd.loadAd(request);
        interstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });
    }

    public RewardedVideoAd rewardwerbung(){
        MobileAds.initialize(c, interstalize);
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(c);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Log.i("HIUA", "loaded");
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Log.i("HIUA", "opem");
                minuten = minuten+1;
            }

            @Override
            public void onRewardedVideoStarted() {
                Log.i("HIUA", "started");
                minuten = minuten+1;
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Log.i("HIUA", "closed");
                angeschaut=false;
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                Log.i("HIUA", "rewartitem");
                angeschaut=true;
                minuten = minuten+5;
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Log.i("HIUA", "leftaplli");
                angeschaut=false;
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Log.i("HIUA", "failed");
                angeschaut=false;
            }
            @Override
            public void onRewardedVideoCompleted() {
                Log.i("HIUA", "colplete");
            }

        });
        mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917", new AdRequest.Builder().build());
        Log.i("HIUA", "geladen");
        mRewardedVideoAd.show();
        mRewardedVideoAd.show();
        return mRewardedVideoAd;

    }
}