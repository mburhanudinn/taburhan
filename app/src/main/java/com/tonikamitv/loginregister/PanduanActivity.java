package com.tonikamitv.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;

public class PanduanActivity extends AppCompatActivity {
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        webview = (WebView)findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/panduan.html");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PanduanActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(PanduanActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PanduanActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
