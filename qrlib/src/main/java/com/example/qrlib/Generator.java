package com.example.qrlib;

import android.graphics.Bitmap;

interface Generator {
    Bitmap generateCode(String content);

    String processString(Double price, String goodsName, String purpose, Long time) ;
}
