package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * @author neha
 * The ISPBusiness class simulates a business providing internet services in a town.
 * It allows the user to populate the town's grid either from a file or randomly with a seed.
 * The class calculates the profitability of the business over a year (12 billing cycles) based on the state of each cell in the town's grid.
 */

public class ISPBusiness {
	
    /**
     * Updates the state of the cells in the town's grid for the next time step.
     *
     * @param tOld The old town configuration.
     * @return A new town configuration after updating the cell states.
     */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		for (int x = 0; x < tOld.getWidth(); x++) {
			for (int y = 0; y < tOld.getLength(); y++) {
				tNew.grid[x][y] = tOld.grid[x][y].next(tNew);
			}
		}
		return tNew;
	}
	
    /**
     * Calculates and returns the profitability of the business based on the number of casual subscribers.
     *
     * @param town The town whose profitability needs to be calculated.
     * @return The profitability as a percentage.
     */
	public static int getProfit(Town town) {
		int casualCount = 0;
		for (int x = 0; x < town.getWidth(); x++) {
			for (int y = 0; y < town.getLength(); y++) {
				if (town.grid[x][y].who() == State.CASUAL) {
					casualCount++;
				}
			}
		}
		return casualCount;
	}
    /**
     * The main method that interacts with the user to populate the town's grid and calculate profitability.
     *
     * @param args Command-line arguments (not used in this application).
     */

	public static void main(String[] args) {
		int userInput;
		final int billingCycle = 12;

		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("How to populate grid (type 1 or 2): 1: from a file. 2: randomly with seed");
			userInput = scan.nextInt();
			Town t = null;

			if (userInput == 1) {
				String filePath = "";
				try {
					System.out.println("Please Enter The File Name or Path!");
					scan.nextLine();
					filePath = scan.nextLine();
					t = new Town(filePath);
				} catch (FileNotFoundException e) {
					System.out.println("Hey looks like you entered an invalid file path!" + e.toString());
				}
			}

			if (userInput == 2) {
				int seed;
				int row;
				int col;
				System.out.println("Enter Rows, Cols and the SEED! separated by a space");
				row = scan.nextInt();
				col = scan.nextInt();
				seed = scan.nextInt();
				t = new Town(row, col);
				t.randomInit(seed);
			}

			double profit = 0.0;

			for (int month = 0; month < billingCycle; month++) {
				profit += (getProfit(t) / ((double) t.getWidth() * (double) t.getLength())) * 100;
				t = updatePlain(t);
			}
			profit = profit / billingCycle;

			System.out.printf("%.2f%c", profit, '%');
		}
	}
}
