package com.pakachu.benfit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class Topbar extends Fragment {

    private Button btn_back;
    private Button btn_main;
    private Button btn_user;

    private FrameLayout frame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_topbar, container, false);

         btn_back=view.findViewById(R.id.btn_login_back);
//         btn_main=view.findViewById(R.id.btn_main);
//         btn_user=view.findViewById(R.id.btn_user);

        return view;
    }
    public void Set_frame_Visibility(Integer view) {
        frame.setVisibility(view);
    }

    public void Set_btn_back_Visibility(Integer view) {
        btn_back.setVisibility(view);
    }

    public void Set_btn_main_Visibility(Integer view) {
        btn_main.setVisibility(view);
    }

    public void Set_btn_user_Visibility(Integer view) {
        btn_user.setVisibility(view);
    }
}