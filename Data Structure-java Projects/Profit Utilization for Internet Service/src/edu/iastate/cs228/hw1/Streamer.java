package edu.iastate.cs228.hw1;

/**
 * @author neha 
 * The Streamer class represents a cell in the Town grid of the
 * Game of Towns simulation. It extends the TownCell class and
 * represents a cell where a Streamer resides.
 */
public class Streamer extends TownCell {

	/**
	 * Constructs a Streamer cell with the given parameters.
	 *
	 * @param p The Town in which the cell resides.
	 * @param r The row index of the cell in the Town grid.
	 * @param c The column index of the cell in the Town grid.
	 */
	public Streamer(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Returns the state of the cell, which is always State.STREAMER for a Streamer
	 * cell.
	 *
	 * @return The state of the Streamer cell, which is State.STREAMER.
	 */
	@Override
	public State who() {
		return State.STREAMER;
	}

	/**
	 * Computes and returns the next state of the Streamer cell in the next time
	 * step.
	 *
	 * @param tNew The Town object representing the next time step.
	 * @return A new TownCell object representing the state of the Streamer cell in
	 *         the next time step.
	 */
	@Override
	public TownCell next(Town tNew) {

		int[] nCensus = new int[5];

		census(nCensus);

		if (nCensus[OUTAGE] + nCensus[EMPTY] <= 1) {
			return new Reseller(tNew, row, col);
		}

		else if (nCensus[RESELLER] > 0) {
			return new Outage(tNew, row, col);
		}

		else if (nCensus[OUTAGE] > 0) {
			return new Empty(tNew, row, col);
		}

		else if (nCensus[STREAMER] > 1) {
			return new Outage(tNew, row, col);
		}

		return new Streamer(tNew, row, col);
	}
}
