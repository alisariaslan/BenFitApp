package com.pakachu.benfit;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Antrenor_login extends Fragment {

    Button dogrula, geri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_antrenor_login, container, false);

        MainActivity.setPadding(0);

        dogrula = view.findViewById(R.id.btn_dogrula);
        geri = view.findViewById(R.id.btn_login_back);

        EditText et_id = view.findViewById(R.id.et_id);
        EditText et_sifre = view.findViewById(R.id.et_sifre);
        EditText et_ip = view.findViewById(R.id.et_ip);

        geri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
                MainActivity.fragmentManager.popBackStack();
            }
        });

        dogrula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);


                String id = et_id.getText().toString();
                String pass = et_sifre.getText().toString();


                if (!(id.matches(""))) {
                    if (!(pass.matches(""))) {
                        if (id.equals("ali") && pass.equals("ali2017")) {
                            login("ali");
                        } else if (id.equals("misafir") && pass.equals("misafir"))
                            login("misafir");
                        else {
                            logout();
                        }
                    }
                }
            }
        });

        return view;
    }

    private void login(String id) {
        Antrenor_menu antrenorGirisi = new Antrenor_menu();
        MainActivity.replaceFragment(antrenorGirisi, "login", true, false);

        MainActivity.status = id;

    }

    private void logout() {
        MainActivity.fragmentManager.popBackStack();
        Toast.makeText(getActivity(), "Hatalı giriş!", Toast.LENGTH_SHORT).show();
    }


}