package de.a0h.mininum.format;

import java.text.DecimalFormat;

public class ThreadLocalForDecimalFormat extends ThreadLocal<DecimalFormat> {

	protected DecimalFormat prototype;

	public ThreadLocalForDecimalFormat(DecimalFormat prototype) {
		this.prototype = prototype;
	}

	@Override
	public DecimalFormat initialValue() {
		return (DecimalFormat) prototype.clone();
	}
}