package com.example.qrlib;

import java.nio.charset.StandardCharsets;

class FNV {
    private static final long FNVOffsetBasis = 0x811c9dc5;
    private static final long FNVPrime = 0x01000193;
    private static final long base32 = 0x100000000l;
    private static final String[] HEX = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
    public static String FNV1a(String msg){
        byte[] bytes = msg.getBytes(StandardCharsets.UTF_8);
        long hash = hash(bytes);
        String output = "";
        for(int i=0;i<8;i++){
            int low = (int)(hash%16);
            output = output + HEX[low];
            hash = (int)(hash/16);
        }
        return output;
    }

    private static long hash(byte[] bytes){
        long hash = FNVOffsetBasis;
        for(byte b:bytes){
            hash =xor( hash,b);
            hash = (hash * FNVPrime)%base32;
            hash = hash<0?base32+hash:hash;
        }
        return hash;
    }
    private static long xor(long a, long b){
        long base = 1;
        long result = 0;
        for(int i=0;i<32;i++){
            int lowa= (int) (a%2);
            a = a/2;
            int lowb = (int) (b%2);
            b = b/2;
            int low = lowa == lowb?0:1;
            result += (long)(low*base);
            base = base*2;
        }
        return result;
    }

}
