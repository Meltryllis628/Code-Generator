package com.example.qrlib;

class GeneratingQRCodeSHA256 extends GeneratingQRCode {
    protected int width = 800;
    protected int height = 800;
    protected String errorLv = "L";
    protected String margin = "1";
    protected String characterSet = "UTF-8";
    protected static final int BLACK = 0xFF000000;
    protected static final int WHITE = 0xFFFFFFFF;
    protected GeneratingQRCodeSHA256(){
        width = 800;
        height = 800;
        errorLv = "L";
        margin = "1";
        characterSet = "UTF-8";
    }
    public String processString(Double price, String goodsName, String purpose, Long time){
        String original = super.processString(price, goodsName, purpose, time);
        String output = SHA256.encrypt(original);
        return output;
    }
}
