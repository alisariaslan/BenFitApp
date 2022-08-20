package com.pakachu.benfit;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONWorkbench {

    protected String url = "http://bahadirduzcan.com.tr/pakachu/quick/select/";
    protected String url2 = "http://bahadirduzcan.com.tr/pakachu/quick/";
    protected String id;

    private Context mContext;
    public Boolean finishStatus=false;
    private ORTAK_ARACLAR ortak;

    public JSONWorkbench(Context context) {
        mContext = context;
    }

    public void UPDATEUSER(String columnName, String text, String id) {
        ortak = new ORTAK_ARACLAR();
        if (columnName.equals("tarih"))
            text = ortak.ConvertDateToEN(text);
        String newText = text;

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finishStatus=true;
                Toast.makeText(mContext, "Üye başarıyla güncellendi.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "    \"quickSql\" : \"UPDATE `uyeler` SET `" + columnName + "` = '" + newText + "' WHERE `id` = " + id + ";\"\n" +
                        "}";
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "Pakachu-Token");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("User-Agent","CenutaUA22");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void DELETEUSER(String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finishStatus=true;
                Toast.makeText(mContext, "Üye başarıyla silindi.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "    \"quickSql\" : \"DELETE FROM uyeler WHERE id = " + id + "\"\n" +
                        "}";
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "Pakachu-Token");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("User-Agent","CenutaUA22");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void ADDUSER(String... strings) {
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finishStatus=true;
                Toast.makeText(mContext, "Üye başarıyla eklendi.", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "    \"quickSql\" : \"INSERT INTO `uyeler` (`id`, `tarih`, `ad`, `yas`,`cinsiyet`, `kilo`, `boy`, `boyun`, `onkol`, `kol`, `bicep`, `omuz`, `gogus`, `karin`, `kalca`, `bacak`, `calf`, `antrenor`) VALUES (NULL, '" + strings[0] + "', '" + strings[1] + "', '" + strings[2] + "', '" + strings[3] + "', '" + strings[4] + "', '" + strings[5] + "', '" + strings[6] + "', '" + strings[7] + "', '" + strings[8] + "','" + strings[9] + "', '" + strings[10] + "', '" + strings[11] + "', '" + strings[12] + "', '" + strings[13] + "', '" + strings[14] + "', '" + strings[15] + "', '" + strings[16] + "')\"\n" +
                        "}";
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "Pakachu-Token");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("User-Agent","CenutaUA22");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public ArrayList<ArrayList> GET(String sql) {
        ArrayList<ArrayList> arrayListArrayList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject respObj = null;
                String status = "";
                String message = "";
                String data = "";
                try {
                    respObj = new JSONObject(response);
                    status = respObj.getString("status");
                    message = respObj.getString("message");
                    data = respObj.getString("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JSONArray jsonarray = null;
                try {

                    jsonarray = new JSONArray(data);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        Iterator<String> iter = jsonobject.keys();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                arrayList.add(jsonobject.get(key).toString());
//                                Log.e("key","key: "+jsonobject.get(key).toString());
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        arrayListArrayList.add(arrayList);
                    }
//                    Log.e("key","size: "+arrayListArrayList.size());
                    finishStatus = true;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "\"select\" : true,\n" +
                        "\"quickSql\": \"" + sql + "\"\n" +
                        "}";
                return httpPostBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", "Pakachu-Token");
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("User-Agent","CenutaUA22");
                return params;
            }
        };
        requestQueue.add(stringRequest);
        return arrayListArrayList;
    }

}
