package day22;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Day22 {

	public static void main(String[] args) throws IOException {
		part1();
		part2();
	}

	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day22/input.txt")));

		String line;
		long total = 0;

		while ((line = br.readLine()) != null) {
			long secret = Long.parseLong(line);
			for (int i = 0; i < 2000; i++) {
				secret = nextSecret(secret);
			}
			total += secret;

		}
		System.out.println("Total: " + total);

	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day22/input.txt")));

		String line;
		long total = 0;
		HashMap<String, ArrayList<Long>> possibleValues = new HashMap<String, ArrayList<Long>>();

		while ((line = br.readLine()) != null) {
			long secret = Long.parseLong(line);
			HashMap<String, Long> vals = new HashMap<String, Long>();
			long lastPrice;
			long diff1, diff2, diff3, diff4;
			lastPrice = secret % 10;
			secret = nextSecret(secret);
			diff2 = secret % 10 - lastPrice;

			lastPrice = secret % 10;
			secret = nextSecret(secret);
			diff3 = secret % 10 - lastPrice;

			lastPrice = secret % 10;
			secret = nextSecret(secret);
			diff4 = secret % 10 - lastPrice;

			for (int i = 3; i < 2000; i++) {
				lastPrice = secret % 10;
				secret = nextSecret(secret);
				diff1 = diff2;
				diff2 = diff3;
				diff3 = diff4;
				diff4 = secret % 10 - lastPrice;
				String key = "" + diff1 + diff2 + diff3 + diff4;
				if (!vals.containsKey(key)) {
					vals.put(key, Long.valueOf(secret % 10));
				}

			}
			Set<String> keys = vals.keySet();
			for (Iterator<String> it = keys.iterator(); it.hasNext();) {
				String key = it.next();
				if (possibleValues.containsKey(key)) {
					possibleValues.get(key).add(vals.get(key));
				} else {
					ArrayList<Long> al = new ArrayList<Long>();
					al.add(vals.get(key));
					possibleValues.put(key, al);
				}
			}

		}
		Set<String> keys = possibleValues.keySet();
		long highestFound = 0;

		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			ArrayList<Long> vals = possibleValues.get(it.next());
			long valTotal = 0;
			for (int i = 0; i < vals.size(); i++) {
				valTotal += vals.get(i).longValue();
			}
			if (valTotal > highestFound) {
				highestFound = valTotal;
			}
		}
		System.out.println("Most bananas possible: " + highestFound);
	}

	private static long nextSecret(long secret) {
		// mix is bitwise xor
		// prune is % 16777216
		// *64
		long l = secret * 64;
		// mix
		secret = secret ^ l;
		// prune
		secret %= 16777216;
		// /32
		l = secret / 32;
		// round down
		// mix
		secret = secret ^ l;
		// prune
		secret %= 16777216;
		// * 2048
		l = secret * 2048;
		// mix
		secret = secret ^ l;
		// prune
		secret %= 16777216;
		return secret;
	}
}
