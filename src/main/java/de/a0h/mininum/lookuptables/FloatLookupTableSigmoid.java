package de.a0h.mininum.lookuptables;

public class FloatLookupTableSigmoid extends FloatLookupTable {

	@Override
	public float calculate(float x) {
		return (float) (1.0D / (1.0D + Math.exp(-x)));
	}
}
