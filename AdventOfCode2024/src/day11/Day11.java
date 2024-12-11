package day11;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;

public class Day11 {

	public static void main(String args[]) throws IOException {

		part1();
		part2();
	}

	private static Stone ingest() throws IOException {
		StringTokenizer st = new StringTokenizer(
				new BufferedReader(new InputStreamReader(new FileInputStream("src/day11/input.txt"))).readLine());

		Stone first = new Stone(Long.parseLong(st.nextToken()), null);
		Stone last = first;
		while (st.hasMoreTokens()) {
			last.next = new Stone(Long.parseLong(st.nextToken()), last);
			last = last.next;
		}
		return first;
	}

	private static void part1() throws IOException {
		Stone stones = ingest();
		for (int i = 0; i < 25; i++) {
			Stone st = stones;
			while (st != null) {
				if (st.value == 0) {
					st.value = 1;
				} else if (Long.toString(st.value).length() % 2 == 0) {
					String valStr = Long.toString(st.value);
					long nextVal = Long.parseLong(valStr.substring(valStr.length() / 2, valStr.length()));
					st.value = Long.parseLong(valStr.substring(0, valStr.length() / 2));
					st.next = new Stone(nextVal, st, st.next);
					st = st.next;
				} else {

					st.value *= 2024;
				}
				st = st.next;
			}
		}
		long count = 0;
		Stone st = stones;
		while (st != null) {
			count++;
			st = st.next;
		}
		System.out.println("Total: " + count);
	}

	private static Stack<Long> ingest2() throws IOException {
		StringTokenizer st = new StringTokenizer(
				new BufferedReader(new InputStreamReader(new FileInputStream("src/day11/input.txt"))).readLine());
		Stack<Long> stones = new Stack<Long>();
		while (st.hasMoreTokens()) {
			stones.add(Long.valueOf(st.nextToken()));
		}
		return stones;
	}

	private static void part2() throws IOException {
		HashMap<Long, HashMap<Long, Long>> blinkResults = new HashMap<Long, HashMap<Long, Long>>();

		long total = 0;
		Stack<Long> stones = ingest2();
		while (!stones.isEmpty()) {
			Long stone = stones.pop();
			total += calculate(stone, Long.valueOf(75), blinkResults);
		}

		System.out.println("Total: " + total);
	}

	private static long calculate(Long value, Long blinksToDo, HashMap<Long, HashMap<Long, Long>> blinkResults) {
		if(blinksToDo == 0) {
			return 1;
		}
		if (blinkResults.containsKey(value) && blinkResults.get(value).containsKey(blinksToDo)) {
			return blinkResults.get(value).get(blinksToDo).longValue();
		} else {
			long result;

			if (value.longValue() == 0) {
				result = calculate(Long.valueOf(1), blinksToDo - 1, blinkResults);

			} else if (value.toString().length() % 2 == 0) {
				String valStr = value.toString();
				long nextVal = Long.parseLong(valStr.substring(valStr.length() / 2, valStr.length()));
				long curVal = Long.parseLong(valStr.substring(0, valStr.length() / 2));
				result = calculate(nextVal, blinksToDo - 1, blinkResults)
						+ calculate(curVal, blinksToDo - 1, blinkResults);
			} else {
				result = calculate(Long.valueOf(value.longValue() * 2024), blinksToDo - 1, blinkResults);
			}

			if (!blinkResults.containsKey(value)) {
				HashMap<Long, Long> hm = new HashMap<Long, Long>();
				hm.put(blinksToDo, result);
				blinkResults.put(value, hm);
			} else if (!blinkResults.get(value).containsKey(blinksToDo)) {
				blinkResults.get(value).put(blinksToDo, result);
			} else {
				throw new RuntimeException("WTF");
			}
			return result;
		}

	}

}

class Stone {
	long value;
	Stone prev, next;

	public Stone() {
		super();
		value = -1;
		prev = next = null;
	}

	public Stone(long value, Stone prev) {
		this();
		this.value = value;
		this.prev = prev;
	}

	public Stone(long value, Stone prev, Stone next) {
		this(value, prev);
		this.next = next;
	}
}
