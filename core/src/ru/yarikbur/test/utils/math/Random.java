package ru.yarikbur.test.utils.math;

public class Random {
	/**
	 * Returns a random number ranging from minimum to maximum (inclusive)
	 * @param min - minimum integer
	 * @param max - maximum integer
	 * @return Integer
	 */
	public static int getRandomIntegerInRange(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
