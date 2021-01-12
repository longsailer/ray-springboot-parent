package org.ray.encrypt.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Base64Utils;

public class AES128Encryption {

	public static String encrypt(String strKey, String strIn) throws Exception {
		SecretKeySpec skeySpec = getKey(strKey);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(strIn.getBytes());
		return Base64Utils.encodeToString(encrypted);
	}

	public static String decrypt(String strKey, String strIn) throws Exception {
		SecretKeySpec skeySpec = getKey(strKey);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
		byte[] encrypted1 = Base64Utils.decodeFromString(strIn);
		byte[] original = cipher.doFinal(encrypted1);
		String originalString = new String(original);
		return originalString;
	}

	private static SecretKeySpec getKey(String strKey) throws Exception {
		byte[] arrBTmp = strKey.getBytes();
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
		return skeySpec;
	}

	public static void main(String[] args) throws Exception {
		String Code = "a5580b5fbcaa47f9b69753c27ef6d54a#1235698547854#1234#61b003ec9bcb48d8b54b174856b8f651";
		String key = "1q2w3e4r";
		String codE = AES128Encryption.encrypt(key, Code);

		System.out.println("原文：" + Code);
		System.out.println("密钥：" + key);
		System.out.println("密文：" + codE);
		System.out.println("解密：" + AES128Encryption.decrypt(key, codE));
	}
}
