package com.example.qrsdk;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qrlib.Caller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Home extends AppCompatActivity {
    Spinner spinner;
    Switch aSwitch;
    Button gene;
    Button clear;
    ImageView code;
    EditText price;
    EditText name;
    EditText purpose;
    TextView errInfo;
    int callingMode = 0;
    int limit = 1000;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bundle bundle = getIntent().getExtras();
        final String[] modes = bundle.getStringArray("List").clone();
        for(String mode:modes){
            Log.d("Logcat",mode);
        }
        Caller caller = new Caller(modes);
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(this,R.layout.item_select,modes);
        spAdapter.setDropDownViewResource(R.layout.item_dropdown);
        price = findViewById(R.id.tx_price);
        price.setText("0");
        name = findViewById(R.id.tx_name);
        name.setText("");
        purpose = findViewById(R.id.tx_purpose);
        purpose.setText("");
        spinner = findViewById(R.id.mode);
        gene = findViewById(R.id.generate);
        code = findViewById(R.id.code);
        errInfo = findViewById(R.id.error);
        spinner.setPrompt("Mode Select");
        spinner.setAdapter(spAdapter);
        aSwitch = findViewById(R.id.ready);
        aSwitch.setChecked(false);
        spinner.setSelection(callingMode);
        spinner.setOnItemSelectedListener(new MySelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                callingMode = i;
            }
        });
        clear = findViewById(R.id.butt_clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errInfo.setText("");
                code.setImageBitmap(null);
                price.setText("0");
                name.setText("");
                purpose.setText("");
            }
        });
        gene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errInfo.setText("");
                    try {
                        Boolean ready = aSwitch.isChecked();
                        String goodsNameS = name.getText().toString();
                        String purposeS = purpose.getText().toString();
                        Double priceD = Double.parseDouble(price.getText().toString());
                        Long timeL = System.currentTimeMillis();
                        int lengthTotal = goodsNameS.length() + purposeS.length() + priceD.toString().length() + timeL.toString().length();
                        if (lengthTotal > limit) {
                            errInfo.setText("Text too long!");
                            Toast.makeText(Home.this, "Text too long!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            getCode(priceD, goodsNameS, purposeS, timeL, modes[callingMode], ready, caller);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        errInfo.setText("Illegal input detected!");
                        Toast.makeText(Home.this, "Illegal input detected!", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        code.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
//                try {
//                    String fileName = name.getText()+".jpeg";
//                    saveBitmap(code, fileName);
//                }catch (Exception e){
//                    Toast.makeText(Home.this, "Fail to save the image!", Toast.LENGTH_SHORT).show();
//                }
                  return false;
            }
        });
    }
    void getCode(Double priceD, String goodsNameS, String purposeS, Long timeL, String modeS, Boolean ready, Caller caller) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap cBitmap = null;
                Log.d("Logcat","Starting");
                try {
                    cBitmap = caller.call(priceD, goodsNameS, purposeS, timeL, modeS, ready);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Bitmap finalCBitmap = cBitmap;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (finalCBitmap != null) {
                            Home.this.code.setImageBitmap(finalCBitmap);
                        }
                        else{
                            Home.this.errInfo.setText("Generation Failed! Have you checked the \"READY\" switch?");
                            Toast.makeText(Home.this, "Generation Failed! Have you checked the \"READY\" switch?", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public void saveBitmap(ImageView view, String fileName) {
        Drawable drawable = view.getDrawable();
        if (drawable == null) {
            return;
        }
        FileOutputStream outStream = null;
        try {
            File file = new File(Environment.DIRECTORY_PICTURES, fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            outStream = new FileOutputStream(file);
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            Toast.makeText(Home.this, "Code image saved.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(Home.this, "Fail to save the image!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    class MySelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            callingMode = i;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Toast.makeText(Home.this,"Please choose mode", Toast.LENGTH_SHORT).show();
        }
    }


}