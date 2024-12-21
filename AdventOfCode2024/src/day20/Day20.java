package day20;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Day20 {

	public static void main(String[] args) throws IOException {
		part1();
		part2();
	}

	private static MazePoint[][] maze;

	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day20/input.txt")));

		ArrayList<String> lines = new ArrayList<String>();

		String line;

		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		maze = new MazePoint[lines.size()][lines.get(0).length()];

		MazePoint end = null;

		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				maze[i][j] = new MazePoint(i, j, line.charAt(j));
				if (maze[i][j].isEnd()) {
					end = maze[i][j];
				}
			}
		}

		printMaze();
		calculate(end);

		int goodCheatsFound = 0;

		for (int i = 1; i < maze.length - 1; i++) {
			for (int j = 1; j < maze.length - 1; j++) {
				if (maze[i][j].isWall()) {
					ArrayList<MazePoint> adjacent = new ArrayList<MazePoint>();
					if (maze[i - 1][j].isSpace()) {
						adjacent.add(maze[i - 1][j]);
					}
					if (maze[i + 1][j].isSpace()) {
						adjacent.add(maze[i + 1][j]);
					}
					if (maze[i][j - 1].isSpace()) {
						adjacent.add(maze[i][j - 1]);
					}
					if (maze[i][j + 1].isSpace()) {
						adjacent.add(maze[i][j + 1]);
					}
					if (adjacent.size() >= 2) {
						for (int k = 0; k < adjacent.size() - 1; k++) {
							for (int l = k + 1; l < adjacent.size(); l++) {
								if (Math.abs(adjacent.get(k).costToEnd - adjacent.get(l).costToEnd) >= 102) {
									// include the 2 steps for traversing the cheat
									goodCheatsFound++;
								}
							}
						}
					}
				}
			}
		}
		System.out.println("Good cheats: " + goodCheatsFound);

	}

	private static void printMaze() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				System.out.print(maze[i][j].symbol);
			}
			System.out.println();
		}
	}

	private static void calculate(MazePoint next) {

		ConcurrentLinkedQueue<MazePoint> queue = new ConcurrentLinkedQueue<MazePoint>();

		queue.add(next);

		while (!queue.isEmpty()) {
			MazePoint point = queue.remove();

			if (point.isStart()) {
				// yay
			}
			if (point.isEnd()) {
				point.costToEnd = 0;
			}

			if (point.row > 0) {

				MazePoint prev = maze[point.row - 1][point.col];

				if (!prev.isWall() && !prev.isEnd()) {
					int newCost = point.costToEnd + 1;
					prev.downCost = newCost;
					if (prev.costToEnd < 0 || newCost < prev.costToEnd) {
						prev.costToEnd = newCost;
						prev.dirToEnd = MazePoint.direction.down;
						queue.add(prev);
					}

				}
			}

			if (point.row < maze.length - 1) {

				MazePoint prev = maze[point.row + 1][point.col];

				if (!prev.isWall() && !prev.isEnd()) {
					int newCost = point.costToEnd + 1;
					prev.upCost = newCost;

					if (prev.costToEnd < 0 || newCost < prev.costToEnd) {
						prev.costToEnd = newCost;
						prev.dirToEnd = MazePoint.direction.up;
						queue.add(prev);
					}
				}
			}

			if (point.col < maze[0].length - 1) {
				MazePoint prev = maze[point.row][point.col + 1];

				if (!prev.isWall() && !prev.isEnd()) {
					int newCost = point.costToEnd + 1;
					prev.leftCost = newCost;

					if (prev.costToEnd < 0 || newCost < prev.costToEnd) {
						prev.costToEnd = newCost;
						prev.dirToEnd = MazePoint.direction.left;
						queue.add(prev);
					}
				}
			}

			if (point.col > 0) {
				MazePoint prev = maze[point.row][point.col - 1];

				if (!prev.isWall() && !prev.isEnd()) {
					int newCost = point.costToEnd + 1;
					prev.rightCost = newCost;

					if (prev.costToEnd < 0 || newCost < prev.costToEnd) {
						prev.costToEnd = newCost;
						prev.dirToEnd = MazePoint.direction.right;
						queue.add(prev);
					}
				}
			}
		}
	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day20/input.txt")));

		ArrayList<String> lines = new ArrayList<String>();

		String line;

		while ((line = br.readLine()) != null) {
			lines.add(line);
		}
		maze = new MazePoint[lines.size()][lines.get(0).length()];

		MazePoint end = null;

		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				maze[i][j] = new MazePoint(i, j, line.charAt(j));
				if (maze[i][j].isEnd()) {
					end = maze[i][j];
				}
			}
		}

		printMaze();
		calculate(end);
		int costToEndNoCheat = maze[1][1].costToEnd;
		System.out.println("cost with no cheats: " + costToEndNoCheat);
		int goodCheatsFound = 0;

		HashSet<String> foundRoutes = new HashSet<String>();

		for (int i = 1; i < maze.length - 1; i++) {
			for (int j = 1; j < maze.length - 1; j++) {
				if (maze[i][j].isSpace()) {
					for (int k = 1; k <= i + 20 && k < maze.length; k++) {
						for (int l = 1; l <= j + 20 && l < maze[k].length; l++) {
							if (maze[k][l].isSpace()) {
								int dist = Math.abs(k - i) + Math.abs(l - j);
								if (dist <= 20) {
									if (Math.abs(maze[i][j].costToEnd - maze[k][l].costToEnd) - dist >= 100) {
										String key = maze[i][j].posString() + ":" + maze[k][l].posString();
										if (!foundRoutes.contains(key)) {
											foundRoutes.add(key);
											goodCheatsFound++;
										}

									}
								}
							}
						}
					}

				}
			}
		}

		System.out.println("Good cheats: " + goodCheatsFound / 2);

	}

}

class MazePoint {

	int row, col;

	char symbol;

	int costToEnd;

	direction dirToEnd;

	int upCost, downCost, leftCost, rightCost;

	static enum direction {

		up, down, left, right

	}

	public MazePoint(int row, int col, char symbol) {

		this.row = row;

		this.col = col;

		this.symbol = symbol;

		costToEnd = -1;

		upCost = downCost = leftCost = rightCost = -1;

	}

	public boolean isStart() {

		return symbol == 'S';

	}

	public boolean isEnd() {

		return symbol == 'E';

	}

	public boolean isWall() {

		return symbol == '#';

	}

	public boolean isSpace() {
		return symbol == '.' || isStart() || isEnd();
	}

	public void reset() {
		costToEnd = -1;

		upCost = downCost = leftCost = rightCost = -1;

	}

	public String posString() {
		return row + "," + col;
	}
}
