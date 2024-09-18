package edu.iastate.cs228.hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw1.Casual;
import edu.iastate.cs228.hw1.Empty;
import edu.iastate.cs228.hw1.Outage;
import edu.iastate.cs228.hw1.Reseller;
import edu.iastate.cs228.hw1.State;
import edu.iastate.cs228.hw1.Town;
import edu.iastate.cs228.hw1.TownCell;

/**
 * @author neha
 * The TownCellTest class is a JUnit test class for testing various methods of TownCell subclasses.
 * It contains test cases to verify the behavior of TownCell's constructor and the census method.
 * This class specifically tests the behavior of subclasses such as Casual, Outage, Reseller, and Empty.
 */
class TownCellTest {

	Town town;
	
    /**
     * Test the constructor of the TownCell class.
     */
	@Test
	void testTownCell() {
		town = new Town(2, 2);

		town.grid[0][0] = new Casual(town, 0, 0);

		assertEquals(State.CASUAL, town.grid[0][0].who());
	}
	
    /**
     * Test the {@link TownCell#census(State[])} method to ensure it correctly counts neighboring cell states.
     */

	@Test
	void testCensus() {
		town = new Town(2, 2);

		town.grid[0][0] = new Outage(town, 0, 0);
		town.grid[0][1] = new Reseller(town, 0, 1);
		town.grid[1][0] = new Empty(town, 1, 0);
		town.grid[1][1] = new Empty(town, 1, 1);

		town.grid[0][0].census(TownCell.nCensus);

		assertEquals(2, TownCell.nCensus[1]);
		assertEquals(1, TownCell.nCensus[0]);
	}

}