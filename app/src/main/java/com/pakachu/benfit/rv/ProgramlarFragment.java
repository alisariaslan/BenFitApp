package com.pakachu.benfit.rv;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pakachu.benfit.DBHandler_Programlar;
import com.pakachu.benfit.MainActivity;
import com.pakachu.benfit.ProgramOlustur;
import com.pakachu.benfit.R;

import java.util.ArrayList;


public class ProgramlarFragment extends Fragment {

    private ProgramAdapter myAdapter;
    private ArrayList<ProgramItem> programItemArrayList;
    private RecyclerView RVparent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_programlarim, container, false);

        RVparent = view.findViewById(R.id.rv_parent);

        Button btn_programlarim_olustur = view.findViewById(R.id.btn_programlarim_olustur);
        btn_programlarim_olustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgramOlustur blank = new ProgramOlustur();
                MainActivity.replaceFragment(blank, "program");
            }
        });

        Button btn_programlarim_qraktar = view.findViewById(R.id.btn_programlarim_qraktar);
        btn_programlarim_qraktar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Bu özellik şuanda kapalıdır.", Toast.LENGTH_SHORT).show();
            }
        });
        Button btn_programlarim_qroku = view.findViewById(R.id.btn_programlarim_qroku);
        btn_programlarim_qroku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Bu özellik şuanda kapalıdır.", Toast.LENGTH_SHORT).show();
            }
        });
        programItemArrayList = new ArrayList<>();

        DBHandler_Programlar dbHandler_programlar = new DBHandler_Programlar(getActivity());
        DefaultProgram defaultProgram = new DefaultProgram();

        ArrayList<String> tables = dbHandler_programlar.getTables();
        defaultProgram.STANDARTSPLIT(getActivity(), tables.size());
        defaultProgram.STANDARTFULLBODY(getActivity(), tables.size());
        tables = dbHandler_programlar.getTables();
        for (String tableName : tables
        ) {
            int resimID = 0;
            switch (tableName)
            {
                case "standartsplit": resimID = R.drawable.standartsplit; break;
                case "standartfullbody": resimID = R.drawable.standartfullbody;break;
            }

            ProgramItem programItem = new ProgramItem(tableName, resimID);
            programItemArrayList.add(programItem);
        }

        myAdapter = new ProgramAdapter(getActivity(), programItemArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        RVparent.setLayoutManager(layoutManager);
        RVparent.setAdapter(myAdapter);

        return view;
    }
}