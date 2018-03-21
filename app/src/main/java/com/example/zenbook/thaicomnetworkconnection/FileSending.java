package com.example.zenbook.thaicomnetworkconnection;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ZENBOOK on 10/1/2017.
 */

public class FileSending {
    private String URLjson = "http://nbtc.ee.psu.ac.th/ThaicomServer/index.php";
    private String URLimage = "http://nbtc.ee.psu.ac.th/PhpReceiveMedia/index.php";
    
    
    public FileSending(){
        
    }
    
    public FileSending(String URLjson, String URLimage){
        this.URLjson = URLjson;
        this.URLimage = URLimage;
    }
    
//    public int sendJson(String patientID, int DeviceID, String date, int set, int time, double[] mv, double[] th){
//        SendingJSON postReq = new SendingJSON(URLj, toJson(patientID, DeviceID, date, set, time, mv, th));
//        postReq.execute("start");
//        uploadJson(URLj, toJson(patientID, DeviceID, date, set, time, mv, th));
//        return 0;
//    }
    
    private JSONObject toJson(String patientID, int DeviceID, String date, int set, int time, double[] mv, double[] th){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("Patient_ID", patientID);
            data.put("Device_ID", DeviceID);
            data.put("DateTime", date);
            data.put("Set", set);
            data.put("Time", time);
            for(int i = 0; i < mv.length; i++) {
                data.put("MV" + (i+1), mv[i]);
            }
            for(int i = 0; i < th.length; i++) {
                data.put("TH" + (i+1), th[i]);
            }
            jsonObject.put("DATA", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        saveFile(jsonObject.toString());
        return jsonObject;
    }
    
    public int uploadJson(String patientID, int DeviceID, String date, int set, int time, double[] mv, double[] th) {
        JSONObject jsonObjSend = toJson(patientID, DeviceID, date, set, time, mv, th);
        HttpURLConnection con = null;
        try {
            URL object = new URL(URLjson);
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");
        
            System.out.println(jsonObjSend.toString());
        
            OutputStreamWriter localDataOutputStream = new OutputStreamWriter(con.getOutputStream());
            localDataOutputStream.write(jsonObjSend.toString());
            localDataOutputStream.flush();
            localDataOutputStream.close();
        
            return con.getResponseCode();
        }
        catch (Exception e){
            Log.v("ErrorAPP",e.toString());
            return 0;
        }
    }
    
//    public void uploadVideoFile(String sourceFileUri){
//        HttpURLConnection conn = null;
//        DataOutputStream dos = null;
//        DataInputStream inStream = null;
//        String lineEnd = "rn";
//        String twoHyphens = "--";
//        String boundary =  "*****";
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int maxBufferSize = 1*1024*1024;
//        String responseFromServer = "";
//        try
//        {
//            //------------------ CLIENT REQUEST
//            FileInputStream fileInputStream = new FileInputStream(new File(sourceFileUri) );
//            // open a URL connection to the Servlet
//            URL url = new URL(upLoadServerUri);
//            // Open a HTTP connection to the URL
//            conn = (HttpURLConnection) url.openConnection();
//            // Allow Inputs
//            conn.setDoInput(true);
//            // Allow Outputs
//            conn.setDoOutput(true);
//            // Don't use a cached copy.
//            conn.setUseCaches(false);
//            // Use a post method.
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
//            dos = new DataOutputStream( conn.getOutputStream() );
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + sourceFileUri + "\"" + lineEnd);
//            dos.writeBytes(lineEnd);
//            // create a buffer of maximum size
//            bytesAvailable = fileInputStream.available();
//            bufferSize = Math.min(bytesAvailable, maxBufferSize);
//            buffer = new byte[bufferSize];
//            // read file and write it into form...
//            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//            while (bytesRead > 0)
//            {
//                dos.write(buffer, 0, bufferSize);
//                bytesAvailable = fileInputStream.available();
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//            }
//            // send multipart form data necesssary after file data...
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//            // close streams
//            Log.e("Debug","File is written");
//            fileInputStream.close();
//            dos.flush();
//            dos.close();
//        }
//        catch (MalformedURLException ex)
//        {
//            Log.e("Debug", "error: " + ex.getMessage(), ex);
//        }
//        catch (IOException ioe)
//        {
//            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
//        }
//        //------------------ read the SERVER RESPONSE
//        try {
//            inStream = new DataInputStream ( conn.getInputStream() );
//            String str;
//
//            while (( str = inStream.readLine()) != null)
//            {
//                Log.e("Debug","Server Response "+str);
//            }
//            inStream.close();
//
//        }
//        catch (IOException ioex){
//            Log.e("Debug", "error: " + ioex.getMessage(), ioex);
//        }
//    }
    
    public int uploadImageFile(String sourceFileUri) {
        String fileName = sourceFileUri;
        int serverResponseCode = 0;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File Does not exist");
            return 0;
        }
        try { // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size
            URL url = new URL(URLimage);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);
//            conn.setFixedLengthStreamingMode(fileInputStream.available());
            dos = new DataOutputStream(conn.getOutputStream());
            
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            
            
            
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            
            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            
            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
//            String serverResponseMessage = conn.getResponseMessage();
            
//            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            
            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();
            
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return serverResponseCode;
    }
    
    private void saveFile(String jsonString) {
        try {
            File externalD = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
            File jsonFile = new File(externalD, "JSONFILE.txt");
            if (!externalD.exists()) {
                File dir = new File(Environment.getExternalStorageDirectory() + "/Download/");
                dir.mkdirs();
                jsonFile.createNewFile();
                System.out.println("CREATE FOLDER AND FILE");
            } else {
                if (!jsonFile.exists()) {
                    jsonFile.createNewFile();
                    System.out.println("CREATE FILE ONLY");
                }
            }
            
            FileOutputStream jsonFileOutputStream = new FileOutputStream(jsonFile, true);
            jsonFileOutputStream.write(jsonString.getBytes());
            jsonFileOutputStream.write("\n".getBytes());
            jsonFileOutputStream.flush();
            jsonFileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        
    }
}
