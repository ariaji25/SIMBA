package com.example.simba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToLogin();
    }

    void goToLogin(){
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
    void login(){
        Intent login = new Intent(this, Login.class);
        startActivity(login);

    }
}
