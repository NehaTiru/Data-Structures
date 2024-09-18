package edu.iastate.cs228.hw1;

/**
 * 
 * @author neha
 * The abstract base class for cells within a Town grid.
 * 
 * Each TownCell represents a cell in a grid, which can be of various types such
 * as RESELLER, EMPTY, CASUAL, OUTAGE, or STREAMER. It provides methods to
 * interact with neighboring cells, count the types of neighbors, and determine
 * the type of the cell in the next cycle.
 * 
 * Subclasses should implement the abstract methods `who()` and `next()` to
 * specify the behavior of different cell types.
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;

	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;

	public static final int NUM_CELL_TYPE = 5;

	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	/**
	 * Constructs a TownCell with a reference to the town grid, row, and column
	 * position.
	 * 
	 * @param p The town grid that contains this cell.
	 * @param r The row position of this cell.
	 * @param c The column position of this cell.
	 */
	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}

	/**
	 * Counts the number of different cell types in the neighborhood (8 neighboring
	 * cells). Populates the `nCensus` array with counts of each cell type.
	 * 
	 * @param nCensus An array to store the counts of each cell type in the
	 *                neighborhood.
	 */
	public void census(int nCensus[]) {
		
		for (int i = 0; i < NUM_CELL_TYPE; i++) {
			nCensus[i] = 0;
		}

		int width = plain.getWidth();
		int length = plain.getLength();

		
		for (int i = row - 1; i < row + 2; i++) {
			for (int j = col - 1; j < col + 2; j++) {
				
				if ((i != row || j != col) && i > -1 && i < length && j > -1 && j < width) {
					
					switch (plain.grid[i][j].who()) {
					case CASUAL:
						nCensus[CASUAL] += 1;
						break;
					case RESELLER:
						nCensus[RESELLER] += 1;
						break;
					case EMPTY:
						nCensus[EMPTY] += 1;
						break;
					case OUTAGE:
						nCensus[OUTAGE] += 1;
						break;
					case STREAMER:
						nCensus[STREAMER] += 1;
						break;
					}
				}
			}
		}
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return The state of the cell (e.g., RESELLER, EMPTY, CASUAL, OUTAGE, or
	 *         STREAMER).
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle based on the neighboring cells and
	 * the current cell type.
	 * 
	 * @param tNew The town grid of the next cycle.
	 * @return A new TownCell representing the cell type in the next cycle.
	 */
	public abstract TownCell next(Town tNew);
}
