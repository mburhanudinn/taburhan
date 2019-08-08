package com.tonikamitv.loginregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "http://laporrektorundip.xyz/Registernew.php";
    private Map<String, String> params;

    public RegisterRequest(String nama, String username, String password, String nim, String jurusan, String fakultas, String angkatan, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("nama", nama);
        params.put("username", username);
        params.put("password", password);
        params.put("nim", nim);
        params.put("jurusan", jurusan);
        params.put("fakultas", fakultas);
        params.put("angkatan", angkatan);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
