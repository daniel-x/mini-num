package de.a0h.mininum.format;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MnFormat {

	protected static final DecimalFormat PROTOTYPE_FORMAT_FULL_FLOAT_PRECISION;

	protected static final DecimalFormat PROTOTYPE_FORMAT_PRETTY;

	protected static final DecimalFormat PROTOTYPE_FORMAT_2DECIMALS;

	protected static final int PRECISION_FLOAT_FULL = 60;

	protected static final int PRECISION_PRETTY = 8;

	static {
		DecimalFormatSymbols symbols = getDecimalFormatSymbols();

		PROTOTYPE_FORMAT_FULL_FLOAT_PRECISION = new DecimalFormat("0." + StringUtil.getHashsigns(PRECISION_FLOAT_FULL));
		PROTOTYPE_FORMAT_FULL_FLOAT_PRECISION.setDecimalFormatSymbols(symbols);
		PROTOTYPE_FORMAT_FULL_FLOAT_PRECISION.setDecimalSeparatorAlwaysShown(true);
		PROTOTYPE_FORMAT_FULL_FLOAT_PRECISION.setGroupingUsed(false);

		PROTOTYPE_FORMAT_PRETTY = new DecimalFormat("0." + StringUtil.getHashsigns(PRECISION_PRETTY));
		PROTOTYPE_FORMAT_PRETTY.setDecimalFormatSymbols(symbols);
		PROTOTYPE_FORMAT_PRETTY.setDecimalSeparatorAlwaysShown(true);
		PROTOTYPE_FORMAT_PRETTY.setGroupingUsed(false);

		PROTOTYPE_FORMAT_2DECIMALS = new DecimalFormat("0.00");
		PROTOTYPE_FORMAT_2DECIMALS.setDecimalFormatSymbols(symbols);
		PROTOTYPE_FORMAT_2DECIMALS.setDecimalSeparatorAlwaysShown(true);
		PROTOTYPE_FORMAT_2DECIMALS.setGroupingUsed(false);
	}

	protected static DecimalFormatSymbols getDecimalFormatSymbols() {
		DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(Locale.US);
		symbols.setNaN("nan");
		symbols.setInfinity("inf");

		return symbols;
	}

	public static final ThreadLocal<DecimalFormat> FORMAT_FULL_FLOAT_PRECISION = new ThreadLocalForDecimalFormat(
			PROTOTYPE_FORMAT_FULL_FLOAT_PRECISION);

	public static final ThreadLocal<DecimalFormat> FORMAT_PRETTY = new ThreadLocalForDecimalFormat(
			PROTOTYPE_FORMAT_PRETTY);

	public static final ThreadLocal<DecimalFormat> FORMAT_2DECIMALS = new ThreadLocalForDecimalFormat(
			PROTOTYPE_FORMAT_2DECIMALS);

	public static String toString(float[][] a) {
		return toStringBuilder(a, new StringBuilder(), 0).toString();
	}

	public static StringBuilder toStringBuilder(float[][] a, StringBuilder buf) {
		return toStringBuilder(a, buf, 0);
	}

	public static StringBuilder toStringBuilder(float[][] a, StringBuilder buf, DecimalFormatWithPadding format) {
		return toStringBuilder(a, buf, 0, format);
	}

	public static String toString(float[][] a, int indentSpaces) {
		return toStringBuilder(a, new StringBuilder(), indentSpaces).toString();
	}

	protected static DecimalFormatWithPadding getFormat(NumberStats aggregation) {
		// return aggregation.getFormatPretty();
		return aggregation.getFormatExact();
	}

	public static StringBuilder toStringBuilder(float[][] a, StringBuilder buf, int indentSpaces) {
		NumberStats aggregation = new NumberStats();
		aggregation.aggregate(a);
		DecimalFormatWithPadding format = getFormat(aggregation);

		return toStringBuilder(a, buf, indentSpaces, format);
	}

	public static StringBuilder toStringBuilder(float[][] a, StringBuilder buf, int indentSpaces,
			DecimalFormatWithPadding format) {
		buf.append("[");

		String indent = StringUtil.getSpaces(indentSpaces + 1);

		for (int i = 0; i < a.length; i++) {
			if (i > 0) {
				buf.append(indent);
			}

			toStringBuilder(a[i], buf, format);

			if (i < a.length - 1) {
				buf.append("\n");
			}
		}

		buf.append("]");

		return buf;
	}

	public static String toString(float v) {
		return toStringBuilder(v, new StringBuilder(10)).toString();
	}

	public static StringBuilder toStringBuilder(float v, StringBuilder buf) {
		NumberStats aggregation = new NumberStats();
		aggregation.aggregate(v);
		DecimalFormatWithPadding format = getFormat(aggregation);

		toStringBuilder(v, buf, format);

		return buf;
	}

	public static StringBuilder toStringBuilder(float v, StringBuilder buf, DecimalFormatWithPadding format) {
		format.formatTo(buf, v);

		return buf;
	}

	public static String toString(float[] a) {
		return toStringBuilder(a, new StringBuilder()).toString();
	}

	public static StringBuilder toStringBuilder(float[] a, StringBuilder buf) {
		NumberStats aggregation = new NumberStats();
		aggregation.aggregate(a);
		DecimalFormatWithPadding format = getFormat(aggregation);

		return toStringBuilder(a, buf, format);
	}

	public static StringBuilder toStringBuilder(float[] a, StringBuilder buf, DecimalFormatWithPadding format) {
		buf.append("[");

		for (int i = 0; i < a.length; i++) {
			toStringBuilder(a[i], buf, format);

			if (i < a.length - 1) {
				buf.append(" ");
			}
		}

		buf.append("]");

		return buf;
	}

	private static boolean isSignBitSet(float f) {
		return (Float.floatToRawIntBits(f) & 0x80000000) != 0;
	}

	public static StringBuilder toRepr(StringBuilder buf, float f) {
		if (Float.isNaN(f)) {
			buf.append("Float.NaN");
		} else if (f == Float.POSITIVE_INFINITY) {
			buf.append("Float.POSITIVE_INFINITY");
		} else if (f == Float.NEGATIVE_INFINITY) {
			buf.append("Float.NEGATIVE_INFINITY");
		} else if (f == 0.0) {
			buf.append(isSignBitSet(f) ? "-0" : "0");
		} else {
			buf.append(MnFormat.FORMAT_FULL_FLOAT_PRECISION.get().format(f));
		}

		return buf;
	}

	// public static final String REPR_AR_START = "{ ";
	// public static final String REPR_AR_END = " }";
	public static final String REPR_AR_START = "[ ";
	public static final String REPR_AR_END = " ]";

	public static StringBuilder toRepr(StringBuilder buf, float[] a) {
		buf.append(REPR_AR_START);

		for (int i = 0; i < a.length; i++) {
			toRepr(buf, a[i]);

			if (i < a.length - 1) {
				buf.append(", ");
			}
		}

		buf.append(REPR_AR_END);

		return buf;
	}

	public static StringBuilder toRepr(StringBuilder buf, float[][] a) {
		buf.append(REPR_AR_START);

		for (int i = 0; i < a.length; i++) {
			toRepr(buf, a[i]);

			if (i < a.length - 1) {
				buf.append(", ");
			}
		}

		buf.append(REPR_AR_END);

		return buf;
	}

}
