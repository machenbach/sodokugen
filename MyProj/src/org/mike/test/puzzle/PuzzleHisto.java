package org.mike.test.puzzle;

import org.junit.Test;
import org.mike.puzzle.NoSolutionException;
import org.mike.puzzle.Puzzle;
import org.mike.util.Range;

public class PuzzleHisto {

	@Test
	public void test() {
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		long total = 0;
		int num = 1000;
		int errors = 0;
		try {
			for (int i : new Range(num)) {
				Puzzle p = new Puzzle();
				if (max < p.getTries()) {
					max = p.getTries();
				}
				if (min > p.getTries()) {
					min = p.getTries();
				}
				total += p.getTries();
			}
		} catch (NoSolutionException e) {
			errors++;
		}
		System.out.println("Errors: " + errors);
		System.out.println("Min: " + min);
		System.out.println("Max: " + max);
		System.out.println("Ave: " + total/num);
	}

}
