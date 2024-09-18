
package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * @author neha
 * The OutageTest class is a JUnit test class for the Outage class. It contains
 * test cases to verify the functionality of the Outage class.
 */

public class OutageTest {

	private Town t;
	private Outage o;

	/**
	 * Sets up the test environment by creating a new Town object and an Outage
	 * object before each test method is executed.
	 */
	@BeforeEach
	void setUp() {
		t = new Town(4, 4);
		o = new Outage(t, 1, 2);
	}

	/**
	 * Tests the functionality of the `who()` method in the Outage class. It
	 * verifies that the `who()` method returns the correct State, which should be
	 * OUTAGE.
	 */
	@Test
	void testWhoMethod() {
		assertEquals(State.OUTAGE, o.who());
	}
}
