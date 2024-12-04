package day4;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Day4 {

	public static void main(String[] args) throws IOException {

//		partOne();

		partTwo();
	}

	private static char[][] buildGrid() throws IOException {
		String content = new Scanner(new File("src/day4/input.txt")).useDelimiter("\\Z").next();
		StringTokenizer st = new StringTokenizer(content, "\n\r");
		String firstLine = st.nextToken();
		char grid[][] = new char[st.countTokens() + 1][firstLine.length()];
		grid[0] = firstLine.toCharArray();
		for (int i = 1; i < grid.length; i++) {
			grid[i] = st.nextToken().toCharArray();
		}
		return grid;
	}

	private static void partOne() throws IOException {
		int total = 0;
		char[][] grid = buildGrid();

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 'X') {
					total += countXmas(grid, i, j);
				}
			}
		}
		System.out.println("Total: " + total);
	}

	private static int countXmas(char[][] grid, int row, int col) {

		int count = 0;
		boolean roomLeft = col >= 3, roomRight = col <= grid[0].length - 4, roomUp = row >= 3,
				roomDown = row <= grid.length - 4;
		if (roomLeft) {// check left
			if (grid[row][col] == 'X' && grid[row][col - 1] == 'M' && grid[row][col - 2] == 'A'
					&& grid[row][col - 3] == 'S') {
				count++;
			}
		}
		if (roomRight) {// check right
			if (grid[row][col] == 'X' && grid[row][col + 1] == 'M' && grid[row][col + 2] == 'A'
					&& grid[row][col + 3] == 'S') {
				count++;
			}
		}
		if (roomDown) { // check down
			if (grid[row][col] == 'X' && grid[row + 1][col] == 'M' && grid[row + 2][col] == 'A'
					&& grid[row + 3][col] == 'S') {
				count++;
			}

		}
		if (roomUp) { // check up
			if (grid[row][col] == 'X' && grid[row - 1][col] == 'M' && grid[row - 2][col] == 'A'
					&& grid[row - 3][col] == 'S') {
				count++;
			}

		}
		if (roomLeft && roomUp) {
			if (grid[row][col] == 'X' && grid[row - 1][col - 1] == 'M' && grid[row - 2][col - 2] == 'A'
					&& grid[row - 3][col - 3] == 'S') {
				count++;
			}

		}
		if (roomRight && roomUp) {
			if (grid[row][col] == 'X' && grid[row - 1][col + 1] == 'M' && grid[row - 2][col + 2] == 'A'
					&& grid[row - 3][col + 3] == 'S') {
				count++;
			}

		}
		if (roomLeft && roomDown) {
			if (grid[row][col] == 'X' && grid[row + 1][col - 1] == 'M' && grid[row + 2][col - 2] == 'A'
					&& grid[row + 3][col - 3] == 'S') {
				count++;
			}

		}
		if (roomRight && roomDown) {
			if (grid[row][col] == 'X' && grid[row + 1][col + 1] == 'M' && grid[row + 2][col + 2] == 'A'
					&& grid[row + 3][col + 3] == 'S') {
				count++;
			}

		}

		return count;
	}

	private static void partTwo() throws IOException {
		int total = 0;
		char[][] grid = buildGrid();

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 'A') {
					total += countMas(grid, i, j);
				}
			}
		}
		System.out.println("Total: " + total);
	}

	private static int countMas(char[][] grid, int row, int col) {

		int count = 0;
		boolean roomLeft = col >= 1, roomRight = col < grid[0].length - 1, roomUp = row >= 1,
				roomDown = row < grid.length - 1;
		if (!roomLeft || !roomRight || !roomUp || !roomDown) {
			return 0;
		}

		if (grid[row][col] == 'A'

				&& (((grid[row - 1][col - 1] == 'M' && grid[row + 1][col + 1] == 'S')
						|| (grid[row - 1][col - 1] == 'S' && grid[row + 1][col + 1] == 'M'))
						&& ((grid[row + 1][col - 1] == 'M' && grid[row - 1][col + 1] == 'S')
								|| (grid[row + 1][col - 1] == 'S' && grid[row - 1][col + 1] == 'M')))) {
			count++;
		}

		return count;
	}

}
