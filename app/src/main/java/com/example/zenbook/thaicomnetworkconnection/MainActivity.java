package com.example.zenbook.thaicomnetworkconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                String PatientID = "58-01-06";
                i++;
                int Device_ID = i;
                double[] MV = new double[10];
                double[] TH = new double[10];
                MV[0] = 1.0;
                MV[1] = 2.0;
                MV[2] = 3.0;
                MV[3] = 4.0;
                MV[4] = 5.0;
                MV[5] = 6.0;
                MV[6] = 7.0;
                MV[7] = 8.0;
                MV[8] = 9.0;
                MV[9] = 10.0;
                TH[0] = 1.0;
                TH[1] = 2.0;
                TH[2] = 3.0;
                TH[3] = 4.0;
                TH[4] = 5.0;
                TH[5] = 6.0;
                TH[6] = 7.0;
                TH[7] = 8.0;
                TH[8] = 9.0;
                TH[9] = 10.0;
                JsonObject jsonObject = new JsonObject(PatientID, Device_ID, MV, TH);
            }
        });
    }
}
