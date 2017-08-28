package com.quiz.series.tvseriesquiz.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

/**
 * Created by Emilio on 04/02/2017.
 */

public abstract class InterstitialActivity extends AppCompatActivity {

    private final static String[] ids = {"ca-app-pub-3356387548141817/5723612283"};
    private final static Random rand = new Random(System.nanoTime());
    private static int position;
    protected InterstitialAd mInterstitialAd;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mInterstitialAd = new InterstitialAd(this);

        position = rand.nextInt(2);
        position %= ids.length;

        setListener();
        load();
    }

    private static void nextPosition(){
        position++;
        position %= ids.length;
    }


    public void load(){
        if(mInterstitialAd != null){
            mInterstitialAd.setAdUnitId(ids[position]);
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();

            mInterstitialAd.loadAd(adRequest);
            nextPosition();
        }
    }

    public void show(){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            onBackPressed();
        }
    }

    public abstract void mapUI();
    public abstract void init();
    protected abstract void setListener();
}
