package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * 
@author neha
 *
 */
public class MsgTree {
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;
	private static String binaryCode;
	

	/**
	 *Constructs a binary tree from a given encoding string. The string represents the structure of the tree
     * and the characters to be encoded.
     * 
	 * @param encodingString A string representing the structure and characters of the tree.
	 */
	public MsgTree(String encodingString) {
		if (encodingString == null || encodingString.length() < 2) {
			return;
		}
		Stack<MsgTree> stack = new Stack<>();
		int index = 0;
		this.payloadChar = encodingString.charAt(index++);
		stack.push(this);
		MsgTree cur = this;
		String prev = "in";
		while (index < encodingString.length()) {
			MsgTree node = new MsgTree(encodingString.charAt(index++));
			if (prev.equals("in")) {
				cur.left = node;
				if (node.payloadChar == '^') {
					cur = stack.push(node);
					prev = "in";
				} else {
					if (!stack.empty())
						cur = stack.pop();
					prev = "out";
				}
			} else { 
				cur.right = node;
				if (node.payloadChar == '^') {
					cur = stack.push(node);
					prev = "in";
				} else {
					if (!stack.empty())
						cur = stack.pop();
					prev = "out";
				}
			}
		}
	}

	/**
	 *Constructs a single tree node with the given character and null children.
	 * 
	 * @param payloadChar The character is stored in this node.
	 */
	public MsgTree(char payloadChar) {
		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;
	}

	/**
	 *method to print characters and their binary codes
	 * 
	 * @param root The root node of the binary tree.
	 * 
	 * @param code A string containing characters to be encoded.
	 */
	public static void printCodes(MsgTree root, String code) {
		System.out.println("character code\n-------------------------");
		for (char ch : code.toCharArray()) {
			findCode(root, ch, binaryCode = "");
			System.out.println("    " + (ch == '\n' ? "\\n" : ch + " ") + "    " + binaryCode);
		}
	}

	

	

	/**
	 * Obtains code and calls oneself repeatedly while setting the alphabet (Helper Method) 
	 * 
	 * 
	 * @param root  The root node of the binary tree.
	 * @param char1 The character for which the code is to be found.
	 * @param way   The current path in binary representation.
	 * @return True if the character is found and its code is set in binaryCode, false otherwise.
	 */
	private static boolean findCode(MsgTree root, char char1, String way) {
		if (root != null) {
			if (root.payloadChar == char1) {
				binaryCode = way;
				return true;
			}
			return findCode(root.left, char1, way + "0") || findCode(root.right, char1, way + "1");
		}
		return false;
	}

	/**
	 * Decodes a message encoded in binary form using the tree.
	 * 
	 * @param codes The root node of the binary tree used for decoding.
	 * @param msg   The binary-encoded message string.
	 */
	public void decode(MsgTree codes, String msg) {
		System.out.println("MESSAGE:");
		MsgTree cur = codes;
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < msg.length(); i++) {
			char char1 = msg.charAt(i);
			cur = (char1 == '0' ? cur.left : cur.right);
			if (cur.payloadChar != '^') {
				findCode(codes, cur.payloadChar, binaryCode = "");
				str.append(cur.payloadChar);
				cur = codes;
			}
		}
		System.out.println(str.toString());
		statistc(msg, str.toString());
	}

	/**
	 * Extra credit statistics. Generates and prints statistics about the encoding and decoding process, 
	 * including average bits per character,total characters, and space savings.
	 * 
	 * @param encode_Str The binary-encoded string.
	 * @param decode_Str The decoded string.
	 */
	private void statistc(String encode_Str, String decode_Str) {
		System.out.println("STATISTICS:");
		System.out.println(String.format("Avg bits/char:     \t%.1f", encode_Str.length() / (double) decode_Str.length()));
		System.out.println("Total Characters:     \t" + decode_Str.length());
		System.out.println(
				String.format("Space Saving:    \t%.1f%%", (1d - decode_Str.length() / (double) encode_Str.length()) * 100));
	}
}