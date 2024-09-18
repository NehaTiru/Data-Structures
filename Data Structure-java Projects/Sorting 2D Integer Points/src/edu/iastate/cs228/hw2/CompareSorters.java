package edu.iastate.cs228.hw2;

/**
 *  
 * @author neha
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		
		int input = 0;
		int trials = 1; 
		Point[] points;
		
		PointScanner[] scanners = new PointScanner[4];
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println("");
		System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
	
		while (input != 3) {
			
			System.out.print("Trial " + trials + ": ");
			input = userInput.nextInt();
			
			if (input == 1) {
				
				System.out.print("Enter number of random points: ");
				int inputNumb = userInput.nextInt();
				points = generateRandomPoints(inputNumb, new Random());
				
				scanners[0] = new PointScanner(points, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(points, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(points, Algorithm.MergeSort);
				scanners[3] = new PointScanner(points, Algorithm.QuickSort);
				
				System.out.println("");
				System.out.println("algorithm   size  time (ns)");
				System.out.println("---------------------------");
				
				for (int i = 0; i < scanners.length; i++) {
					scanners[i].scan();
					System.out.println(scanners[i].stats());
				}
				
				System.out.println("-----------------------------");
				trials++;
				}
			
			else if (input == 2) {
				
				System.out.println("Points from a file");
				System.out.print("File name: ");
				String fileName = userInput.next();
				
				scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new PointScanner(fileName, Algorithm.QuickSort);
				
				System.out.println("");
				System.out.println("algorithm   size  time (ns)");
				System.out.println("---------------------------");
				
				for (int i = 0; i < scanners.length; i++) {
					scanners[i].scan();
					System.out.println(scanners[i].stats());
				}
				
				System.out.println("-----------------------------");
				trials++;
			}
			
			else if( input!=1 && input!=2 && input!=3 ) {
				System.out.println("Enter another input: ");
				input=userInput.nextInt();
			}
		}
		userInput.close();
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		 
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}
		
		Point[] pointsArr = new Point[numPts];
		
		for (int i = 0; i < pointsArr.length; i++) {
			int a = rand.nextInt(101) - 50;
			int b = rand.nextInt(101) - 50;
			pointsArr[i] = new Point(a, b);
		}
		
		return pointsArr;
	}
	
}
