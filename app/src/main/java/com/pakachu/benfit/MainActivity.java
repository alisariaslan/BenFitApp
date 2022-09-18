package com.pakachu.benfit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.pakachu.benfit.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding binding;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        AdRequest adRequest = new AdRequest.Builder().build();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });

        InterstitialAd.load(this, getResources().getString(R.string.interstatialAds), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                mInterstitialAd = null;
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });


         adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        binding.cvAdd.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadein));
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                binding.cvAdd.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadeout));
                binding.clSearch.setVisibility(View.VISIBLE);
                binding.clSearch.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.to_down));
                disabler.start();
                setAdapter(false, "");
                new CountDownTimer(10000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        if(mInterstitialAd!=null)
                        mInterstitialAd.show(getParent());
                    }
                }.start();
            }
        });
        binding.ivWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.pakachu.apaydinfitness")));
            }
        });
        binding.editTextTextPersonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setAdapter(false, "SELECT * FROM " + DisplayDB.TABLE + " WHERE " + DisplayDB.TABLE_COL2 + " LIKE '" + s + "%'");
            }
        });

        reset();
    }

    ArrayList<DisplayItem> displayItems = new ArrayList<>();
    boolean first_run = true;

    public void reset() {
        binding.ivAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.rvMain.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadeout));
                binding.clSearch.startAnimation(AnimationUtils.loadAnimation(getBaseContext(), R.anim.fadeout));
                new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        binding.clSearch.clearAnimation();
                        binding.clSearch.setVisibility(View.GONE);
                        binding.editTextTextPersonName.setEnabled(false);
                        setAdapter(true, "");
                    }
                }.start();
            }
        });
    }

    public void setAdapter(boolean addingnew, String sql) {
        binding.rvMain.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadeinfast));
        displayItems.clear();
        if (!addingnew) {
            DisplayDB displayDB = new DisplayDB(getBaseContext());
            Cursor data;
            if (sql.equals(""))
                data = displayDB.getsql("SELECT * FROM " + DisplayDB.TABLE + " ORDER BY " + DisplayDB.TABLE_COL1 + " DESC LIMIT 10");
            else
                data = displayDB.getsql(sql);
            if (data.getCount() != 0) {
                while (data.moveToNext()) {
                    DisplayItem displayItem = new DisplayItem(data.getInt(0),1, data.getString(1), data.getInt(2) == 1,
                            data.getInt(3), data.getFloat(4), data.getInt(5), data.getFloat(6),
                            data.getFloat(7), data.getFloat(8), data.getFloat(9), data.getFloat(10),
                            data.getInt(11), data.getFloat(12), data.getString(13));
                    displayItems.add(displayItem);
                }
                first_run=false;
            }
            if (!first_run)
                binding.textView4.setText(data.getCount() + " " + getString(R.string.count));
            else
                first_run = false;

        } else {
            DisplayItem displayItem = new DisplayItem(0);
            displayItems.add(displayItem);
        }
        DisplayAdapter adapterMember = new DisplayAdapter(this, this, displayItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.rvMain.setLayoutManager(linearLayoutManager);
        binding.rvMain.setAdapter(adapterMember);
    }

    CountDownTimer disabler = new CountDownTimer(1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            binding.ivWallpaper.setVisibility(View.GONE);
        }
    };

}