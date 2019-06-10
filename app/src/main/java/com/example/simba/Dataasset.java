package com.example.simba;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Dataasset extends Databaseconnect {
    static ArrayList<AssetIn> dataasset;
    static void   getDataAsset(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        AssetIn assetIn=null;
//        final ArrayList<AssetIn> dataasset = new ArrayList<>();
        Log.w("select asset", User.keyDoc);
//        dataasset.clear();
        Dataasset.dataasset = new ArrayList<>();
        db.collection("user").document(User.keyDoc)
                .collection("assetIn")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Log.w("asset", ""+document.get("nama"));
                                AssetIn assetIn = new AssetIn();
                                assetIn.setNama(document.get("nama").toString());
                                assetIn.setJumlah(document.get("jumlah").toString());
                                assetIn.setHarga(document.get("nilai").toString());
                                assetIn.setTanggal(document.get("tanngal").toString());
                                Dataasset.dataasset.add(assetIn);
                            }
                        }
                        else {
                            Log.w("asset", "filure");
                        }
                    }
                });

    }

}
