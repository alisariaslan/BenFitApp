package com.pakachu.benfit;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.pakachu.benfit.rv.ProgramlarFragment;

public class Anasayfa extends Fragment {

    private View view;
    private Button btn_giris, btn_olcu, btn_yag;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_anasayfa, container, false);

//                Toast.makeText(getActivity(), ""+MainActivity.fragmentManager.getBackStackEntryCount(), Toast.LENGTH_SHORT).show();

        if(savedInstanceState!=null)
        {
            int a=savedInstanceState.getInt("counter");

        }

        MainActivity.setPadding(0);
        init();

        Button user = view.findViewById(R.id.anasayfa_user);
        if (MainActivity.status == "user") {
            user.setEnabled(false);

        } else {
            user.setText(MainActivity.status);
            user.setEnabled(true);
        }

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                MainActivity.status = "user";
                                user.setText(MainActivity.status);
                                user.setEnabled(false);
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Çıkış yapmak istediğinizden emin misiniz?").setPositiveButton("Evet", dialogClickListener)
                        .setNegativeButton("İptal", dialogClickListener).setTitle("Hesaptan çıkış yapılıyor").show();

            }
        });

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);

        btn_giris.setOnClickListener(view -> {
            btn_giris.startAnimation(animation);
            new CountDownTimer(600, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    btn_giris.clearAnimation();

                    if (!MainActivity.status.matches("user")) {
                        Antrenor_menu antrenorMenu = new Antrenor_menu();
                        MainActivity.replaceFragment(antrenorMenu, "aMenu", true, false);
                    } else {
                        Antrenor_login antrenorLogin = new Antrenor_login();
                        MainActivity.replaceFragment(antrenorLogin, "aLogin", true, false);
                    }

                }
            }.start();

        });

        Button btn_arena = view.findViewById(R.id.btn_arena);
        btn_arena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_arena.startAnimation(animation);
                new CountDownTimer(600, 1000) {
                    @Override
                    public void onTick(long l) {
                    }
                    @Override
                    public void onFinish() {
                        btn_arena.clearAnimation();
                        Arena arena = new Arena();
                        MainActivity.replaceFragment(arena, "test", true, false);

                    }
                }.start();
            }
        });

        Button btn_diger = view.findViewById(R.id.btn_diger);
        btn_diger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Pakachu"));
                startActivity(new Intent(openURL));
            }
        });

        Button btn_insta = view.findViewById(R.id.btn_insta);
        btn_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                openURL.setData(Uri.parse("https://www.instagram.com/pakachu.s/?hl=tr"));
                startActivity(new Intent(openURL));
            }
        });
        Button btn_diyetlerim = view.findViewById(R.id.btn_diyetlerim);
        btn_diyetlerim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Bu özellik henüz yapım aşamasındadır.", Toast.LENGTH_SHORT).show();
            }
        });
        Button btn_takviyeler = view.findViewById(R.id.btn_takviyeler);
        btn_takviyeler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Bu özellik henüz yapım aşamasındadır.", Toast.LENGTH_SHORT).show();
            }
        });
        Button btn_hareketlerim = view.findViewById(R.id.btn_hareketlerim);
        btn_hareketlerim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_hareketlerim.startAnimation(animation);
                new CountDownTimer(600, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        btn_hareketlerim.clearAnimation();
//                    tableLayout.startAnimation(animation1);

                        ProgramlarFragment blankFragment = new ProgramlarFragment();
                        MainActivity.replaceFragment(blankFragment, "hareketlerim", true, false);


                    }
                }.start();            }
        });

        btn_yag.setOnClickListener(view -> {
            btn_yag.startAnimation(animation);
            new CountDownTimer(600, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    btn_yag.clearAnimation();
//                    tableLayout.startAnimation(animation1);

                        YagOrani blankFragment = new YagOrani();
                        MainActivity.replaceFragment(blankFragment, "yag", true, false);


                }
            }.start();
        });


        btn_olcu.setOnClickListener(view -> {
            btn_olcu.startAnimation(animation);
            new CountDownTimer(600, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    btn_olcu.clearAnimation();
//                    tableLayout.startAnimation(animation1);
                    Olculerim blankFragment = new Olculerim();
                    MainActivity.replaceFragment(blankFragment, "anasayfa", true, false);

                }
            }.start();


        });

        return view;
    }

    private void init() {
        btn_giris = view.findViewById(R.id.btn_giris);
        btn_olcu = view.findViewById(R.id.btn_olcu);
        btn_yag = view.findViewById(R.id.btn_yag);
    }

}