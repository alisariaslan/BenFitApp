package com.pakachu.benfit;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Olculerim extends Fragment {

    private TextView tv_tarih, tv_ad, tv_yas, tv_cinsiyet, tv_kilo, tv_boy, tv_boyun, tv_onkol, tv_kol, tv_bicep,
            tv_omuz, tv_gogus, tv_karin, tv_kalca, tv_bacak, tv_calf, tv_antrenor, tv_yagorani, tv_vucutKitleEndeksi;
    private Button bul, geri, duzenle, sil;
    private ScrollView sv_info;
    private Spinner spin_tarih;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private EditText editText;
    private JSONWorkbench jsonWorkbench;
    public ArrayList<ArrayList> arrayListArrayList;
    private boolean editMod = false;
    private String aktifAd, aktifTarih, aktifIndex;
    private String adsoyad = "", yeniveri;
    private Switch sw_karsilastir;

    private CountDownTimer countDownFind;
    private CountDownTimer countDownUpdate;
    private CountDownTimer countDownDelete;

    private Boolean timerFind = false;
    private Boolean timerDelete = false;
    private Boolean timerUpdate = false;

    private ORTAK_ARACLAR ortak;
    private String kars = "";

    public void stopCountDownTimersAndCloseKeyboard() {
        ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
        if (timerFind)
            countDownFind.cancel();
        if (timerDelete)
            countDownDelete.cancel();
        if (timerUpdate)
            countDownUpdate.cancel();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopCountDownTimersAndCloseKeyboard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_olculerim, container, false);
        ortak = new ORTAK_ARACLAR();
        init(view1);
        sw_karsilastir = view1.findViewById(R.id.sw_karsilastir);
        duzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sw_karsilastir.setVisibility(View.GONE);
                sw_karsilastir.setChecked(false);
                editMod = true;
                sil.setVisibility(View.GONE);
                duzenle.setVisibility(View.GONE);
                editText.setText("");
                editText.setHint("Lütfen alan seçin");
                editText.setEnabled(false);
                bul.setText("Düzenle");
                bul.setEnabled(false);
            }
        });

        sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(aktifAd.matches("")) && !(aktifTarih.matches(""))) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    sw_karsilastir.setVisibility(View.GONE);
                                    sw_karsilastir.setChecked(false);
                                    sil.setVisibility(View.GONE);
                                    progressBar.setVisibility(View.VISIBLE);
                                    linearLayout.setVisibility(View.GONE);
                                    sv_info.clearAnimation();
                                    sv_info.setVisibility(View.GONE);
                                    editText.setEnabled(false);
                                    bul.setEnabled(false);
                                    duzenle.setVisibility(View.GONE);
                                    jsonWorkbench = new JSONWorkbench(getActivity());
                                    jsonWorkbench.DELETEUSER(aktifIndex);
                                    countDownDelete = new CountDownTimer(10000, 1000) {
                                        @Override
                                        public void onTick(long l) {
                                            timerDelete = true;
                                            if (jsonWorkbench.finishStatus) {
                                                ItsDeleted();
                                                timerDelete = false;
                                                cancel();

                                            }
                                        }

                                        @Override
                                        public void onFinish() {
                                            timerDelete = false;
                                            Toast.makeText(getActivity(), "Zaman aşımı!", Toast.LENGTH_SHORT).show();
                                            sil.setVisibility(View.VISIBLE);
                                            progressBar.setVisibility(View.GONE);
                                            linearLayout.setVisibility(View.VISIBLE);
                                            sv_info.setVisibility(View.VISIBLE);
                                            editText.setEnabled(true);
                                            bul.setEnabled(true);
                                        }
                                    }.start();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(" ").setPositiveButton("Sil", dialogClickListener)
                            .setNegativeButton("İptal", dialogClickListener).setTitle("Emin misiniz?").show();

                }
            }
        });

        MainActivity.setPadding(50);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink_anim);
        bul.startAnimation(animation);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().matches("")) {
                    bul.startAnimation(animation);
                    bul.setEnabled(false);
                } else {
                    bul.clearAnimation();
                    bul.setEnabled(true);
                }
            }
        });


        spin_tarih.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    sw_karsilastir.setVisibility(View.GONE);
                    sw_karsilastir.setChecked(false);
                    sv_info.clearAnimation();
                    sv_info.setVisibility(View.GONE);
                    sil.setVisibility(View.GONE);
                    duzenle.setVisibility(View.GONE);
                    aktifTarih = null;
                    aktifAd = null;
                } else {
                    sw_karsilastir.setVisibility(View.VISIBLE);
                    ArrayList<String> arrayList = arrayListArrayList.get(i - 1);
                    aktifIndex = arrayList.get(0).trim();
                    aktifTarih = arrayList.get(1);
                    aktifAd = arrayList.get(2);
                    float cins = 0,
                            yas = 0,
                            kilo = 0,
                            boy = 0;
                    int a = 0;
                    for (TextView tv : textViewArrayList
                    ) {
                        if (a < 17)
                            a++;
                        tv.setVisibility(View.VISIBLE);
                        if (arrayList.get(a).matches(String.valueOf(0)) | arrayList.get(a).matches("") | arrayList.get(a).matches("null")) {
                            tv.setVisibility(View.GONE);
                        } else {
                            if (tv.getTag().toString().matches("ad") | tv.getTag().toString().matches("tarih"))
                                if (!MainActivity.status.matches("user"))
                                    tv.setVisibility(View.VISIBLE);
                                else tv.setVisibility(View.GONE);
                            if (sw_karsilastir.isChecked())
                                kars = " > ";
                            else kars = "";

                            if(!tv.getTag().toString().equals("vke")&&!tv.getTag().equals("tyo"))
                            tv.setText(tv.getText().toString() + kars + arrayList.get(a));

                            switch (tv.getTag().toString()) {
                                case "tarih":
                                    tv.setText("Tarih: " + arrayList.get(a));
                                    break;
                                case "ad":
                                    tv.setText("Ad: " + arrayList.get(a));
                                    break;
                                case "yas":
                                    tv.setText("Yaş: " + arrayList.get(a));
                                    yas = Float.parseFloat(arrayList.get(a).trim());
                                    break;
                                case "boy":
                                    tv.setText("Boy: " + arrayList.get(a));
                                    boy = Float.parseFloat(arrayList.get(a).trim());
                                    break;
                                case "cinsiyet":
                                    tv.setText("Cinsiyet: " + arrayList.get(a));
                                    if (arrayList.get(a).trim().equals("KADIN"))
                                        cins = 1f;
                                    break;
                                case "antrenor":
                                    tv.setText("Antrenör: " + arrayList.get(a));
                                    break;
                            }

                            if (!sw_karsilastir.isChecked()) {
                                switch (tv.getTag().toString()) {
                                    case "kilo":
                                        tv.setText("Kilo: " + arrayList.get(a));
                                        kilo = Float.parseFloat(arrayList.get(a).trim());
                                        break;
                                    case "boyun":
                                        tv.setText("Boyun kalınlığı: " + arrayList.get(a));
                                        break;
                                    case "onkol":
                                        tv.setText("Ön kol: " + arrayList.get(a));
                                        break;
                                    case "kol":
                                        tv.setText("Kol: " + arrayList.get(a));
                                        break;
                                    case "bicep":
                                        tv.setText("Bicep: " + arrayList.get(a));
                                        break;
                                    case "omuz":
                                        tv.setText("Omuz: " + arrayList.get(a));
                                        break;
                                    case "gogus":
                                        tv.setText("Göğüs: " + arrayList.get(a));
                                        break;
                                    case "karin":
                                        tv.setText("Karın: " + arrayList.get(a));
                                        break;
                                    case "kalca":
                                        tv.setText("Kalça: " + arrayList.get(a));
                                        break;
                                    case "bacak":
                                        tv.setText("Bacak: " + arrayList.get(a));
                                        break;
                                    case "vke":
                                        tv.setText("Vücut kitle endeksi: ");
                                        break;
                                    case "tyo":
                                        tv.setText("Tahmini yağ oranı: ");
                                        break;
                                    case "calf":
                                        tv.setText("Calf: " + arrayList.get(a));
                                        break;
                                }
                            }

                        }

                    }
if(!sw_karsilastir.isChecked()) {
    float bmi = kilo / ((boy / 100) * (boy / 100));
    float yo;
    float sex;
    float women = 5.4f;
    float man = 16.2f;
    if (cins == 1)
        sex = women;
    else
        sex = man;
    yo = (1.20f * bmi) + (0.23f * yas) - sex;
    tv_vucutKitleEndeksi.setText(tv_vucutKitleEndeksi.getText().toString() + (int) bmi);
    tv_yagorani.setText(tv_yagorani.getText().toString() + (int) yo);
}


                    sv_info.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
                    sv_info.startAnimation(animation);
                    if (!MainActivity.status.matches("user")) {
                        sil.setVisibility(View.VISIBLE);
                        duzenle.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        bul.setOnClickListener(view ->
        {
            ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
            sw_karsilastir.setVisibility(View.GONE);
            sw_karsilastir.setChecked(false);
            progressBar.setVisibility(View.VISIBLE);
            bul.setEnabled(false);
            editText.setEnabled(false);
            linearLayout.setVisibility(View.GONE);
            sv_info.clearAnimation();
            sv_info.setVisibility(View.GONE);
            if (editMod) {
                for (TextView tv : textViewArrayList
                ) {
                    tv.setBackgroundColor(Color.argb(0, 0, 0, 0));
                }
                yeniveri = editText.getText().toString();
                jsonWorkbench = new JSONWorkbench(getActivity());
                jsonWorkbench.UPDATEUSER(columnName, yeniveri, aktifIndex);
                countDownUpdate = new CountDownTimer(10000, 1000) {
                    @Override
                    public void onTick(long l) {
                        timerUpdate = true;
                        if (jsonWorkbench.finishStatus) {
                            editMod = false;
                            bul.setText("Bul");
                            bul.setEnabled(true);
                            editText.setEnabled(true);
                            editText.setText(adsoyad);
                            sil.setVisibility(View.GONE);
                            duzenle.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            sv_info.clearAnimation();
                            sv_info.setVisibility(View.GONE);
                            timerUpdate = false;
                            cancel();

                        }
                    }

                    @Override
                    public void onFinish() {
                        timerUpdate = false;
                        Toast.makeText(getActivity(), "Zaman aşımı!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        bul.setEnabled(true);
                        editText.setEnabled(true);
                        linearLayout.setVisibility(View.VISIBLE);
                        sv_info.clearAnimation();
                        sv_info.setVisibility(View.VISIBLE);
                    }
                }.start();
            } else {
                if (!(editText.getText().toString().matches(""))) {
                    adsoyad = editText.getText().toString();
                    arrayListArrayList.clear();
                    jsonWorkbench = new JSONWorkbench(getActivity());
                    arrayListArrayList = jsonWorkbench.GET("SELECT * FROM uyeler WHERE ad='" + adsoyad + "'");
                    countDownFind = new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long l) {
                            timerFind = true;
                            if (jsonWorkbench.finishStatus) {
                                ItsOk();
                                timerFind = false;
                                cancel();

                            }
                        }

                        @Override
                        public void onFinish() {
                            timerFind = false;
                            Toast.makeText(getActivity(), "Zaman aşımı!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            bul.setEnabled(true);
                            editText.setEnabled(true);
                        }
                    }.start();
                }
            }
        });
        geri.setOnClickListener(view ->
        {
            stopCountDownTimersAndCloseKeyboard();
            MainActivity.fragmentManager.popBackStack();
        });
        return view1;
    }


    private void ItsDeleted() {
        editText.setEnabled(true);
        editText.setText("");
        sil.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        sv_info.clearAnimation();
        sv_info.setVisibility(View.GONE);
    }

    private void ItsOk() {
        progressBar.setVisibility(View.GONE);
        bul.setEnabled(true);
        editText.setEnabled(true);
        String tarihler[] = new String[arrayListArrayList.size() + 1];
        tarihler[0] = "Lütfen tarih seçin.";
        int x = 1;
        for (ArrayList<String> arrayList : arrayListArrayList
        ) {
            tarihler[x] = ortak.ConvertDateToTR(arrayList.get(1));
            x++;
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, tarihler);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spin_tarih.setAdapter(spinnerArrayAdapter);
        if (tarihler.length < 2) {
            Toast.makeText(getActivity(), "Üye bulunamadı!\nLütfen en sona boşluk ekleyip veya kaldırıp tekrar deyin.", Toast.LENGTH_SHORT).show();
        } else {
            linearLayout.setVisibility(View.VISIBLE);
        }
    }

    private String columnName;

    private ArrayList<TextView> textViewArrayList = new ArrayList<>();

    private void init(View view) {
        editText = view.findViewById(R.id.et_adsoyad);
        progressBar = view.findViewById(R.id.progressBar2);
        bul = view.findViewById(R.id.btn_bul);
        geri = view.findViewById(R.id.btn_olcu_backl);
        duzenle = view.findViewById(R.id.btn_olcu_duzenle);
        sil = view.findViewById(R.id.btn_olcu_sil);
        arrayListArrayList = new ArrayList<>();
        sv_info = view.findViewById(R.id.sv_info);
        spin_tarih = view.findViewById(R.id.spin_tarih);

        tv_ad = view.findViewById(R.id.tv_ad);
        tv_tarih = view.findViewById(R.id.tv_tarih);

        if (!MainActivity.status.matches("user")) {
            tv_ad.setVisibility(View.VISIBLE);
            tv_tarih.setVisibility(View.VISIBLE);
        }

        tv_yas = view.findViewById(R.id.tv_yas);
        tv_cinsiyet = view.findViewById(R.id.tv_cinsiyet);
        tv_kilo = view.findViewById(R.id.tv_kilo);
        tv_boy = view.findViewById(R.id.tv_boy);
        tv_boyun = view.findViewById(R.id.tv_boyun);
        tv_onkol = view.findViewById(R.id.tv_onkol);
        tv_kol = view.findViewById(R.id.tv_kol);
        tv_bicep = view.findViewById(R.id.tv_bicep);
        tv_omuz = view.findViewById(R.id.tv_omuz);
        tv_gogus = view.findViewById(R.id.tv_gogus);
        tv_karin = view.findViewById(R.id.tv_karin);
        tv_kalca = view.findViewById(R.id.tv_kalca);
        tv_bacak = view.findViewById(R.id.tv_bacak);
        tv_calf = view.findViewById(R.id.tv_calf);
        tv_vucutKitleEndeksi = view.findViewById(R.id.tv_vucutKitleEndeksi);
        tv_yagorani = view.findViewById(R.id.tv_yagorani);
        tv_antrenor = view.findViewById(R.id.tv_antrenor);

        textViewArrayList.add(tv_tarih);
        textViewArrayList.add(tv_ad);
        textViewArrayList.add(tv_yas);
        textViewArrayList.add(tv_cinsiyet);
        textViewArrayList.add(tv_kilo);
        textViewArrayList.add(tv_boy);
        textViewArrayList.add(tv_boyun);
        textViewArrayList.add(tv_onkol);
        textViewArrayList.add(tv_kol);
        textViewArrayList.add(tv_bicep);
        textViewArrayList.add(tv_omuz);
        textViewArrayList.add(tv_gogus);
        textViewArrayList.add(tv_karin);
        textViewArrayList.add(tv_kalca);
        textViewArrayList.add(tv_bacak);
        textViewArrayList.add(tv_calf);
        textViewArrayList.add(tv_vucutKitleEndeksi);
        textViewArrayList.add(tv_yagorani);
        textViewArrayList.add(tv_antrenor);

        for (TextView tv : textViewArrayList
        ) {
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!MainActivity.status.matches("user") & editMod) {
                        for (TextView tv : textViewArrayList
                        ) {
                            tv.setBackgroundColor(Color.argb(0, 0, 0, 0));
                        }
                        columnName = view.getTag().toString();
                        if (columnName.equals("vke") || columnName.equals("tyo")) {
                            Toast.makeText(getActivity(), "Bu alanlar otomatikdir. Değiştirilemez!", Toast.LENGTH_SHORT).show();
                        } else {
                            view.setBackgroundColor(Color.argb(50, 0, 0, 0));
                            Toast.makeText(getActivity(), "\"" + columnName + "\" seçildi.", Toast.LENGTH_SHORT).show();
                            editText.setHint("Lütfen yeni değeri girin");
                            editText.setEnabled(true);
                            bul.setEnabled(true);
                        }
                    }
                }
            });
        }
        linearLayout = view.findViewById(R.id.ll_tarih);
    }
}