package org.mike.puzzle;

import org.mike.util.Range;

public class Puzzle {
	/*
	 * The basis array is a basic puzzle, with all squares correct.  We will 
	 * create a (correct) random mapping of the basis space
	 */
	public static int[][] basis;
	
	Integer[] digits = Range.shuffleRange(1, 10).toArray(new Integer[0]);
	Integer[][] colShuffle;
	Integer[][] rowShuffle;
	
	int[][] buildBasis() {
		Integer[] basisShuffle = Range.shuffleRange(9).toArray(new Integer[0]);
		int start;
		int[][] basis = new int[9][9];
		for (int r = 0; r < 9; r++) {
			start = r/3 + (r%3)*3;
			for (int c = 0; c < 9; c++) {
				basis[r][c] = basisShuffle[start % 9];
				start ++;
			}
		}
		return basis;
	}
	
	
	public Puzzle() {
		Integer[] a = new Integer[0];
		colShuffle = new Integer[3][];
		rowShuffle = new Integer[3][];
		Integer[] tlist = Range.shuffleRange(3).toArray(a);
		for (int t : new Range(3)) {
			colShuffle[tlist[t]] = Range.shuffleRange(t*3, t*3 + 3).toArray(a);
		}
		tlist = Range.shuffleRange(3).toArray(a);
		for (int t : new Range(3)) {
			rowShuffle[tlist[t]] = Range.shuffleRange(t*3, t*3 + 3).toArray(a);
		}
		basis = buildBasis();
	}
	
	int colmap(int col) {
		int t = col/3;
		int c = col % 3;
		return colShuffle[t][c];
	}
	
	int rowmap(int row) {
		int t = row/3;
		int r = row % 3;
		return rowShuffle[t][r];
	}

	public Integer puzzle(int row, int column)  {
		return digits[basis[rowmap(row)][colmap(column)]];
	}
	
	public String puzzleElement(int row, int column) {
		return Integer.toString(puzzle(row, column));
	}

	public int[][] toArray() {
		int[][] ret;
		ret = new int[9][9];
		for (int r : new Range(9)) {
			for (int c : new Range(9)) {
				ret[r][c] = puzzle(r, c);
			}
		}
		return ret;
	}

}
