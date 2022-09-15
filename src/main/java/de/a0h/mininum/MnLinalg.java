package de.a0h.mininum;

import java.util.Arrays;

public class MnLinalg {

//	public static final float machEps = floatMachEps();
//
//	private static float floatMachEps() {
//		float eps = 1f;
//
//		while (1f + 0.5f * eps != 1f) {
//			eps *= 0.5f;
//		}
//
//		return eps;
//	}
//	public static final double machEps_d = doubleMachEps();
//
//	private static double doubleMachEps() {
//		double eps = 1d;
//
//		while (1d + 0.5d * eps != 1d) {
//			eps *= 0.5d;
//		}
//
//		return eps;
//	}

	/**
	 * Outer product of two vectors u and v, creating a matrix a. a = u ⊗ v, a[i][j]
	 * = u[i] * v[j].
	 */
	public static void outerProduct(float[] u, float[] v, float[][] a) {
		outerProduct(u, u.length, v, v.length, a);
	}

	/**
	 * Outer product of two vectors u and v, creating a matrix a. a = u ⊗ v, a[i][j]
	 * = u[i] * v[j]. u and v are treated as if they had only the first m and n
	 * elements and the results are assigned to only the area (m x n) upper left
	 * area of a.
	 */
	public static void outerProduct( //
			float[] u, int m, float[] v, int n, float[][] a //
	) {
		if (u == null) {
			throw new IllegalArgumentException("input vector u may not be null");
		}
		if (v == null) {
			throw new IllegalArgumentException("input vector v may not be null");
		}
		if (a == null) {
			throw new IllegalArgumentException("ouput matrix a may not be null");
		}

		if (u.length < m) {
			throw new IllegalArgumentException(
					"array holding input vector u[" + u.length + "] must be at least of size m = " + m);
		}
		if (v.length < n) {
			throw new IllegalArgumentException(
					"array holding input vector v[" + v.length + "] must be at least of size n = " + n);
		}

		// a is aM x aN
		int aM = a.length;
		if (aM < m) {
			String aNStr = (aM > 0) ? Integer.toString(a[0].length) : "?";

			throw new IllegalArgumentException("" + //
					"u[" + m + "] ⊗ v[" + n + "] " + //
					"doesn't fit the matrix a[" + aM + "][" + aNStr + "]" + //
					"" //
			);
		}

		if (m == 0) {
			return;
		}

		int aN = a[0].length;
		if (aN < n) {
			throw new IllegalArgumentException("" + //
					"u[" + m + "] ⊗ v[" + n + "] " + //
					"doesn't fit the matrix a[" + aM + "][" + aN + "]" + //
					"" //
			);
		}

		if (m == 2 && n == 4) {
			a[0][0] = u[0] * v[0];
			a[0][1] = u[0] * v[1];
			a[0][2] = u[0] * v[2];
			a[0][3] = u[0] * v[3];
			a[1][0] = u[1] * v[0];
			a[1][1] = u[1] * v[1];
			a[1][2] = u[1] * v[2];
			a[1][3] = u[1] * v[3];
			return;
		}

		for (int i = 0; i < m; i++) {
			float tmp = u[i];
			float[] aRow_i = a[i];

			for (int j = 0; j < n; j++) {
				aRow_i[j] = tmp * v[j];
			}
		}
	}

	/**
	 * Multiplies row vector by a matrix and stores the result in vector y, i.e. y =
	 * x*A. Returned is y.
	 */
	public static float[] mulVecMat(float[] x, float[][] a, float[] y) {
		return mulVecMat(x, x.length, a, y);
	}

