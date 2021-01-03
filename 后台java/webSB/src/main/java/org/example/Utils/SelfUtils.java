package org.example.Utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelfUtils {
    public static String EncodeByMd5(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest=MessageDigest.getInstance("MD5");
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = messageDigest.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static boolean isNumeric(String str){

        Pattern pattern = Pattern.compile("[0-9]*");

        Matcher isNum = pattern.matcher(str);

        if( !isNum.matches() ){

            return false;

        }

        return true;

    }
}
