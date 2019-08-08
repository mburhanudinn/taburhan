package com.tonikamitv.loginregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LaporRequest extends StringRequest {
    private static final String LAPOR_REQUEST_URL = "http://laporrektorundip.xyz/laporrequest.php";
    private Map<String, String> params;

    public LaporRequest(String kolom_laporan, Response.Listener<String> listener) {
        super(Method.POST, LAPOR_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("kolom_laporan", kolom_laporan);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
