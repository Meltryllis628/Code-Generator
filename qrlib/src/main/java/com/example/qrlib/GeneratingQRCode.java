package com.example.qrlib;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

class GeneratingQRCode implements Generator {
    protected int width = 800;
    protected int height = 800;
    protected String errorLv = "L";
    protected String margin = "1";
    protected String characterSet = "UTF-8";
    protected static final int BLACK = 0xFF000000;
    protected static final int WHITE = 0xFFFFFFFF;
    protected GeneratingQRCode(){
        width = 800;
        height = 800;
        errorLv = "L";
        margin = "1";
        characterSet = "UTF-8";
    }
    public String processString(Double price, String goodsName, String purpose, Long time){
        try {
            String output = "{ ";
            output += ("\"Price\": " + price.toString()+", ");
            output += ("\"GoodsName\": \"" + goodsName+"\", ");
            output += ("\"Purpose\": \"" + purpose+"\", ");
            output += ("\"Time\": " + time.toString());
            output += " }";
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
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
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
            return null;
        }
    }
}
