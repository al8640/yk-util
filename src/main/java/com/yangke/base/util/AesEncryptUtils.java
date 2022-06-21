package com.yangke.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密算法
 *
 * @author keyang
 */
public class AesEncryptUtils {

    /**
     * JsonUtils工具类中的常量 .
     */
    public static final String JSON_UTILS_DOT = ".";

    /**
     * 工具类中的 常量 2
     */
    public static final int UTILS_NUMBER_2 = 2;

    /**
     * 工具类加解密中的 常量 16
     */
    public static final int ENCRYPT_UTIL_NUMBER_16 = 16;


    /**
     * HTTP正常响应响应码200
     */
    public static final int HTTP_OK_CODE = 200;

    /**
     * &符号
     */
    public static final String AND_SYMBOL = "&";
    private static final Logger LOGGER = LoggerFactory.getLogger(AesEncryptUtils.class);

    private static String ivParameterSpec = "3465281205462396";
    private static String key = "uQAZBjZrbshFp9Ix";

    public static String encrypt(String phone) {
        return assemble(phone, key, ivParameterSpec);
    }

    public static String encrypt(String phone, String sKey) {
        return assemble(phone, sKey, ivParameterSpec);
    }

    public static String encrypt(String phone, String sKey, String sIv) {
        return assemble(phone, sKey, sIv);
    }

    public static String decrypt(String securityPhone) {
        return disassemble(securityPhone, key, ivParameterSpec);
    }

    public static String decrypt(String securityPhone, String sKey) {
        return disassemble(securityPhone, sKey, ivParameterSpec);
    }

    public static String decrypt(String securityPhone, String sKey, String sIv) {
        return disassemble(securityPhone, sKey, sIv);
    }

    public static String longToEncript(Long data) {
        try {
            return data == null ? null : encrypt(String.valueOf(data));
        } catch (Exception var2) {
            return null;
        }
    }

    public static Long decriptToLong(String securityData) {
        try {
            String data = decrypt(securityData);
            return data == null ? null : Long.valueOf(data);
        } catch (Exception var2) {
            return null;
        }
    }

    private static String assemble(String sSrc, String sKey, String sIv) {
        try {
            if (sKey == null) {
                return null;
            }

            if (sKey.length() != ENCRYPT_UTIL_NUMBER_16) {
                return null;
            }
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes());
            return byte2hex(encrypted).toLowerCase();
        } catch (Exception e) {
            LOGGER.error("AESEncryptUtils assemble : error", e);
        }
        return null;
    }

    private static String disassemble(String sSrc, String sKey, String sIv) {
        try {
            if (sKey == null) {
                return null;
            }

            if (sKey.length() != ENCRYPT_UTIL_NUMBER_16) {
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = hex2byte(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original);
        } catch (Exception e) {
            LOGGER.error("AESEncryptUtils disassemble : error", e);
        }
        return null;
    }

    public static byte[] hex2byte(String strHex) {
        if (strHex == null) {
            return null;
        }
        int l = strHex.length();
        if (l % UTILS_NUMBER_2 == 1) {
            return null;
        }
        byte[] b = new byte[l / UTILS_NUMBER_2];
        for (int i = 0; i != l / UTILS_NUMBER_2; i++) {
            b[i] = (byte) Integer.parseInt(strHex.substring(i * UTILS_NUMBER_2, i * UTILS_NUMBER_2 + UTILS_NUMBER_2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder("");
        String tmp;
        for (int n = 0; n < b.length; n++) {
            tmp = (Integer.toHexString(b[n] & 0XFF));
            if (tmp.length() == 1) {
                hs.append("0").append(tmp);
            } else {
                hs.append(tmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(AesEncryptUtils.encrypt("17602153155"));
        System.out.println(assemble("18621528899", "SfBOnGETLWHEvAIP", "9116592517136211"));
        System.out.println(disassemble("d926f98568ae5712b350f3f97b1b5d21", "SfBOnGETLWHEvAIP", "9116592517136211"));
        System.out.println(AesEncryptUtils.decrypt("e6a98a2d50099e10e498fab1643b3b87"));

    }
}
