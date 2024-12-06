package day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Day5 {

	public static void main(String[] args) throws IOException {

		// partOne();

		partTwo();
	}

	private static Instructions instructions;
	private static ArrayList<int[]> lists;

	private static void ingest() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day5/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		instructions = new Instructions();
		lists = new ArrayList<int[]>();

		while ((line = br.readLine()).length() != 0) {
			StringTokenizer st = new StringTokenizer(line, "|");
			instructions.addInstruction(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
		}
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ",");
			int list[] = new int[st.countTokens()];
			for (int i = 0; i < list.length; i++) {
				list[i] = Integer.parseInt(st.nextToken());
			}
			lists.add(list);
		}
	}

	private static void partOne() throws IOException {
		int total = 0;
		ingest();

		for (int i = 0; i < lists.size(); i++) {
			int[] list = lists.get(i);
			if (instructions.isValid(list)) {
				total += list[list.length / 2];
			}
		}
		System.out.println("Total: " + total);
	}

	private static void partTwo() throws IOException {
		int total = 0;
		ingest();

		for (int i = 0; i < lists.size(); i++) {
			int[] list = lists.get(i);
			if (!instructions.isValid(list)) {
				instructions.fix(list);
				total += list[list.length / 2];
			}
		}
		System.out.println("Total: " + total);

	}

	public static void printList(int[] list) {
		System.out.print("[ ");
		for (int i = 0; i < list.length - 1; i++) {
			System.out.print(list[i] + ", ");
		}

		System.out.println(list[list.length - 1] + " ]");
	}
}

class Instructions {
	ArrayList<Rule> instr;

	public Instructions() {
		instr = new ArrayList<Rule>();
	}

	public void addInstruction(int num1, int num2) {
		instr.add(new Rule(num1, num2));
	}

	public boolean isValid(int list[]) {
		for (int i = 0; i < instr.size(); i++) {
			if (!instr.get(i).isValid(list)) {
				return false;
			}
		}
		return true;
	}

	public void fix(int list[]) {
		ArrayList<Integer> newList = new ArrayList<Integer>();
		for (int i = 0; i < list.length; i++) {
			newList.add(Integer.valueOf(list[i]));
		}
		boolean madeAChange = true;
		while (madeAChange) {
			madeAChange = false;
			for (int i = 0; i < instr.size(); i++) {
				boolean changed = instr.get(i).fix(newList);
				if (changed) {
					madeAChange = true;
				}
			}
		}
		for (int i = 0; i < list.length; i++) {
			list[i] = newList.get(i).intValue();
		}

	}
}

class Rule {
	int num1, num2;

	public Rule(int num1, int num2) {
		this.num1 = num1;
		this.num2 = num2;
	}

	public boolean isValid(int list[]) {
		int n1 = -1, n2 = -1;
		for (int i = 0; i < list.length && (n1 == -1 || n2 == -1); i++) {
			if (list[i] == num1) {
				n1 = i;
			}
			if (list[i] == num2) {
				n2 = i;
			}
		}
		return n2 == -1 || n1 == -1 || n1 < n2;
	}

	public boolean fix(ArrayList<Integer> list) {
		boolean madeAChange = false;
		int n1 = -1, n2 = -1;
		for (int i = 0; i < list.size() && (n1 == -1 || n2 == -1); i++) {
			if (list.get(i).intValue() == num1) {
				n1 = i;
			}

			if (list.get(i).intValue() == num2) {
				n2 = i;
			}
		}
		if (n1 != -1 && n2 != -1 && n1 > n2) {

			list.remove(n1);
			list.add(n2, num1);
			madeAChange = true;
		}
		return madeAChange;
	}
}
