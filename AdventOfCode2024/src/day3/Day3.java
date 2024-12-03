package day3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Day3 {

	public static void main(String[] args) throws IOException {

//		partOne();

		partTwo();
	}

	private static void partOne() throws IOException {
		String content = new Scanner(new File("src/day3/input.txt")).useDelimiter("\\Z").next();

		int total = 0;

		int index = 0;
		while (index < content.length() - 3) {
			index = content.indexOf("mul(", index);
			if (index == -1) {
				break;
			}
			int close = content.indexOf(")", index);
			if (close != -1) {
				String expr = content.substring(index + 4, close + 1);
				// index = close;
				StringTokenizer st = new StringTokenizer(expr, ",)", true);
				if (st.countTokens() == 4) {
					try {
						int one = Integer.parseInt(st.nextToken());
						if (st.nextToken().equals(",")) {
							int two = Integer.parseInt(st.nextToken());
							if (st.nextToken().equals(")")) {
								total += one * two;
							}
						}
					} catch (NumberFormatException nfe) {

					}
				}
			} else {
				break;
			}
			index += 4;
		}
		System.out.println("Total: " + total);
	}

	private static void partTwo() throws IOException {
		String content = new Scanner(new File("src/day3/input.txt")).useDelimiter("\\Z").next();

		int total = 0;

		int index = 0;
		boolean dont = false;
		while (index < content.length() - 3) {
			if (dont) {
				int doIndex = content.indexOf("do()", index);
				if(doIndex == -1) { //no more do, we're done
					break;
				}
				if (doIndex > index) { //skip to next do
					index = doIndex + 4;
					dont = false;
				}
			} else { // do
				int dontIndex = content.indexOf("don't()", index);
				if (dontIndex == -1) {
					dontIndex = content.length();
				}
				int mulIndex = content.indexOf("mul(", index);

				if (mulIndex < dontIndex) { // mul before dont
					if (mulIndex == -1) { //no more mul, we're done
						break;
					}
					int close = content.indexOf(")", mulIndex);
					if (close != -1) { 
						String expr = content.substring(mulIndex + 4, close + 1);
						StringTokenizer st = new StringTokenizer(expr, ",)", true);
						if (st.countTokens() == 4) {
							try {
								int one = Integer.parseInt(st.nextToken());
								if (st.nextToken().equals(",")) {
									int two = Integer.parseInt(st.nextToken());
									if (st.nextToken().equals(")")) {
										total += one * two; //valid command, add to total
									}
								}
							} catch (NumberFormatException nfe) {
									//not a valid command
							}
						}
					} else { //no more valid commands, we're done
						break;
					}
					index = mulIndex + 4;
				} else { // don't before mul, skip to the don't index, start looking for a do()
					dont = true;
					index = dontIndex + 6;
				}
			}
		}
		System.out.println("Total: " + total);
	}
}
