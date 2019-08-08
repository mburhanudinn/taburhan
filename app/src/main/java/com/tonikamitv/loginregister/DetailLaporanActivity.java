package com.tonikamitv.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tonikamitv.loginregister.Library.Constant;

public class DetailLaporanActivity extends AppCompatActivity {
    private View parent_view;
    TextView title,status, content;
    ImageView image;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private FloatingActionButton fab;
    String linkYoutube = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);
        parent_view = findViewById(android.R.id.content);
        title = (TextView) findViewById(R.id.title);
        status = (TextView) findViewById(R.id.status);
        content = (TextView) findViewById(R.id.content);
        image = (ImageView) findViewById(R.id.image);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
//        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setTitle("");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title.setText(extras.getString("title"));
            status.setText("2. Status Laporan\n" + extras.getString("status"));
            content.setText("1. Isi Laporan\n" + extras.getString("content"));
            Glide.with(DetailLaporanActivity.this)
                    .load(Constant.PICT_URL+extras.getString("image"))
                    .error(R.drawable.noimage)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(image);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(DetailLaporanActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(DetailLaporanActivity.this,LihatActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(DetailLaporanActivity.this,LihatActivity.class);
        startActivity(i);
        finish();
    }
}
