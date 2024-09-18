package edu.iastate.cs228.hw1;


/**
 * @author neha
 * The Casual class represents a type of TownCell in a cellular automaton
 * simulation of a town's power grid. It extends the TownCell class and
 * implements specific behavior for Casual cells.
 */
public class Casual extends TownCell {

	/**
	 * Constructs a Casual cell with the specified parameters.
	 *
	 * @param p The Town object that contains this cell.
	 * @param r The row index of this cell within the Town grid.
	 * @param c The column index of this cell within the Town grid.
	 */
	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Returns the State type of this cell, which is State.CASUAL.
	 *
	 * @return The State representing a Casual cell.
	 */
	@Override
	public State who() {
		return State.CASUAL;
	}

	/**
	 * Determines the next state of this Casual cell based on certain rules and
	 * returns the corresponding TownCell.
	 *
	 * @param tNew The Town object that represents the next state of the town's
	 *             power grid.
	 * @return A new TownCell object representing the next state of this cell.
	 */
	@Override
	public TownCell next(Town tNew) {
		int[] nCensus = new int[5];

		
		census(nCensus);

		
		if (nCensus[OUTAGE] + nCensus[EMPTY] <= 1) {
			return new Reseller(tNew, row, col);
		} else if (nCensus[RESELLER] > 0) {
			return new Outage(tNew, row, col);
		} else if (nCensus[STREAMER] > 0 || nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		} else {
			return new Casual(tNew, row, col);
		}
	}
}
