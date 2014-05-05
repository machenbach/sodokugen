package org.mike.puzzle;

import java.util.HashSet;
import java.util.PriorityQueue;

import org.mike.util.Range;

public class Puzzle {
	/*
	 * The basis array is a basic puzzle, with all squares correct.  We will 
	 * create a (correct) random mapping of the basis space
	 */
	
	// this is the puzzle
	Integer[][] puzzle;
	
	public Puzzle() {
		/*
		 * Build a puzzle. This proceeds as follows:
		 * There are two main routines: fillbox and fixbox
		 * fillbox fills the box with random numbers.  We will use this on the diagonal boxes
		 * fixbox computes the box based on all the other rows and columns
		 * 
		 */
		while (true) {
			puzzle = new Integer[9][9];
			try {
				fillbox(0,0);
				
				fixbox(1,0);
				fixbox(0,1);
				fixbox(1,1);
				
				fixbox(2,0);
				fixbox(2,1);
				fixbox(0,2);
				fixbox(1,2);
				fixbox(2,2);
				
				break;
			}
			catch (NoSolutionException e) {
			}
		}
	}

	void fillbox(int rb, int cb) {
		Integer[] boxnums = Range.shuffleRange(1,10).toArray(new Integer[0]);
		int cur = 0;
		for (int r : new Range(3)) {
			for (int c : new Range(3)) {
				puzzle[rc(rb,r)][rc(cb,c)] = boxnums[cur++];
			}
		}
				
	}
	
	public class PossibleElem extends HashSet<Integer> implements Comparable<PossibleElem> {


		private static final long serialVersionUID = 2450400441412942120L;
		
		int row;
		int column;
		
		public PossibleElem(int row, int column)
		{
			super(Range.rangeList(1, 10));
			this.row = row;
			this.column = column;
		}

		@Override
		public int compareTo(PossibleElem o) {
			return Integer.compare(this.size(), o.size());
		}

		public int getRow() {
			return row;
		}

		public int getColumn() {
			return column;
		}
		
	}
	void fixbox (int rb, int cb) throws NoSolutionException {
		// create the set elements for the possible values
		PossibleElem[][] pelems = new PossibleElem[3][3];
		PriorityQueue<PossibleElem> pqueue = new PriorityQueue<>();
		
		for (int r : new Range(3)) {
			for (int c : new Range(3)) {
				PossibleElem p = new PossibleElem(rc(rb,r), rc(cb,c));
				pelems[r][c] = p;
				pqueue.add(p);
			}
		}
		
		// find the possible values in each cell.  First, sort through the rows
		// in this loop pr, pc are box relative r and c are values in the puzzle
		for (int pr : new Range(3)) {
			int r = rc(rb, pr);
			for (int c : new Range(9)) {
				// for each r/c, check if the puzzle has been set
				if (puzzle[r][c] != null) {
					// if it has, for each column in this box on this row, we remove it as a possible value
					for (int pc : new Range(3)) {
						pelems[pr][pc].remove(puzzle[r][c]);
					}
				}
			}
		}
		
		// same thing, this time for each of the columns
		
		for (int pc : new Range(3)) {
			int c = rc(cb, pc);
			for (int r : new Range(9)) {
				// for each r/c, check if the puzzle has been set
				if (puzzle[r][c] != null) {
					// if it has, for each column in this box on this row, we remove it as a possible value
					for (int pr : new Range(3)) {
						pelems[pr][pc].remove(puzzle[r][c]);
					}
				}
			}
		}
		// First check:  make sure the union of the all the sets contains 9 elements
		HashSet<Integer> possibleUnion = new HashSet<>();
		for (PossibleElem e : pqueue) {
			possibleUnion.addAll(e);
		}
		if (possibleUnion.size() != 9) {
			throw new NoSolutionException("The box does not have all the numbers");
		}
		
		// second check:  See if any of the cells have empty sets
		for (PossibleElem e : pqueue) {
			if (e.isEmpty()) {
				throw new NoSolutionException("Empty Cell at " + e.getRow() + ", " + e.getColumn());
			}
		}
		
		// we now know what's possible.  Create the set of elements we still need
		HashSet<Integer> boxNeeds = new HashSet<Integer>(Range.rangeList(1, 10));
		
		// Run through the queue, smallest set first
		while (! pqueue.isEmpty()) {
			PossibleElem elem = pqueue.poll();
			// intersect with our needs
			elem.retainAll(boxNeeds);
			// if set is empty, there's now way to fix this puzzle
			if (elem.isEmpty()) {
				throw new NoSolutionException("Puzzle not possible");
			}
			
			// pick an element.  I guess we'll get an iterator, and choose the first one
			int val = elem.iterator().next();
			
			// set this in the puzzle, remove it from needed and the rest of the sets
			puzzle[elem.getRow()][elem.getColumn()] = val;
			boxNeeds.remove(val);
			for (PossibleElem e : pqueue) {
				e.remove(val);
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

	public Integer[][] toArray() {
		return puzzle;
	}

}
