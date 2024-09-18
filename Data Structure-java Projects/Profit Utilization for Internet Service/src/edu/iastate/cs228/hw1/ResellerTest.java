package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author neha
 * The ResellerTest class is a JUnit test class for testing the Reseller class.
 * It contains test cases to verify the behavior of the Reseller class's methods.
 */
public class ResellerTest {

	private Town t;
	private Reseller r;
	
    /**
     * Set up the test fixture by creating a Town and a Reseller object before each test.
     */

	@BeforeEach
	void setUp() {
		t = new Town(4, 4);
		r = new Reseller(t, 1, 2);
	}
	
    /**
     * Test the {@link Reseller#who()} method to ensure it returns the correct State.
     */
	@Test
	void testWho() {
		assertEquals(State.RESELLER, r.who());
	}
}
