package com.example.simba;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class AssetList extends Fragment {
    private RecyclerView asset;
    private ArrayList<AssetIn> list = new ArrayList<>();
    private FloatingActionButton fab;

    @SuppressLint("ValidFragment")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
//        list.addAll(Dataasset.dataasset);
        View v = inflater.inflate(R.layout.assetlist_flayout, container, false);
        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCreateAsset();
            }
        });
        asset = v.findViewById(R.id.assetlist);
        asset.setHasFixedSize(true);
        showRecyclerList();
        return v;
    }

    private void goCreateAsset(){
        Intent create = new Intent(getContext(),AddFirstAsset.class);
        startActivity(create);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.addAll(Dataasset.dataasset);
    }


    private void showRecyclerList(){
        asset.setLayoutManager(new LinearLayoutManager(getActivity()));
        AssetAdapter assetAdapter = new AssetAdapter(getActivity());
        assetAdapter.setAssetdata(list);
        asset.setAdapter(assetAdapter);

//        ItemClickSupport.addTo(dosaCat).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                showDosaNana(list.get(position));
//
//            }
//        });
    }


}
