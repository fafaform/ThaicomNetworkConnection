package com.example.zenbook.thaicomnetworkconnection;

import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ZENBOOK on 10/1/2017.
 */

public class JsonObject {
    private static String URL = "http://nbtc.ee.psu.ac.th/ThaicomServer/index.php";
    
    public JsonObject(String patientID, int DeviceID, double[] mv, double[] th){
        SendingJSON postReq = new SendingJSON(URL, toJson(patientID, DeviceID, mv, th));
        postReq.execute("start");
    }
    
    private JSONObject toJson(String patientID, int DeviceID, double[] mv, double[] th){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        try {
            data.put("Patient_ID", patientID);
            data.put("Device_ID", DeviceID);
            data.put("MV1", mv[0]);
            data.put("MV2", mv[1]);
            data.put("MV3", mv[2]);
            data.put("MV4", mv[3]);
            data.put("MV5", mv[4]);
            data.put("MV6", mv[5]);
            data.put("MV7", mv[6]);
            data.put("MV8", mv[7]);
            data.put("MV9", mv[8]);
            data.put("MV10", mv[9]);
            data.put("TH1", th[0]);
            data.put("TH2", th[1]);
            data.put("TH3", th[2]);
            data.put("TH4", th[3]);
            data.put("TH5", th[4]);
            data.put("TH6", th[5]);
            data.put("TH7", th[6]);
            data.put("TH8", th[7]);
            data.put("TH9", th[8]);
            data.put("TH10", th[9]);
            jsonObject.put("DATA", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        saveFile(jsonObject.toString());
        return jsonObject;
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
