package com.pakachu.benfit.rv;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.benfit.DBHandler_Programlar;
import com.pakachu.benfit.R;

import java.util.ArrayList;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {


    private Activity activity;
    ArrayList<ProgramItem> programItemArrayList;


    public ProgramAdapter(Activity activity, ArrayList<ProgramItem> programItemArrayList) {
        this.activity = activity;
        this.programItemArrayList = programItemArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_program, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //holder.tvName.setText(arrayList.get(position));


        ProgramItem programItem = programItemArrayList.get(position);

        holder.btn_program.setText(programItem.programAdi);

        if (programItem.imageId != 0) {
            Drawable img = ResourcesCompat.getDrawable(activity.getResources(), programItem.imageId, null);
            img.setBounds(0, 0, 100, 100);
            holder.btn_program.setCompoundDrawables(null, null, img, null);
        }


        holder.btn_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.rv_gunler.getVisibility() == View.VISIBLE)
                    holder.rv_gunler.setVisibility(View.GONE);
                else
                    holder.rv_gunler.setVisibility(View.VISIBLE);
            }
        });

        DBHandler_Programlar dbHandler_programlar = new DBHandler_Programlar(activity);
        Cursor table = dbHandler_programlar.getData(programItem.programAdi);

        ArrayList<GunItem> gunItemArrayList = new ArrayList<>();
        for (String columnName : table.getColumnNames()
        ) {
            GunItem parentItem = new GunItem(columnName);
            if(!columnName.equals("id"))
            gunItemArrayList.add(parentItem);
        }

        GunAdapter adapterMember = new GunAdapter(activity, gunItemArrayList, programItem.programAdi);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.rv_gunler.setLayoutManager(linearLayoutManager);
        holder.rv_gunler.setAdapter(adapterMember);

    }

    @Override
    public int getItemCount() {

        return programItemArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button btn_program;
        RecyclerView rv_gunler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            btn_program = itemView.findViewById(R.id.btn_program);
            rv_gunler = itemView.findViewById(R.id.rv_gunler);

        }
    }


}
