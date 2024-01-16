/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 *
 * @author Trong Huy
 */
public class Encode {

    public static String encodeToSHA256(String str) {
        String salt = "asjjkerj3245ufdf.,sdf,eqweqwecz][";
        String result = null;

        str += salt;
        try {
//            byte[] dataBytes = str.getBytes("UTF-8");
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            result = Base64.encodeBase64String(md.digest(dataBytes));
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            result = Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static void main(String[] args) {
        Encode e = new Encode();
        System.out.println(e.encodeToSHA256("123456"));
    }
}
