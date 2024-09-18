
package edu.iastate.cs228.hw1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author neha
 * The CasualTest class is responsible for testing the behavior of the Casual class.
 * It includes a JUnit test case that checks if the `who` method of the Casual class returns the correct state, which is `State.CASUAL`.
 */

public class CasualTest {
    private Town town;
    private Casual casual;

    @BeforeEach
    void setUp() {
        
        town = new Town(4, 4);
        casual = new Casual(town, 1, 2);
    }

    /**
     * This JUnit test case checks if the `who` method of the Casual class returns
     * the expected state.
     */
    @Test
    void testWhoMethodReturnsCasualState() {
        // Verify that the `who` method of the Casual object returns State.CASUAL.
        assertEquals(casual.who(), State.CASUAL);
    }
}

