
package day16;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Day16 {

	public static void main(String[] args) throws IOException {

		part1();
		part2();

	}

	protected static MazePoint[][] maze;

	private static void part1() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day16/input.txt")));

		ArrayList<String> lines = new ArrayList<String>();

		String line;

		while ((line = br.readLine()) != null) {

			lines.add(line);

		}

		maze = new MazePoint[lines.size()][lines.get(0).length()];

		MazePoint end = null;
		MazePoint start = null;

		for (int i = 0; i < lines.size(); i++) {

			line = lines.get(i);

			for (int j = 0; j < line.length(); j++) {

				maze[i][j] = new MazePoint(i, j, line.charAt(j));

				if (maze[i][j].isEnd()) {

					end = maze[i][j];

				} else if (maze[i][j].isStart()) {
					start = maze[i][j];
				}

			}

		}

		calculate(end);
		int cost = start.costToEnd + calcCost(MazePoint.direction.right, start.dirToEnd);
		System.out.println("Cost: " + cost);

	}

	private static void part2() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day16/input.txt")));
		ArrayList<String> lines = new ArrayList<String>();

		String line;

		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		maze = new MazePoint[lines.size()][lines.get(0).length()];

		MazePoint end = null;
		MazePoint start = null;

		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				maze[i][j] = new MazePoint(i, j, line.charAt(j));
				if (maze[i][j].isEnd()) {
					end = maze[i][j];
				} else if (maze[i][j].isStart()) {
					start = maze[i][j];
				}
			}
		}
		calculate(end);

		boolean[][] marks = new boolean[maze.length][maze[0].length];
		markPaths(start, MazePoint.direction.right, marks);
		int total = 0;
		for (int i = 0; i < marks.length; i++) {
			for (int j = 0; j < marks[i].length; j++) {
				if (marks[i][j]) {
					System.out.print("X");
					total++;
				} else {
					System.out.print(maze[i][j].symbol + "");
				}
			}
			System.out.println();
		}

		System.out.println("Total: " + total);

	}

	private static void printMaze() {
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[0].length; j++) {
				System.out.print(maze[i][j].symbol);
			}
			System.out.println();
		}
	}

	private static void markPaths(MazePoint start, MazePoint.direction dir, boolean[][] marks) {
		if (start.isWall()) {
			return;
		}
		marks[start.row][start.col] = true;
		if (start.isEnd()) {
			return;
		} else {

			int actualDownCost = start.downCost < 0 ? Integer.MAX_VALUE
					: start.downCost + (dir == MazePoint.direction.down ? 0
							: dir == MazePoint.direction.left ? 1000 : dir == MazePoint.direction.right ? 1000 : 2000);
			int actualUpCost = start.upCost < 0 ? Integer.MAX_VALUE
					: start.upCost + (dir == MazePoint.direction.down ? 2000
							: dir == MazePoint.direction.left ? 1000 : dir == MazePoint.direction.right ? 1000 : 0);
			int actualRightCost = start.rightCost < 0 ? Integer.MAX_VALUE
					: start.rightCost + (dir == MazePoint.direction.down ? 1000
							: dir == MazePoint.direction.left ? 2000 : dir == MazePoint.direction.right ? 0 : 1000);
			int actualLeftCost = start.leftCost < 0 ? Integer.MAX_VALUE
					: start.leftCost + (dir == MazePoint.direction.down ? 1000
							: dir == MazePoint.direction.left ? 0 : dir == MazePoint.direction.right ? 2000 : 1000);

			int actualCostToEnd = Math.min(actualRightCost,
					Math.min(actualLeftCost, Math.min(actualUpCost, actualDownCost)));

			if (actualCostToEnd == actualDownCost) {
				markPaths(maze[start.row + 1][start.col], MazePoint.direction.down, marks);
			}
			if (actualCostToEnd == actualUpCost) {
				markPaths(maze[start.row - 1][start.col], MazePoint.direction.up, marks);
			}
			if (actualCostToEnd == actualRightCost) {
				markPaths(maze[start.row][start.col + 1], MazePoint.direction.right, marks);
			}
			if (actualCostToEnd == actualLeftCost) {
				markPaths(maze[start.row][start.col - 1], MazePoint.direction.left, marks);
			}

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
					int newCost = point.costToEnd + 1 + calcCost(MazePoint.direction.down, point.dirToEnd);
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
					int newCost = point.costToEnd + 1 + calcCost(MazePoint.direction.up, point.dirToEnd);
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
					int newCost = point.costToEnd + 1 + calcCost(MazePoint.direction.left, point.dirToEnd);
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
					int newCost = point.costToEnd + 1 + calcCost(MazePoint.direction.right, point.dirToEnd);
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

	private static int calcCost(MazePoint.direction from, MazePoint.direction to) {

		if (from == to || to == null) {

			return 0;

		} else if ((from == MazePoint.direction.up && to == MazePoint.direction.down)

				|| (to == MazePoint.direction.up && from == MazePoint.direction.down)) {

			return 2000;

		} else {

			return 1000;

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
}