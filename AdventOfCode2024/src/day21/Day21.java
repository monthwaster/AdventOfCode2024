package day21;

import java.util.ArrayList;

public class Day21 {

//	029A: <vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A
//	980A: <v<A>>^AAAvA^A<vA<AA>>^AvAA<^A>A<v<A>A>^AAAvA<^A>A<vA>^A<A>A
//	179A: <v<A>>^A<vA<A>>^AAvAA<^A>A<v<A>>^AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A
//	456A: <v<A>>^AA<vA<A>>^AAvAA<^A>A<vA>^A<A>A<vA>^A<A>A<v<A>A>^AAvA<^A>A
//	379A: <v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A
	public static final String[] sample = new String[] { "029A", "980A", "179A", "456A", "379A" };
	public static final String[] input = new String[] { "129A", "176A", "985A", "170A", "528A", };

	public static void main(String args[]) {
		part1();
	}

	public static void part1() {
		Robot r1 = new Robot(
				new char[][] { { '7', '8', '9' }, { '4', '5', '6' }, { '1', '2', '3' }, { 'X', '0', 'A' } },
				new Position(3, 2));
		Robot r2 = new Robot(new char[][] { { 'X', '^', 'A' }, { '<', 'v', '>' } }, new Position(0, 2));
		Robot r3 = new Robot(new char[][] { { 'X', '^', 'A' }, { '<', 'v', '>' } }, new Position(0, 2));
//if substring between A's has both (^ or >) and (< or v), (^ or >) must come first.
		int total = 0;
//		for (int i = 0; i < sample.length; i++) {
//			String buttons = sample[i];
		for (int i = 0; i < input.length; i++) {
			String buttons = input[i];

			ArrayList<String> step1ways = new ArrayList<String>();
			step1ways.add("");
			for (int j = 0; j < buttons.length(); j++) {
				char nextButton1 = buttons.charAt(j);
				ArrayList<String> ways = r1.pressAllways(nextButton1);
				ArrayList<String> newWays = new ArrayList<String>();
				for (int k = 0; k < step1ways.size(); k++) {
					for (int l = 0; l < ways.size(); l++) {
						newWays.add(step1ways.get(k) + ways.get(l));
					}
				}
				step1ways = newWays;
			}
			ArrayList<String> step2ways = new ArrayList<String>();
			for (int j = 0; j < step1ways.size(); j++) {
				ArrayList<String> tobeWays = new ArrayList<String>();
				tobeWays.add("");
				for (int k = 0; k < step1ways.get(j).length(); k++) {
					char nextButton2 = step1ways.get(j).charAt(k);
					ArrayList<String> ways = r2.pressAllways(nextButton2);
					ArrayList<String> newWays = new ArrayList<String>();
					for (int l = 0; l < tobeWays.size(); l++) {
						for (int m = 0; m < ways.size(); m++) {
							newWays.add(tobeWays.get(l) + ways.get(m));
						}
					}
					tobeWays = newWays;
				}
				step2ways.addAll(tobeWays);
			}
			String theWay = "";
			for (int j = 0; j < step2ways.size(); j++) {
				String aWay = "";
				for (int k = 0; k < step2ways.get(j).length(); k++) {
					char nextButton3 = step2ways.get(j).charAt(k);
					aWay += r3.press(nextButton3);
				}
				System.out.println("checking length: " + aWay.length() + " against " + theWay.length() + " for: "
						+ step2ways.get(j));
				if (theWay.length() == 0 || aWay.length() < theWay.length()) {
					theWay = aWay;
				}
			}
//			System.out.println(
//					theWay.length() + " * " + Integer.parseInt(sample[i].substring(0, sample[i].length() - 1)));
//			int complexity = theWay.length() * Integer.parseInt(sample[i].substring(0, sample[i].length() - 1));
			System.out
					.println(theWay.length() + " * " + Integer.parseInt(input[i].substring(0, input[i].length() - 1)));
			int complexity = theWay.length() * Integer.parseInt(input[i].substring(0, input[i].length() - 1));
			total += complexity;

		}
		// 137800 too high
		System.out.println("Total: " + total);
	}

