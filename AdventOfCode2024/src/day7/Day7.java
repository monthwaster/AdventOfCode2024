package day7;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Day7 {

	public static void main(String args[]) throws IOException {

		part1();

		part2();
	}

	private static void part1() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day7/input.txt")));
		String line;
		ArrayList<Line> lines = new ArrayList<Line>();
		
		
		while ((line = br.readLine()) != null) {
			lines.add(new Line(line));
		}

		long total = 0;
		for(int i = 0; i < lines.size();i++) {
			Line l = lines.get(i);
			if(l.isValid1()) {
				total += l.value;
			}
		}

		System.out.println("Total: " + total);

	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day7/input.txt")));
		String line;
		ArrayList<Line> lines = new ArrayList<Line>();
		
		
		while ((line = br.readLine()) != null) {
			lines.add(new Line(line));
		}

		long total = 0;
		for(int i = 0; i < lines.size();i++) {
			Line l = lines.get(i);
			if(l.isValid2()) {
				total += l.value;
			}
		}

		System.out.println("Total: " + total);

	}
}

class Line {
	long value;
	long[] inputs;

	public Line(String str) {
		StringTokenizer st = new StringTokenizer(str, ": ");
		value = Long.parseLong(st.nextToken());
		inputs = new long[st.countTokens()];
		for (int i = 0; i < inputs.length; i++) {
			inputs[i] = Long.parseLong(st.nextToken());
		}
	}

	public boolean isValid1() {
		return isValid1(inputs);
	}

	private boolean isValid1(long[] ins) {
		if (ins.length == 1) {
			return ins[0] == value;
		} else {
			long[] check1 = new long[ins.length - 1];
			check1[0] = ins[0] * ins[1];
			System.arraycopy(ins, 2, check1, 1, ins.length - 2);

			long[] check2 = new long[ins.length - 1];
			check2[0] = ins[0] + ins[1];
			System.arraycopy(ins, 2, check2, 1, ins.length - 2);

			return isValid1(check1) || isValid1(check2);
		}
	}
	public boolean isValid2() {
		return isValid2(inputs);
	}

	private boolean isValid2(long[] ins) {
		if (ins.length == 1) {
			return ins[0] == value;
		} else {
			long[] check1 = new long[ins.length - 1];
			check1[0] = ins[0] * ins[1];
			System.arraycopy(ins, 2, check1, 1, ins.length - 2);

			long[] check2 = new long[ins.length - 1];
			check2[0] = ins[0] + ins[1];
			System.arraycopy(ins, 2, check2, 1, ins.length - 2);

			long[] check3 = new long[ins.length - 1];
			check3[0] = Long.parseLong(""+ ins[0] + ins[1]);
			System.arraycopy(ins, 2, check3, 1, ins.length - 2);

			return isValid2(check1) || isValid2(check2) || isValid2(check3);
		}
	}

	public long total() {
		long total = 0;
		for (int i = 0; i < inputs.length; i++) {
			total += inputs[i];
		}
		return total;
	}
}