	/**
	 * Multiplies row vector by a matrix and stores the result in vector y, i.e. y =
	 * x*A. Returned is y.
	 */
	public static float[] mulVecMat(float[] x, int m, float[][] a, float[] y) {
		if (a == null) {
			throw new IllegalArgumentException("matrix a may not be null");
		}
		if (x == null) {
			throw new IllegalArgumentException("input vector x may not be null");
		}
		if (y == null) {
			throw new IllegalArgumentException("output vector y may not be null");
		}
		if (x == y) {
			throw new IllegalArgumentException("vectors x and y point to the same memory, but that's not allowed");
		}

		if (x.length < m) {
			throw new IllegalArgumentException("input vector x[" + x.length + "] must be at least of size m = " + m);
		}

		// a is m x n
		int aM = a.length;
		if (aM != m) {
			throw new IllegalArgumentException(
					"input vector x has size " + m + ", thus it doesn't fit the matrix a, which has " + aM + " rows");
		}

		if (aM == 0) {
			return y;
		}

		int aN = a[0].length;
		if (y.length != aN) {
			throw new IllegalArgumentException("output vector y has size " + y.length
					+ ", thus it doesn't fit the matrix a, which has " + aN + " columns");
		}

		if (aN == 0) {
			return y;
		}

		float[] aRow = a[0];
		float tmp = x[0];
		for (int j = 0; j < aN; j++) {
			y[j] = tmp * aRow[j];
		}

		for (int i = 1; i < aM; i++) {
			aRow = a[i];
			tmp = x[i];
			for (int j = 0; j < aN; j++) {
				y[j] += tmp * aRow[j];
			}
		}

		return y;
	}

	/**
	 * Multiplies matrix a by vector x and stores the result in vector y, i.e. y =
	 * A*x. Returned is y.
	 */
	public static float[] mulMatVec(float[][] a, float[] x, float[] y) {
		if (a == null) {
			throw new IllegalArgumentException("matrix a may not be null");
		}
		if (x == null) {
			throw new IllegalArgumentException("input vector x may not be null");
		}
		if (y == null) {
			throw new IllegalArgumentException("output vector y may not be null");
		}
		if (x == y) {
			throw new IllegalArgumentException("vectors x and y point to the same memory, but that's not allowed");
		}

		// a is m x n
		int m = a.length;
		if (y.length != m) {
			throw new IllegalArgumentException("result vector y has size " + y.length
					+ ", thus it doesn't fit the matrix a, which has " + m + " rows");
		}

		if (m == 0) {
			return y;
		}

		int n = a[0].length;
		if (x.length != n) {
			throw new IllegalArgumentException("input vector x has size " + x.length
					+ ", thus it doesn't fit the matrix a, which has " + n + " columns");
		}

		if (m == 2 && n == 4) {
			// some compilers love this for optimizations
			y[0] = a[0][0] * x[0] + a[0][1] * x[1] + a[0][2] * x[2] + a[0][3] * x[3];
			y[1] = a[1][0] * x[0] + a[1][1] * x[1] + a[1][2] * x[2] + a[1][3] * x[3];

			return y;
		}

//		for (int i = 0; i < m; i++) {
//			float[] row = a[i];
//			
//			float tmp = row[0] * x[0];
//			for (int j = 1; j < n; j++) {
//				tmp += row[j] * x[j];
//			}
//			y[i] = tmp;
//		}

		for (int i = 0; i < m; i++) {
			float[] row = a[i];

			float tmp = 0.0f;

			for (int j = 0; j < n; j++) {
				tmp += row[j] * x[j];
			}

			y[i] = tmp;
		}

		return y;
	}

	/**
	 * Element-wise add for vectors, results stored in c, i.e. c[i] = a[i] + b[i].
	 */
	public static void add(float[] a, float[] b, float[] c) {
		ensureSaneElementwiseOp_A_eq_B_and_C_gtOrEq_A(a, b, c);

		for (int i = 0; i < a.length; i++) {
			c[i] = a[i] + b[i];
		}
	}

	/**
	 * Element-wise add for vectors, a gets overwritten with the corresponding
	 * results, i.e. a[i] += b[i].
	 */
	public static void add(float[] a, float[] b) {
		ensureSaneElementwiseOp_A_eq_B(a, b);

		for (int i = 0; i < a.length; i++) {
			a[i] += b[i];
		}
	}

	/**
	 * Element-wise multiplication for vectors, results stored in c, i.e. c[i] =
	 * a[i] * b[i].
	 */
	public static void mul(float[] a, float[] b, float[] c) {
		ensureSaneElementwiseOp_A_eq_B_and_C_gtOrEq_A(a, b, c);

		for (int i = 0; i < a.length; i++) {
			c[i] = a[i] * b[i];
		}
	}

