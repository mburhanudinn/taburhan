package com.tonikamitv.loginregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    EditText etNama;
    EditText etUsername;
    EditText etPassword;
    EditText etNim;
    EditText etJurusan;
    EditText etFakultas;
    EditText etAngkatan;
    Button bRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        etNama = (EditText) findViewById(R.id.etNama);
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etNim = (EditText) findViewById(R.id.etNim);
        etJurusan = (EditText) findViewById(R.id.etJurusan);
        etFakultas = (EditText) findViewById(R.id.etFakultas);
        etAngkatan = (EditText) findViewById(R.id.etAngkatan);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etNama.getText().toString().equals("")){
                    if(!etUsername.getText().toString().equals("")){
                        if(!etPassword.getText().toString().equals("")){
                            if(!etNim.getText().toString().equals("")){
                                if(!etJurusan.getText().toString().equals("")){
                                    if(!etFakultas.getText().toString().equals("")){
                                        if(!etAngkatan.getText().toString().equals("")){
                                            RegisterProcess();
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Angkatan belum diisi!",Toast.LENGTH_SHORT).show();
                                        }
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Fakultas belum diisi!",Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"Jurusan belum diisi!",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(),"NIM belum diisi!",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),"Password belum diisi!",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"Username belum diisi!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Nama belum diisi!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void RegisterProcess(){
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
                                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
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
                params.put("function", Constant.FUNCTION_REGISTER);
                params.put("key", Constant.KEY);
                params.put("nama", etNama.getText().toString());
                params.put("username", etUsername.getText().toString());
                params.put("password", etPassword.getText().toString());
                params.put("nim", etNim.getText().toString());
                params.put("jurusan", etJurusan.getText().toString());
                params.put("fakultas", etFakultas.getText().toString());
                params.put("angkatan", etAngkatan.getText().toString());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterActivity.this);
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }
}