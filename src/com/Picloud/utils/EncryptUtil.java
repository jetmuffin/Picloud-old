package com.Picloud.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryptUtil {
    public static final String KEY_SHA = "SHA";  
    public static final String KEY_MD5 = "MD5";  
  
    /** 
     * MAC算法可选以下多种算法 
     *  
     * <pre> 
     * HmacMD5  
     * HmacSHA1  
     * HmacSHA256  
     * HmacSHA384  
     * HmacSHA512 
     * </pre> 
     */  
    public static final String KEY_MAC = "HmacMD5";  
  
    /** 
     * BASE64解密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptBASE64(String key) throws Exception {  
        return (new BASE64Decoder()).decodeBuffer(key);  
    }  
  
    /** 
     * BASE64加密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static String encryptBASE64(byte[] key) throws Exception {  
    	String base64 =  new BASE64Encoder().encodeBuffer(key);
    	//为方便URL传参取出密文中的"="
    	base64 = base64.replace("=", "");
    	base64 = base64.replace("\n", "");
        return base64;  
    }  
  
    /** 
     * MD5加密 
     *  
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public static String encryptMD5(byte[] data) throws Exception {  
  
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);  
        md5.update(data);  
        BigInteger md5BI = new BigInteger(md5.digest());  
        return  md5BI.toString(16);  
  
    }  
  
    /** 
     * SHA加密 
     *  
     * @param data 
     * @return 
     * @throws Exception 
     */  
    public static String encryptSHA(byte[] data) throws Exception {  
  
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);  
        sha.update(data);  
        BigInteger shaBI = new BigInteger(sha.digest());  
        return shaBI.toString(32) ;  
    }  
  
    /** 
     * 初始化HMAC密钥 
     *  
     * @return 
     * @throws Exception 
     */  
    public static String initMacKey() throws Exception {  
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);  
  
        SecretKey secretKey = keyGenerator.generateKey();  
        return encryptBASE64(secretKey.getEncoded());  
    }  
  
    /** 
     * HMAC加密 
     *  
     * @param data 
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static String encryptHMAC(byte[] data, String key) throws Exception {  
  
        SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);  
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());  
        mac.init(secretKey);  
        BigInteger macBI = new BigInteger(mac.doFinal(data));  
        return macBI.toString(16);  
    }
    
    /**
     * 图片加密方法 imageName_uid 再进行md5加密后再进行sha加密，最后进行base64加密
     * @param imageName
     * @param uid
     * @return
     * @throws Exception
     */
    public static String imageEncryptKey(String imageName,String uid) throws Exception{
    	String data = imageName + "_" + uid;
    	String md5 = encryptMD5(data.getBytes());
    	String sha = encryptSHA(md5.getBytes());
    	String key = encryptBASE64(sha.getBytes());
    	key = key.substring(0, 12);
    	return key;
    }
   
    /**
     * 空间名加密方法 spaceName_uid 在进行base64加密
     * @param spaceName
     * @param uid
     * @return
     * @throws Exception
     */
    public static String spaceEncryptKey(String spaceName,String uid) throws Exception{
		String data =spaceName + "_" + uid;
		String key = encryptBASE64(data.getBytes());
		return key;
    }
    
    public static String hdEncryptKey(String hdImageName,String uid) throws Exception{
    	String data = hdImageName + "_" + uid + "_hd";
    	String sha = encryptSHA(data.getBytes());
    	String md5 = encryptMD5(sha.getBytes());
    	String key = encryptBASE64(md5.getBytes());
    	key = key.substring(0, 12);    	
    	return key;
    }
    
    public static String tdEncryptKey(String tdImageName,String uid) throws Exception{
    	String data = tdImageName + "_" + uid + "_td";
    	String sha = encryptSHA(data.getBytes());
    	String md5 = encryptMD5(sha.getBytes());
    	String key = encryptBASE64(md5.getBytes());
    	key = key.substring(0, 12);    	
    	return key;
    }
    public static String panoEncryptKey(String panoImageName,String uid) throws Exception{
    	String data = panoImageName + "_" + uid + "_pano";
    	String sha = encryptSHA(data.getBytes());
    	String md5 = encryptMD5(sha.getBytes());
    	String key = encryptBASE64(md5.getBytes());
    	key = key.substring(0, 12);    	
    	return key;
    }
    public static void main(String[] args) throws Exception {
		String str = "测试空间_chen9434";
		byte[] data = str.getBytes();
		
		String imageKey = imageEncryptKey("测试空间", "chen9434");
		System.out.println("ImageKey:" + imageKey);
		
		String base64 = encryptBASE64(data);  
        System.out.println("BASE64:\n" + base64);  
        
        String md5 = encryptMD5(data);
        System.out.println("MD5:\n" + md5);  
  
        String sha = encryptSHA(data);
        System.out.println("SHA:\n" + sha);  
  
        String mac = encryptHMAC(data, str);
        System.out.println("HMAC:\n" + mac);  
	}
}  

