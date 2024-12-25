package day24;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Day24 {

	public static void main(String[] args) throws IOException {
		part1();
		part2();
	}

	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day24/input.txt")));

		String line;
		HashMap<String, Boolean> gates = new HashMap<String, Boolean>();
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ": ");
			if (!st.hasMoreTokens()) {
				break;
			} else {
				String regName = st.nextToken();
				Boolean regValue = st.nextToken().equals("1") ? Boolean.TRUE : Boolean.FALSE;
				gates.put(regName, regValue);
			}
		}

		ArrayDeque<String> commandQueue = new ArrayDeque<String>();
		while ((line = br.readLine()) != null) {
			commandQueue.add(line);
		}

		while (!commandQueue.isEmpty()) {
			String command = commandQueue.removeFirst();

			// x00 AND y00 -> z00
			StringTokenizer st = new StringTokenizer(command, " ->");
			String g1 = st.nextToken();
			String operation = st.nextToken();
			String g2 = st.nextToken();
			String dest = st.nextToken();

			if (!gates.containsKey(g1) || !gates.containsKey(g2)) {
				commandQueue.add(command);
			} else {

				boolean val1 = gates.get(g1).booleanValue();
				boolean val2 = gates.get(g2).booleanValue();
				boolean result;
				if (operation.equals("AND")) {
					result = val1 && val2;
				} else if (operation.equals("OR")) {
					result = val1 || val2;
				} else if (operation.equals("XOR")) {
					result = val1 ^ val2;
				} else {
					throw new RuntimeException("dang");
				}
				gates.put(dest, Boolean.valueOf(result));
			}
		}

		ArrayList<String> zGates = new ArrayList<String>();
		for (Iterator<String> it = gates.keySet().iterator(); it.hasNext();) {
			String gate = it.next();
			if (gate.charAt(0) == 'z') {
				zGates.add(gate);
			}
		}
		String[] zGatesOrdered = new String[zGates.size()];
		for (int i = 0; i < zGatesOrdered.length; i++) {
			zGatesOrdered[i] = zGates.get(i);
		}
		Arrays.sort(zGatesOrdered);
		String bitString = "";
		for (int i = 0; i < zGatesOrdered.length; i++) {
			bitString = (gates.get(zGatesOrdered[i]).booleanValue() ? "1" : "0") + bitString;
		}

		long result = Long.parseLong(bitString, 2);
		System.out.println("Result: " + result);
	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day24/sample.txt")));
	}

}
