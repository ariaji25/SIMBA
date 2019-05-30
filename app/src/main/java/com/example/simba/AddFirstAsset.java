package com.example.simba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddFirstAsset extends AppCompatActivity {
    private Button addAsset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_first_asset);

        addAsset = findViewById(R.id.tambah);
        addAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    void goHome(){
        Intent home = new Intent(this, Home.class);
        startActivity(home);

    }

}
