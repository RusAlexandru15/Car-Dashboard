package com.example.myapplication1710;

public class HexConverter {

    //hex value conversion
    public static int fourHexConvert(String hexNumber) {
        if(hexNumber.length()!=4)
            return -1;
        int num = 0;
        for (int i = 0; i < 4; i++) {
            num += Math.pow(16, 3 - i) * charToNumber(hexNumber.charAt(i));
        }
        return num;
    }

    //speed value conversion
    public static int twoHexConvert(String hexNumber) {
        if(hexNumber.length()!=2)
            return -1;
        return 16*charToNumber(hexNumber.charAt(0)) + charToNumber(hexNumber.charAt(1));
    }

    public static int charToNumber(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c >= 'A' && c <= 'F') {
            return 10 + (c - 'A');
        } else if (c >= 'a' && c <= 'f') {
            return 10 + (c - 'a');
        } else {
            return -1;
        }
    }

}
