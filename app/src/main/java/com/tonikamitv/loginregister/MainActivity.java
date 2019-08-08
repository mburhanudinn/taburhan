package com.tonikamitv.loginregister;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] permissionsRequired = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23){
            checkPermissions();
        }

        ImageButton idLapor = (ImageButton)findViewById(R.id.tesss);
        idLapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,LaporActivity.class);
                startActivity(i);
                finish();
            }
        });

        ImageButton lihat = (ImageButton)findViewById(R.id.btLihat);
        lihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(MainActivity.this,LihatActivity.class);
                startActivity(e);
                finish();
            }
        });

        ImageButton panduan = (ImageButton)findViewById(R.id.btPanduan);
        panduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent(MainActivity.this,PanduanActivity.class);
                startActivity(g);
                finish();
            }
        });

        ImageButton tentang = (ImageButton)findViewById(R.id.btTentang);
        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent h = new Intent(MainActivity.this,TentangActivity.class);
                startActivity(h);
                finish();
            }
        });

        ImageButton kontak = (ImageButton)findViewById(R.id.btKontak);
        kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this,KontakActivity.class);
                startActivity(j);
                finish();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                LogoutProcess();
                break;
        }
        return true;
    }

    public void LogoutProcess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("LAPOR");
        builder.setMessage("Anda yakin ingin logout?");
        String positiveText = "Ya";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = getSharedPreferences("LaporApps", Context.MODE_PRIVATE).edit();
                        editor.clear();
                        editor.commit();
                        Toast.makeText(getApplicationContext(),"Logout berhasil",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
        String negativeText = "Tidak";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissionsRequired) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }
}
