package edu.iastate.cs228.hw4;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;



/**
 *The Main class is designed to decode a message using a binary tree pattern.
 * It reads a file specified by the user, processes its content to extract a pattern
 * and an encoded message, and then uses these to decode the message.
 * 
 * The decoding process involves constructing a binary tree based on the pattern,
 * and then traversing this tree according to the binary code to reveal the decoded message.
 * 
 * The class relies on the MsgTree class for tree construction and message decoding.
 *
 *@author neha
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Please enter filename to decode:");
		Scanner scnr = new Scanner(System.in);
		
		String fileName = scnr.nextLine();
		scnr.close();
		

		
		String info = new String(Files.readAllBytes(Paths.get(fileName))).trim();
		int pos = info.lastIndexOf('\n');
		String binaryCode = info.substring(pos).trim(); 
        String pattern = info.substring(0, pos); 
    
		String chrDict = new String();
		for (char c : pattern.toCharArray()) {
			if (c != '^') {
				chrDict+=c;
			}
		}
	
		
		System.out.println("character code\n-----------------------------");
		MsgTree root = new MsgTree(pattern);
		MsgTree.printCodes(root, chrDict);
		root.decode(root, binaryCode);
	}
}