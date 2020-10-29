package com.ttit.zhihuibeijing.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JW.S on 2020/10/10 1:05 AM.
 */
public class MD5Utils {
    public static String encode(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(password.getBytes());
            StringBuilder mBuild = new StringBuilder();
            for (byte b : result) {
                int number = (int) (b & 0xff);
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    mBuild.append("0");
                }
                mBuild.append(str);
            }
            return mBuild.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
