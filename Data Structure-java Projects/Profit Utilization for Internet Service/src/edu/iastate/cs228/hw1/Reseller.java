package edu.iastate.cs228.hw1;

/**
 * @author neha
 * 
 *The Reseller class represents a cell in the Town grid of the Game of
 *Towns simulation. It extends the TownCell class and represents a cell
 *where a Reseller resides.
 */
public class Reseller extends TownCell {

	/**
	 * Constructs a Reseller cell with the given parameters.
	 *
	 * @param p The Town in which the cell resides.
	 * @param r The row index of the cell in the Town grid.
	 * @param c The column index of the cell in the Town grid.
	 */
	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Returns the state of the cell, which is always State.RESELLER for a Reseller
	 * cell.
	 *
	 * @return The state of the Reseller cell, which is State.RESELLER.
	 */
	@Override
	public State who() {
		return State.RESELLER;
	}

	/**
	 * Computes and returns the next state of the Reseller cell in the next time
	 * step.
	 *
	 * @param tNew The Town object representing the next time step.
	 * @return A new TownCell object representing the state of the Reseller cell in
	 *         the next time step.
	 */
	@Override
	public TownCell next(Town tNew) {
		int[] nCensus = new int[5];
		census(nCensus);

		if (nCensus[CASUAL] <= 3 || nCensus[EMPTY] >= 3) {
			return new Empty(tNew, row, col);
		}

		else if (nCensus[CASUAL] >= 5) {
			return new Streamer(tNew, row, col);
		}

		else {
			return this;
		}
	}
}
