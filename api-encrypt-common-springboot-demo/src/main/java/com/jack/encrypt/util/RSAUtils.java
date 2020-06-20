package com.jack.encrypt.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加解密工具类
 */
public class RSAUtils {

    public static final String RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";
    public static final int KEY_SIZE_2048 = 2048;
    public static final int KEY_SIZE_1024 = 1024;
    private static final String ALGORITHM = "RSA";

    private static final String MODULUS = "23980620463405317733450541547993067911469925381335802940848597765219879864233714142808464559967221902238021608823089153589243147032507967846944654919637305649942355752331272311825270625254536063328969297028452046930971780064120466012727459905676872930367444701866806377862161233193270946345147845356705075726092691756923026150781872960590384658942738355161340253282367729185939369935150900449889464402542907381634445136058025878805967666434618930745714831977274063051795422155526274152261258316754786389434748403074164163070271498567545680044016880096351548963078319951190953058330399434709743552070083390262376024243";
    private static final String PUBLIC_EXPONENT = "65537";
    private static final String PRIVATE_EXPONENT = "23027791745479452231857468777024394508026562308674582090672823033684524764572994371871844914360089353701655765568510455205942276457525122305137067123266773829559215016883042562218285186370198789591864515768063424775422235298767006234325300734607361400839440276829634035399841202504724361297270081813838297917979622109738062605100632372687823773665910296346777078761364248231116883333846276835921258584322004440192103069571609792688854026439225065173734596686338434026300353920334059119835602707716272031532562467829105109320881844031200724853185001924276968035944677355648481377403577161636113484646559275617516866513";


    private RSAUtils() {
    }

    /**
     * 生成密钥对，KEY默认长度为2048
     *
     * @return 生成的密钥对
     */
    public static KeyPair generateKeyPair() {
        return generateKeyPair(KEY_SIZE_2048);
    }

