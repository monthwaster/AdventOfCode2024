package day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Day1 {

	public static void main(String[] args) throws IOException {

		// partOne();

		partTwo();
	}

	private static void partOne() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day1/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		int answer = 0;
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);

			left.add(Integer.parseInt(st.nextToken()));
			right.add(Integer.parseInt(st.nextToken()));

		}
		Collections.sort(left);
		Collections.sort(right);
		for (int i = 0; i < left.size(); i++) {
			answer += Math.abs(left.get(i).intValue() - right.get(i).intValue());
		}

		System.out.println("Total:" + answer);

	}

	private static void partTwo() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day1/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		int answer = 0;
		ArrayList<Integer> left = new ArrayList<Integer>();
		ArrayList<Integer> right = new ArrayList<Integer>();
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line);

			left.add(Integer.parseInt(st.nextToken()));
			right.add(Integer.parseInt(st.nextToken()));

		}
		Collections.sort(left);
		Collections.sort(right);
		for (int i = 0; i < left.size(); i++) {
			answer += left.get(i).intValue() * count(left.get(i).intValue(), right);
		}

		System.out.println("Total:" + answer);

	}

	private static int count(int number, ArrayList<Integer> list) {
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			int cur = list.get(i).intValue();
			if (cur == number) {
				count++;
			} else if (cur > number) {
				return count;
			}
		}
		return count;
	}

}
