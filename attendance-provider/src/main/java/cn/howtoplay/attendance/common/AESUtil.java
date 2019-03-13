package cn.howtoplay.attendance.common;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * AES对称加密工具
 *
 * @author xiaoqi
 */
public class AESUtil {

    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

    private static final String AES_RULE_DEFAULT = "AES_RULE_SEED";

    /**
     26      * 签名算法
     27      */
     public static final String SIGN_ALGORITHMS = "SHA1PRNG";

    /**
     * AES对称加密
     *
     * @param original 原文
     * @return 密文
     */
    public static String aesEncrypt(String original) {
        try {
            //产生密钥
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(AES_RULE_DEFAULT.getBytes());
            keygen.init(128, random);
            SecretKey secretKey = keygen.generateKey();
            byte[] raw = secretKey.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");


            //加密
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byteEncode = original.getBytes("utf-8");
            byte[] byteAes = cipher.doFinal(byteEncode);

            //编码返回
            return Base64.encodeBase64URLSafeString(byteAes);
        } catch (Exception e) {
            logger.error("加密失败", e);
        }
        return null;
    }


    /**
     * @param cipherText 密文
     * @return 原文
     */
    public static String aesDecrypt(String cipherText) {
        try {
            //产生密钥
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance(SIGN_ALGORITHMS);
            random.setSeed(AES_RULE_DEFAULT.getBytes());
            keygen.init(128, random);
            SecretKey secretKey = keygen.generateKey();
            byte[] raw = secretKey.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");


            //解密
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] byteContent = Base64.decodeBase64(cipherText);
            byte[] byteDecode = cipher.doFinal(byteContent);
            return new String(byteDecode, "utf-8");
        } catch (Exception e) {
            logger.error("解密失败", e);
        }
        return null;
    }

}