    /**
     * 生成密钥对
     *
     * @param keySize Key长度
     * @return 生成的密钥对
     */
    public static KeyPair generateKeyPair(int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(keySize);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("生成密钥对失败", e);
        }
    }

    /**
     * 通过Base64编码后的公钥获取公钥
     *
     * @param base64PublicKey Base64编码后的公钥
     * @return 公钥
     */
    public static PublicKey getPublicKey(String base64PublicKey) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(base64PublicKey));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            throw new IllegalArgumentException("获取公钥失败", e);
        }
    }

    /**
     * 通过模和指数生成公钥
     *
     * @param modulus  模
     * @param exponent 指数
     * @return 公钥
     */
    public static PublicKey getPublicKey(BigInteger modulus, BigInteger exponent) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("获取公钥失败", e);
        }
    }

    /**
     * 通过模和指数生成公钥
     *
     * @param modulus  模
     * @param exponent 指数
     * @return 公钥
     */
    public static PublicKey getPublicKey(String modulus, String exponent) {
        BigInteger mod = new BigInteger(modulus);
        BigInteger exp = new BigInteger(exponent);
        return getPublicKey(mod, exp);
    }


    /**
     * 获取Base64编码后的公钥字符串
     *
     * @param publicKey 公钥
     * @return Base64编码后的公钥字符串
     */
    public static String getBase64PublicKey(PublicKey publicKey) {
        return Base64.encodeBase64String(publicKey.getEncoded());
    }

    /**
     * 通过Base64编码后的私钥字符串获取私钥
     *
     * @param base64PrivateKey Base64编码后的私钥字符
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String base64PrivateKey) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(base64PrivateKey));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            throw new IllegalArgumentException("获取私钥失败", e);
        }
    }

    /**
     * 通过模和指数生成私钥
     *
     * @param modulus  模
     * @param exponent 指数
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(BigInteger modulus, BigInteger exponent) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, exponent);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new IllegalArgumentException("获取私钥失败", e);
        }
    }

    /**
     * 通过模和指数生成私钥
     *
     * @param modulus  模
     * @param exponent 指数
     * @return 私钥
     */
    public static PrivateKey getPrivateKey(String modulus, String exponent) {
        BigInteger mod = new BigInteger(modulus);
        BigInteger exp = new BigInteger(exponent);
        return getPrivateKey(mod, exp);
    }

    /**
     * 通过私钥获取Base64编码后的私钥字符串
     *
     * @param privateKey 私钥
     * @return Base64编码后的私钥字符串
     */
    public static String getBase64PrivateKey(PrivateKey privateKey) {
        return Base64.encodeBase64String(privateKey.getEncoded());
    }

    /**
     * 通过公钥进行加密
     *
     * @param data      需要加密的字符串内容
     * @param publicKey 公钥
     * @return 加密后的二进制数组内容
     */
    public static byte[] encryptAsByteArray(String data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data.getBytes());
        } catch (Exception e) {
            throw new IllegalArgumentException("加密失败", e);
        }
    }

    /**
     * 通过Based64编码后的公钥字符串进行加密
     *
     * @param data            需要加密的字符串内容
     * @param base64PublicKey Base64编码后的公钥字符串
     * @return 加密后的二进制数组内容
     */
    public static byte[] encryptAsByteArray(String data, String base64PublicKey) {
        return encryptAsByteArray(data, getPublicKey(base64PublicKey));
    }

    /**
     * 通过公钥进行字符串加密
     *
     * @param data      需要加密的字符串内容
     * @param publicKey 公钥
     * @return 加密后的字符串
     */
    public static String encryptAsString(String data, PublicKey publicKey) {
        return Base64.encodeBase64String(encryptAsByteArray(data, publicKey));
    }

    /**
     * 通过Base64编码后的公钥字符串进行字符串加密
     *
     * @param data            需要加密的字符串内容
     * @param base64PublicKey Base64编码后的公钥字符串
     * @return 加密后的字符串
     */
    public static String encryptAsString(String data, String base64PublicKey) {
        return Base64.encodeBase64String(encryptAsByteArray(data, getPublicKey(base64PublicKey)));
    }

    /**
     * 通过私钥对加密后的二进制数据进行解密
     *
     * @param data       需要解密的二进制数据
     * @param privateKey 私钥
     * @return 解密后的字符串
     */
    public static String decryptAsString(byte[] data, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(data));
        } catch (Exception e) {
            throw new IllegalArgumentException("解密失败", e);
        }
    }

    /**
     * 通过Base64编码后的私钥对加密后的二进制数据进行解密
     *
     * @param data             需要解密的二进制数据
     * @param base64PrivateKey Base64编码后的私钥
     * @return 解密后的字符串内容
     */
    public static String decryptAsString(byte[] data, String base64PrivateKey) {
        return decryptAsString(data, getPrivateKey(base64PrivateKey));
    }

    /**
     * 通过私钥对加密后的字符串进行解密
     *
     * @param data       需要解密的字符串内容
     * @param privateKey 私钥
     * @return 解密后的字符串内容
     */
    public static String decryptAsString(String data, PrivateKey privateKey) {
        return decryptAsString(Base64.decodeBase64(data), privateKey);
    }

    /**
     * 通过Base64编码后的私钥对加密后的字符串内容进行解密
     *
     * @param data             需要解密的字符串内容
     * @param base64PrivateKey Base64编码后的私钥
     * @return 解密后的字符串内容
     */
    public static String decryptAsString(String data, String base64PrivateKey) {
        return decryptAsString(Base64.decodeBase64(data), getPrivateKey(base64PrivateKey));
    }


    public static void main(String[] args) throws Exception {
        //test1();
        //test2();
        //test3();
        test4();
    }

    private static void test1() {
        KeyPair keyPair = RSAUtils.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        System.out.println("private key：" + Base64.encodeBase64String(privateKey.getEncoded()));
        System.out.println("public key：" + Base64.encodeBase64String(publicKey.getEncoded()));

        // 明文
        String data = "jackluo&lzyforever";

        String str = encryptAsString(data, publicKey);
        System.out.println(str);

        System.out.println(decryptAsString(str, privateKey));

    }

    private static void test2() {
        KeyPair keyPair = generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        System.out.println(publicKey.getModulus());
        System.out.println(privateKey.getModulus());
        System.out.println(publicKey.getPublicExponent());
        System.out.println(privateKey.getPrivateExponent());
    }

    private static void test3() {
        PublicKey publicKey = getPublicKey(MODULUS, PUBLIC_EXPONENT);
        PrivateKey privateKey = getPrivateKey(MODULUS, PRIVATE_EXPONENT);

        System.out.println("public key：" + Base64.encodeBase64String(publicKey.getEncoded()));
        System.out.println("private key：" + Base64.encodeBase64String(privateKey.getEncoded()));

        // 明文
        String data = "jackluo&lzyforever";

        String str = encryptAsString(data, publicKey);
        System.out.println("encrypt data：" + str);

        System.out.println("decrypt data：" + decryptAsString(str, privateKey));

    }

    public static void test4() throws UnsupportedEncodingException {
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm0457zGyQJuPhvIX2CIm9UcgyjBW2QnHp2EXFdelS+dlaE5frunl0QjOBC/qh9o4r6PvSSz2QgN80cVJaKiokZohxwjGYtsaE7uNRAE+VOj7/M4pzthlYVfeq9h0NCnfA2J1eu4FsoAYgj5vPxEzSPz6UTHqBUe0k3lV0zTblqpblvTaTaObfSZPjd149qb5xWzwfRlK+Uy83MwPmvaQBlCVDr6BnTPEb3D7HmoJGfgl/W3KsXC5dZD5ey5JVk5dFy72YliynfWDeBC9BR3PAoIN4RRjUaRNokukHXLKjhxT1ZhOlpxUUEy1kJNGNnylFRYMY4KvI2t8EXEx6UUEqQIDAQAB";
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCbTjnvMbJAm4+G8hfYIib1RyDKMFbZCcenYRcV16VL52VoTl+u6eXRCM4EL+qH2jivo+9JLPZCA3zRxUloqKiRmiHHCMZi2xoTu41EAT5U6Pv8zinO2GVhV96r2HQ0Kd8DYnV67gWygBiCPm8/ETNI/PpRMeoFR7STeVXTNNuWqluW9NpNo5t9Jk+N3Xj2pvnFbPB9GUr5TLzczA+a9pAGUJUOvoGdM8RvcPseagkZ+CX9bcqxcLl1kPl7LklWTl0XLvZiWLKd9YN4EL0FHc8Cgg3hFGNRpE2iS6QdcsqOHFPVmE6WnFRQTLWQk0Y2fKUVFgxjgq8ja3wRcTHpRQSpAgMBAAECggEBAInRIE4nzqPQp8w7l3c/z0yH4wwpW1hcemUJHoBOjivOWsn2CqjgfQVDh/ZlQv1tZDJRCq3oDVvFVoWcSilqybPzkBfcrJfbEMgD/jcj11loKv1LQgSBOW3pmPrsKSqFIZcST2hYQowFajpiBzxzGgyiGcvlvGMVH6Ri3wt+84I9pbkhCYVTymmulBzH63tTtVtX6oGg4cR0Fz2nn9ZyGb5bYRA5vGPqz5C7pBtvwesqy3ntehPCZitjwRnIT4zQ69DQmLI0zwJ4xqNfSNLFj8IrMokbouJDF9w8Q5hVhxr8OLHO1fcif6fTJ/fO0uSYkGxLm4z45HxjpJr0Ogq+HwECgYEA2UrrlItGPAW+cyYlrcoPTeWaQnUM9c0/8ce+07ww8zGX0DaPLrOWf8wbpM+yyqOLz5e+PEt8f/WK7NDLPatmeBXxfrqhiUtjLQ3rXt+eEA1zsaKOONe5TjVkAhZO92zJelR1uXuweNCJ6EYxnDLyXbc2H91uSeWAQb/IL+bRsGECgYEAtviK3qXmnIS1TdlIJu4kZpsrioqFw/vEMukSuIkUsifxb9UvBHa81hUct2veui6d2xRvpTZPdU8whWJ8HODdyzqGOmrGDnGFrWHCv0qQxkLa5jcg0A7Gv2zQ9IeXMQz/rFPs647kIg9LNm33mikrd3ZU1qX0xBZ/3bSLceIoWUkCgYBpxSNJSkYk33LmIDKFFu8+Lr5ZDRsC5ybyLRXBgUCekIxiaLxnsjlW+eaW4hcYtmVO5oFOXjw7GS3HupbD7RPS7nOACZ6p8w1SmAzzQIxzKekgFY1/M9raq/iDX5KOs+Ca8FPhtsie9nu6mbh6XP5HCfCJsEKIK/OzjYQHdSaq4QKBgQClBOYiRlgFw/7MtnHHgrJbxAjyC1r3VgMus1zKCAAdab7r0MZ17S0Em8fpeaVIH7EJHJZQ1/mz4HbvZdJwLTJ8D01uNExqTGJ27Bu7cOG/nh5U0VWLsDf5/4c5c2ydtY6F+q9MB/Si704zSdytMgJc3OFJMiXeEDjEWQgG7yTa2QKBgD4TkPc7UecOTA6S8d9hajIUL/1GFjkQAd5SmWVxOTh0tpZ/5KCKvcAI0uT6QCUmlxaLIENOD7RBpNVs9uNYPBVzmjpxK1N1NOn+alaaL0HBHAWZA03RAmlJE/ak29QSyJSq75igVyUIPYSN4di0lJw4IvkOFhUZnf0VYpwfyhzq";

        // 明文
        String data = "luozy";

        String str = encryptAsString(data, publicKey);
        System.out.println(str);

        String dec = "B639NetXbnlxEp0GCEiwY4q5JLK+V/WjpkzTpZ08KR90EjQ1YDF4JmN6NyHob6WvzynmftNY7ofUNP5rPyNtOsa09esgAiP8nZvtLSngSOB7Y8DhJEQbbRCEetD16Zl57H+fi8S1XbJPvIPuwff878MC1Sqg5My6Kuj9XJ3yim+KpzJgp8F+RaUiMemZ2cEBrBoLMpta8W3iDJDenyDiirEg8KqPFC6jJQmPOxYOCQ4b6Y3HW6H8UQGBVOlpF1PhqbfnDlTms9dpcqcNnFkkoXFwPXB1PHXsiPRHER8XZNrfdd3Ynx87JgITJED/XAmZ3sTd73rOW/kbUYMFWQUrrA==";

        String dec1 = "PYbsOj0abOQLyEpDKxXrMhfagUraXAouPgwGISubqeu007hY11HbNgaaQ13yml9ZiXY5jix5uWv6PuLAYeH6iG0oTyHWBMlZiFw92tW8J5tklP1ZWbdmpY5nblKfJX4nK23fQo44KEOeRQar0YquuCUYmm6cwEEQtRIcPgeseKY9gUpXK%2Bx5a%2FwP8dXENFmuqnn%2BAbnRM7%2FkFVceIz4iKC4KIu90o9bG0TlVI4ofJe5bdaHO4t414R6ij6l95bwAO6EDpsJzmsoLUa3zdWFchuVjUf17ZObjshqRFshP40jXs8%2BGYL03ULZEOC3xRcFEd9EHHx14MioBGomqHodeSQ%3D%3D";
        String res = URLDecoder.decode(dec1, "UTF-8");
        System.out.println(res);

        System.out.println(decryptAsString(res, privateKey));

    }

}
