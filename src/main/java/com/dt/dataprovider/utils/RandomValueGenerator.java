package com.dt.dataprovider.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomValueGenerator {

	public static final double MAX_VALUE = 99.9999;

	public static final double MIN_VALUE = 0.0001;

	public static final int PERCENTAGE_MAX_VALUE = 100;

	public static final int PERCENTAGE_MIN_VALUE = 0;

	public double buildRandomValue() {
		return MIN_VALUE + Math.random() * (MAX_VALUE - MIN_VALUE);
	}

	public int buildPercentage() {
		Random randomPercentage = new Random();
		return randomPercentage.nextInt((PERCENTAGE_MAX_VALUE - PERCENTAGE_MIN_VALUE) + 1) + PERCENTAGE_MIN_VALUE;
	}

}
