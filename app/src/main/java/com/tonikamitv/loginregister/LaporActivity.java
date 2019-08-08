package com.tonikamitv.loginregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tonikamitv.loginregister.Library.Constant;
import com.tonikamitv.loginregister.Library.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LaporActivity extends AppCompatActivity {
    EditText etLapor;
    Button btReg;
    ImageView imageView;
    String imgString = "";
    String namaKategori = "";
    RadioButton rad1, rad2, rad3, rad4;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        etLapor = (EditText) findViewById(R.id.etLapor);
        btReg = (Button) findViewById(R.id.btReg);
        imageView = (ImageView) findViewById(R.id.imageView);
        rad1 = (RadioButton) findViewById(R.id.rd1);
        rad2 = (RadioButton) findViewById(R.id.rd2);
        rad3 = (RadioButton) findViewById(R.id.rd3);
        rad4 = (RadioButton) findViewById(R.id.rd4);

        rad1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaKategori = rad1.getText().toString();
            }
        });

        rad2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaKategori = rad2.getText().toString();
            }
        });

        rad3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaKategori = rad3.getText().toString();
            }
        });

        rad4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaKategori = rad4.getText().toString();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });


        btReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!etLapor.getText().toString().equals("")){
                    if(!namaKategori.toString().equals("")){
                        if(!imgString.toString().equals("")){
                            LaporProcess();
                        }else{
                            Toast.makeText(getApplicationContext(),"Gambar belum dipilih!",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Kategori belum dipilih!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Masukan belum diisi!",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void LaporProcess(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.ROOT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.d("CETAK",Constant.ROOT_URL+" "+response);
//                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject userDetails = obj.getJSONObject("hasil");
                            String message = userDetails.getString("message");
                            String success = userDetails.getString("success");
                            if(success.equals("1")){
                                Intent i = new Intent(LaporActivity.this,LaporActivity.class);
                                startActivity(i);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();

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
                params.put("function", Constant.FUNCTION_LAPOR);
                params.put("key", Constant.KEY);
                params.put("user_id", SharedPrefManager.getInstance(getApplicationContext()).getID());
                params.put("masukan", etLapor.getText().toString());
                params.put("kategori", namaKategori);
                params.put("foto", imgString);
                return params;
            }
        };
        DefaultRetryPolicy policy = new DefaultRetryPolicy(0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                imgString = getStringImage(bitmap);
                Log.d("IMAGE",imgString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LaporActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(LaporActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LaporActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

}