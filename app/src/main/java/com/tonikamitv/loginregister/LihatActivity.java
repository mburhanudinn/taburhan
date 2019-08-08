package com.tonikamitv.loginregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tonikamitv.loginregister.Library.LaporanAdapter;
import com.tonikamitv.loginregister.Library.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LihatActivity extends AppCompatActivity {
    ListView list;
    private ProgressDialog progressDialog;
    List<String> listid_laporan = new ArrayList<String>();
    List<String> listkolom_laporan = new ArrayList<String>();
    List<String> listnama_kategori = new ArrayList<String>();
    List<String> liststatus = new ArrayList<String>();
    List<String> listuser_id = new ArrayList<String>();
    List<String> listfoto_laporan = new ArrayList<String>();
    List<String> listnama = new ArrayList<String>();
    Button btnCari;
    EditText edtIdLaporan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        list = (ListView)findViewById(R.id.listView);
        btnCari = (Button)findViewById(R.id.btnCari);
        edtIdLaporan = (EditText)findViewById(R.id.edtIdLaporan);
        LoadProcess("");

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadProcess(edtIdLaporan.getText().toString());
            }
        });

    }

    public void LoadProcess(final String id){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("CETAK",response);
                        progressDialog.dismiss();
                        listid_laporan.clear();
                        listkolom_laporan.clear();
                        listnama_kategori.clear();
                        liststatus.clear();
                        listuser_id.clear();
                        listfoto_laporan.clear();
                        listnama.clear();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray jsonArray = obj.getJSONArray("hasil");
                            if(jsonArray.length()==0){
                                Toast.makeText(getApplicationContext(),"Laporan tidak ditemukan!", Toast.LENGTH_SHORT).show();
                                list.setVisibility(View.GONE);
                            }else{
                                list.setVisibility(View.VISIBLE);
                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject isiArray = jsonArray.getJSONObject(i);
                                    String id_laporan = isiArray.getString("id_laporan");
                                    String kolom_laporan = isiArray.getString("kolom_laporan");
                                    String nama_kategori = isiArray.getString("nama_kategori");
                                    String status = isiArray.getString("status");
                                    String user_id = isiArray.getString("user_id");
                                    String foto_laporan = isiArray.getString("foto_laporan");
                                    String nama = isiArray.getString("nama");
                                    listid_laporan.add(id_laporan);
                                    listkolom_laporan.add(kolom_laporan);
                                    listnama_kategori.add(nama_kategori);
                                    liststatus.add(status);
                                    listuser_id.add(user_id);
                                    listfoto_laporan.add(foto_laporan);
                                    listnama.add(nama);
                                }
                                getAllData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("function", Constant.FUNCTION_LISTLAPORAN);
                params.put("key", Constant.KEY);
                params.put("id", id);
                return params;
            }
        };
        DefaultRetryPolicy policy = new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void getAllData(){
        list.setAdapter(null);
        LaporanAdapter adapter = new LaporanAdapter(LihatActivity.this, listid_laporan,listkolom_laporan,listnama_kategori,liststatus,listuser_id,listfoto_laporan,listnama);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(LihatActivity.this, DetailLaporanActivity.class);
                i.putExtra("title",listnama.get(position)+" ("+listuser_id.get(position)+")");
                i.putExtra("status",liststatus.get(position));
                i.putExtra("content",listkolom_laporan.get(position));
                i.putExtra("image",listfoto_laporan.get(position));
                startActivity(i);
                finish();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                dialogAdd(position);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LihatActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(LihatActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LihatActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
