package day15;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Day15 {

	public static WarehouseThing[][] warehouse;

	public static void main(String[] args) throws IOException {
		part1();
		part2();
	}

	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day15/input.txt")));

		ArrayList<String> lines = new ArrayList<String>();
		String line;
		while ((line = br.readLine()).length() > 0) {
			lines.add(line);
		}
		warehouse = new WarehouseThing[lines.size()][lines.get(0).length()];
		Robot r = null;
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				warehouse[i][j] = WarehouseThing.create(line.charAt(j), j, i);
				if (warehouse[i][j] != null && warehouse[i][j].isRobot()) {
					r = (Robot) warehouse[i][j];
				}
			}
		}
		String instr = "";
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "\n\r");
			instr += st.nextToken();
		}
		r.parseInstructions(instr);

		System.out.println("\nBefore: ");
		printWarehouse();
		r.process();
		System.out.println("\nAfter: ");
		printWarehouse();

		int total = 0;
		for (int i = 0; i < warehouse.length; i++) {
			for (int j = 0; j < warehouse[i].length; j++) {

				total += warehouse[i][j] == null ? 0 : warehouse[i][j].gps();
			}
		}
		System.out.println("total: " + total);

	}

	public static void printWarehouse() {
		for (int i = 0; i < warehouse.length; i++) {
			for (int j = 0; j < warehouse[i].length; j++) {

				if (warehouse[i][j] == null) {
					System.out.print(".");
				} else {
					System.out.print(warehouse[i][j]);
				}
			}
			System.out.println();
		}

	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day15/input.txt")));

		ArrayList<String> lines = new ArrayList<String>();
		String line;
		while ((line = br.readLine()).length() > 0) {
			lines.add(line);
		}
		warehouse = new WarehouseThing[lines.size()][lines.get(0).length() * 2];
		Robot r = null;
		for (int i = 0; i < lines.size(); i++) {
			line = lines.get(i);
			for (int j = 0; j < line.length(); j++) {
				WarehouseThing[] next = WarehouseThing.create2(line.charAt(j), 2 * j, i);
				warehouse[i][2 * j] = next[0];
				warehouse[i][2 * j + 1] = next[1];
				if (warehouse[i][2 * j] != null && warehouse[i][2 * j].isRobot()) {
					r = (Robot) warehouse[i][2 * j];
				}
			}
		}
		String instr = "";
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "\n\r");
			instr += st.nextToken();
		}
		r.parseInstructions(instr);

		System.out.println("\nBefore: ");
		printWarehouse();
		r.process();
		System.out.println("\nAfter: ");
		printWarehouse();

		long total = 0;
		for (int i = 0; i < warehouse.length; i++) {
			for (int j = 0; j < warehouse[i].length; j++) {

				total += warehouse[i][j] == null ? 0 : warehouse[i][j].gps();
			}
		}
		System.out.println("total: " + total);

	}

}

abstract class WarehouseThing {
	Point position;
	WarehouseThing[][] warehouse;

	public WarehouseThing(int x, int y) {
		position = new Point(x, y);
		this.warehouse = Day15.warehouse;
	}

	public void move(Point dir) {
		WarehouseThing dest = Day15.warehouse[position.y + dir.y][position.x + dir.x];
		if (dest == null) {
			Day15.warehouse[position.y][position.x] = null;
			position.x += dir.x;
			position.y += dir.y;
			Day15.warehouse[position.y][position.x] = this;
		} else if (dest.isWall()) {
			return;
		} else if (dest.isBoxLeft()) {
			Point destPos = new Point(dest.position);
			moveBox(dest, Day15.warehouse[position.y + dir.y][position.x + dir.x + 1], dir);
			if (destPos.equals(dest.position)) {
				return;
			} else {
				move(dir);
			}
		} else if (dest.isBoxRight()) {
			Point destPos = new Point(dest.position);
			moveBox(Day15.warehouse[position.y + dir.y][position.x + dir.x - 1], dest, dir);
			if (destPos.equals(dest.position)) {
				return;
			} else {
				move(dir);
			}

		} else if (dest.isBox()) {
			Point destPos = new Point(dest.position);
			dest.move(dir);
			if (destPos.equals(dest.position)) {
				return;
			} else {
				move(dir);
			}
		} else {
			throw new RuntimeException("WTF");
		}

	}

	public static void moveBox(WarehouseThing left, WarehouseThing right, Point dir) {
		if (canMoveBox(left, right, dir)) {
			if (dir == Point.UP || dir == Point.DOWN || dir == Point.LEFT) {
				Point destLPos = new Point(left.position);
				left.move(dir);
				if (destLPos.equals(left.position)) {
					return;
				} else {
					Point destRPos = new Point(right.position);
					right.move(dir);
					if (destRPos.equals(right.position)) {
						left.move(new Point(-dir.x, -dir.y));
					}
				}
			} else if (dir == Point.RIGHT) {
				Point destRPos = new Point(right.position);
				right.move(dir);
				if (destRPos.equals(right.position)) {
					return;
				} else {
					Point destLPos = new Point(left.position);
					left.move(dir);
					if (destLPos.equals(left.position)) {
						right.move(new Point(-dir.x, -dir.y));
					}
				}

			}
		}
	}

