package org.ray.encrypt.utils;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.ray.encrypt.utils.codec.Hex;

public class SecretAlgorithm {
	private static Logger log = LoggerFactory.getLogger(SecretAlgorithm.class);
	public static final String MD5 = "MD5";
	public static final String SHA256 = "SHA-256";
	public static final String SHA1 = "SHA-1";

	/**
	 * 系统管理平台的密码加密专用
	 * @author ray
	 * @param password
	 * @param salt
	 * @param hashIterations
	 * @return String
	 */
	public static String sha1(String password, String salt){
		return computeHex(password, salt, SHA1, 1024);
	}
	
	public static String sha1(String str){
		return compute(str, SHA1);
	}
	
	public static String sha256(String str){
		return compute(str, SHA256);
	}
	
	public static String md5(String str){
		return compute(str, MD5);
	}
	
	public static String computeHex(String str, String salt, String alg, int hashIterations) {
		return compute(str, salt, alg, true, hashIterations);
	}
	
	public static String compute(String str, String alg) {
		return compute(str, null, alg, false, 0);
	}
	
	public static String compute(String str, String salt, String alg) {
		return compute(str, salt, alg, false, 0);
	}
	
	public static String compute(String str, String salt, String alg, boolean isHex, int hashIterations) {
		try {
			byte[] byteArray = str.getBytes();
			MessageDigest digest = MessageDigest.getInstance(alg);
			if(salt != null && !"".equals(salt)) {
	            digest.reset();
	            digest.update(salt.getBytes());
	        }
			
			byte[] hashBytes = digest.digest(byteArray);
			
			int iterations = hashIterations - 1;
			for(int i = 0; i < iterations; ++i) {
	            digest.reset();
	            hashBytes = digest.digest(hashBytes);
	        }
			String hexString = byteToHex(hashBytes, isHex);
			return hexString;
		} catch (Exception e) {
			log.error("Have error happend in the method compute of the class SecretAlgorithm.", e);
			return null;
		}
	}
	
	private static String byteToHex(byte[] md5Bytes, boolean isHex){
		if(isHex){
			return Hex.encodeToString(md5Bytes);
		}
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = (md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}