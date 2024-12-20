package day19;

//too low: 1344455988565
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Day19 {

	public static void main(String[] args) throws IOException {
		part1();
		part2();

	}

	private static ArrayList<String> towels;

	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day19/input.txt")));

		StringTokenizer st = new StringTokenizer(br.readLine(), ", ");
		towels = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			towels.add(st.nextToken());
		}
		trimTowels();

		for (int i = 0; i < towels.size(); i++) {
			System.out.println(towels.get(i));
		}
		br.readLine();
		String pattern;
		int total = 0;
		while ((pattern = br.readLine()) != null) {
			if (isBuildable(pattern)) {
				total++;
			}

		}
		System.out.println("Total: " + total);

	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day19/input.txt")));

		StringTokenizer st = new StringTokenizer(br.readLine(), ", ");
		towels = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			towels.add(st.nextToken());
		}
		for (int i = 0; i < towels.size(); i++) {
			System.out.println(towels.get(i));
		}

		br.readLine();
		String pattern;
		long total = 0;
		HashMap<String, Long> solutions = new HashMap<String, Long>();
		while ((pattern = br.readLine()) != null) {
			total += countSolutions(pattern, solutions);
		}
		System.out.println("Total: " + total);

	}

	private static void trimTowels() {
		boolean moved = false;
		do {
			moved = false;
			for (int i = 0; i < towels.size(); i++) {
				if (i < towels.size() - 1 && towels.get(i).length() > towels.get(i + 1).length()) {
					String s = towels.remove(i);
					towels.add(i + 1, s);
					moved = true;
				}
			}
		} while (moved);

		for (int i = towels.size() - 1; i > 0; i--) {
			String towel = towels.remove(i);
			if (!isBuildable(towel)) {
				towels.add(i, towel);
			}
		}

	}

	private static boolean isBuildable(String pattern) {
		ArrayList<String> contained = new ArrayList<String>();
		for (int i = 0; i < towels.size(); i++) {
			if (pattern.equals(towels.get(i))) {
				return true;
			} else if (pattern.contains(towels.get(i))) {
				contained.add(towels.get(i));
			}
		}
		return isBuildable(pattern, contained);
	}

	private static long countSolutions(String pattern, HashMap<String, Long> solutions) {
		if (solutions.containsKey(pattern)) {
			return solutions.get(pattern).longValue();
		}
		ArrayList<String> contained = new ArrayList<String>();

		for (int i = 0; i < towels.size(); i++) {
			if (pattern.contains(towels.get(i))) {
				contained.add(towels.get(i));
			}
		}
		long numberOfSolutions = countSolutions(pattern, contained, solutions);
		solutions.put(pattern, numberOfSolutions);
		return numberOfSolutions;
	}

	private static boolean isBuildable(String pattern, ArrayList<String> containedTowels) {
		for (int i = 0; i < containedTowels.size(); i++) {
			if (pattern.equals(containedTowels.get(i))) {
				return true;
			} else if (pattern.startsWith(containedTowels.get(i))) {
				if (isBuildable(pattern.substring(containedTowels.get(i).length()), containedTowels)) {
					return true;
				}
			}
		}
		return false;

	}

	private static long countSolutions(String pattern, ArrayList<String> containedTowels,
			HashMap<String, Long> solutions) {
		if (solutions.containsKey(pattern)) {
			return solutions.get(pattern).longValue();
		}
		long count = 0;
		for (int i = 0; i < containedTowels.size(); i++) {
			if (pattern.equals(containedTowels.get(i))) {
				count++;
			} else if (pattern.startsWith(containedTowels.get(i))) {
				count += countSolutions(pattern.substring(containedTowels.get(i).length()), containedTowels, solutions);
			}
		}
		solutions.put(pattern, count);
		return count;

	}

}
