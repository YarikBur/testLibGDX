package ru.yarikbur.test.utils.math;

public class Random {
	public int getRandomIntegerInRange(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}
}
