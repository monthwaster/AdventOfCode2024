package day17;

public class Day17 {

	static long regA = 65804993;
	static long regB = 0;
	static long regC = 0;

	// static long[][] program = { { 0, 1 }, { 5, 4 }, { 3, 0 } };
	static int[][] program = { { 2, 4 }, { 1, 1 }, { 7, 5 }, { 1, 4 }, { 0, 3 }, { 4, 5 }, { 5, 5 }, { 3, 0 } };
//	static int[][] program = { { 0, 3 }, { 5, 4 }, { 3, 0 } };
	static String output = "";
	static int instrPtr = 0;
	static String desired = "2,4,1,1,7,5,1,4,0,3,4,5,5,5,3,0";
//static String desired = "0,3,5,4,3,0";
	static boolean skip = false;

	public static void main(String[] args) {
		part1();
		part2();
	}

	private static void part1() {
		while (instrPtr < program.length) {
			execute(program[instrPtr]);
		}
		output = output.substring(0, output.length() - 1);

		System.out.println("output: " + output);
	}

	private static long skip16 = 35184372088832l - 1;
	private static long skip15 = 4398046511104l - 1;
	private static long skip14 = 549755813888l - 1;
	private static long skip13 = 68719476736l - 1;
	private static long skip12 = 8589934592l - 1;
	private static long skip11 = 1073741824l - 1;
	private static long skip10 = 134217728l - 1;
	private static long skip9 = 16777216 - 1;
	private static long skip8 = 2097152 - 1;
	private static long skip7 = 262144 - 1;

	private static void part2() {

		long startA = -1;
		output = "";
		while (!output.equals(desired)) {
			regA = ++startA;
			regB = 0;
			regC = 0;
			output = "";
			instrPtr = 0;

			while (instrPtr < program.length) {
				execute(program[instrPtr]);
			}

			output = output.substring(0, output.length() - 1);
			if (!output.equals(desired)) {
				if (output.length() > desired.length()) {
					throw new RuntimeException("ruh roh");
				} else if (output.length() < desired.length()) {
					startA += skip16;
				} else if (output.charAt(output.length() - 1) != desired.charAt(desired.length() - 1)) {
					startA += skip16;
				} else if (output.charAt(output.length() - 3) != desired.charAt(desired.length() - 3)) {
					startA += skip15;
				} else if (output.charAt(output.length() - 5) != desired.charAt(desired.length() - 5)) {
					startA += skip14;
				} else if (output.charAt(output.length() - 7) != desired.charAt(desired.length() - 7)) {
					startA += skip13;
				} else if (output.charAt(output.length() - 9) != desired.charAt(desired.length() - 9)) {
					startA += skip12;
				} else if (output.charAt(output.length() - 11) != desired.charAt(desired.length() - 11)) {
					startA += skip11;
				} else if (output.charAt(output.length() - 13) != desired.charAt(desired.length() - 13)) {
					startA += skip10;
				} else if (output.charAt(output.length() - 15) != desired.charAt(desired.length() - 15)) {
					startA += skip9;
				} else if (output.charAt(output.length() - 17) != desired.charAt(desired.length() - 17)) {
					startA += skip8;
				} else if (output.charAt(output.length() - 19) != desired.charAt(desired.length() - 19)) {
					startA += skip7;
				}
			}
		}

		System.out.println("a value: " + startA);
	}

	private static void execute(int[] instr) {
		if (instr[0] == 0) {
			long denom = (long) Math.pow(2, combo(instr[1]));
			regA = regA / denom;
		} else if (instr[0] == 1) {
			regB = regB ^ instr[1];
		} else if (instr[0] == 2) {
			regB = combo(instr[1]) % 8;
		} else if (instr[0] == 3) {
			if (regA != 0) {
				instrPtr = instr[1];
				return;
			}
		} else if (instr[0] == 4) {
			regB = regB ^ regC;
		} else if (instr[0] == 5) {
			output += combo(instr[1]) % 8 + ",";
		} else if (instr[0] == 6) {
			long denom = (long) Math.pow(2, combo(instr[1]));
			regB = regA / denom;
		} else if (instr[0] == 7) {
			long denom = (long) Math.pow(2, combo(instr[1]));
			if (denom == 0) {
				long a = combo(instr[1]);
			}
			regC = regA / denom;
		} else {
			throw new RuntimeException("WTF");
		}
		instrPtr++;
	}

	private static long combo(int d) {
		if (d <= 3) {
			return d;
		} else if (d == 4) {
			return regA;
		} else if (d == 5) {
			return regB;
		} else if (d == 6) {
			return regC;
		} else {
			throw new RuntimeException("WTF");
		}
	}
}
