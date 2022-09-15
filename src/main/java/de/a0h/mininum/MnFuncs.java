package de.a0h.mininum;

import java.util.Random;

public class MnFuncs {

	/**
	 * The probability mass function (pmf) specifies a probability distribution for
	 * a discretely distributed random variable, of which one randomly drawn sample
	 * is returned by this method. The pmf is expected to be well-formed, i.e. it is
	 * expected that the values in pmf are all within the interval [0,1] and that
	 * they add up to 1.0.
	 */
	public static int draw(float[] pmf, Random rnd) {
		float x = rnd.nextFloat();

		int i = 0;
		float pSum = 0;
		for (; i < pmf.length; i++) {
			pSum += pmf[i];

			if (x < pSum) {
				return i;
			}
		}

		if (pSum < 0.999999f) {
			throw new IllegalArgumentException("pmf does not add up to 1.0: sum(pmf) = " + pSum);
		}

		return --i;
	}

	/**
	 * Calculates and returns the cross entropy of vectors a and b.
	 */
	public static float crossEntropy_notZeroSafe(float[] a, float[] b) {
		MnLinalg.ensureSaneElementwiseOp_A_eq_B(a, b);

		float result = 0;
		for (int i = 0; i < a.length; i++) {
			result += a[i] * MnFuncs.log(b[i]);
		}

		return result;
	}

	/**
	 * Calculates and returns the cross entropy. This version of cross entropy is
	 * also safe against zero values in b[i] by adding Float.MIN_NORMAL to every
	 * b[i] before taking the log.
	 */
	public static float crossEntropy_zeroSafe(float[] y, float[] b) {
		MnLinalg.ensureSaneElementwiseOp_A_eq_B(y, "y", b, "b");

		float result = 0;
		for (int i = 0; i < y.length; i++) {
			result += y[i] * (float) Math.log(b[i] + Float.MIN_NORMAL);
		}

		return result;
	}

	/**
	 * Initializes the given matrix using the specified random number generator to
	 * generate a gaussian distribution.
	 */
	public static void assignGaussian(float[][] a, Random rnd) {
		for (int i = 0; i < a.length; i++) {
			assignGaussian(a[i], rnd);
		}
	}

	/**
	 * Initializes the given matrix using the specified random number generator to
	 * generate a uniform distribution in the range [min,max), i.e. values can be
	 * equal to min, but max is excluded from the range.
	 */
	public static void assignUniform(float[][] a, float min, float max, Random rnd) {
		for (int i = 0; i < a.length; i++) {
			assignUniform(a[i], min, max, rnd);
		}
	}

	/**
	 * Initializes the given vector using the specified random number generator to
	 * generate a gaussian distribution.
	 */
	public static void assignUniform(float[] v, float min, float max, Random rnd) {
		float diam = max - min;

		for (int j = 0; j < v.length; j++) {
			v[j] = min + rnd.nextFloat() * diam;
		}
	}

	/**
	 * Initializes the given vector using the specified random number generator to
	 * generate a gaussian distribution.
	 */
	public static void assignGaussian(float[] v, Random rnd) {
		for (int j = 0; j < v.length; j++) {
			v[j] = (float) rnd.nextGaussian();
		}
	}

	/**
	 * Calculate the sum of all elements of vector a and return the result.
	 */
	public static float sum(float[] a) {
		float result = 0f;

		for (int j = 0; j < a.length; j++) {
			result += a[j];
		}

		return result;
	}

	public static float abs(float x) {
		return Math.abs(x);
	}

