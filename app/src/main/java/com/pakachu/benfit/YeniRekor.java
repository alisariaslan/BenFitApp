package com.pakachu.benfit;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;


public class YeniRekor extends Fragment {

    private  RekorJSONWorkbench jsonWorkbench;
    private ScrollView scrollView;
    private EditText et_tarih, et_ad, et_hareket, et_tekrar, et_agirlik;
    private Button btn_ekle;
    private RadioButton rb_s, rb_b, rb_d;
    private String hareket = "Bench Press";
    private ProgressBar progressBar;
    private String info = "";
    private CountDownTimer countDownTimer;
    private Boolean timer = false;
    private String tarih,ad,tekrar,agirlik;
    private ORTAK_ARACLAR ortak;

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_yeni_rekor, container, false);
        Tanimla(view);
        ortak = new ORTAK_ARACLAR();
        et_tarih.setText(ortak.GetDate());
        et_hareket.setText(hareket);
        rb_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hareket = "Squat";
                et_hareket.setText(hareket);
            }
        });
        rb_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hareket = "Bench Press";
                et_hareket.setText(hareket);
            }
        });
        rb_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hareket = "Deadlift";
                et_hareket.setText(hareket);
            }
        });
        btn_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ekle();
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timer)
            countDownTimer.cancel();
        ortak.KlavyeKapat(getContext(),getView());
    }

    private void Eklendi() {
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        et_ad.getText().clear();
        et_agirlik.getText().clear();
        et_tekrar.getText().clear();
    }

    private void Ekle() {
        tarih=et_tarih.getText().toString();
        ad=et_ad.getText().toString();
        agirlik=et_agirlik.getText().toString();
        tekrar=et_tekrar.getText().toString();
        info="Tarih: "+tarih+"\nİsim: "+ad+"\n"+"Hareket: "+hareket+"\n"+"Tekrar: "+tekrar+"\nAğırlık: "+agirlik;
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Boolean gecis;
                        if (et_tarih.getText().toString().matches("") | et_ad.getText().toString().matches("") | et_agirlik.getText().toString().matches("") | et_hareket.getText().toString().matches("") | et_tekrar.getText().toString().matches("")) {
                            gecis = false;
                        } else
                            gecis = true;
                        if (gecis) {
                            ortak.KlavyeKapat(getContext(),getView());
                            scrollView.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            ORTAK_ARACLAR dateEdit = new ORTAK_ARACLAR();
                            tarih = dateEdit.ConvertDateToEN(tarih);
                             jsonWorkbench = new RekorJSONWorkbench(getActivity());
                            jsonWorkbench.ADDUSER(tarih,ad,hareket,agirlik,tekrar);
                            countDownTimer = new CountDownTimer(10000, 1000) {
                                @Override
                                public void onTick(long l) {
                                    timer = true;
                                    if (jsonWorkbench.finishStatus) {
                                        Eklendi();
                                        timer=false;
                                        cancel();

                                    }
                                }
                                @Override
                                public void onFinish() {
                                    timer = false;
                                    Toast.makeText(getActivity(), "Zaman aşımı!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    scrollView.setVisibility(View.VISIBLE);
                                }
                            }.start();
                        } else
                            Toast.makeText(getActivity(), "Lütfen boş alanları doldurun.", Toast.LENGTH_SHORT).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        progressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(info).setPositiveButton("Kaydet", dialogClickListener)
                .setNegativeButton("İptal", dialogClickListener).setTitle("Tekrar kontrol edelim").show();
    }


    private void Tanimla(View view) {
        et_tarih = view.findViewById(R.id.et_tarih2);
        et_ad = view.findViewById(R.id.et_isim2);
        et_hareket = view.findViewById(R.id.et_hareket);
        et_tekrar = view.findViewById(R.id.et_tekrar);
        et_agirlik = view.findViewById(R.id.et_agirlik);
        rb_s = view.findViewById(R.id.rb_squat);
        rb_b = view.findViewById(R.id.rb_bench);
        rb_d = view.findViewById(R.id.rb_deadlift);
        btn_ekle = view.findViewById(R.id.btn_ekle2);
        progressBar = view.findViewById(R.id.progressBar3);
        scrollView=view.findViewById(R.id.sv_giris2);
    }
}