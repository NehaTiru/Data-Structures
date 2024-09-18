
package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author neha
 * This class represents a set of unit tests for the ISPBusiness class.
 * It uses JUnit 5 for testing.
 */

public class ISPBusinessTest {

	Town t = new Town(4, 4);

	/**
	 * This method is a JUnit 5 test that checks the ISPBusiness class's behavior.
	 * It performs the following steps: 1. Initializes the Town t with random
	 * occupancy values. 2. Calls the ISPBusiness.getProfit() method on the Town t.
	 * 3. Asserts that the result of getProfit() is equal to 1.
	 */
	@Test
	void test() {
		t.randomInit(10);
		assertEquals(ISPBusiness.getProfit(t), 1);
	}
}
