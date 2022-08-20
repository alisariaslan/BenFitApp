package com.pakachu.benfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {


    public static FragmentManager fragmentManager;
    public static FragmentTransaction fragmentTransaction;
    private static ConstraintLayout constraintLayout;

    public static String status = "user";

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        AddLoader addLoader=new AddLoader(MainActivity.this);

//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        init();

        Anasayfa mainAnasayfa = new Anasayfa();
        replaceFragment(mainAnasayfa, null, false, false);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                addLoader.initAds();
                AdView mAdView = findViewById(R.id.adView);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }
        });



    }

    public static void setPadding(int a) {
        constraintLayout.setPadding(a, a, a, a);
    }


    public static void replaceFragment(Fragment fragment, String thisFragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.lefttoright, R.anim.fadeout, R.anim.lefttoright, R.anim.fadeout);
        fragmentTransaction.replace(R.id.main_constraint, fragment).addToBackStack(thisFragment).commit();
    }

    public static void replaceFragment(Fragment fragment, String thisFragment, Boolean geri, Boolean remove) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.lefttoright, R.anim.fadeout, R.anim.lefttoright, R.anim.fadeout);

        if (geri) {
            fragmentTransaction.replace(R.id.main_constraint, fragment).addToBackStack(thisFragment).commit();
        } else {
            fragmentTransaction.replace(R.id.main_constraint, fragment).disallowAddToBackStack().commit();
        }
        if (remove) {
            fragmentTransaction.remove(fragment);
        }

    }


    private void init() {
        fragmentManager = getSupportFragmentManager();
        constraintLayout = findViewById(R.id.main_constraint);
    }
}