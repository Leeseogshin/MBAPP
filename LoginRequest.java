package com.example.final_task;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = "http://toto3568.dothome.co.kr/Login.php";
    private Map<String, String> map;

    public LoginRequest(String userID, String userPassword, Response.Listener<String> listerner){
        super(Method.POST, URL, listerner, null);

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userPassword",userPassword);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;

    }

    public LoginRequest(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }
}
