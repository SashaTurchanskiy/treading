package com.zosh.treading.utils;

import java.util.Random;

public class OtpUtils {

    public static String generateOTP(){
        int lengthOTP = 6;
        Random random = new Random();

        StringBuilder otp = new StringBuilder();
        for(int i=0;i<lengthOTP;i++){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
