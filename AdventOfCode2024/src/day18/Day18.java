package day18;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Day18 {

	public static void main(String[] args) throws IOException {
		part1();
		part2();
	}

	private static MazePoint[][] maze;

	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day18/sample.txt")));

		ArrayList<int[]> drops = new ArrayList<int[]>();

		String line;

		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ",");
			drops.add(new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) });
		}
//		buildMaze(71, 71);
		buildMaze(7, 7);
		printMaze();
//		for (int i = 0; i < 1024; i++) {
		for (int i = 0; i < 12; i++) {
			int[] dropLoc = drops.get(i);
			maze[dropLoc[1] + 1][dropLoc[0] + 1].symbol = '#';
		}
		printMaze();
		calculate(maze[maze.length - 2][maze[0].length - 2]);
		System.out.println("output: " + maze[1][1].costToEnd);
	}

	private static void buildMaze(int rows, int cols) {
		maze = new MazePoint[rows + 2][cols + 2];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				if (i == 0 || j == 0 || j == maze[0].length - 1 || i == maze.length - 1) {
					maze[i][j] = new MazePoint(i, j, '#');
				} else if (i == 1 && j == 1) {
					maze[i][j] = new MazePoint(i, j, 'S');

				} else if (i == maze.length - 2 && j == maze[0].length - 2) {
					maze[i][j] = new MazePoint(i, j, 'E');
				} else {
					maze[i][j] = new MazePoint(i, j, '.');
				}
			}
		}
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

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day18/input.txt")));

		ArrayList<int[]> drops = new ArrayList<int[]>();

		String line;

		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, ",");
			drops.add(new int[] { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) });
		}
		buildMaze(71, 71);
//		buildMaze(7, 7);
		printMaze();
		for (int i = 0; i < drops.size(); i++) {
			int[] dropLoc = drops.get(i);
			maze[dropLoc[1] + 1][dropLoc[0] + 1].symbol = '#';
			calculate(maze[maze.length - 2][maze[0].length - 2]);
			if (maze[1][1].costToEnd < 0) {
				printMaze();
				System.out.println("Failed after: " + dropLoc[0] + "," + dropLoc[1]);
				break;
			}
			reset();
		}
	}

	private static void reset() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				maze[i][j].reset();
			}
		}
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

	public void reset() {
		costToEnd = -1;

		upCost = downCost = leftCost = rightCost = -1;

	}
}
