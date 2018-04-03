package com.liberty.share.sign;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

/**
 * 接口签名
 * @since 2018-03-15 09:55
 */
public class SignHelper {

    private static String concatParams(Map<String, String> params2) throws UnsupportedEncodingException {
		Object[] key_arr = params2.keySet().toArray();
        Arrays.sort(key_arr);
        String str = "";

        for (Object key : key_arr) {
            String val = params2.get(key);
            key = URLEncoder.encode(key.toString(), "UTF-8");
            val = URLEncoder.encode(val, "UTF-8");
            str += "&" + key + "=" + val;
        }

        return str.replaceFirst("&", "");
    }
 
    private static String byte2hex(byte[] b) {
        StringBuffer buf = new StringBuffer();
        int i;
 
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }

        return buf.toString();
    }
 
    public static String genSig(String pathUrl, Map<String, String> params,
                                String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String str = concatParams(params);
        str = pathUrl + "?" + str + secret;

        MessageDigest md = MessageDigest.getInstance("SHA1");
        return byte2hex(md.digest(byte2hex(str.getBytes("UTF-8")).getBytes()));
    }

}