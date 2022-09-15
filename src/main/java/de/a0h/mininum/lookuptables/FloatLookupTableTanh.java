package de.a0h.mininum.lookuptables;

public class FloatLookupTableTanh extends FloatLookupTable {

	@Override
	public float calculate(float x) {
		return (float) Math.tanh(x);
	}
}
