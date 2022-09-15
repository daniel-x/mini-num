package de.a0h.mininum.lookuptables;

/**
 * Lookup table implementation for functions. To create new classes of this
 * type, you only need to override the method calculate().
 */
public abstract class FloatLookupTable {

	/**
	 * The data of the lookup table.
	 */
	protected float[] v;

	protected float xMin;
	protected float xMax;
	protected float xSamplesPerUnit;

	/**
	 * Creates a new, ready to use instance by using the method calculate() to fill
	 * the lookup table.
	 */
	public FloatLookupTable() {
		this(-25f, +25f, 0.02f);
	}

	/**
	 * This method returns the same fully initialized singleton.
	 */
	public FloatLookupTable(float xMin, float xMax, float xSampleInterval) {
		if (xMin >= xMax) {
			throw new IllegalArgumentException("xMin must be less than xMax; xMin=" + xMin + " xMax=" + xMax);
		}

		this.xMin = xMin;
		this.xMax = xMax;
		this.xSamplesPerUnit = 1.0f / xSampleInterval;

		double lenDbl = (xMax - (double) xMin) * xSamplesPerUnit;
		int len = (int) Math.ceil(lenDbl);
		len += 1; // n intervals means (n + 1) interval borders

		if (len == 0) {
			throw new IllegalArgumentException("something wrong with the parameters: xMin=" + //
					xMin + " xMax=" + xMax + " xSamplingInterval=" + xSampleInterval);
		}

		v = new float[len];

		float x = 0, y;

		for (int i = 0; i < len; i++) {
			x = xMin + xSampleInterval * i;

			y = calculate(x);

			v[i] = y;
		}

		this.xMax = x;
	}

	/**
	 * Returns f(x) for this function, fetched from the lookup table.
	 */
	public float value(float x) {
		if (x < xMin) {
			return extensionNegative(x);
		} else if (x > xMax) {
			return extensionPositive(x);
		}

		int idx = (int) ((x - xMin) * xSamplesPerUnit);

		return v[idx];
	}

	/**
	 * Approximates the function value when x < xMin. This implementation returns
	 * f(xMin). Subclasses can overwrite this method to supply better
	 * approximations, or call the exact but presumably slower calculate(x).
	 */
	public float extensionNegative(float x) {
		return v[0];
	}

	/**
	 * Approximates the function value when x > xMax. This implementation returns
	 * f(xMax). Subclasses can overwrite this method to supply better
	 * approximations, or call the exact but presumably slower calculate(x).
	 */
	public float extensionPositive(float x) {
		return v[v.length - 1];
	}

	/**
	 * This is the implementation of the function. This method calculates the value
	 * of the function for populating the lookup table. Overwrite this method in
	 * subclasses with the desired function to create a working lookup table. Lookup
	 * table initialization is then handled for you.
	 */
	public abstract float calculate(float x);
}
