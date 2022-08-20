package com.pakachu.benfit;

import android.content.Context;
import android.util.Log;
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

public class RekorJSONWorkbench {

    protected String url = "https://bahadirduzcan.com.tr/pakachu/quick/select/";
    protected String url2 = "https://bahadirduzcan.com.tr/pakachu/quick/";
    protected String id, tarih, ad, hareket, agirlik, tekrar;

    private Context mContext;
    public Boolean finishStatus = false;
private ORTAK_ARACLAR ortak;
    public RekorJSONWorkbench(Context context) {
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
                finishStatus = true;
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
                finishStatus = true;
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
        for (String a : strings
        ) {
//            Toast.makeText(mContext, ""+a, Toast.LENGTH_SHORT).show();
            Log.e("info: ", a + " " + strings.length);
        }

        tarih = strings[0];
        ad = strings[1];
        hareket = strings[2];
        agirlik = strings[3];
        tekrar = strings[4];


//        Toast.makeText(mContext, ""+strings[28], Toast.LENGTH_SHORT).show();
//        Toast.makeText(mContext, ""+strings[29], Toast.LENGTH_SHORT).show();

//        BackupDatabase backupDatabase = new BackupDatabase(mContext);
//        boolean a = backupDatabase.addData(tarih, ad, yas, cins, kilo, boy, boyun, onkol, kol, bicep, omuz, gogus, karin, kalca, bacak, calf, antrenor, sagkol, solkol, gogusalti, bel, gobek, altkum, basen, sagust, sagalt, solust, solalt, rahatsizlik);
//        if (a)
//            Toast.makeText(mContext, "Kayıt yedeklendi.", Toast.LENGTH_SHORT).show();
//        else Toast.makeText(mContext, "Yedekleme hatası!", Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, "Yeni rekor başarıyla eklendi.", Toast.LENGTH_SHORT).show();
                finishStatus = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                String httpPostBody = "{\n" +
                        "    \"quickSql\" : \"INSERT INTO `rekorlar` (`id`, `zaman`, `isim`, `hareket`,`agirlik`, `tekrar`) VALUES (NULL, '" + strings[0] + "', '" + strings[1] + "', '" + strings[2] + "', '" + strings[3] + "', '" + strings[4] + "')\"\n" +
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

    public ArrayList<ArrayList> GET(String username) {
        ArrayList<ArrayList> arrayListArrayList = new ArrayList<ArrayList>();
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
                        ArrayList<String> arrayList = new ArrayList<String>();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        id = jsonobject.getString("id");
                        arrayList.add(id);
                        tarih = jsonobject.getString("tarih");

                        tarih = ortak.ConvertDateToTR(tarih);
                        arrayList.add(tarih);
                        ad = jsonobject.getString("ad");
                        arrayList.add(ad);

                        arrayListArrayList.add(arrayList);
                    }
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
                        "\"quickSql\": \"SELECT * FROM `uyeler` WHERE `ad` LIKE '" + username + "'\"\n" +
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

    public ArrayList<ArrayList> GETFIRSTTEN(String sql) {
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
                        ArrayList<String> arrayList = new ArrayList<String>();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        id = jsonobject.getString("id");
                        arrayList.add(id);


//                        tarih = jsonobject.getString("tarih");
//                        arrayList.add(tarih);

                        ad = jsonobject.getString("ad");
                        arrayList.add(ad);

                        arrayListArrayList.add(arrayList);
                    }
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


    public ArrayList<ArrayList> GETALL(String sql) {
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
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        Iterator<String> iter = jsonobject.keys();
                        ArrayList<String> arrayList = new ArrayList<>();
                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                arrayList.add(jsonobject.get(key).toString());
                            } catch (JSONException e) {
                                // Something went wrong!
                            }

                        }
                        arrayListArrayList.add(arrayList);
                    }

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
