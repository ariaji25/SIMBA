package com.example.simba;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class User extends Databaseconnect{
    static String email;
    static String nama;
    static String keyDoc;
    boolean haveAdded;
    static  Context c;
    void addUser(){
        Map<String, Object> user = new HashMap<>();
        user.put("nama", User.nama);
        user.put("email", User.email);
        db.collection("user")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.w("addUser", "succes");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("addUser", "failure");
                    }
                });
    }

    void checkIsAdded(Context c){
        User.c = c;
        Log.w("user",User.email);
        db.collection("user")
                .whereEqualTo("email", User.email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String key;
                            int size =0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("checkUser", document.getId() + " => " + document.getData());
                                User.keyDoc = document.getId();
                                size++;
                            }
                            Log.w("checkUser", ""+size);
                            if(size==0){
                                addUser();
                                getKey();
                                Thread t1 = new Thread(){
                                    @Override
                                    public void run() {
                                        super.run();
                                        try {
                                            while (User.keyDoc==null){
                                                Log.w("user", "checkuser");
                                                sleep(1000);
                                            }
                                            if(User.keyDoc!=null){
                                                Log.w("user", "notEmpty");
                                                goCreate(User.c);

                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                t1.start();

                            }
                            else{
                                Log.w("checkUser", "have added");
                                getKey();
//                                goToHome(User.c);
                                Thread t1 = new Thread(){
                                    @Override
                                    public void run() {
                                        super.run();
                                        try {
                                            while (Dataasset.dataasset==null){
                                                Log.w("user", "checkuser");
                                                sleep(1000);
                                            }
                                            if(Dataasset.dataasset!=null){
                                                Log.w("user", "notEmpty");
                                                goToHome(User.c);
                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                t1.start();
                            }
                        } else {
                            Log.w("checkUser", "Error getting documents.", task.getException());
                            addUser();
                        }
                    }
                });
//        return haveAdded;
    }

    void goToHome(Context c){
        Intent home = new Intent(c,Home.class);
        c.startActivity(home);

    }
    void goCreate(Context c){
        Intent home = new Intent(c,AddFirstAsset.class);
        c.startActivity(home);
    }

    void getKey(){
        db.collection("user")
                .whereEqualTo("email", User.email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("checkUser", document.getId() + " => " + document.getData());
                                User.keyDoc = document.getId();
                                Dataasset.getDataAsset();
                            }
                        } else {
                            Log.w("checkUser", "Error getting documents.", task.getException());
                            addUser();
                        }
                    }
                });
    }

}
