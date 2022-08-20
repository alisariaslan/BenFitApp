package com.pakachu.benfit;

import android.content.DialogInterface;
import android.os.Bundle;
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

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


public class Antrenor_uyeEkle extends Fragment {
    private ScrollView sv_giris;
    private ProgressBar progressBar;
    private Button ekle;
    private EditText et_tarih, et_ad, et_yas, et_cinsiyet, et_kilo, et_boy, et_boyun, et_onkol, et_kol, et_bicep,
            et_omuz, et_gogus, et_karin, et_kalca, et_bacak, et_calf, et_antrenor;
    private String cinsiyet = "ERKEK";
    private RadioButton rb1;
    private CountDownTimer countDownTimer;
    private Boolean timer = false;
    private String info;
    private ORTAK_ARACLAR ortak;
    private JSONWorkbench jsonWorkbench;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(timer)
            countDownTimer.cancel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_antrenor_ekle, container, false);

        ortak = new ORTAK_ARACLAR();

        init(view1);
        MainActivity.setPadding(50);

        ekle.setOnClickListener(view -> {

            ortak.KlavyeKapat(getActivity(), view);

            if (MainActivity.status.matches("ali"))
                info = "Tarih: " + et_tarih.getText().toString() + "\nAd: " + et_ad.getText().toString() +
                        "\nYaş: " + et_yas.getText().toString() + "\nKilo: " + et_kilo.getText().toString() +
                        "\nBoy: " + et_boy.getText().toString() + "\nBoyun: " + et_boyun.getText().toString() +
                        "\nÖnkol: " + et_onkol.getText().toString() +
                        "\nKol: " + et_kol.getText().toString() + "\nBicep: " + et_bicep.getText().toString() +
                        "\nOmuz: " + et_omuz.getText().toString() + "\nGöğüs: " + et_gogus.getText().toString() +
                        "\nKarın: " + et_karin.getText().toString() + "\nKalça: " + et_kalca.getText().toString() +
                        "\nBacak: " + et_bacak.getText().toString() + "\nCalf: " + et_calf.getText().toString() +
                        "\nAntrenör: " + et_antrenor.getText().toString();

            Eminmisin();

        });



            et_antrenor.setText(MainActivity.status);
        et_tarih.setText(ortak.GetDate());
        return view1;
    }



    private void Eminmisin() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:

                        if (!et_tarih.getText().toString().equals("")) {
                            if (!et_ad.getText().toString().equals("")) {
                                if (rb1.isChecked())
                                    cinsiyet = "ERKEK";
                                else
                                    cinsiyet = "KADIN";

                                sv_giris.setVisibility(View.GONE);
                                progressBar.setVisibility(View.VISIBLE);

                                String newDate = ortak.ConvertDateToEN(et_tarih.getText().toString());

                                jsonWorkbench = new JSONWorkbench(getActivity());
                                jsonWorkbench.ADDUSER(newDate, et_ad.getText().toString(), et_yas.getText().toString(), cinsiyet,
                                        et_kilo.getText().toString(), et_boy.getText().toString(), et_boyun.getText().toString(),
                                        et_onkol.getText().toString(), et_kol.getText().toString(), et_bicep.getText().toString(),
                                        et_omuz.getText().toString(), et_gogus.getText().toString(), et_karin.getText().toString(),
                                        et_kalca.getText().toString(), et_bacak.getText().toString(), et_calf.getText().toString(),
                                        et_antrenor.getText().toString());

                                countDownTimer = new CountDownTimer(10000, 1000) {
                                    @Override
                                    public void onTick(long l) {
                                        timer = true;
                                        if (jsonWorkbench.finishStatus) {
                                            Ekle();
                                            timer = false;
                                            cancel();
                                        }
                                    }

                                    @Override
                                    public void onFinish() {
                                        timer = false;
                                        Toast.makeText(getActivity(), "Zaman aşımı!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        sv_giris.setVisibility(View.VISIBLE);
                                    }
                                }.start();
                            } else
                                Toast.makeText(getActivity(), "Lütfen adı doldurun", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getActivity(), "Lütfen tarihi belirtin ", Toast.LENGTH_SHORT).show();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        progressBar.setVisibility(View.GONE);
                        sv_giris.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(info).setPositiveButton("Kaydet", dialogClickListener)
                .setNegativeButton("İptal", dialogClickListener).setTitle("Tekrar kontrol edelim").show();

    }

    private void Ekle() {
        progressBar.setVisibility(View.GONE);
        sv_giris.setVisibility(View.VISIBLE);

        et_kilo.getText().clear();
        et_boy.getText().clear();
        et_boyun.getText().clear();
        et_onkol.getText().clear();
        et_kol.getText().clear();
        et_omuz.getText().clear();
        et_gogus.getText().clear();
        et_karin.getText().clear();
        et_kalca.getText().clear();
        et_bacak.getText().clear();
        et_calf.getText().clear();
    }

    private void init(View view) {
        ekle = view.findViewById(R.id.btn_ekle);

        et_tarih = view.findViewById(R.id.et_tarih);
        et_ad = view.findViewById(R.id.et_isim);
        et_yas = view.findViewById(R.id.et_yas);
        et_kilo = view.findViewById(R.id.et_kilo);
        et_boy = view.findViewById(R.id.et_boy);
        et_boyun = view.findViewById(R.id.et_boyun);
        et_onkol = view.findViewById(R.id.et_onkol);
        et_kol = view.findViewById(R.id.et_kol);
        et_bicep = view.findViewById(R.id.et_bicep);
        et_omuz = view.findViewById(R.id.et_omuz);
        et_gogus = view.findViewById(R.id.et_gogus);
        et_karin = view.findViewById(R.id.et_karin);
        et_kalca = view.findViewById(R.id.et_kalca);
        et_bacak = view.findViewById(R.id.et_bacak);
        et_calf = view.findViewById(R.id.et_calf);
        et_antrenor = view.findViewById(R.id.et_ant);

        sv_giris = view.findViewById(R.id.sv_giris);
        progressBar = view.findViewById(R.id.progressBar);
        rb1 = view.findViewById(R.id.rb_erkek);
    }

}