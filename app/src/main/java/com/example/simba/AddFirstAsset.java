package com.example.simba;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.simba.R.color.colorPrimary;

public class AddFirstAsset extends AppCompatActivity {
    private Button addAsset;
    private EditText tanggal;
    private EditText nama;
    private EditText harga;
    private EditText jumlah;
    private DatePickerDialog datePickerDialog;
    private AssetIn assets;
    private TextView message;
    static String mode="";

    private TextView ern;
    private TextView erj;
    private TextView erni;
    private TextView ertg;
    private  String assetkey;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_first_asset);
//        asset = new AssetIn();
        message = findViewById(R.id.message);
        ern = findViewById(R.id.errorname);
        ern.setVisibility(View.GONE);
        erj = findViewById(R.id.errorjml);
        erj.setVisibility(View.GONE);
        erni = findViewById(R.id.errornilai);
        erni.setVisibility(View.GONE);
        ertg = findViewById(R.id.errortgl);
        ertg.setVisibility(View.GONE);
        nama = findViewById(R.id.assetName);
        harga = findViewById(R.id.nilai);
        jumlah = findViewById(R.id.jumlahAsset);
        tanggal = findViewById(R.id.tanggal);
        addAsset = findViewById(R.id.tambah);
        if(mode.equals("1")){
            message.setText("Silahkan masukkan data asset Anda :");
        }
        else if(mode.equals("2")){
            addAsset.setText("Simpan");
            Intent asset = getIntent();
            assetkey = asset.getStringExtra("key");
            message.setText("Silahkan edit data asset Anda : \n"+asset.getStringExtra("key"));
            nama.setText(asset.getStringExtra("nama"));
            harga.setText(asset.getStringExtra("nilai"));
            jumlah.setText(asset.getStringExtra("jumlah"));
            tanggal.setText(asset.getStringExtra("tanggal"));
        }
        else{
            message.setText("Silahkan masukkan data asset pertama Anda :");
        }
        addAsset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ern.setVisibility(View.GONE);
                erni.setVisibility(View.GONE);
                erj.setVisibility(View.GONE);
                ertg.setVisibility(View.GONE);
                if(nama.getText().toString().isEmpty() || harga.getText().toString().isEmpty() ||
                        jumlah.getText().toString().isEmpty() || tanggal.getText().toString().isEmpty()){
                    if(nama.getText().toString().isEmpty()){
                        ern.setVisibility(View.VISIBLE);
                    }
                    if(jumlah.getText().toString().isEmpty()){
                        erj.setVisibility(View.VISIBLE);
                    }
                    if(harga.getText().toString().isEmpty()){
                        erni.setVisibility(View.VISIBLE);
                    }
                    if(tanggal.getText().toString().isEmpty()){
                        ertg.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    if(mode.equals("1")||mode.equals("0")){
                        goHome();
                    }
                    else{
                        goEdit();
                    }

                }

            }
        });
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddFirstAsset.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                view.setBackgroundColor(500000);
                                // set day of month , month and year value in the edit text
                                tanggal.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }

    void goHome(){
        final ProgressDialog dialog = ProgressDialog.show(AddFirstAsset.this, "",
                "Sedang menambah. Mohon tunggu sebentar...", true);
        Dataasset.dataasset = null;
        assets = new AssetIn();
        assets.setNama(nama.getText().toString());
        assets.setJumlah(jumlah.getText().toString());
        assets.setHarga(harga.getText().toString());
        assets.setTanggal(tanggal.getText().toString());
        assets.createMap();
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
                        goneHome();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
    }

    void goEdit(){
        final ProgressDialog dialog = ProgressDialog.show(AddFirstAsset.this, "",
                "Sedang mengupdate. Mohon tunggu sebentar...", true);
        Dataasset.dataasset = null;
        assets = new AssetIn();
        assets.setNama(nama.getText().toString());
        assets.setJumlah(jumlah.getText().toString());
        assets.setHarga(harga.getText().toString());
        assets.setTanggal(tanggal.getText().toString());
        assets.updateAsset(assetkey,assets.getNama(),assets.getJumlah(), assets.getHarga(), assets.getTanggal());
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
                        goneHome();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
    }

    public void goneHome(){
        Intent home = new Intent(this, Home.class);
        startActivity(home);
        this.finish();
    }

}
