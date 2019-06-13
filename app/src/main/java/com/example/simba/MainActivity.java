package com.example.simba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    Thread t;
    private FirebaseAuth userAuth;
    private FirebaseUser currentUser;
    private  User user1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userAuth = FirebaseAuth.getInstance();
        currentUser = userAuth.getCurrentUser();
        Log.w("user", "getUser");
        goToLogin();

    }

    void goToLogin(){
        if(currentUser==null){
            t = new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(3000);
                        login();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }
        else{
            User.nama = currentUser.getDisplayName();
            User.email = currentUser.getEmail();
            Dataasset.dataasset=null;
            user1 = new User();
            user1.getKey();
            Thread t1 = new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        while (Dataasset.dataasset==null||Dataasset.dataasset.size()==0){
                            Log.w("asset", "checkasset");
                            sleep(1000);
                        }
                        if(Dataasset.dataasset.size()!=0){
                            Log.w("asset", "notEmpty");
                            goHome();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t1.start();

        }
    }
//    @Nullable
    void login(){
        Intent login = new Intent(this, Login.class);
        startActivity(login);
        this.finish();
    }

    void goHome(){
        Intent home = new Intent(this, Home.class);
        startActivity(home);
        this.finish();
    }
}
