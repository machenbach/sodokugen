package org.mike.puzzle;

import org.mike.util.Range;

public class Puzzle {
	/*
	 * The basis array is a basic puzzle, with all squares correct.  We will 
	 * create a (correct) random mapping of the basis space
	 */
	
	// this is the puzzle
	Integer[][] puzzle = new Integer[9][9];
	
	public Puzzle() {
		randomFill();
	}

	void randomFill() {
		for (int rb : new Range(3)) {
			for (int cb : new Range(3)) {
				Integer[] boxnums = Range.shuffleRange(1,10).toArray(new Integer[0]);
				int cur = 0;
				for (int r : new Range(3)) {
					for (int c : new Range(3)) {
						puzzle[rc(rb,r)][rc(cb,c)] = boxnums[cur++];
					}
				}
				
			}
		}
	}
	
	/**
	 * Convert a row or column box + row or column box number to a row or column 
	 * @param rcb Row or Column Box
	 * @param rc Row or COlumn in that box
	 * @return row or column index
	 */
	int rc(int rcb, int rc) {
		return rcb * 3 + rc;
	}
	

	public Integer puzzleInt(int row, int column)  {
		return puzzle[row][column];
	}
	
	public String puzzleElement(int row, int column) {
		return Integer.toString(puzzleInt(row, column));
	}

	public int[][] toArray() {
		int[][] ret;
		ret = new int[9][9];
		for (int r : new Range(9)) {
			for (int c : new Range(9)) {
				ret[r][c] = puzzleInt(r, c);
			}
		}
		return ret;
	}

}