	/**
	 * Element-wise multiplication for vectors, a gets overwritten with the results,
	 * i.e. a[i] *= b[i].
	 */
	public static void mul(float[] a, float[] b) {
		ensureSaneElementwiseOp_A_eq_B(a, b);

		mul(a, a.length, b);
	}

	/**
	 * Element-wise multiplication for vectors, a gets overwritten with the results,
	 * i.e. a[i] *= b[i]. a and b are treated as a vector of length len. The
	 * elements at indices >= len remain unused and untouched.
	 */
	public static void mul(float[] a, int len, float[] b) {
		if (a.length < len) {
			throw new IllegalArgumentException(
					"array holding input/output vector a[" + a.length + "] must be at least of size len = " + len);
		}

		if (b.length < len) {
			throw new IllegalArgumentException(
					"array holding input vector b[" + b.length + "] must be at least of size len = " + len);
		}

		for (int i = 0; i < len; i++) {
			a[i] *= b[i];
		}
	}

	/**
	 * Element-wise multiplication for matrices, results stored in c, i.e. c[i][j] =
	 * a[i][j] * b[i][j].
	 */
	public static float[][] mulElwise(float[][] a, float[][] b, float[][] c) {
		ensureSaneElementwiseOp(a, b, c);

		for (int i = 0; i < a.length; i++) {
			mul(a[i], b[i], c[i]);
		}

		return c;
	}

	/**
	 * Element-wise subtraction for vectors, results stored in c, i.e. c[i] = a[i] -
	 * b[i].
	 */
	public static float[] sub(float[] a, float[] b, float[] c) {
		ensureSaneElementwiseOp_A_eq_B_and_C_gtOrEq_A(a, b, c);

		int n = a.length;
		for (int i = 0; i < n; i++) {
			c[i] = a[i] - b[i];
		}

		return c;
	}

	/**
	 * Element-wise subtraction for vectors, a gets overwritten with results, i.e.
	 * a[i] -= b[i].
	 */
	public static void sub(float[] a, float[] b) {
		ensureSaneElementwiseOp_A_eq_B(a, b);

		int len = a.length;
		for (int i = 0; i < len; i++) {
			a[i] -= b[i];
		}
	}

	/**
	 * Element-wise subtraction vector -= scalar. a[i] -= b.
	 */
	public static float[] sub(float[] a, float b) {
		for (int i = 0; i < a.length; i++) {
			a[i] -= b;
		}

		return a;
	}

	/**
	 * Element-wise subtraction for matrices, a gets overwritten with results, i.e.
	 * a[i][j] -= b[i][j].
	 */
	public static void sub(float[][] a, float[][] b) {
		ensureSaneElementwiseOp_A_eq_B(a, b);

		int len = a.length;
		for (int i = 0; i < len; i++) {
			sub(a[i], b[i]);
		}
	}

	/**
	 * Element-wise subtraction scalar - vector. c[i] = a - b[i].
	 */
	public static void sub(float a, float[] b, float[] c) {
		if (c.length < b.length) {
			throw new IllegalArgumentException(
					"c has size " + c.length + ", but it must be at least the size of b, which is " + b.length);
		}

		int n = b.length;
		for (int i = 0; i < n; i++) {
			c[i] = a - b[i];
		}
	}

	/**
	 * Element-wise addition vector + scalar. c[i] = a[i] + b.
	 */
	public static float[] add(float[] a, float b, float[] c) {
		ensureSaneElementwiseOp_C_gtOrEq_A(a, c);

		int n = a.length;
		for (int i = 0; i < n; i++) {
			c[i] = a[i] + b;
		}

		return c;
	}

	/**
	 * Element-wise addition vector += scalar. a[i] += b.
	 */
	public static float[] add(float[] a, float b) {
		for (int i = 0; i < a.length; i++) {
			a[i] += b;
		}

		return a;
	}