	public static void part2() {
		Robot r1 = new Robot(
				new char[][] { { '7', '8', '9' }, { '4', '5', '6' }, { '1', '2', '3' }, { 'X', '0', 'A' } },
				new Position(3, 2));
		Robot r2 = new Robot(new char[][] { { 'X', '^', 'A' }, { '<', 'v', '>' } }, new Position(0, 2));
		Robot r3 = new Robot(new char[][] { { 'X', '^', 'A' }, { '<', 'v', '>' } }, new Position(0, 2));
//if substring between A's has both (^ or >) and (< or v), (^ or >) must come first.?
		//figure out best move from each symbol to the other, then just lookup, + memoization.
		int total = 0;
		for (int i = 0; i < sample.length; i++) {
			String buttons = sample[i];
//		for (int i = 0; i < input.length; i++) {
//			String buttons = input[i];

			ArrayList<String> step1ways = new ArrayList<String>();
			step1ways.add("");
			for (int j = 0; j < buttons.length(); j++) {
				char nextButton1 = buttons.charAt(j);
				ArrayList<String> ways = r1.pressAllways(nextButton1);
				ArrayList<String> newWays = new ArrayList<String>();
				for (int k = 0; k < step1ways.size(); k++) {
					for (int l = 0; l < ways.size(); l++) {
						newWays.add(step1ways.get(k) + ways.get(l));
					}
				}
				step1ways = newWays;
			}
			step1ways = trimCrap(step1ways);
			ArrayList<String> step2ways = new ArrayList<String>();
			for (int j = 0; j < step1ways.size(); j++) {
				ArrayList<String> tobeWays = new ArrayList<String>();
				tobeWays.add("");
				for (int k = 0; k < step1ways.get(j).length(); k++) {
					char nextButton2 = step1ways.get(j).charAt(k);
					ArrayList<String> ways = r2.pressAllways(nextButton2);
					ArrayList<String> newWays = new ArrayList<String>();
					for (int l = 0; l < tobeWays.size(); l++) {
						for (int m = 0; m < ways.size(); m++) {
							newWays.add(tobeWays.get(l) + ways.get(m));
						}
					}
					tobeWays = newWays;
				}
				step2ways.addAll(tobeWays);
			}
			step2ways = trimCrap(step2ways);
			String theWay = "";
			for (int j = 0; j < step2ways.size(); j++) {
				String aWay = "";
				for (int k = 0; k < step2ways.get(j).length(); k++) {
					char nextButton3 = step2ways.get(j).charAt(k);
					aWay += r3.press(nextButton3);
				}
				System.out.println("checking length: " + aWay.length() + " against " + theWay.length() + " for: "
						+ step2ways.get(j));
				if (theWay.length() == 0 || aWay.length() < theWay.length()) {
					theWay = aWay;
				}
			}
			System.out.println(
					theWay.length() + " * " + Integer.parseInt(sample[i].substring(0, sample[i].length() - 1)));
			int complexity = theWay.length() * Integer.parseInt(sample[i].substring(0, sample[i].length() - 1));
//			System.out
//					.println(theWay.length() + " * " + Integer.parseInt(input[i].substring(0, input[i].length() - 1)));
//			int complexity = theWay.length() * Integer.parseInt(input[i].substring(0, input[i].length() - 1));
			total += complexity;

		}
		// 137800 too high
		System.out.println("Total: " + total);
	}

	static ArrayList<String> trimCrap(ArrayList<String> crapful) {
		ArrayList<String> decrapped = new ArrayList<String>();

		for (int i = 0; i < crapful.size(); i++) {
			String thing = crapful.get(i);
			int a = -1;
//			while(a)
		}
		return null;
	}
}

class Robot {
	char[][] keypad;
	Position pos;

	public Robot(char[][] pad, Position p) {
		keypad = pad;
		pos = p;
	}

	public String press(char c) {
		Position to = find(c);
		String steps = moveToFrom(to, pos, "") + 'A';
		pos = to;
		return steps;
	}

	public ArrayList<String> pressAllways(char c) {
		Position to = find(c);
		ArrayList<String> ways = movesToFrom(to, pos, "");
		pos = to;
		return ways;
	}

