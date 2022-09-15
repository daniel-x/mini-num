package de.a0h.mininum;

import java.lang.reflect.Array;
import java.util.Random;

public class MnArrays {

	public static void shuffle(boolean[] a, Random rnd) {
		boolean tmp;

		for (int i = 0; i < a.length - 1; i++) {
			int j = rnd.nextInt(a.length - i) + i;

			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

	public static void shuffle(byte[] a, Random rnd) {
		byte tmp;

		for (int i = 0; i < a.length - 1; i++) {
			int j = rnd.nextInt(a.length - i) + i;

			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

	public static void shuffle(short[] a, Random rnd) {
		short tmp;

		for (int i = 0; i < a.length - 1; i++) {
			int j = rnd.nextInt(a.length - i) + i;

			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

	public static void shuffle(char[] a, Random rnd) {
		char tmp;

		for (int i = 0; i < a.length - 1; i++) {
			int j = rnd.nextInt(a.length - i) + i;

			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

	public static void shuffle(int[] a, Random rnd) {
		int tmp;

		for (int i = 0; i < a.length - 1; i++) {
			int j = rnd.nextInt(a.length - i) + i;

			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

	public static void shuffle(long[] a, Random rnd) {
		long tmp;

		for (int i = 0; i < a.length - 1; i++) {
			int j = rnd.nextInt(a.length - i) + i;

			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

	public static void shuffle(float[] a, Random rnd) {
		float tmp;

		for (int i = 0; i < a.length - 1; i++) {
			int j = rnd.nextInt(a.length - i) + i;

			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

	public static void shuffle(double[] a, Random rnd) {
		double tmp;

		for (int i = 0; i < a.length - 1; i++) {
			int j = rnd.nextInt(a.length - i) + i;

			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

	public static <T> void shuffle(T[] a, Random rnd) {
		T tmp;

		for (int i = 0; i < a.length - 1; i++) {
			int j = rnd.nextInt(a.length - i) + i;

			tmp = a[i];
			a[i] = a[j];
			a[j] = tmp;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] shuffle(T[] original, Random rnd, T[] shuffled) {
		if (shuffled == null) {
			Class<?> componentType = original.getClass().getComponentType();
			shuffled = (T[]) Array.newInstance(componentType, original.length);
		}

		System.arraycopy(original, 0, shuffled, 0, original.length);

		shuffle(shuffled, rnd);

		return shuffled;
	}

	public static int[] generateShuffle(int length, Random rnd) {
		int[] shuffle = new int[length];

		shuffle(shuffle, rnd);

		return shuffle;
	}
}
