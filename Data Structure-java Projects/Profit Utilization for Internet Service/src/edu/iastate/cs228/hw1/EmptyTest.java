
package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * @author neha
 * The EmptyTest class is a JUnit test class for the Empty class, which represents
 * an empty cell in a town grid. It tests the behavior of the Empty class by
 * checking if it correctly returns its state using the `who()` method.
 */

public class EmptyTest {

	
	private Town t;

	
	private Empty e;

	/**
	 * Sets up the test environment before each test method is executed.
	 */
	@BeforeEach
	void setUp() {
		
		t = new Town(4, 4);
		e = new Empty(t, 1, 2);
	}

	/**
	 * Test the `who()` method of the Empty class to ensure it correctly returns the
	 * state of the Empty object, which should be State.EMPTY.
	 */
	@Test
	void testWho() {
		
		assertEquals(State.EMPTY, e.who());
	}
}
