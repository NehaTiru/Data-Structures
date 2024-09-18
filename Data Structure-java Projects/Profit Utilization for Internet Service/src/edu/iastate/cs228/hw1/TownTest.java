package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author neha 
 * The TownTest class is responsible for testing the Town class. It
 * contains test cases to verify the functionality of the Town class.
 */
public class TownTest {

	Town t = new Town(4, 4);

	/**
	 * Test case to check the correctness of the getLength() method. It verifies
	 * that the length of the town matches the expected value.
	 */
	@Test
	void test() {
		assertEquals(t.getLength(), 4);
	}
}
