package com.example.zenbook.thaicomnetworkconnection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    
    int i = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button =(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String PatientID = "58-01-06";
                        i++;
                        int Device_ID = i;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = new Date();
                        int set = i;
                        int time = i;
                        double[] MV = new double[6];
                        double[] TH = new double[4];
                        MV[0] = 1.0;
                        MV[1] = 2.0;
                        MV[2] = 3.0;
                        MV[3] = 4.0;
                        MV[4] = 5.0;
                        MV[5] = 6.0;
//                MV[6] = 7.0;
//                MV[7] = 8.0;
//                MV[8] = 9.0;
//                MV[9] = 10.0;
                        TH[0] = 1.0;
                        TH[1] = 2.0;
                        TH[2] = 3.0;
                        TH[3] = 4.0;
//                TH[4] = 5.0;
//                TH[5] = 6.0;
//                TH[6] = 7.0;
//                TH[7] = 8.0;
//                TH[8] = 9.0;
//                TH[9] = 10.0;
                        //TODO: Used default link
                        FileSending fileSending = new FileSending();
                        //TODO: Input link
//                        FileSending fileSending1 = new FileSending("URLjson", "URLimage");
                        //used function
                        System.out.println("JSON Response Code: " + fileSending.uploadJson(PatientID, Device_ID, dateFormat.format(date), set, time, MV, TH));
                        System.out.println("File Response Code:: " + fileSending.uploadImageFile("/sdcard/DCIM/Camera/Aaa.jpg"));
//                        fileSending.uploadVideoFile("/sdcard/DCIM/Camera/Bbb.mp4");
                    }
                }).start();
            }
        });
    }
}
