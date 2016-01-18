/**
 * Project: jshop-activity-common
 * 
 * File Name : AES4MEncrypt.java
 * Create Date : 2014年4月3日
 * 
 * 
 * Copyright 2014 360buy.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * 360buy Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with 360buy.com.
 */
package com.myapp.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * 移动端接入加密/解密算法工具类
 * 
 * @author liuxun
 */
public class AES4MEncrypt {
    private static final Logger log      = Logger.getLogger(AES4MEncrypt.class);
    private static final String ENCODING = "UTF-8";
    
    public static final String KEY = "65370A474F01AAF1C2B33BDA34CE2FD205E336A161EEAAE31C9299D0033A4957";

    /**
     * 加密字符串
     * 
     * @param sSrc 字符串
     * @param sKey 密钥KEY
     * @return
     * @throws Exception
     * @author cdduqiang
     * @date 2014年4月3日
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            log.error("Encrypt Key 不能为空！");
            throw new Exception("Encrypt Key 不能为空！");
        }

        byte[] raw = hex2byte(sKey);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] anslBytes = sSrc.getBytes(ENCODING);// string2AnslBytes(sSrc);
        byte[] encrypted = cipher.doFinal(anslBytes);
        return byte2hex(encrypted).toUpperCase();
    }

    /**
     * 解密
     * 
     * @param sSrc 解密前的字符串
     * @param sKey 解密KEY
     * @return
     * @throws Exception
     * @author cdduqiang
     * @date 2014年4月3日
     */
    public static String decrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            log.error("Decrypt Key 不能为空！");
            throw new Exception("Decrypt Key 不能为空！");
        }

        byte[] raw = hex2byte(sKey);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] encrypted1 = hex2byte(sSrc);

        byte[] original = cipher.doFinal(encrypted1);
        return new String(original, ENCODING);// anslBytes2String(original);
    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    public static byte[] string2AnslBytes(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            sb.append(",");
            sb.append((int) c);
        }

        String anslStr = sb.substring(1);
        byte[] b = new byte[anslStr.length()];
        int i = 0;
        for (char c : anslStr.toCharArray()) {
            b[i++] = (byte) c;
        }
        return b;
    }

    public static String anslBytes2String(byte[] anslBytes) {
        char[] cs = new char[anslBytes.length];
        for (int i = 0; i < anslBytes.length; i++) {
            cs[i] = (char) anslBytes[i];

        }
        StringBuilder sb = new StringBuilder();
        String[] srcArray = new String(cs).split(",");
        for (int i = 0; i < srcArray.length; i++) {
            sb.append((char) (Integer.parseInt(srcArray[i])));
        }
        return sb.toString();

    }

    /**
     * 解码
     * 
     * @param str
     * @return string
     * @throws UnsupportedEncodingException
     */
    public static String BASE64decode(String str) throws UnsupportedEncodingException {
        byte[] bt = null;
        bt = Base64.decodeBase64(str.getBytes());
        String res = new String(bt, ENCODING);
        return res;
    }

    public static String BASE64encode(String str) throws UnsupportedEncodingException {
        byte[] bt = null;
        bt = Base64.encodeBase64(str.getBytes());
        String res = new String(bt,ENCODING);
        return res;
    }
    
    public static void main(String[] args) throws Exception {
        AES4MEncrypt aes = new AES4MEncrypt();
       // aes.setSecretKey("F50DBAB515286F4C88D44CADE0819334829C15F60D859F43");
        String en = aes.encrypt(aes.BASE64encode("joeixx"), "65370A474F01AAF1C2B33BDA34CE2FD205E336A161EEAAE31C9299D0033A4957");
        System.out.println(en);
        System.out.println(aes.BASE64decode(aes.decrypt(en, "65370A474F01AAF1C2B33BDA34CE2FD205E336A161EEAAE31C9299D0033A4957")));
    }


}