	/**
	 * Element-wise division for vectors, results stored in c, i.e. c[i] = a[i] /
	 * b[i].
	 */
	public static float[] div(float[] a, float[] b, float[] c) {
		ensureSaneElementwiseOp_A_eq_B_and_C_gtOrEq_A(a, b, c);

		for (int i = 0; i < a.length; i++) {
			c[i] = a[i] / b[i];
		}

		return c;
	}

	/**
	 * Element-wise division for vectors, a gets overwritten with results, i.e. a[i]
	 * /= b[i].
	 */
	public static float[] div(float[] a, float[] b) {
		ensureSaneElementwiseOp_A_eq_B(a, b);

		for (int i = 0; i < a.length; i++) {
			a[i] /= b[i];
		}

		return a;
	}

	/**
	 * Element-wise division for matrices, results stored in c, i.e. c[i][j] =
	 * a[i][j] / b[i][j].
	 */
	public static float[][] div(float[][] a, float[][] b, float[][] c) {
		ensureSaneElementwiseOp(a, b, c);

		for (int i = 0; i < a.length; i++) {
			div(a[i], b[i], c[i]);
		}

		return c;
	}

	public static void ensureSaneElementwiseOp_A_eq_B(float[] a, String aName, float[] b, String bName) {
		if (b.length != a.length) {
			throw new IllegalArgumentException(bName + " has size " + b.length + ", " + //
					"but it must match the size of " + aName + ", which is " + a.length);
		}
	}

	public static void ensureSaneElementwiseOp_A_eq_B(float[] a, float[] b) {
		ensureSaneElementwiseOp_A_eq_B(a, "a", b, "b");
	}

	public static void ensureSaneElementwiseOp_B_gtOrEq_A(float[] a, float[] b) {
		if (b.length < a.length) {
			throw new IllegalArgumentException(
					"b has size " + b.length + ", but it must be at least the size of a, which is " + a.length);
		}
	}

	public static void ensureSaneElementwiseOp_C_gtOrEq_A(float[] a, float[] c) {
		if (c.length < a.length) {
			throw new IllegalArgumentException(
					"c has size " + c.length + ", but it must be at least the size of a, which is " + a.length);
		}
	}

	public static void ensureSaneElementwiseOp_A_eq_B_and_C_gtOrEq_A(float[] a, float[] b, float[] c) {
		if (b.length != a.length) {
			throw new IllegalArgumentException(
					"b has size " + b.length + ", but it must match a, which has size " + a.length);
		}

		ensureSaneElementwiseOp_C_gtOrEq_A(a, c);
	}

	public static void ensureSaneElementwiseOp_A_eq_B(float[][] a, float[][] b) {
		if (b.length != a.length) {
			throw new IllegalArgumentException("b.length = " + b.length + ", but it must match a.length = " + a.length);
		}
	}

	public static void ensureSaneElementwiseOp(float[][] a, float[][] b, float[][] c) {
		if (b.length != a.length) {
			throw new IllegalArgumentException("b.length = " + b.length + ", but it must match a.length = " + a.length);
		}

		if (c.length != a.length) {
			throw new IllegalArgumentException("c.length = " + c.length + ", but it must match a.length = " + a.length);
		}
	}

	/**
	 * Multiply matrix a with scalar s, store in b.
	 */
	public static void mul(float[][] a, float s, float[][] b) {
		for (int i = 0; i < a.length; i++) {
			mul(a[i], s, b[i]);
		}
	}

	/**
	 * Multiply matrix a with scalar s, overwrite a with the results.
	 */
	public static void mul(float[][] a, float s) {
		for (int i = 0; i < a.length; i++) {
			mul(a[i], s);
		}
	}

	/**
	 * Multiply vector a with scalar s, store in b.
	 */
	public static void mul(float[] a, float s, float[] b) {
		ensureSaneElementwiseOp_B_gtOrEq_A(a, b);

		for (int j = 0; j < a.length; j++) {
			b[j] = a[j] * s;
		}
	}

	/**
	 * Multiply vector a up to including element (len - 1) with scalar s, store in
	 * b.
	 */
	public static void mul(float[] a, int len, float s, float[] b) {
		for (int j = 0; j < len; j++) {
			b[j] = a[j] * s;
		}
	}

