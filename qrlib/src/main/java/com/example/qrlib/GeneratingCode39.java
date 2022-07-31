package com.example.qrlib;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code39Writer;

import java.util.Hashtable;

class GeneratingCode39 implements Generator {
    protected int width = 1600;
    protected int height = 800;
    protected String errorLv = "L";
    protected String margin = "1";
    protected String characterSet = "UTF-8";
    protected static final int BLACK = 0xFF000000;
    protected static final int WHITE = 0xFFFFFFFF;
    protected GeneratingCode39(){
        width = 1600;
        height = 600;
        errorLv = "L";
        margin = "1";
        characterSet = "UTF-8";
    }
    public String processString(Double price, String goodsName, String purpose, Long time){
        try {
            //Amount-3.40&Good-”Apple”&Purpose-”Groceries”&Time-1584870000&UUID-”10248133
            String output = "";
            output += ("Price-" + price.toString()+"&");
            output += ("Good-\"" + goodsName+"\"&");
            output += ("Purpose-\"" + purpose+"\"&");
            output += ("Time-" + time.toString());
            return output;
        }catch (Exception e){
            Log.e("Logcat","Error while formalizing parameters");
            return null;
        }
    }
    public Bitmap generateCode(String content){
        return generateCode(content,width,height, characterSet, errorLv, margin, BLACK, WHITE);
    }
    private Bitmap generateCode(String content, int width, int height,
                               String characterSet, String errorCorrectionLevel,
                               String margin, int colorBlack, int colorWhite) {
        final String TAG = "Logcat";
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        try {
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            if (!TextUtils.isEmpty(characterSet)) {
                hints.put(EncodeHintType.CHARACTER_SET, characterSet);
            }
            if (!TextUtils.isEmpty(errorCorrectionLevel)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
            }
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            BitMatrix bitMatrix = new Code39Writer().encode(content, BarcodeFormat.CODE_39, width, height, hints);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = colorBlack;
                    } else {
                        pixels[y * width + x] = colorWhite;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
