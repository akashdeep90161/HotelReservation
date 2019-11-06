package com.innovationm.hotel.security;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class HashMapUtils {
    private HashMap<String,String> hashMap = new HashMap<>();

    public HashMap<String, String> getHashMap() {
        return hashMap;
    }

    public static String encrypt(String secret)
    {
        /** It will be impemented later**/
        return secret;
    }


    public static String getAlphaNumericString()
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
