package com.jack.jwt.utils;

import com.jack.jwt.common.ResponseCode;
import io.jsonwebtoken.*;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * JWT认证工具类，采用RSA加密
 */
public class JWTUtils {
    private static RSAPrivateKey privateKey;
    private static RSAPublicKey publicKey;

    private static class SingletonHolder {
        private static final JWTUtils INSTANCE = new JWTUtils();
    }

    public synchronized static JWTUtils getInstance(String modulus, String privateExponent, String publicExponent) {
        if (privateKey == null && publicKey == null) {
            privateKey = RSAUtils.getPrivateKey(modulus, privateExponent);
            publicKey = RSAUtils.getPublicKey(modulus, publicExponent);
        }
        return SingletonHolder.INSTANCE;
    }

    public synchronized static void reload(String modulus, String privateExponent, String publicExponent) {
        privateKey = RSAUtils.getPrivateKey(modulus, privateExponent);
        publicKey = RSAUtils.getPublicKey(modulus, publicExponent);
    }

    public synchronized static JWTUtils getInstance() {
        if (privateKey == null && publicKey == null) {
            privateKey = RSAUtils.getPrivateKey(RSAUtils.MODULUS, RSAUtils.PRIVATE_EXPONENT);
            publicKey = RSAUtils.getPublicKey(RSAUtils.MODULUS, RSAUtils.PUBLIC_EXPONENT);
        }
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取Token
     * @param uid 用户ID
     * @param exp 失效时间，单位分钟
     */
    public static String getToken(String uid, int exp) {
        long endTime = System.currentTimeMillis() + 1000 * 60 * exp;
        return Jwts.builder().setSubject(uid).setExpiration(new Date(endTime)).signWith(SignatureAlgorithm.RS512, privateKey).compact();
    }


    /**
     * 获取Token，默认失效时间为24小时
     * @param uid 用户ID
     */
    public String getToken(String uid) {
        // 默认24小时过期
        long endTime = System.currentTimeMillis() + 1000 * 60 * 60 * 24;
        return Jwts.builder().setSubject(uid).setExpiration(new Date(endTime)).signWith(SignatureAlgorithm.RS512, privateKey).compact();
    }

    /**
     * 检查Token是否合法
     * @param token 验证token
     * @return JWTResult
     */
    public JWTResult checkToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
            String sub = claims.get("sub", String.class);
            return new JWTResult(true, sub, "合法请求", ResponseCode.SUCCESS_CODE.getCode());
        } catch (ExpiredJwtException e) {
            // 在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
            return new JWTResult(false, null, "token已过期", ResponseCode.TOKEN_TIMEOUT_CODE.getCode());
        } catch (SignatureException  e) {
            // 在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
            return new JWTResult(false, null, "非法请求", ResponseCode.NO_AUTH_CODE.getCode());
        } catch (Exception e) {
            return new JWTResult(false, null, "非法请求", ResponseCode.NO_AUTH_CODE.getCode());
        }
    }


    /**
     * JWT验证结果类
     */
    public static class JWTResult {
        private boolean status;
        private String uid;
        private String msg;
        private int code;

        public JWTResult() {
            super();
        }

        public JWTResult(boolean status, String uid, String msg, int code) {
            super();
            this.status = status;
            this.uid = uid;
            this.msg = msg;
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }


}
