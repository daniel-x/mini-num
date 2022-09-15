package de.a0h.mininum.format;

import java.util.Arrays;

public class StringUtil {

	protected static final String spaces = getFilledString(' ', 64);

	protected static final String hashes = getFilledString('#', 64);

	public static String getHashsigns(int length) {
		if (length <= hashes.length()) {
			return hashes.substring(0, length);
		} else {
			return getFilledString('#', length);
		}
	}

	public static String getSpaces(int length) {
		if (length <= spaces.length()) {
			return spaces.substring(0, length);
		} else {
			return getFilledString(' ', length);
		}
	}

	protected static String getFilledString(char c, int length) {
		char[] a = new char[length];

		Arrays.fill(a, c);

		return new String(a);
	}

	public static String leftPad(String s, int resultLength) {
		StringBuilder buf = new StringBuilder(resultLength);

		int padLen = resultLength - s.length();
		if (padLen <= 0) {
			return s;
		}

		for (int i = 0; i < padLen; i++) {
			buf.append(' ');
		}

		buf.append(s);

		return buf.toString();
	}
}
