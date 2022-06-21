package com.yangke.base.utils.callback;

import com.yangke.base.util.AesEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


/**
 * @author cuiguibin
 */
@Slf4j
public class AesUtil {
    /**
     * 文件加密
     */
    public static byte[] fileEncrypt(byte[] decryptData, AesEnum aesEnum) {
        return fileAssemble(decryptData, aesEnum.getKey(), aesEnum.getIv());
    }

    /**
     * 文件解密
     */
    public static byte[] fileDecrypt(byte[] securityData, AesEnum aesEnum) {
        return fileDisassemble(securityData, aesEnum.getKey(), aesEnum.getIv());
    }

    public static String encrypt(String value, AesEnum aesEnum) {
        try {
            return assemble(value, aesEnum.getKey(), aesEnum.getIv());
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String secValue, AesEnum aesEnum) {
        try {
            return disassemble(secValue, aesEnum.getKey(), aesEnum.getIv());
        } catch (Exception e) {
            return null;
        }
    }

    public static String encryptBase64(String value, AesEnum aesEnum) {
        try {
            return assembleBase64(value, aesEnum.getKey(), aesEnum.getIv());
        } catch (Exception e) {
            return null;
        }
    }

    public static String decryptBase64(String secValue, AesEnum aesEnum) {
        try {
            return disassembleBase64(secValue, aesEnum.getKey(), aesEnum.getIv());
        } catch (Exception e) {
            log.error("AesUtil decryptBase64  error", e);
            return null;
        }
    }

    public static String encrypt(String value, String key, String iv) {
        try {
            return assemble(value, key, iv);
        } catch (Exception e) {
            return null;
        }
    }

    public static String decrypt(String secValue, String key, String iv) {
        try {
            return disassemble(secValue, key, iv);
        } catch (Exception e) {
            return null;
        }
    }

    private static String assemble(String sSrc, String sKey, String ivStr) throws Exception {
        if (sKey == null) {
            return null;
        }
        if (sKey.length() != 16) {
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        if (ivStr != null) {
            IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
        }
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return byte2hex(encrypted).toLowerCase();
    }

    private static String assembleBase64(String sSrc, String sKey, String ivStr) throws Exception {
        if (sKey == null) {
            return null;
        }
        if (sKey.length() != 16) {
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        if (ivStr != null) {
            IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, iv);
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
        }
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private static String disassemble(String sSrc, String sKey, String ivStr) throws Exception {
        try {
            if (sKey == null) {
                return null;
            }
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            if (ivStr != null) {
                IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
            }
            byte[] encrypted1 = hex2byte(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    private static String disassembleBase64(String sSrc, String sKey, String ivStr) throws Exception {
        try {
            if (sKey == null) {
                return null;
            }
            if (sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec sKeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            if (ivStr != null) {
                IvParameterSpec iv = new IvParameterSpec(ivStr.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, sKeySpec, iv);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
            }
            byte[] encrypted1 = Base64.getDecoder().decode(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                log.error("AesUtil disassembleBase64  error", e);
                return null;
            }
        } catch (Exception ex) {
            log.error("AesUtil disassembleBase64  error", ex);
            return null;
        }
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

    /**
     * 加密
     */
    private static byte[] fileAssemble(byte[] decryptData, String sKey, String sIv) {
        try {
            if (StringUtils.isBlank(sKey) || sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(decryptData);
            return encrypted;
        } catch (Exception e) {
            log.error("AESEncryptUtils fileAssemble : error", e);
        }
        return null;
    }

    /**
     * 解密
     */
    private static byte[] fileDisassemble(byte[] securityData, String sKey, String sIv) {
        try {
            if (StringUtils.isBlank(sKey) || sKey.length() != 16) {
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(sIv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(securityData);
            return original;
        } catch (Exception e) {
            log.error("AESEncryptUtils fileDisassemble : error", e);
        }
        return null;
    }
}
