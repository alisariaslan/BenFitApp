package com.pakachu.benfit;

import android.content.DialogInterface;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Antrenor_menu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_antrenor_menu, container, false);

//        Toast.makeText(getActivity(), ""+MainActivity.fragmentManager.getBackStackEntryCount(), Toast.LENGTH_SHORT).show();


        Button geri = view.findViewById(R.id.menu_home);
        Button ekle = view.findViewById(R.id.btn_uye_ekle);
        Button yenirekor = view.findViewById(R.id.btn_yenirekor);
        Button btn_yeniprogram = view.findViewById(R.id.btn_yeniprogram);
        Button btn_toLocal = view.findViewById(R.id.btn_toLocal);

        btn_toLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                JSONWorkbench jsonWorkbench = new JSONWorkbench(getActivity());
                                ArrayList<ArrayList> arrayListArrayList = jsonWorkbench.GET("SELECT * FROM uyeler");

                                CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        Toast.makeText(getActivity(), ""+millisUntilFinished, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFinish() {
                                        for (ArrayList arrayList : arrayListArrayList) {
                                            for (int i = 0; i < arrayList.size(); i++) {
                                                Toast.makeText(getActivity(), (i+1)+". Veri: "+arrayList.get(i).toString(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                        Toast.makeText(getActivity(), "Veriler başarıyla aktarıldı.", Toast.LENGTH_SHORT).show();

                                    }
                                }.start();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Bu işlem verileri internet üzerinden telefonunuza aktaracaktır. Bundan emin misiniz?").setPositiveButton("Evet", dialogClickListener)
                        .setNegativeButton("İptal", dialogClickListener).setTitle("Uyarı").show();


            }
        });

        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.fragmentManager.popBackStack();
                MainActivity.fragmentManager.popBackStack();
            }
        });

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
                ekle.startAnimation(animation);
                new CountDownTimer(600, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        ekle.clearAnimation();
                        Antrenor_uyeEkle antrenorGirisi = new Antrenor_uyeEkle();
                        MainActivity.replaceFragment(antrenorGirisi, "menu", true, false);
                    }
                }.start();
            }
        });


        yenirekor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
                yenirekor.startAnimation(animation);
                new CountDownTimer(600, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        yenirekor.clearAnimation();
                        YeniRekor antrenorGirisi = new YeniRekor();
                        MainActivity.replaceFragment(antrenorGirisi, "menu", true, false);
                    }
                }.start();
            }
        });


        btn_yeniprogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
                btn_yeniprogram.startAnimation(animation);
                new CountDownTimer(600, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        btn_yeniprogram.clearAnimation();
                        ProgramOlustur blankFragment = new ProgramOlustur();
                        MainActivity.replaceFragment(blankFragment, "programOlustur", true, false);
                    }
                }.start();


            }
        });

        return view;
    }
}