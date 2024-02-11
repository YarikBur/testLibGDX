package ru.yarikbur.test.utils.math;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	private static String getHexString(byte[] hash) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : hash) {
			final String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		return hexString.toString();
	}

	public static String sha512(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));

			return getHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String sha256(String base) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));

			return getHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
