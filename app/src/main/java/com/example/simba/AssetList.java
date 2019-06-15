package com.example.simba;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageButton;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class AssetList extends Fragment {
    private RecyclerView asset;
    private ArrayList<AssetIn> list = new ArrayList<>();
    private FloatingActionButton fab;
    private Handler updateuihandler;

    @SuppressLint("ValidFragment")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        list.addAll(Dataasset.dataasset);
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
        AddFirstAsset.mode="1";
        Intent create = new Intent(getContext(),AddFirstAsset.class);
        startActivity(create);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        showRecyclerList();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showRecyclerList(){
//        Log.w("asset", list.get(0).jumlah);
        asset.setLayoutManager(new LinearLayoutManager(getActivity()));
        AssetAdapter assetAdapter = new AssetAdapter(getActivity());
        assetAdapter.setAssetdata(list);
        asset.setAdapter(assetAdapter);
        ItemClickSupport.addTo(asset).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Silahkan pilih aksi untuk asset anda")
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK button
                                String key = list.get(position).getKey();
                                deleteAsset(key);
                            }
                        })
                        .setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String nama = list.get(position).getNama();
                                String jml = list.get(position).getJumlah();
                                String nil = list.get(position).getHarga();
                                String tgl = list.get(position).getTanggal();
                                String key = list.get(position).getKey();
                                goEdit(key, nama, jml, nil, tgl);
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    private  void deleteAsset(String key){
        AssetIn asset = new AssetIn();
        asset.deleteAsset(key);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "",
                "Sedang menghapus. Mohon tunggu sebentar...", true);
        Dataasset.dataasset = null;
        cretaeUpdateUiHandler();
        Thread t1 = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    while (Dataasset.dataasset==null||Dataasset.dataasset.size()==0){
                        Log.w("assetonCreate", "checkasset");
                        sleep(1000);
                    }
                    if(Dataasset.dataasset.size()!=0){
                        Log.w("assetonCreate", "notEmpty");
                        dialog.cancel();
                        Message message = new Message();
                        message.obj="delete";
                        updateuihandler.sendMessage(message);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
    }


    private void goEdit(String key, String nama, String jml, String nilai, String tgl){
        AddFirstAsset.mode="2";
        Intent edit = new Intent(getActivity(), AddFirstAsset.class);
        edit.putExtra("nama", nama);
        edit.putExtra("jumlah", jml);
        edit.putExtra("key", key);
        edit.putExtra("nilai", nilai);
        edit.putExtra("tanggal", tgl);
        startActivity(edit);
    }

    public  void cretaeUpdateUiHandler(){
        if(updateuihandler==null){
            updateuihandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    reset(Dataasset.dataasset);
                }
            };
        }
    }

    public void reset(ArrayList<AssetIn> list1){
        this.list = list1;
        showRecyclerList();
    }

}
