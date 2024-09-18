package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author neha
 * The `Town` class represents a grid-based town simulation. Each cell in the
 * grid can be one of the following types: Casual, Streamer, Reseller, Empty, or
 * Outage. The grid can be generated either randomly or based on a provided
 * input file.
 */
public class Town {

	private int length, width;
	public TownCell[][] grid;

	/**
	 * Constructor to be used when the user wants to generate a grid randomly, with
	 * the given seed. This constructor initializes the grid but does not populate
	 * each cell.
	 * 
	 * @param width  The width of the town grid.
	 * @param length The length of the town grid.
	 */
	public Town(int width, int length) {
		this.length = width;
		this.width = length;
		grid = new TownCell[width][length];
	}

	/**
	 * Constructor to be used when the user wants to populate the grid based on a
	 * file.
	 * 
	 * @param inputFileName The name of the input file containing grid data.
	 * @throws FileNotFoundException If the specified input file is not found.
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File file = new File(inputFileName);

		try {
			Scanner scan = new Scanner(file);
			this.width = scan.nextInt();
			this.length = scan.nextInt();
			grid = new TownCell[width][length];

			while (scan.hasNextLine()) {
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < length; y++) {
						switch (scan.next()) {
						case "C":
							grid[x][y] = new Casual(this, x, y);
							break;
						case "S":
							grid[x][y] = new Streamer(this, x, y);
							break;
						case "R":
							grid[x][y] = new Reseller(this, x, y);
							break;
						case "E":
							grid[x][y] = new Empty(this, x, y);
							break;
						case "O":
							grid[x][y] = new Outage(this, x, y);
							break;
						}
					}
				}
			}

			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Hey, it looks like you entered an invalid file path!" + e.toString());
		}
	}

	/**
	 * Returns the width of the grid.
	 * 
	 * @return The width of the grid.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the length of the grid.
	 * 
	 * @return The length of the grid.
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initializes the grid by randomly assigning cells with one of the following
	 * class objects: Casual, Empty, Outage, Reseller, or Streamer.
	 * 
	 * @param seed The seed for the random number generator.
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < length; y++) {
				int randVal = rand.nextInt(5);
				switch (randVal) {
				case TownCell.CASUAL:
					grid[x][y] = new Casual(this, x, y);
					break;
				case TownCell.STREAMER:
					grid[x][y] = new Streamer(this, x, y);
					break;
				case TownCell.RESELLER:
					grid[x][y] = new Reseller(this, x, y);
					break;
				case TownCell.EMPTY:
					grid[x][y] = new Empty(this, x, y);
					break;
				case TownCell.OUTAGE:
					grid[x][y] = new Outage(this, x, y);
					break;
				}
			}
		}
	}

	/**
	 * Outputs the town grid as a string. For each square, it outputs the first
	 * letter of the cell type, separated by either a single space or a tab. Each
	 * row is on a new line, with no extra line between the rows.
	 * 
	 * @return A string representation of the town grid.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < length; y++) {
				s.append(grid[x][y].who().toString().charAt(0)).append(" ");
			}
			s.append("\n");
		}

		return s.toString();
	}
}
