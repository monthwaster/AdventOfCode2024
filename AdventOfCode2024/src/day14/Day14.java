package day14;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Day14 {

	public static void main(String[] args) throws IOException {
		part1();
		part2();
	}

//x/cols = 101
//y/rows = 103
	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day14/input.txt")));

		String line;
		int nw, ne, sw, se;
		nw = ne = sw = se = 0;
		// p=62,20 v=85,-14
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "p=, v");
			Guard g = new Guard(Integer.valueOf(st.nextToken()), Integer.valueOf(st.nextToken()),
					Integer.valueOf(st.nextToken()), Integer.valueOf(st.nextToken()));

			g.advance(100, 101, 103);
			if (g.posx < 50 && g.posy < 51) {
				nw++;
			} else if (g.posx > 50 && g.posy < 51) {
				ne++;
			} else if (g.posx < 50 && g.posy > 51) {
				sw++;
			} else if (g.posx > 50 && g.posy > 51) {
				se++;
			}
		}

		System.out.println("Total safety factor:" + (nw * ne * sw * se));

	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day14/input.txt")));

		ArrayList<Guard> al = new ArrayList<Guard>();

		String line;
		int nw, ne, sw, se;
		nw = ne = sw = se = 0;
		// p=62,20 v=85,-14
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "p=, v");
			al.add(new Guard(Integer.valueOf(st.nextToken()), Integer.valueOf(st.nextToken()),
					Integer.valueOf(st.nextToken()), Integer.valueOf(st.nextToken())));
		}

		for (int i = 1; i < 77698; i++) {
			int colCount[] = new int[101];
			int rowCount[] = new int[103];
			for (int j = 0; j < al.size(); j++) {
				al.get(j).advance(1, 101, 103);
				colCount[al.get(j).posx]++;
				rowCount[al.get(j).posy]++;
			}
			int bigCols = 0;
			for (int j = 0; j < colCount.length; j++) {
				if (colCount[j] > 32) {
					bigCols++;
				}
			}
			int bigRows = 0;
			for (int j = 0; j < rowCount.length; j++) {
				if (rowCount[j] > 30) {
					bigRows++;
				}
			}

			if (bigCols >= 2 && bigRows >= 2) {
				System.out.println("Step:" + i);
				printGrid(al, 101, 103);
				break;
			}
		}
	}

	private static void printGrid(ArrayList<Guard> al, int cols, int rows) {
		char grid[][] = new char[rows][cols];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = '.';
			}
		}
		for (int i = 0; i < al.size(); i++) {
			al.get(i).plot(grid);
		}
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}

}

class Guard {
	int posx, posy, velx, vely;

	public Guard(int x, int y, int vx, int vy) {
		super();
		posx = x;
		posy = y;
		velx = vx;
		vely = vy;
	}

	// x is cols, y is rows
	public void advance(int steps, int maxx, int maxy) {
		for (int i = 0; i < steps; i++) {
			posx = posx + velx;
			if (posx < 0) {
				posx = maxx + posx;

			} else if (posx >= maxx) {
				posx -= maxx;
			}
			posy = posy + vely;
			if (posy < 0) {
				posy = maxy + posy;

			} else if (posy >= maxy) {
				posy -= maxy;
			}
		}
	}

	public void plot(char[][] grid) {
		grid[posy][posx] = 'X';
	}

}