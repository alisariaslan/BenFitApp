package com.pakachu.benfit.rv;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pakachu.benfit.R;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class DetayAdapter extends RecyclerView.Adapter<DetayAdapter.ViewHolder> {

    ArrayList<DetayItem> detayAdapterArrayList;

    private Activity activity;

    public DetayAdapter(Activity activity, ArrayList<DetayItem> detayAdapterArrayList) {

        this.activity = activity;
        this.detayAdapterArrayList = detayAdapterArrayList;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detay, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        DetayItem detayItem = detayAdapterArrayList.get(position);

        holder.tv_detay_hareket.setText(detayItem.hareketAdi);
        holder.tv_detay_hakkinda.setText(detayItem.hakkinda);

        if(detayItem.kaslar!=0)
        holder.imageView.setImageResource(detayItem.kaslar);
        if(detayItem.gif!=0)
        holder.gifImageView.setImageResource(detayItem.gif);

        holder.btn_detay_nasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!detayItem.videourl.equals("NO URL")) {
                    Intent openURL = new Intent(android.content.Intent.ACTION_VIEW);
                    openURL.setData(Uri.parse(detayItem.videourl));
                    activity.startActivity(new Intent(openURL));
                } else {
                    Toast.makeText(activity, "Video mevcut değil!", Toast.LENGTH_SHORT).show();
                    holder.btn_detay_nasil.setEnabled(false);
                }

            }
        });
    }

    @Override
    public int getItemCount() {

        return detayAdapterArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

                Button btn_detay_nasil;
        TextView tv_detay_hareket, tv_detay_hakkinda;
        GifImageView gifImageView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btn_detay_nasil=itemView.findViewById(R.id.btn_detay_nasil);
            tv_detay_hareket = itemView.findViewById(R.id.tv_detay_hareket);
            tv_detay_hakkinda = itemView.findViewById(R.id.tv_detay_aciklama);
            gifImageView = itemView.findViewById(R.id.gifImageView);
            imageView = itemView.findViewById(R.id.iv_detay_kaslar);

        }
    }


}