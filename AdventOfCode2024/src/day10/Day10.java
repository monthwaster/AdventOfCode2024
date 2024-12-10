package day10;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

public class Day10 {

	public static void main(String args[]) throws IOException {

		part1();
		part2();
	}

	private static Node[][] ingest() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day10/input.txt")));
		ArrayList<String> lines = new ArrayList<String>();
		String line;

		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		Node[][] grid = new Node[lines.size()][lines.get(0).length()];

		for (int i = 0; i < lines.size(); i++) {
			String l = lines.get(i);
			for (int j = 0; j < l.length(); j++) {
				grid[i][j] = new Node(Character.getNumericValue(l.charAt(j)), i, j);
			}
		}
		return grid;
	}

	private static void part1() throws IOException {
		Node[][] grid = ingest();
		int total = 0;

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].value == 0) {
					total += grid[i][j].findNines(grid).size();
				}
			}
		}
		System.out.println("Total 1: " + total);

	}

	private static void part2() throws IOException {
		Node[][] grid = ingest();
		int total = 0;

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j].value == 0) {
					total += grid[i][j].findPaths(grid);
				}
			}
		}
		System.out.println("Total 2: " + total);
	}
}

class Node {
	int value, row, col;
	private HashSet<Node> reachableNines;
	int paths;

	public Node(int value, int row, int col) {
		this.value = value;
		this.row = row;
		this.col = col;
		paths = -1;
	}

	public HashSet<Node> findNines(Node[][] grid) {

		if (reachableNines == null) {
			reachableNines = new HashSet<Node>();
			if (value == 9) {
				reachableNines.add(this);
			} else {
				if (row > 0 && grid[row - 1][col].value == value + 1) {
					reachableNines.addAll(grid[row - 1][col].findNines(grid));
				}
				if (row < grid.length - 1 && grid[row + 1][col].value == value + 1) {
					reachableNines.addAll(grid[row + 1][col].findNines(grid));
				}
				if (col > 0 && grid[row][col - 1].value == value + 1) {
					reachableNines.addAll(grid[row][col - 1].findNines(grid));
				}
				if (col < grid[row].length - 1 && grid[row][col + 1].value == value + 1) {
					reachableNines.addAll(grid[row][col + 1].findNines(grid));
				}
			}
		}
		return reachableNines;
	}

	public int findPaths(Node[][] grid) {
		if (paths == -1) {
			if (value == 9) {
				return 1;
			} else {
				paths = 0;
				if (row > 0 && grid[row - 1][col].value == value + 1) {
					paths += grid[row - 1][col].findPaths(grid);
				}
				if (row < grid.length - 1 && grid[row + 1][col].value == value + 1) {
					paths += grid[row + 1][col].findPaths(grid);
				}
				if (col > 0 && grid[row][col - 1].value == value + 1) {
					paths += grid[row][col - 1].findPaths(grid);
				}
				if (col < grid[row].length - 1 && grid[row][col + 1].value == value + 1) {
					paths += grid[row][col + 1].findPaths(grid);
				}

			}
		}
		return paths;
	}

}