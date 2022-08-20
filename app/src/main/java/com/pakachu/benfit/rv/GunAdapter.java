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

public class GunAdapter extends RecyclerView.Adapter<GunAdapter.ViewHolder> {

    Button btngun;


    public int yapilanHareket = 0;



    private Activity activity;
    private ArrayList<GunItem> gunItemArrayList;
    private String tabloadi;


    public GunAdapter(Activity activity, ArrayList<GunItem> gunItemArrayList, String tabloadi) {
        this.activity = activity;
        this.gunItemArrayList = gunItemArrayList;
        this.tabloadi = tabloadi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gun, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //holder.tvName.setText(arrayList.get(position));


        GunItem gunItem = gunItemArrayList.get(position);

        holder.btn_gun.setText(gunItem.gunAdi);

//        Drawable img = ResourcesCompat.getDrawable(activity.getResources(), gunItem.imageId, null);
//        img.setBounds(0, 0, 200, 200);
//        holder.btn_gun.setCompoundDrawables(img, null, null, null);


        holder.btn_gun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.rv_hareketler.getVisibility() == View.VISIBLE) {
                    holder.btn_gun.setText(gunItem.gunAdi);
                    holder.rv_hareketler.setVisibility(View.GONE);

                }

                else {
                    holder.rv_hareketler.setVisibility(View.VISIBLE);
                    holder.btn_gun.setText(holder.btn_gun.getText()+" (0/"+holder.toplamhareket+")");
                }

            }
        });

        DBHandler_Programlar dbHandler_programlar = new DBHandler_Programlar(activity);
        Cursor hareketlerTable = dbHandler_programlar.getColumnData(tabloadi, gunItem.gunAdi);

        ArrayList<HareketItem> hareketItemArrayList = new ArrayList<>();

        while (hareketlerTable.moveToNext()) {
            String hareket = hareketlerTable.getString(0);
            if (hareket != null) {
                holder.toplamhareket++;
                String adi = "";
                String detay = "";
                if (hareket.contains("("))
                    adi = hareket.substring(0, hareket.indexOf('(')).trim();
                else adi = hareket.trim();
                if (hareket.contains("(")) {
                    detay = hareket.substring(hareket.indexOf('('), hareket.indexOf(')') + 1).trim();
                }

                HareketItem hareketItem = new HareketItem(adi + "\n" + detay);
                hareketItem.hareketAdi = adi;
                hareketItem.hareketDetayi = detay;

                switch (adi) {
                    case "ısınma":
                        hareketItem.imageID=R.drawable.isinmaicon;
                        break;
                    case "kardiyo":
                        hareketItem.imageID=R.drawable.kardiyoicon;
                        break;
                    case "off":
                        hareketItem.imageID=R.drawable.offdayicon;
                        break;

                    case "bench press":
                        hareketItem.imageID = R.drawable.benchpress;
                        break;
                    case "incline dumbell press":
                        hareketItem.imageID = R.drawable.incldumbellpress;
                        break;
                    case "chest press":
                        hareketItem.imageID = R.drawable.chestpressicon;
                        break;
                    case "cable crossover":
                        hareketItem.imageID = R.drawable.cablecrossovericon;
                        break;
                    case "machine fly":
                        hareketItem.imageID = R.drawable.machineflyicon;
                        break;
                    case "dumbell fly":
                        hareketItem.imageID = R.drawable.dumbellflyicon;
                        break;
                    case "bicep curl":
                        hareketItem.imageID = R.drawable.bicepcurlicon;
                        break;
                    case "hammer curl":
                        hareketItem.imageID = R.drawable.hammercurlicon;
                        break;
                    case "barfiks":
                        hareketItem.imageID = R.drawable.barfiksicon;
                        break;
                    case "machine row":
                        hareketItem.imageID = R.drawable.machinerowicon;
                        break;
                    case "dumbell row":
                        hareketItem.imageID = R.drawable.dumbellrowicon;
                        break;
                    case "lat pull":
                        hareketItem.imageID = R.drawable.latpullicon;
                        break;
                    case "seated cable row":
                        hareketItem.imageID = R.drawable.seatedcablerowicon;
                        break;
                    case "hyperextension":
                        hareketItem.imageID = R.drawable.hyperextensionicon;
                        break;
                    case "dumbell skullcrusher":
                        hareketItem.imageID = R.drawable.dumbellskullcrushericon;
                        break;
                    case "close grip bench press":
                        hareketItem.imageID = R.drawable.closegripbenchicon;
                        break;
                    case "squat":
                        hareketItem.imageID = R.drawable.squatdo;
                        break;
                    case "lunge":
                        hareketItem.imageID = R.drawable.lungeicon;
                        break;
                    case "leg press":
                        hareketItem.imageID = R.drawable.legpressicon;
                        break;
                    case "leg extension":
                        hareketItem.imageID = R.drawable.legextensionicon;
                        break;
                    case "leg curl":
                        hareketItem.imageID = R.drawable.legcurlicon;
                        break;
                    case "calf raise":
                        hareketItem.imageID = R.drawable.calfraiseicon;
                        break;
                    case "deadlift":
                        hareketItem.imageID = R.drawable.deadliftdo;
                        break;
                    case "dips":
                        hareketItem.imageID = R.drawable.dipsicon;
                        break;
                    case "dumbell shoulder press":
                        hareketItem.imageID = R.drawable.dumbellshoulderpressicon;
                        break;
                    case "lateral raise":
                        hareketItem.imageID = R.drawable.lateralraiseicon;
                        break;
                    case "front raise":
                        hareketItem.imageID = R.drawable.frontraiseicon;
                        break;
                    case "rear delt fly":
                        hareketItem.imageID = R.drawable.reardeltflyicon;
                        break;
                    case "facepull":
                        hareketItem.imageID = R.drawable.ropeicon;
                        break;
                    case "preacher curl":
                        hareketItem.imageID = R.drawable.preachercurlicon;
                        break;
                    case "cable curl":
                        hareketItem.imageID = R.drawable.cablecurlicon;
                        break;
                    case "rope pushdown":
                        hareketItem.imageID = R.drawable.ropeicon;
                        break;
                    case "cable pushdown":
                        hareketItem.imageID = R.drawable.cablepushdownicon;
                        break;

                }

                hareketItemArrayList.add(hareketItem);

            }
        }



        HareketAdapter adapterMember = new HareketAdapter(activity, hareketItemArrayList, holder.btn_gun);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        holder.rv_hareketler.setLayoutManager(linearLayoutManager);
        holder.rv_hareketler.setAdapter(adapterMember);

    }

    @Override
    public int getItemCount() {

        return gunItemArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        int toplamhareket=0;

        Button btn_gun;
        RecyclerView rv_hareketler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            btn_gun = itemView.findViewById(R.id.btn_gun);
            btngun = btn_gun;
            rv_hareketler = itemView.findViewById(R.id.rv_hareketler);

        }
    }


}
