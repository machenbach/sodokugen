package org.mike.test.puzzle;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mike.puzzle.Puzzle;
import org.mike.util.Range;

public class PuzzleTests {

	Integer[][] puzzle;
	
	@Before
	public void setup() {
		Puzzle p = new Puzzle();
		puzzle = p.toArray();
		for (int r : new Range(9)) {
			for (int c : new Range(9)) {
				System.out.print(puzzle[r][c] == null ? "  " : puzzle[r][c] + " ");
			}
			System.out.println();
		}
	}
	
	@Test
	public void testColums() {
		for (int c : new Range(9)) {
			Set<Integer> nums = new HashSet<>(Range.rangeList(1,10));
			for (int r : new Range(9)) {
				nums.remove(puzzle[r][c]);
			}
			if (nums.size() != 0) {
				printDups(nums);
				fail("Columns had duplicates");
			}
		}
	}

	@Test
	public void testRows() {
		for (int r : new Range(9)) {
			Set<Integer> nums = new HashSet<>(Range.rangeList(1,10));
			for (int c : new Range(9)) {
				nums.remove(puzzle[r][c]);
			}
			if (nums.size() != 0) {
				printDups(nums);
				fail("Rows had duplicates");
			}
		}
	}

	@Test
	public void testBoxes() {
		for (int tr : new Range(3)) {
			for (int tc : new Range(3)) {
				Set<Integer> nums = new HashSet<>(Range.rangeList(1, 10));
				for (int r : new Range(3)) {
					for (int c : new Range(3)) {
						nums.remove(puzzle[tr * 3 + r][tc * 3 + c]);
					}
				}
				if (nums.size() != 0) {
					printDups(nums);
					fail("Boxes had duplicates");
				}
			}
		}
	}
	
	
	private void printDups(Set<Integer> nums) {
		for (int i : nums) {
			//System.out.print(" "+i);
		}
		//System.out.println();
	}

}
