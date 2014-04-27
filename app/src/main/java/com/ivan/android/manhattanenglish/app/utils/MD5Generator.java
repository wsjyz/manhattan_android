package com.ivan.android.manhattanenglish.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Ztian
 */
public class MD5Generator {

	private static final String CHARSET = "UTF-8";
	private static final String MD5 = "MD5";
	private static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String getHexMD5(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance(MD5);
			byte[] bytes = data.getBytes(CHARSET);
			md.update(bytes);
			byte[] byteDigest = md.digest();
			return hexDigest(byteDigest);
		} catch (Exception e) {
//			Log.e("MD5", "Can't create message digest!", e);
		}

		return null;
	}

	private static String hexDigest(byte[] byteDigest) {
		char[] chars = new char[byteDigest.length * 2];
		for (int i = 0; i < byteDigest.length; i++) {
			// left is higher.
			chars[i * 2] = HEX_DIGITS[byteDigest[i] >> 4 & 0x0F];
			// right is lower.
			chars[i * 2 + 1] = HEX_DIGITS[byteDigest[i] & 0x0F];
		}

		return new String(chars);
	}
}
