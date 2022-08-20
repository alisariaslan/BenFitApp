package com.pakachu.benfit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Topten extends Fragment {

    private ArrayList<ArrayList> arrayListArrayList;
    private TextView tv_topten, tv_baslik;
    public int size = 10;
    public String secim;
    private ProgressBar progressBar;
    private String listString = "";
    private JSONWorkbench jsonWorkbench;
    private RekorJSONWorkbench rekorJSONWorkbench;

    private Boolean countDownBool = false;

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownBool)
            countDownTimer.cancel();
        if(countDownTimerTOTAL)
            countDownTOTAL.cancel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topten, container, false);
        progressBar = view.findViewById(R.id.progressBar4);
        tv_topten = view.findViewById(R.id.tv_topten);
        tv_baslik = view.findViewById(R.id.tv_baslik);
        jsonWorkbench = new JSONWorkbench(getActivity());
        rekorJSONWorkbench = new RekorJSONWorkbench(getActivity());
        progressBar.setVisibility(View.VISIBLE);
        jsonWorkbench.finishStatus = false;
        switch (secim) {
            case "kol":
                arrayListArrayList = jsonWorkbench.GET("SELECT *, MAX(bicep) FROM uyeler GROUP BY ad ORDER BY MAX(bicep) DESC LIMIT " + size + "");
                break;
            case "omuz":
                arrayListArrayList = jsonWorkbench.GET("SELECT *, MAX(omuz) FROM uyeler GROUP BY ad ORDER BY MAX(omuz) DESC LIMIT " + size + "");
                break;
            case "bench":
                arrayListArrayList = rekorJSONWorkbench.GETALL("SELECT * FROM rekorlar where hareket='bench press' ORDER BY (agirlik*tekrar) DESC LIMIT  " + size + "");
                break;
            case "squat":
                arrayListArrayList = rekorJSONWorkbench.GETALL("SELECT * FROM rekorlar where hareket='squat' ORDER BY (agirlik*tekrar) DESC LIMIT  " + size + "");
                break;
            case "deadlift":
                arrayListArrayList = rekorJSONWorkbench.GETALL("SELECT * FROM rekorlar where hareket='deadlift' ORDER BY (agirlik*tekrar) DESC LIMIT  " + size + "");
                break;
            case "total":
                jsonWorkbench.finishStatus = true;
                break;
        }
        countDownTimer.start();
        countDownBool = true;
        return view;
    }

    CountDownTimer countDownTimer = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long l) {

            Log.e("timertop", "timertop: " + l);
            if (jsonWorkbench.finishStatus || rekorJSONWorkbench.finishStatus) {
                switch (secim) {
                    case "kol":
                        TopTenKol();
                        break;
                    case "omuz":
                        TopTenOmuz();
                        break;
                    case "bench":
                        Bench();
                        break;
                    case "squat":
                        Squat();
                        break;
                    case "deadlift":
                        Deadlift();
                        break;
                    case "total":
                        Total();
                        break;
                }
                countDownBool = false;
                cancel();

            }
        }

        @Override
        public void onFinish() {
            countDownBool = false;
            Toast.makeText(getActivity(), "Zaman aşımı!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
    };

    private void BSD() {
        tv_baslik.setVisibility(View.VISIBLE);
        int a = 0;
        for (ArrayList arrayList : arrayListArrayList
        ) {
            String ad = arrayList.get(1).toString();
            Integer tekrar = Integer.valueOf(arrayList.get(3).toString());
            Integer agirlik = Integer.valueOf(arrayList.get(4).toString());
            Integer total = tekrar * agirlik;

            String kupa = "";
            if (a == 0)
                kupa = "🥇";
            else if (a == 1)
                kupa = "🥈";
            else if (a == 2)
                kupa = "🥉";
            if (a < 3)
                listString += kupa + "\n" + ad + ":\nToplam: " + total + " kg\n" + "Ağırlık: " + agirlik + " kg\nTekrar: " + tekrar + " rm\n\n\n";
            else
                listString += (a + 1) + ".\n" + ad + ":\nToplam: " + total + " kg\n" + "Ağırlık: " + agirlik + " kg\nTekrar: " + tekrar + " rm\n\n\n";
            a++;
        }
        tv_topten.setText(listString);
        tv_topten.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void Bench() {
        tv_baslik.setText("Bench Press\nilk " + size);
        tv_baslik.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bench, 0, 0, 0);
        BSD();
    }

    private void Squat() {
        tv_baslik.setText("Squat\nilk " + size);
        tv_baslik.setCompoundDrawablesWithIntrinsicBounds(R.drawable.squat, 0, 0, 0);
        BSD();
    }

    private void Deadlift() {
        tv_baslik.setText("Deadlift\nilk " + size);
        tv_baslik.setCompoundDrawablesWithIntrinsicBounds(R.drawable.squat, 0, 0, 0);
        BSD();
    }

    private void TopTenKol() {
        TopTenThing("kol (bicep)", R.drawable.kol);
    }

    private void TopTenOmuz() {
        TopTenThing("omuz", R.drawable.shoulder);
    }

    private void TopTenThing(String bolge, int drawable) {
        int a = 0;
        for (ArrayList arrayList : arrayListArrayList
        ) {
            a++;
            String ad, olcu;
            ad = arrayList.get(2).toString().trim();
            olcu = arrayList.get(arrayList.size() - 1).toString().trim();
            listString += a + ". " + ad + ": " + olcu + " cm\n\n\n";
            tv_topten.setText(listString);
            Log.e("person", a + ". " + ad + ": " + olcu);
        }
        tv_baslik.setText(bolge + "\nilk " + size);
        tv_baslik.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
        tv_topten.setVisibility(View.VISIBLE);
        tv_baslik.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }


    private CountDownTimer countDownTOTAL;
    private Boolean countDownTimerTOTAL=false;
    private RekorJSONWorkbench rekorJSONWorkbench2;
    private RekorJSONWorkbench rekorJSONWorkbench3;
    private RekorJSONWorkbench rekorJSONWorkbench4;

    private void Total() {
        rekorJSONWorkbench2 = new RekorJSONWorkbench(getActivity());
        rekorJSONWorkbench3 = new RekorJSONWorkbench(getActivity());
        rekorJSONWorkbench4 = new RekorJSONWorkbench(getActivity());
        ArrayList<ArrayList> benchArrays = rekorJSONWorkbench2.GETALL("SELECT * FROM rekorlar where hareket='bench press' ORDER BY agirlik DESC LIMIT  " + size + "");
        ArrayList<ArrayList> squatArrays = rekorJSONWorkbench3.GETALL("SELECT * FROM rekorlar where hareket='squat' ORDER BY agirlik DESC LIMIT  " + size + "");
        ArrayList<ArrayList> deadliftArrays = rekorJSONWorkbench4.GETALL("SELECT * FROM rekorlar where hareket='deadlift' ORDER BY agirlik DESC LIMIT  " + size + "");
        countDownTimerTOTAL =true;
        countDownTOTAL = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                Log.e("timertop", "ct2: " + l);
                Log.e("size", "\nbench: " + benchArrays.size() + "\nsquat: " + squatArrays.size() + "\ndeadlift: " + deadliftArrays.size());
                if (benchArrays.size() == size && squatArrays.size() == size && deadliftArrays.size() == size) {
                    CalcTotal(benchArrays, squatArrays, deadliftArrays);
                    countDownTimerTOTAL =false;
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                countDownTimerTOTAL =false;
                Toast.makeText(getActivity(), "Sıralama yapılabilecek kadar rekor sayısı olmadığı için iptal edildi.", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        }.start();

    }

    private void CalcTotal(ArrayList<ArrayList> benchArrays, ArrayList<ArrayList> squatArrays, ArrayList<ArrayList> deadliftArrays) {
        for (int i = 0; i < size; i++) {
            ArrayList bench = benchArrays.get(i);
            ArrayList squat = squatArrays.get(i);
            ArrayList deadlift = deadliftArrays.get(i);
            String ad = bench.get(1).toString();
            Integer b = Integer.valueOf(bench.get(4).toString());
            Integer s = Integer.valueOf(squat.get(4).toString());
            Integer d = Integer.valueOf(deadlift.get(4).toString());
            Integer total = (b + s + d);
            String kupa = "";
            if (i == 0)
                kupa = "🥇";
            else if (i == 1)
                kupa = "🥈";
            else if (i == 2)
                kupa = "🥉";
            if (i < 3)
                listString += kupa + "\n" + ad + ":\nTOTAL: " + total + " kg\n" + "Bench Press: " + b + " kg\nSquat: " + s + " kg\nDeadlift: " + d + " kg\n\n\n";
            else
                listString += (i + 1) + ".\n" + ad + ":\nTOTAL: " + total + " kg\n" + "Bench Press: " + b + " kg\nSquat: " + s + " kg\nDeadlift: " + d + " kg\n\n\n";

        }
        tv_topten.setText(listString);
        tv_baslik.setText("Powerlift Total\nilk " + size + "\n(tek tekrar)");
        tv_baslik.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bench, 0, 0, 0);
        tv_topten.setVisibility(View.VISIBLE);
        tv_baslik.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

}