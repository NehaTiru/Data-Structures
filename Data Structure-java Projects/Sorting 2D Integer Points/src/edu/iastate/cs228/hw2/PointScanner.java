package edu.iastate.cs228.hw2;

import java.io.File;

/**
 * 
 * @author neha
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	protected String filename ;
	
	protected String outputFileName ;
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if (pts == null || pts.length == 0) {
			throw  new IllegalArgumentException() ;
		
		}
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			points[i] = pts[i];
		}
		sortingAlgorithm = algo;
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		
		File file = new File(inputFileName);
		this.sortingAlgorithm = algo;
		
		Scanner intScanner = new Scanner(file);
		Scanner scanPoint = new Scanner(file); 
		
		int numb = 0;
		
		while (intScanner.hasNextInt()) {
			intScanner.nextInt();
			numb++;
		}
		if (numb % 2 != 0) {
			intScanner.close();
			scanPoint.close();
			throw new InputMismatchException();
		}
		
		points = new Point[numb / 2];
		for (int i = 0; i < numb/2 ; i++) {
			points[i] = new Point(scanPoint.nextInt(), scanPoint.nextInt());
		}
		intScanner.close();
		scanPoint.close();
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		 
		AbstractSorter aSorter = null; 
		
		
		if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		} else if (sortingAlgorithm == Algorithm.QuickSort) {
			aSorter = new QuickSorter(points);
		} else if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		}
		
		long xStarting = System.nanoTime();
		aSorter.setComparator(0);
		aSorter.sort();
		int x = aSorter.getMedian().getX();
		long xTime = System.nanoTime() - xStarting;
		
		long yStarting = System.nanoTime();
		aSorter.setComparator(1);
		aSorter.sort();
		int y = aSorter.getMedian().getY();
		long yTime = System.nanoTime() - yStarting;
		
		medianCoordinatePoint = new Point(x, y);
		scanTime = xTime + yTime ;
		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
	 
		if(sortingAlgorithm == Algorithm.InsertionSort || sortingAlgorithm == Algorithm.SelectionSort) {
			return sortingAlgorithm + "  " + points.length + " " + scanTime;
		}
		
		else {
			return sortingAlgorithm + "      " + points.length + " " + scanTime;
			
		}
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		return "MCP: (" + medianCoordinatePoint.getX() +", "+medianCoordinatePoint.getY()+ ")";
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		 
		File file = new File(outputFileName);
		PrintWriter p = new PrintWriter(file);
		p.write(this.toString());
		p.close();
	}	

	

		
}
