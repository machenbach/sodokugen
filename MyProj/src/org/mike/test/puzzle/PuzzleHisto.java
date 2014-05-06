package org.mike.test.puzzle;

import org.junit.Test;
import org.mike.puzzle.NoSolutionException;
import org.mike.puzzle.Puzzle;
import org.mike.util.Range;

public class PuzzleHisto {
	static int BUCKET_SIZE = 5;
	static int NUM_SAMPLES = 10000;

	String histoBar(int val, int max, String stars) {
		String s = stars.substring(0, (val * 100)/max);
		if (val != 0 && s.length() == 0) {
			s = "*";
		}
		return s;
	}
	
	
	void printHisto(int[] histo, int max) {
		// find max
		int maxVal = 0;
		int range = 100;
		
		for (int i : new Range(max/BUCKET_SIZE)) {
			if (histo[i] > maxVal) {
				maxVal = histo[i];
			}
		}
		
		String stars = new String(new char[range]).replace('\0', '*');

		for (int i : new Range(max/BUCKET_SIZE)) {
			System.out.printf("%4d: %s\n", i * BUCKET_SIZE,histoBar(histo[i], maxVal, stars));
		}
		
	}
	@Test
	public void test() {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		int total = 0;
		int errors = 0;

		// Size of histogram is max number of retries / bucket size
		int[] histo = new int[1000 / BUCKET_SIZE];

		try {
			for (int i : new Range(NUM_SAMPLES)) {
				Puzzle p = new Puzzle();
				if (max < p.getTries()) {
					max = p.getTries();
				}
				if (min > p.getTries()) {
					min = p.getTries();
				}
				histo[p.getTries()/BUCKET_SIZE]++;
				total += p.getTries();
			}
		} catch (NoSolutionException e) {
			errors++;
		}
		System.out.println("Errors: " + errors);
		System.out.println("Min: " + min);
		System.out.println("Max: " + max);
		System.out.println("Ave: " + total/NUM_SAMPLES);
		printHisto(histo, max);
		
	}

}
