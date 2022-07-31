package com.example.qrsdk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button initialize;
    private CheckBox code39u;
    private CheckBox code39n;
    private CheckBox code39p;
    private CheckBox qrcode;
    private CheckBox qrcode2;
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String content = "Hello, World!";
        code39u = findViewById(R.id.cb_39u);
        checkBoxes.add(code39u);
        code39n = findViewById(R.id.cb_39n);
        checkBoxes.add(code39n);
        code39p = findViewById(R.id.cb_39p);
        checkBoxes.add(code39p);
        qrcode = findViewById(R.id.cb_qr);
        checkBoxes.add(qrcode);
        qrcode2 = findViewById(R.id.cb_qr2);
        checkBoxes.add(qrcode2);
        initialize = findViewById(R.id.butt_init);
        initialize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                for(CheckBox checkBox:checkBoxes){
                    if (checkBox.isChecked()){
                        count ++;
                    }
                }
                String[] checkedList = new String[count];
                count = 0;
                for(CheckBox checkBox:checkBoxes){
                    if (checkBox.isChecked()){
                        checkedList[count] = checkBox.getText().toString();
                        count ++;
                    }
                }
                if (count<=1){
                    Toast.makeText(MainActivity.this,"Please choose more modes.",Toast.LENGTH_SHORT).show();
                }else{
                    for(String mode:checkedList ){
                        Log.d("Logcat",mode);
                    }
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    intent.putExtra("List",checkedList.clone());
                    startActivity(intent);
                }
            }
        });
    }

}