	private ArrayList<String> movesToFrom(Position to, Position from, String instr) {
		ArrayList<String> moves = new ArrayList<String>();
//add trim by countmoves
		if (to.equals(from)) {
			moves.add("A");
			return moves;

		}
		int shortest = 0;
		if (from.c < to.c && keypad[from.r][from.c + 1] != 'X') {
			ArrayList<String> rightMoves = movesToFrom(to, new Position(from.r, from.c + 1), ">");
			if (shortest == 0 || rightMoves.get(0).length() <= shortest - 1) {
				if (rightMoves.get(0).length() < shortest - 1) {
					moves = new ArrayList<String>();
					shortest = rightMoves.get(0).length() + 1;
				}
				for (int i = 0; i < rightMoves.size(); i++) {
					moves.add('>' + rightMoves.get(i));
				}
			}
		}
		if (from.r > to.r && keypad[from.r - 1][from.c] != 'X') {
			ArrayList<String> upMoves = movesToFrom(to, new Position(from.r - 1, from.c), "^");
			if (shortest == 0 || upMoves.get(0).length() <= shortest - 1) {
				if (upMoves.get(0).length() < shortest - 1) {
					moves = new ArrayList<String>();
					shortest = upMoves.get(0).length() + 1;
				}
				for (int i = 0; i < upMoves.size(); i++) {
					moves.add('^' + upMoves.get(i));
				}
			}
		}
		if (from.c > to.c && keypad[from.r][from.c - 1] != 'X') {
			ArrayList<String> leftMoves = movesToFrom(to, new Position(from.r, from.c - 1), "<");
			if (shortest == 0 || leftMoves.get(0).length() <= shortest - 1) {
				if (leftMoves.get(0).length() < shortest - 1) {
					moves = new ArrayList<String>();
					shortest = leftMoves.get(0).length() + 1;
				}
				for (int i = 0; i < leftMoves.size(); i++) {
					moves.add('<' + leftMoves.get(i));
				}
			}
		}
		if (from.r < to.r && keypad[from.r + 1][from.c] != 'X') {
			ArrayList<String> downMoves = movesToFrom(to, new Position(from.r + 1, from.c), "v");
			if (shortest == 0 || downMoves.get(0).length() <= shortest - 1) {
				if (downMoves.get(0).length() < shortest - 1) {
					moves = new ArrayList<String>();
					shortest = downMoves.get(0).length() + 1;
				}
			}
			for (int i = 0; i < downMoves.size(); i++) {
				moves.add('v' + downMoves.get(i));
			}

		}
		if (from.c < to.c && keypad[from.r][from.c + 1] != 'X') {
			ArrayList<String> rightMoves = movesToFrom(to, new Position(from.r, from.c + 1), ">");
			if (shortest == 0 || rightMoves.get(0).length() <= shortest - 1) {
				if (rightMoves.get(0).length() < shortest - 1) {
					moves = new ArrayList<String>();
					shortest = rightMoves.get(0).length() + 1;
				}
				for (int i = 0; i < rightMoves.size(); i++) {
					moves.add('>' + rightMoves.get(i));
				}
			}
		}

		int fewest = 0;
		for (int i = 0; i < moves.size(); i++) {
			int posMoves = countMoves(moves.get(i));
			if (fewest == 0 || posMoves < fewest) {
				fewest = posMoves;
			}
		}
		for (int i = 0; i < moves.size(); i++) {
			int posMoves = countMoves(moves.get(i));
			if (posMoves != fewest) {
				moves.remove(i);
				i--;
			}

		}
		return moves;

	}

	private String moveToFrom(Position to, Position from, String instr) {
		String down = "";
		String up = "";
		String left = "";
		String right = "";
		String best = "";
		int bestMoves = 0;

		if (to.equals(from)) {
			return "";
		}
		if (from.r < to.r && keypad[from.r + 1][from.c] != 'X') {
			down = 'v' + moveToFrom(to, new Position(from.r + 1, from.c), "v");
			best = down;
			bestMoves = countMoves(instr + best);
		}
		if (from.r > to.r && keypad[from.r - 1][from.c] != 'X') {
			up = '^' + moveToFrom(to, new Position(from.r - 1, from.c), "^");
			int upMoves = countMoves(instr + up);
			if (bestMoves == 0 || upMoves < bestMoves) {
				best = up;
				bestMoves = upMoves;
			}
		}
		if (from.c > to.c && keypad[from.r][from.c - 1] != 'X') {
			left = '<' + moveToFrom(to, new Position(from.r, from.c - 1), "<");
			int leftMoves = countMoves(instr + left);
			if (bestMoves == 0 || leftMoves < bestMoves) {
				best = left;
				bestMoves = leftMoves;
			}
		}
		if (from.c < to.c && keypad[from.r][from.c + 1] != 'X') {
			right = '>' + moveToFrom(to, new Position(from.r, from.c + 1), ">");
			int rightMoves = countMoves(instr + right);
			if (bestMoves == 0 || rightMoves < bestMoves) {
				bestMoves = rightMoves;
				best = right;
			}
		}
		return best;

	}

	private int countMoves(String instr) {
		int moves = 0;
		for (int i = 0; i < instr.length() - 1; i++) {
			if (instr.charAt(i) != instr.charAt(i + 1)) {
				moves++;
			}
		}
		return moves;
	}

	private Position find(char c) {
		for (int i = 0; i < keypad.length; i++) {
			for (int j = 0; j < keypad[i].length; j++) {
				if (keypad[i][j] == c) {
					return new Position(i, j);
				}
			}
		}
		return null;
	}
}

class Position {
	int r, c;

	public Position(int row, int col) {
		r = row;
		c = col;
	}

	public boolean equals(Object o) {
		if (o instanceof Position) {
			return ((Position) o).r == this.r && ((Position) o).c == this.c;
		} else {
			return false;
		}
	}

}