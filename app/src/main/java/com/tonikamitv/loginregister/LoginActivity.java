package com.tonikamitv.loginregister;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    TextView tvRegisterLink;
    Button bLogin;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        if(SharedPrefManager.getInstance(getApplicationContext()).getLogin()!=null){
            if(SharedPrefManager.getInstance(getApplicationContext()).getLogin().equals("1")){
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
        }

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        bLogin = (Button) findViewById(R.id.bSignIn);

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etUsername.getText().toString().equals("")){
                    if(!etPassword.getText().toString().equals("")){
                        LoginProcess(etUsername.getText().toString(),etPassword.getText().toString());
                    }else{
                        Toast.makeText(getApplicationContext(),"Password belum diisi",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Username belum diisi",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void LoginProcess(final String username, final String password){
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
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONObject userDetails = obj.getJSONObject("hasil");
                            String message = userDetails.getString("message");
                            String success = userDetails.getString("success");

                            if(success.equals("1")){
                                //Succes
                                String user_id = userDetails.getString("user_id");
                                String name = userDetails.getString("nama");
                                SharedPrefManager.getInstance(getApplicationContext()).setID(user_id);
                                SharedPrefManager.getInstance(getApplicationContext()).setNAMA(name);
                                SharedPrefManager.getInstance(getApplicationContext()).isLogin();
                                Intent i = new Intent(LoginActivity.this,MainActivity.class);
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
                params.put("function", Constant.FUNCTION_LOGIN);
                params.put("key", Constant.KEY);
                params.put("username", username);
                params.put("password", password);
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

}
