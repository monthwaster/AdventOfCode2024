package day6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Day6 {

	public static void main(String args[]) throws IOException {

		part1();

		part2();
	}

	private static void part1() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day6/input.txt")));
		ArrayList<String> lines = new ArrayList<String>();
		int posr = -1, posc = -1;
		String line;

		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		char[][] grid = new char[lines.size()][lines.get(0).length()];

		for (int i = 0; i < lines.size(); i++) {
			String nextLine = lines.get(i);

			for (int j = 0; j < nextLine.length(); j++) {
				grid[i][j] = nextLine.charAt(j);

				if ((grid[i][j] != '.') && (grid[i][j] != '#')) {
					posr = i;
					posc = j;
				}
			}
		}

		int incrr = -1, incrc = 0; // start facing up
		int count = 0;

		while (posr >= 0 && posr < grid.length && posc >= 0 && posc < grid[0].length) {

			if (grid[posr][posc] != 'X') {
				grid[posr][posc] = 'X';
				count++;
			}

			int newPosr = posr + incrr;
			int newPosc = posc + incrc;

			if (newPosr < 0 || newPosr >= grid.length || newPosc < 0 || newPosc >= grid[0].length) { // done
				break;
			}

			if (grid[newPosr][newPosc] == '#') { // turn
				if (incrr == 0 && incrc == 1) {
					incrr = 1;
					incrc = 0;
				} else if (incrr == 1 && incrc == 0) {
					incrr = 0;
					incrc = -1;
				} else if (incrr == 0 && incrc == -1) {
					incrr = -1;
					incrc = 0;
				} else if (incrr == -1 && incrc == 0) {
					incrr = 0;
					incrc = 1;
				}
			} else { // step
				posr = newPosr;
				posc = newPosc;
			}
		}

		System.out.println("Steps: " + count);

	}

	private static void part2() throws IOException {

		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day6/input.txt")));
		int posr = -1, posc = -1;
		String line = "";

		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		Cell[][] grid = new Cell[lines.size()][lines.get(0).length()];

		for (int i = 0; i < lines.size(); i++) {
			String nextLine = lines.get(i);

			for (int j = 0; j < nextLine.length(); j++) {
				grid[i][j] = new Cell(nextLine.charAt(j), i, j);

				if (grid[i][j].value != '.' && grid[i][j].value != '#') {
					posr = i;
					posc = j;
				}
			}
		}

		int incrr = -1, incrc = 0; // start facing up

		// make a list of original steps
		// check for a loop at each step if place an obstacle there
		// count good spots

		ArrayList<Cell> path = new ArrayList<Cell>();

		while (posr >= 0 && posr < grid.length && posc >= 0 && posc < grid[0].length) {

			int newPosr = posr + incrr;
			int newPosc = posc + incrc;

			if (newPosr < 0 || newPosr >= grid.length || newPosc < 0 || newPosc >= grid[0].length) { // done
				grid[posr][posc].traverse(incrr, incrc);
				path.add(grid[posr][posc]);
				break;
			}

			if (grid[newPosr][newPosc].isObstacle()) { // turn
				if (incrr == 0 && incrc == 1) {
					incrr = 1;
					incrc = 0;
				} else if (incrr == 1 && incrc == 0) {
					incrr = 0;
					incrc = -1;
				} else if (incrr == 0 && incrc == -1) {
					incrr = -1;
					incrc = 0;
				} else if (incrr == -1 && incrc == 0) {
					incrr = 0;
					incrc = 1;
				}
			} else { // step
				grid[posr][posc].traverse(incrr, incrc);
				path.add(grid[posr][posc]);
				posr = newPosr;
				posc = newPosc;
			}
		}

		HashSet<Cell> goodSpots = new HashSet<Cell>();

		for (int i = 1; i < path.size(); i++) {
			for (int j = 0; j < grid.length; j++) {
				for (int k = 0; k < grid[j].length; k++) {
					grid[j][k].reset();
				}
			}

			grid[path.get(i).row][path.get(i).col].value = '#';
			boolean loops = doesItLoop(grid, path.get(0));

			if (loops) {
				goodSpots.add(path.get(i));
			}
			grid[path.get(i).row][path.get(i).col].value = '.';
		}
		System.out.println("Possible spots: " + goodSpots.size());
	}

	private static boolean doesItLoop(Cell[][] grid, Cell start) {

		// check if loops
		int posr = start.row;
		int posc = start.col;
		int incrr = -1, incrc = 0; // start facing up

		while (posr >= 0 && posr < grid.length && posc >= 0 && posc < grid[0].length) {

			int newPosr = posr + incrr;
			int newPosc = posc + incrc;

			if (newPosr < 0 || newPosr >= grid.length || newPosc < 0 || newPosc >= grid[0].length) { // done
				boolean looped = grid[posr][posc].traverse(incrr, incrc);
				if (looped) {
					return true;
				}
				break;
			}

			if (grid[newPosr][newPosc].isObstacle()) { // turn
				if (incrr == 0 && incrc == 1) {
					incrr = 1;
					incrc = 0;
				} else if (incrr == 1 && incrc == 0) {
					incrr = 0;
					incrc = -1;
				} else if (incrr == 0 && incrc == -1) {
					incrr = -1;
					incrc = 0;
				} else if (incrr == -1 && incrc == 0) {
					incrr = 0;
					incrc = 1;
				}

			} else { // step
				boolean looped = grid[posr][posc].traverse(incrr, incrc);
				if (looped) {
					return true;
				}
				posr = newPosr;
				posc = newPosc;
			}
		}
		return false;
	}
}

class Cell {

	char value;

	int row, col;

	boolean up, down, left, right;

	public String toString() {
		return "[ " + row + ", " + col + " ]: " + value;
	}

	public Cell(Cell c) {
		this(c.value, c.row, c.col);
	}

	public Cell(char value, int r, int c) {

		this.value = value;

		row = r;

		col = c;

		reset();

	}

	public boolean isObstacle() {
		return value == '#';
	}

	public void reset() {
		up = down = left = right = false;
	}

	public boolean traverse(int incrr, int incrc) {

		boolean looped = false;

		if (incrr == 0 && incrc == 1) {

			looped = right;
			right = true;

		} else if (incrr == 1 && incrc == 0) {

			looped = down;
			down = true;

		} else if (incrr == 0 && incrc == -1) {

			looped = left;
			left = true;

		} else if (incrr == -1 && incrc == 0) {

			looped = up;
			up = true;

		}
		return looped;

	}

}
