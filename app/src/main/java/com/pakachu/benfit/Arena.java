package com.pakachu.benfit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class Arena extends Fragment {
    boolean giris=true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        giris=true;
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_arena, container, false);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.zoomin);

        Button btn_degisim = view.findViewById(R.id.btn_degisim);
        btn_degisim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Bu özellik yapım aşamasındadır...", Toast.LENGTH_SHORT).show();
            }
        });
        Button btn_kol = view.findViewById(R.id.btn_kol);
        btn_kol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(giris) {
                    btn_kol.startAnimation(animation);
                    new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            btn_kol.clearAnimation();
                            Topten topten = new Topten();
                            topten.size = 10;
                            topten.secim = "kol";
                            MainActivity.replaceFragment(topten, "kol", true, false);
                        }
                    }.start();
                    giris=false;
                }
            }
        });
        Button btn_omuz = view.findViewById(R.id.btn_omuz);

        btn_omuz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(giris) {
                    btn_omuz.startAnimation(animation);
                    new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            btn_omuz.clearAnimation();
                            Topten topten = new Topten();
                            topten.size = 10;
                            topten.secim = "omuz";
                            MainActivity.replaceFragment(topten, "omuz", true, false);
                        }
                    }.start();
                    giris=false;
                }

            }
        });
        Button btn_enbuyuk = view.findViewById(R.id.btn_enbuyuk);
        btn_enbuyuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Bu özellik yapım aşamasındadır...", Toast.LENGTH_SHORT).show();
            }
        });
        Button btn_enfit = view.findViewById(R.id.btn_enfit);
        btn_enfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Bu özellik yapım aşamasındadır...", Toast.LENGTH_SHORT).show();
            }
        });
        Button btn_total = view.findViewById(R.id.btn_total);
        btn_total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(giris) {
                    btn_total.startAnimation(animation);
                    new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            btn_total.clearAnimation();
                            Topten topten = new Topten();
                            topten.size = 10;
                            topten.secim = "total";
                            MainActivity.replaceFragment(topten, "total", true, false);
                        }
                    }.start();
                    giris=false;
                }                    }
        });
        Button btn_bench = view.findViewById(R.id.btn_bench);
        btn_bench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(giris) {
                    btn_bench.startAnimation(animation);
                    new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            btn_bench.clearAnimation();
                            Topten topten = new Topten();
                            topten.size = 10;
                            topten.secim = "bench";
                            MainActivity.replaceFragment(topten, "bench", true, false);
                        }
                    }.start();
                    giris=false;
                }            }
        });
        Button btn_squat = view.findViewById(R.id.btn_squat);
        btn_squat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(giris) {
                    btn_squat.startAnimation(animation);
                    new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            btn_squat.clearAnimation();
                            Topten topten = new Topten();
                            topten.size = 10;
                            topten.secim = "squat";
                            MainActivity.replaceFragment(topten, "squat", true, false);
                        }
                    }.start();
                    giris=false;
                }                }
        });
        Button btn_deadlift = view.findViewById(R.id.btn_deadlift);
        btn_deadlift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(giris) {
                    btn_deadlift.startAnimation(animation);
                    new CountDownTimer(2000, 2000) {
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            btn_deadlift.clearAnimation();
                            Topten topten = new Topten();
                            topten.size = 10;
                            topten.secim = "deadlift";
                            MainActivity.replaceFragment(topten, "deadlift", true, false);
                        }
                    }.start();
                    giris=false;
                }                   }
        });
        return view;
    }
}