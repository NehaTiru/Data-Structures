package edu.iastate.cs228.hw1;

/**
 * @author neha
 * The Empty class represents an empty cell in a Town grid. It is a subclass of
 * the TownCell class. Empty cells do not have any occupants and can transition
 * to either a Reseller or Casual cell based on certain conditions.
 */
public class Empty extends TownCell {

	/**
	 * Constructs an Empty cell with the given parameters.
	 *
	 * @param p The Town object to which this cell belongs.
	 * @param r The row coordinate of this cell in the Town grid.
	 * @param c The column coordinate of this cell in the Town grid.
	 */
	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Returns the State of this cell, which is always State.EMPTY for an Empty
	 * cell.
	 *
	 * @return The State of this cell (State.EMPTY).
	 */
	@Override
	public State who() {
		return State.EMPTY;
	}

	/**
	 * Computes the next state of this Empty cell based on its neighboring cells and
	 * returns the corresponding TownCell object. Empty cells transition to either
	 * Reseller or Casual cells based on certain conditions: - If there is 1 or
	 * fewer OUTAGE and EMPTY neighbors, it transitions to a Reseller cell. -
	 * Otherwise, it transitions to a Casual cell.
	 *
	 * @param tNew The Town object for the next generation.
	 * @return A new TownCell object representing the next state of this cell.
	 */
	@Override
	public TownCell next(Town tNew) {
		int[] nCensus = new int[5];
		census(nCensus);

		// Check conditions for transitioning to Reseller or Casual

		if (nCensus[OUTAGE] + nCensus[EMPTY] <= 1) {
			return new Reseller(tNew, row, col);
		} else {
			return new Casual(tNew, row, col);
		}
	}
}
