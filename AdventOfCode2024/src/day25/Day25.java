package day25;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Day25 {

	public static void main(String[] args) throws IOException {
		part1();
		part2();
	}

	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day25/input.txt")));
//		#####
//		.####
//		.####
//		.####
//		.#.#.
//		.#...
//		.....
		ArrayList<int[]> locks = new ArrayList<int[]>();
		ArrayList<int[]> keys = new ArrayList<int[]>();

		String firstLine;
		HashMap<String, Boolean> gates = new HashMap<String, Boolean>();
		while ((firstLine = br.readLine()) != null) {
			int[] pins = new int[5];
			String line;
			for (int i = 0; i < 5; i++) {
				line = br.readLine();
				for (int j = 0; j < 5; j++) {
					pins[j] += line.charAt(j) == '#' ? 1 : 0;
				}
			}
			br.readLine(); //end of pattern
			br.readLine(); //blank line
			if (firstLine.equals("#####")) {
				locks.add(pins);
			} else {
				keys.add(pins);
			}
		}
		int combos = 0;
		for (int i = 0; i < locks.size(); i++) {
			int[] lock = locks.get(i);
			for (int j = 0; j < keys.size(); j++) {
				int[] key = keys.get(j);
				boolean goodCombo = true;
				for (int k = 0; goodCombo && k < 5; k++) {
					if ((lock[k] + key[k]) > 5) {
						goodCombo = false;
					}
				}
				if (goodCombo) {
					combos++;
				}
			}
		}
		System.out.println("Combos: " + combos);
	}

	private static void part2() throws IOException {
	}
}
