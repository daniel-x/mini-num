package de.a0h.mininum.format;

import java.text.DecimalFormat;

/**
 * Thread-safe decimal number format which also handles padding.
 */
public class DecimalFormatWithPadding {

	/**
	 * Length of format in front of decimal point, including padding.
	 */
	int lengthLeadingPoint;

	/**
	 * Total length of format, including padding.
	 */
	int length;

	/**
	 * The base DecimalFormat to use, as a ThreadLocal to make formatting
	 * thread-safe.
	 */
	ThreadLocal<DecimalFormat> base;

	public String toString() {
		return String.format("%s[%s length:%s lengthLeadingPoint:%s]", //
				getClass().getSimpleName(), //
				base.get().toPattern(), //
				length, //
				lengthLeadingPoint //
		);
	}

	public void formatTo(StringBuilder buf, float f) {
		String s = base.get().format(f);

		int indexOfDot = s.indexOf('.');

		int leadingPadLen;
		if (indexOfDot != -1) {
			leadingPadLen = lengthLeadingPoint - indexOfDot;
		} else {
			leadingPadLen = lengthLeadingPoint - s.length();
			leadingPadLen = (leadingPadLen < 0) ? 0 : leadingPadLen;
		}

		int trailingPadLen = length - (leadingPadLen + s.length());

		buf.append(StringUtil.spaces, 0, leadingPadLen);
		buf.append(s);
		buf.append(StringUtil.spaces, 0, trailingPadLen);
	}

}