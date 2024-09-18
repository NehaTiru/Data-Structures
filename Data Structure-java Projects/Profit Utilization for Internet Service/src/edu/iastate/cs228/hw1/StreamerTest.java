package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author neha
 * The StreamerTest class is a JUnit test class for testing the Streamer class.
 * It contains a test case to verify the behavior of the Streamer class's method.
 */
public class StreamerTest {

	private Town t;
	private Streamer s;
	
    /**
     * Set up the test fixture by creating a Town and a Streamer object before each test.
     */
	@BeforeEach
	void setUp() {
		t = new Town(4, 4);
		s = new Streamer(t, 1, 2);
	}
	
    /**
     * Test the {@link Streamer#who()} method to ensure it returns the correct State, which should be {@link State#STREAMER}.
     */
	@Test
	void testWhoReturnsStreamer() {
		assertEquals(State.STREAMER, s.who());
	}

	
}