	public static void abs(float[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i] = abs(x[i]);
		}
	}

	public static void abs(float[] x, float[] y) {
		for (int i = 0; i < x.length; i++) {
			y[i] = abs(x[i]);
		}
	}

	public static void abs(float[] x, int len) {
		for (int i = 0; i < len; i++) {
			x[i] = abs(x[i]);
		}
	}

	public static void abs(float[] x, int len, float[] y) {
		for (int i = 0; i < len; i++) {
			y[i] = abs(x[i]);
		}
	}

	public static float exp(float x) {
		return (float) Math.exp(x);
	}

	public static void exp(float[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i] = exp(x[i]);
		}
	}

	public static void exp(float[] x, float[] y) {
		for (int i = 0; i < x.length; i++) {
			y[i] = exp(x[i]);
		}
	}

	public static void exp(float[] x, int len) {
		for (int i = 0; i < len; i++) {
			x[i] = exp(x[i]);
		}
	}

	public static void exp(float[] x, int len, float[] y) {
		for (int i = 0; i < len; i++) {
			y[i] = exp(x[i]);
		}
	}

	public static float log(float x) {
		return (float) Math.log(x);
	}

	public static void log(float[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i] = log(x[i]);
		}
	}

	public static void log(float[] x, float[] y) {
		for (int i = 0; i < x.length; i++) {
			y[i] = log(x[i]);
		}
	}

	public static void log(float[] x, int len) {
		for (int i = 0; i < len; i++) {
			x[i] = log(x[i]);
		}
	}

	public static void log(float[] x, int len, float[] y) {
		for (int i = 0; i < len; i++) {
			y[i] = log(x[i]);
		}
	}

	public static float log_zeroSafe(float x) {
		return (float) Math.log(x + Float.MIN_NORMAL);
	}

	public static void log_zeroSafe(float[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i] = log_zeroSafe(x[i]);
		}
	}

	public static void log_zeroSafe(float[] x, float[] y) {
		for (int i = 0; i < x.length; i++) {
			y[i] = log_zeroSafe(x[i]);
		}
	}

	public static void log_zeroSafe(float[] x, int len) {
		for (int i = 0; i < len; i++) {
			x[i] = log_zeroSafe(x[i]);
		}
	}

	public static void log_zeroSafe(float[] x, int len, float[] y) {
		for (int i = 0; i < len; i++) {
			y[i] = log_zeroSafe(x[i]);
		}
	}

	public static float sig(float x) {
		return 1.0f / (1.0f + (float) Math.exp(-x));
	}

	public static void sig(float[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i] = sig(x[i]);
		}
	}

	public static void sig(float[] x, float[] y) {
		for (int i = 0; i < x.length; i++) {
			y[i] = sig(x[i]);
		}
	}

	public static void sig(float[] x, int len) {
		for (int i = 0; i < len; i++) {
			x[i] = sig(x[i]);
		}
	}

	public static void sig(float[] x, int len, float[] y) {
		for (int i = 0; i < len; i++) {
			y[i] = sig(x[i]);
		}
	}

	public static float tanh(float x) {
		return (float) Math.tanh(x);
	}

	public static void tanh(float[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i] = tanh(x[i]);
		}
	}

	public static void tanh(float[] x, float[] y) {
		for (int i = 0; i < x.length; i++) {
			y[i] = tanh(x[i]);
		}
	}

	public static void tanh(float[] x, int len) {
		for (int i = 0; i < len; i++) {
			x[i] = tanh(x[i]);
		}
	}

	public static void tanh(float[] x, int len, float[] y) {
		for (int i = 0; i < len; i++) {
			y[i] = tanh(x[i]);
		}
	}

	public static void sqrt(float[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i] = sqrt(x[i]);
		}
	}

	public static void sqrt(float[] x, float[] y) {
		for (int i = 0; i < x.length; i++) {
			y[i] = sqrt(x[i]);
		}
	}

	public static void sqrt(float[] x, int len) {
		for (int i = 0; i < len; i++) {
			x[i] = sqrt(x[i]);
		}
	}

	public static void sqrt(float[] x, int len, float[] y) {
		for (int i = 0; i < len; i++) {
			y[i] = sqrt(x[i]);
		}
	}

	public static String onehotSequenceToCharString(float[][] a) {
		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < a.length; i++) {
			char c = (char) ('0' + onehotToIndex(a[i]));
			buf.append(c);
		}

		return buf.toString();
	}

	public static String similarOnehotSequenceToCharString(float[][] a) {
		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < a.length; i++) {
			char c = (char) ('0' + idxMax(a[i]));
			buf.append(c);
		}

		return buf.toString();
	}

	/**
	 * Converts a category, which is specified by a number, into a one-hot vector.
	 */
	public static float[] indexToOnehot(int categoryCount, int categoryIndex) {
		float[] result = new float[categoryCount];
		result[categoryIndex] = 1f;

		return result;
	}

	/**
	 * Converts a one-hot vector into an index.
	 */
	public static int onehotToIndex(float[] a) {
		int oneIdx = -1;

		for (int i = 0; i < a.length; i++) {
			if (a[i] == 1f) {
				if (oneIdx != -1) {
					throw new IllegalArgumentException(
							"not a one-hot vector. there is more than one element equal to 1."
									+ " second 1 encountered at index " + i);
				}

				oneIdx = i;

			} else if (a[i] != 0f) {
				throw new IllegalArgumentException(
						"not a one-hot vector. value other than 0 or 1 encountered at index " + i + ": " + a[i]);
			}
		}

		if (oneIdx == -1) {
			throw new IllegalArgumentException("not a one-hot vector. there was no 1.");
		}

		return oneIdx;
	}

	/**
	 * Returns the index of the maximum.
	 */
	public static int idxMax(float[] a) {
		int idxMax = -1;
		float max = Float.NEGATIVE_INFINITY;

		for (int i = 0; i < a.length; i++) {
			if (a[i] > max) {
				max = a[i];
				idxMax = i;
			}
		}

		return idxMax;
	}

	/**
	 * Element-wise negation for vectors. b[i] = -a[i].
	 */
	public static float[] neg(float[] a, float[] b) {
		MnLinalg.ensureSaneElementwiseOp_B_gtOrEq_A(a, b);

		for (int i = 0; i < a.length; i++) {
			b[i] = -a[i];
		}

		return b;
	}

	/**
	 * Element-wise reciprocal (1/x) for vectors. b[i] = 1.0f / a[i].
	 */
	public static float[] reciprocal(float[] a, float[] b) {
		MnLinalg.ensureSaneElementwiseOp_B_gtOrEq_A(a, b);

		for (int i = 0; i < a.length; i++) {
			b[i] = 1.0f / a[i];
		}

		return b;
	}

	/**
	 * Element-wise reciprocal (1/x) for matrices. b[i][j] = 1.0f / a[i][j].
	 */
	public static float[][] reciprocal(float[][] a, float[][] b) {
		MnLinalg.ensureSaneElementwiseOp_A_eq_B(a, b);

		for (int i = 0; i < a.length; i++) {
			reciprocal(a[i], b[i]);
		}

		return b;
	}

	/**
	 * Returns the norm2 (= the length) of the vector a.
	 */
	public static float norm2(float[] a) {
		float result = norm2Squared(a);

		result = (float) Math.sqrt(result);

		return result;
	}

	/**
	 * Returns the squared norm2 (the squared length) of the vector a.
	 */
	public static float norm2Squared(float[] a) {
		float result = 0;

		for (int i = 0; i < a.length; i++) {
			result += a[i] * a[i];
		}

		return result;
	}

	public static float min(float[] a) {
		float result = Float.POSITIVE_INFINITY;

		for (int j = 0; j < a.length; j++) {
			result = Math.min(result, a[j]);
		}

		return result;
	}

	public static float max(float[] a) {
		float result = Float.NEGATIVE_INFINITY;

		for (int j = 0; j < a.length; j++) {
			result = Math.max(result, a[j]);
		}

		return result;
	}

	public static float min(float[][] a) {
		float result = Float.POSITIVE_INFINITY;

		for (int i = 0; i < a.length; i++) {
			float[] aRow_i = a[i];

			for (int j = 0; j < aRow_i.length; j++) {
				result = Math.min(result, aRow_i[j]);
			}
		}

		return result;
	}

	public static float max(float[][] a) {
		float result = Float.NEGATIVE_INFINITY;

		for (int i = 0; i < a.length; i++) {
			float[] aRow_i = a[i];

			for (int j = 0; j < aRow_i.length; j++) {
				result = Math.max(result, aRow_i[j]);
			}
		}

		return result;
	}

	public static float maxAbs(float[] a) {
		float result = Float.NEGATIVE_INFINITY;

		for (int j = 0; j < a.length; j++) {
			result = Math.max(result, Math.abs(a[j]));
		}

		return result;
	}

	public static float maxAbs(float[][] a) {
		float result = Float.NEGATIVE_INFINITY;

		for (int i = 0; i < a.length; i++) {
			float[] aRow_i = a[i];

			for (int j = 0; j < aRow_i.length; j++) {
				result = Math.max(result, Math.abs(aRow_i[j]));
			}
		}

		return result;
	}

	public static final float sqrt(float f) {
		return (float) Math.sqrt(f);
	}

	public static float getSqSum(float[][] a) {
		float sqSum = 0;

		float tmp;

		for (int i = 0; i < a.length; i++) {
			float[] aRow_i = a[i];

			for (int j = 0; j < aRow_i.length; j++) {
				tmp = aRow_i[j];
				sqSum = tmp * tmp;
			}
		}

		return sqSum;
	}

	public static float getSqSum(float[] a) {
		float sqSum = 0;

		float tmp;

		for (int i = 0; i < a.length; i++) {
			tmp = a[i];
			sqSum += tmp * tmp;
		}

		return sqSum;
	}

	public static float mean(float[] x) {
		float result = 0;

		for (int i = 0; i < x.length; i++) {
			result += x[i];
		}

		result /= x.length;

		return result;
	}

	public static float variance(float[] x, float mean) {
		float result = 0;

		float tmp;
		for (int i = 0; i < x.length; i++) {
			tmp = x[i] - mean;
			result += tmp * tmp;
		}

		result /= x.length;

		return result;
	}

	public static float variance(float[] x) {
		float mean = mean(x);
		return variance(x, mean);
	}

	public static float stddev(float[] x, float mean) {
		return sqrt(variance(x, mean));
	}

	public static float stddev(float[] x) {
		float mean = mean(x);
		return stddev(x, mean);
	}

	public static float pow(float a, float b) {
		return (float) Math.pow(a, b);
	}

}
