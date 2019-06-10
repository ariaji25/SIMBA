package com.example.simba;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AssetIn extends Databaseconnect{
//    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String nama ="";
    String jumlah="";
    String harga="";
    String tanggal="";
    AssetIn(){
        super();
    }
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void createMap(){
        Map<String, Object> asset = new HashMap<>();
        asset.put("nama", ""+this.getNama()+"");
        asset.put("jumlah", ""+this.getJumlah()+"");
        asset.put("nilai", ""+this.getHarga()+"");
        asset.put("tanngal", ""+this.getTanggal()+"");
        addAsset(asset);
    }

    public void addAsset(Map a){
        Log.w("addAsser", User.keyDoc);
        db.collection("user").document(User.keyDoc).collection("assetIn")
                .add(a)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Log.w("addAss", "Succes");
                        Dataasset.getDataAsset();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addAss", "filed");
                    }
                });
    }

    public void selectAsset(){
        Log.w("select asset", User.keyDoc);
        db.collection("user").document(User.keyDoc)
                .collection("assetIn")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.w("asset", ""+document.get("nama"));
                            }
                        }
                        else {
                            Log.w("asset", "filure");
                        }
                    }
                });
    }
}