package de.a0h.mininum.format;

import java.text.DecimalFormat;

/**
 * Statistics about decimal numbers suitable to derive an adequate format for
 * displaying these numbers in a nicely lined up table.
 */
public class NumberStats {
	boolean containsNegativeInf = false;
	boolean containsPositiveInf = false;
	boolean containsNan = false;

	float maxAbs = Float.NEGATIVE_INFINITY;
	float minAbsGreaterZero = Float.POSITIVE_INFINITY;

	int maxCharsBeforePoint = 0;
	int maxCharsBehindPoint = 0;

	public String toString() {
		return String.format("%s[%s %s %s %s %s %s %s]", //
				getClass().getSimpleName(), //
				containsNegativeInf, //
				containsPositiveInf, //
				containsNan, //
				maxAbs, //
				minAbsGreaterZero, //
				maxCharsBeforePoint, //
				maxCharsBehindPoint //
		);
	}

	public NumberStats aggregate(float[][] a) {
		for (int i = 0; i < a.length; i++) {
			aggregate(a[i]);
		}

		return this;
	}

	public NumberStats aggregate(float[][][] a) {
		for (int i = 0; i < a.length; i++) {
			aggregate(a[i]);
		}

		return this;
	}

	public NumberStats aggregate(float[] a) {
		for (int i = 0; i < a.length; i++) {
			aggregate(a[i]);
		}

		return this;
	}

	public NumberStats aggregate(float f) {
		if (f == Float.NEGATIVE_INFINITY) {
			containsNegativeInf = true;

		} else if (f == Float.POSITIVE_INFINITY) {
			containsPositiveInf = true;

		} else if (Float.isNaN(f)) {
			containsNan = true;

		} else {
			float fAbs = Math.abs(f);
			maxAbs = Math.max(fAbs, maxAbs);
			if (fAbs > 0) {
				minAbsGreaterZero = Math.min(fAbs, minAbsGreaterZero);
			}

			String s = MnFormat.FORMAT_FULL_FLOAT_PRECISION.get().format(f);

			int indexOfDot = s.indexOf('.');
			if (indexOfDot == -1) {
				throw new RuntimeException("indexOfDot == -1: '" + s + "'");
			}

			int beforePoint = indexOfDot;
			int behindPoint = s.length() - indexOfDot - 1;

			maxCharsBeforePoint = Math.max(beforePoint, maxCharsBeforePoint);
			maxCharsBehindPoint = Math.max(behindPoint, maxCharsBehindPoint);
		}

		return this;
	}

	public DecimalFormatWithPadding getFormatPretty() {
		DecimalFormatWithPadding format = new DecimalFormatWithPadding();

		int precision;
		if (maxAbs < 0.1f && maxAbs != 0.0f) {
			int zerosBehindPoint = countZerosBehindPoint(maxAbs);
			precision = zerosBehindPoint + MnFormat.PRECISION_PRETTY;

			DecimalFormat baseFormat = new DecimalFormat("0." + StringUtil.getHashsigns(precision));
			baseFormat.setDecimalFormatSymbols(MnFormat.getDecimalFormatSymbols());
			baseFormat.setDecimalSeparatorAlwaysShown(true);
			baseFormat.setGroupingUsed(false);

			format.base = new ThreadLocalForDecimalFormat(baseFormat);

		} else {
			precision = MnFormat.PRECISION_PRETTY;
			format.base = MnFormat.FORMAT_PRETTY;
		}

		format.lengthLeadingPoint = maxCharsBeforePoint;
		format.length = maxCharsBeforePoint + 1 + Math.min(maxCharsBehindPoint, precision);

		if (containsNegativeInf) {
			format.length = Math.max(format.length, 4);
		} else if (containsPositiveInf || containsNan) {
			format.length = Math.max(format.length, 3);
		}

		return format;
	}

	public DecimalFormatWithPadding getFormatExact() {
		DecimalFormatWithPadding format = new DecimalFormatWithPadding();

		format.lengthLeadingPoint = maxCharsBeforePoint;
		format.length = maxCharsBeforePoint + 1 + maxCharsBehindPoint;

		if (containsNegativeInf) {
			format.length = Math.max(format.length, 4);
		} else if (containsPositiveInf || containsNan) {
			format.length = Math.max(format.length, 3);
		}

		format.base = MnFormat.FORMAT_FULL_FLOAT_PRECISION;

		return format;
	}

	/**
	 * Suitable for abs(f) < 1 only.
	 */
	public static int countZerosBehindPoint(float f) {
		f = Math.abs(f);

		for (int i = 0; i < 61; i++) {
			f *= 10f;
			if (f >= 1f) {
				return i;
			}
		}

		return 0;
	}

	public static boolean isSignBitSet(float f) {
		return (Float.floatToRawIntBits(f) & 0x80000000) != 0;
	}

//	public static int countCharsBeforePoint(float f) {
//		return countDigitsBeforePoint(f) + (isSignBitSet(f) ? 1 : 0);
//	}
//
//	public static int countDigitsBeforePoint(float f) {
//		f = Math.abs(f);
//
//		for (int i = 1; i < 40; i++) {
//			f *= 0.1f;
//			if (f < 1f) {
//				return i;
//			}
//		}
//
//		return 0;
//	}
}
