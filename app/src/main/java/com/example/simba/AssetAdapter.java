package com.example.simba;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<AssetIn> assetdata;

    public AssetAdapter(Context c){
        this.context =c;
    }

    public ArrayList<AssetIn> getAssetdata() {
        return assetdata;
    }

    public void setAssetdata(ArrayList<AssetIn> asset){
        this.assetdata = asset;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View rowItems = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new CategoryViewHolder(rowItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        categoryViewHolder.name.setText(getAssetdata().get(i).getNama());
        categoryViewHolder.tgl.setText("Tgl Masuk \t: "+getAssetdata().get(i).getTanggal());
        categoryViewHolder.jmlh.setText("Jumlah \t: "+getAssetdata().get(i).getJumlah());
        categoryViewHolder.nilai.setText("Nilai \t: "+getAssetdata().get(i).getHarga());

    }

    @Override
    public int getItemCount() {
        return getAssetdata().size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView tgl;
        TextView jmlh;
        TextView nilai;
        ImageView imgPhoto;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_item_name);
            tgl = itemView.findViewById(R.id.tanggal);
            jmlh = itemView.findViewById(R.id.jumlah);
            nilai = itemView.findViewById(R.id.nilai);
//            imgPhoto = itemView.findViewById(R.id.img_item_photo);
        }
    }
}
