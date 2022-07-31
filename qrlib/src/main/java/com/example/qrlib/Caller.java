package com.example.qrlib;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.HashMap;

public class Caller {
    private HashMap<String,Generator> generators = new HashMap<String,Generator>();
    public Caller(String[] list){
        for(String mode:list){
            if(mode.equals("Code39 for Name")){
                generators.put(mode,new GeneratingCode39c());
            }
            if(mode.equals("Code39 for UUID")){
                generators.put(mode,new GeneratingCode39b());
            }
            if(mode.equals("Code39 for Price")){
                generators.put(mode,new GeneratingCode39a());
            }
            if(mode.equals("QR Code")){
                generators.put(mode,new GeneratingQRCode());
            }
            if(mode.equals("QR Code SHA256")){
                generators.put(mode,new GeneratingQRCodeSHA256());
            }
        }
    }
    public Bitmap call(Double price, String goodsName, String purpose, Long time, String mode, Boolean ready){
        try {
            if(!ready){return null;}
            Log.d("Logcat",mode);
            String content = generators.get(mode).processString(price, goodsName, purpose, time);
            Log.d("Logcat",content);
            return generators.get(mode).generateCode(content);
        }catch (Exception e){
            return null;
        }
    }
}