	/**
	 * Multiply vector a with scalar s, overwrite a with the results.
	 */
	public static void mul(float[] a, float s) {
		mul(a, a.length, s);
	}

	/**
	 * Multiply vector a up to including element (len - 1) with scalar s, overwrite
	 * a with the results.
	 */
	public static void mul(float[] a, int len, float s) {
		for (int j = 0; j < len; j++) {
			a[j] *= s;
		}
	}

	/**
	 * Multiplies matrix a with scalar s and then adds it to matrix b, storing the
	 * result in b. The matrix a is not modified. I.e. b[i][j] += s * a[i][j].
	 */
	public static void mulAdd(float[][] a, float s, float[][] b) {
		ensureSaneElementwiseOp_A_eq_B(a, b);

		for (int i = 0; i < a.length; i++) {
			mulAdd(a[i], s, b[i]);
		}
	}

	/**
	 * Multiplies vector a with scalar s and then adds it to vector b, storing the
	 * result in b. The vector a is not modified. I.e. b[i] += s * a[i].
	 */
	public static void mulAdd(float[] a, float s, float[] b) {
		ensureSaneElementwiseOp_B_gtOrEq_A(a, b);

		for (int j = 0; j < a.length; j++) {
			b[j] += s * a[j];
		}
	}

	/**
	 * Element-wise add for matrices, results stored in c, i.e. c[i][j] = a[i][j] +
	 * b[i][j].
	 */
	public static void add(float[][] a, float[][] b, float[][] c) {
		ensureSaneElementwiseOp(a, b, c);

		for (int i = 0; i < a.length; i++) {
			add(a[i], b[i], c[i]);
		}
	}

	/**
	 * Element-wise add for matrices, a gets overwritten with the results, i.e.
	 * a[i][j] += b[i][j].
	 */
	public static void add(float[][] a, float[][] b) {
		ensureSaneElementwiseOp_A_eq_B(a, b);

		for (int i = 0; i < a.length; i++) {
			add(a[i], b[i]);
		}
	}

	public static void div(float[] a, float b, float[] c) {
		ensureSaneElementwiseOp_C_gtOrEq_A(a, c);

		for (int j = 0; j < a.length; j++) {
			c[j] = a[j] / b;
		}
	}

	public static void div(float[] a, float b) {
		div(a, a.length, b);
	}

	public static void div(float[] a, int len, float b) {
		for (int j = 0; j < len; j++) {
			a[j] /= b;
		}
	}

	public static void div(float[][] a, float b) {
		for (int i = 0; i < a.length; i++) {
			div(a[i], b);
		}
	}

	/**
	 * Copies the values of a to b. Both arrays must be of equal size.
	 */
	public static void assign(float[] a, float[] b) {
		ensureSaneElementwiseOp_A_eq_B(a, b);
		System.arraycopy(a, 0, b, 0, a.length);
	}

	/**
	 * Copies the values of vector a to vector b, up to including element len - 1.
	 */
	public static void assign(float[] a, int len, float[] b) {
		System.arraycopy(a, 0, b, 0, len);
	}

	/**
	 * Deep-copies the values of a to b. Both matrices must be of equal size.
	 */
	public static void assign(float[][] a, float[][] b) {
		ensureSaneElementwiseOp_A_eq_B(a, b);

		for (int i = 0; i < a.length; i++) {
			assign(a[i], b[i]);
		}
	}

	public static void assign(float[][] a, float b) {
		for (int i = 0; i < a.length; i++) {
			assign(a[i], b);
		}
	}

	public static void assign(float[] a, float b) {
		Arrays.fill(a, b);
	}

	public static void assign(float[] a, int len, float b) {
		Arrays.fill(a, 0, len, b);
	}

	public static boolean equals(float[][] a, float[][] b) {
		if (a.length != b.length) {
			return false;
		}

		for (int i = 0; i < a.length; i++) {
			if (!equals(a[i], b[i])) {
				return false;
			}
		}

		return true;
	}

	public static boolean equals(float[] a, float[] b) {
		return Arrays.equals(a, b);
	}
}
