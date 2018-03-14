package com.example.test.andlang.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * 
 * @author
 * 
 */
public final class EncryptUtil {


	private static final String[] HEX_DIGITS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
		"e", "f" };

	private static final String CHARSET_UTF8 = "UTF-8";

	private static final int SIXTEEN = 16;

	private EncryptUtil() {

	}

	public static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / SIXTEEN;
		int d2 = n % SIXTEEN;

		return HEX_DIGITS[d1] + HEX_DIGITS[d2];
	}

	public static String Encry(String strSrc) throws Exception {
		String returnStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			returnStr = byteArrayToHexString(md5.digest(strSrc.getBytes(Charset.forName(CHARSET_UTF8))));
		} catch (Exception e) {
			throw new Exception("Exception: ", e);

		}

		if (returnStr == null) {
			throw new Exception("md5Encry null result");
		}

		return returnStr;
	}

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuilder buf = new StringBuilder("");
			for (byte aB : b) {
				i = aB;
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}


	public static String encryptMD5(String data) throws IOException {
		byte[] bytes = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data.getBytes(CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			String msg = getStringFromException(gse);
			throw new IOException(msg, gse);
		}

		return byte2hex(bytes);
	}

	public static String encryptSHA(String data) throws IOException {
		byte[] bytes = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			bytes = md.digest(data.getBytes(CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			String msg = getStringFromException(gse);
			throw new IOException(msg, gse);
		}

		return byte2hex(bytes);
	}

	public static String encryptHMAC(String data, String secret) throws IOException {
		byte[] bytes = null;

		try {
			SecretKey secretKey = new SecretKeySpec(secret.getBytes(CHARSET_UTF8), "HmacMD5");
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			bytes = mac.doFinal(data.getBytes(CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			String msg = getStringFromException(gse);
			throw new IOException(msg, gse);
		}

		return byte2hex(bytes);
	}

	private static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();

		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}

		return sign.toString();
	}

	private static String getStringFromException(Throwable e) {
		String result = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			PrintStream ps = new PrintStream(bos, false, CHARSET_UTF8);
			e.printStackTrace(ps);
			result = bos.toString(CHARSET_UTF8);
		} catch (UnsupportedEncodingException ee) {
			ee.printStackTrace();
		}

		return result;
	}

}
