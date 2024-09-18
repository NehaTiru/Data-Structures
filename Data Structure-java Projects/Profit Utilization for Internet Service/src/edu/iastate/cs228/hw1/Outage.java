package edu.iastate.cs228.hw1;

/**
 * @author neha
 *The Outage class represents an Outage state in the Town grid, which is a
 * subclass of TownCell. When an Outage cell is updated in the simulation, it
 * transitions to an Empty cell, indicating that the service outage has been
 * resolved.
 */
public class Outage extends TownCell {

	/**
	 * Constructs an Outage object with a given parent Town, row, and column
	 * coordinates.
	 *
	 * @param p The Town in which this Outage cell resides.
	 * @param r The row coordinate of the Outage cell.
	 * @param c The column coordinate of the Outage cell.
	 */
	public Outage(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Returns the current state of this Outage cell, which is State.OUTAGE.
	 *
	 * @return The State enumeration representing the Outage state.
	 */
	@Override
	public State who() {
		return State.OUTAGE;
	}

	/**
	 * Determines the next state of this Outage cell in the simulation. When
	 * updated, an Outage cell transitions to an Empty cell, indicating that the
	 * service outage has been resolved.
	 *
	 * @param tNew The Town representing the next state of the grid.
	 * @return A new Empty cell representing the resolved service outage.
	 */
	@Override
	public TownCell next(Town tNew) {
		return new Empty(tNew, row, col);
	}
}
