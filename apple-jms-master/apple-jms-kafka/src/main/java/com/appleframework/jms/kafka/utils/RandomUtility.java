package com.appleframework.jms.kafka.utils;

import java.util.Random;

public class RandomUtility {

	public static int genRandom(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}

}
