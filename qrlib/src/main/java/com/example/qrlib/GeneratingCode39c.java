package com.example.qrlib;

import android.util.Log;

class GeneratingCode39c extends GeneratingCode39 {
    protected int width = 1600;
    protected int height = 800;
    protected String errorLv = "L";
    protected String margin = "1";
    protected String characterSet = "UTF-8";
    protected static final int BLACK = 0xFF000000;
    protected static final int WHITE = 0xFFFFFFFF;
    protected GeneratingCode39c(){
        width = 1600;
        height = 600;
        errorLv = "L";
        margin = "1";
        characterSet = "UTF-8";
    }
    @Override
    public String processString(Double price, String goodsName, String purpose, Long time){
        try {
            //Amount-3.40&Good-”Apple”&Purpose-”Groceries”&Time-1584870000&UUID-”10248133
            String output = "";
            output += ("*Name-" + goodsName + "*");
            return output;
        }catch (Exception e){
            Log.e("Logcat","Error while formalizing parameters");
            return null;
        }
    }
}