	protected static boolean canMoveBox(WarehouseThing left, WarehouseThing right, Point dir) {
		return canMove(left, dir) && canMove(right, dir);
	}

	protected static boolean canMove(WarehouseThing thing, Point dir) {
		WarehouseThing dest = Day15.warehouse[thing.position.y + dir.y][thing.position.x + dir.x];
		if (dest == null) {
			return true;

		} else if (dest.isWall()) {
			return false;
		} else if (dest.isBoxLeft()) {
			if (dir == Point.UP || dir == Point.DOWN) {
				return canMove(dest, dir) && canMove(Day15.warehouse[dest.position.y][dest.position.x + 1], dir);
			} else if (dir == Point.LEFT) {
				return canMove(dest, dir);
			} else {
				return canMove(Day15.warehouse[dest.position.y][dest.position.x + 1], dir);
			}

		} else if (dest.isBoxRight())
			if (dir == Point.UP || dir == Point.DOWN) {
				return canMove(dest, dir) && canMove(Day15.warehouse[dest.position.y][dest.position.x - 1], dir);
			} else if (dir == Point.RIGHT) {
				return canMove(dest, dir);
			} else {
				return canMove(Day15.warehouse[dest.position.y][dest.position.x - 1], dir);
			}
		else {
			return canMove(dest, dir);
		}

	}

	public int gps() {
		return 0;
	}

	public void parseInstructions(String instr) {

	}

	public void process() {
	}

	public static WarehouseThing create(char c, int x, int y) {
		if (c == '#') {
			return new Wall(x, y);
		} else if (c == '@') {
			return new Robot(x, y);
		} else if (c == 'O') {
			return new Box(x, y);
		} else {
			return null;
		}
	}

	public static WarehouseThing[] create2(char c, int x, int y) {
		WarehouseThing[] r = new WarehouseThing[2];

		if (c == '#') {
			r[0] = new Wall(x, y);
			r[1] = new Wall(x + 1, y);
		} else if (c == '@') {
			r[0] = new Robot(x, y);
			r[1] = null;
		} else if (c == 'O') {
			r[0] = new BoxLeft(x, y);
			r[1] = new BoxRight(x + 1, y);
		} else {
			r[0] = null;
			r[1] = null;
		}
		return r;
	}

	public boolean isRobot() {
		return false;
	}

	public boolean isBox() {
		return false;
	}

	public boolean isWall() {
		return false;
	}

	public boolean isBoxLeft() {
		return false;
	}

	public boolean isBoxRight() {
		return false;
	}

	public String toString() {
		if (isWall()) {
			return "#";
		} else if (isBoxLeft()) {
			return "[";
		} else if (isBoxRight()) {
			return "]";
		} else if (isBox()) {
			return "O";
		} else if (isRobot()) {
			return "@";
		} else {
			return "?";
		}
	}
}

class Wall extends WarehouseThing {
	public Wall(int x, int y) {
		super(x, y);
	}

	public boolean isWall() {
		return true;
	}

	public void move(Point dir) {

	}
}

class Robot extends WarehouseThing {
	Point[] instructions;

	public Robot(int x, int y) {
		super(x, y);
	}

	public boolean isRobot() {
		return true;
	}

	public void parseInstructions(String instr) {
		instructions = new Point[instr.length()];
		for (int i = 0; i < instructions.length; i++) {
			instructions[i] = Point.getDir(instr.charAt(i));
		}
	}

	public void process() {
		for (int i = 0; i < instructions.length; i++) {
			if (canMove(this, instructions[i])) {
				move(instructions[i]);
			}
		}
	}

}

class Box extends WarehouseThing {
	public Box(int x, int y) {
		super(x, y);
	}

	public boolean isBox() {
		return true;
	}

	public int gps() {
		return position.x + position.y * 100;
	}

}

class BoxLeft extends Box {
	public BoxLeft(int x, int y) {
		super(x, y);
	}

	public boolean isBoxLeft() {
		return true;
	}
}

class BoxRight extends Box {
	public BoxRight(int x, int y) {
		super(x, y);
	}

	public boolean isBoxRight() {
		return true;
	}

	public int gps() {
		return 0;
	}

}

class Point {
	public static final Point UP = new Point(0, -1);
	public static final Point DOWN = new Point(0, 1);
	public static final Point LEFT = new Point(-1, 0);
	public static final Point RIGHT = new Point(1, 0);

	public static Point getDir(char c) {
		if (c == '^') {
			return UP;
		} else if (c == '<') {
			return LEFT;
		} else if (c == '>') {
			return RIGHT;
		} else if (c == 'v') {
			return DOWN;
		} else {
			return null;
		}
	}

	int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

	public boolean equals(Object o) {
		return o != null && ((Point) o).x == this.x && ((Point) o).y == this.y;
	}

	public String toString() {
		return x + ", " + y;
	}
}