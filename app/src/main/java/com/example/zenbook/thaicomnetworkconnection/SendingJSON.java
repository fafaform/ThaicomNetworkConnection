package com.example.zenbook.thaicomnetworkconnection;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static android.R.attr.id;
import static android.content.ContentValues.TAG;

/**
 * Created by ZENBOOK on 9/22/2017.
 */

public class SendingJSON extends AsyncTask<String, String, String> {
    private String url_String = "";
    private JSONObject jsonObjSend;
    
    public SendingJSON(String url_String, JSONObject jsonObjSend) {
        this.url_String = url_String;
        this.jsonObjSend = jsonObjSend;
    }
        
    @Override
    protected String doInBackground(String... params) {
        try {
            
            URL object = new URL(url_String);
        
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
            
            System.out.println(jsonObjSend.toString());
    
            OutputStreamWriter localDataOutputStream = new OutputStreamWriter(con.getOutputStream());
            localDataOutputStream.write(jsonObjSend.toString());
            localDataOutputStream.flush();
        
            System.out.println(con.getResponseMessage());
        }
        catch (Exception e){
            Log.v("ErrorAPP",e.toString());
        }
        return "";
    }
    
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println("post execute");
    }
    
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("pre execute");
    }
    
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
}
