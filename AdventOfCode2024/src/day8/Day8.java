package day8;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Day8 {

	public static void main(String args[]) throws IOException {

		part1();

		part2();
	}

	private static void part1() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day8/input.txt")));
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		int total = 0;

		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		char[][] grid = new char[lines.size()][lines.get(0).length()];
		char[][] markedGrid = new char[lines.size()][lines.get(0).length()];

		for (int i = 0; i < lines.size(); i++) {
			String nextLine = lines.get(i);

			for (int j = 0; j < nextLine.length(); j++) {
				grid[i][j] = nextLine.charAt(j);
				markedGrid[i][j] = grid[i][j];
			}
		}

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				char cur = grid[i][j];
				if (cur == '.' || cur == '#') {
					continue;
				} else {
					for (int k = i; k < grid.length; k++) {
						for (int l = 0; l < grid[k].length; l++) {
							if (k == i && l <= j) {
								continue;
							}
							if (grid[i][j] == grid[k][l]) {
								if (i == k) {// right
									int dist = l - j;
									if (j - dist >= 0) {
										markedGrid[i][j - dist] = '#';
									}
									if (l + dist < grid[i].length) {
										markedGrid[i][l + dist] = '#';
									}
								} else if (i < k && j == l) {// down
									int dist = k - i;
									if (i - dist >= 0) {
										markedGrid[i - dist][j] = '#';
									}
									if (k + dist < grid.length) {
										markedGrid[k + dist][j] = '#';
									}

								} else if (i < k && j > l) {// downleft
									int distr = k - i;
									int distc = j - l;

									if (i - distr >= 0 && j + distc < grid[i].length) {
										markedGrid[i - distr][j + distc] = '#';
									}
									if (k + distr < grid.length && l - distc >= 0) {
										markedGrid[k + distr][l - distc] = '#';
									}
								} else if (i < k && j < l) {// downright
									int distr = k - i;
									int distc = l - j;

									if (i - distr >= 0 && j - distc >= 0) {
										markedGrid[i - distr][j - distc] = '#';
									}
									if (k + distr < grid.length && l + distc < grid[i].length) {
										markedGrid[k + distr][l + distc] = '#';
									}

								} else {
									throw new RuntimeException("WTF");
								}
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < markedGrid.length; i++) {
			for (int j = 0; j < markedGrid[i].length; j++) {
				if (markedGrid[i][j] == '#') {
					total++;
				}
			}
		}
		System.out.println("Total: " + total);

	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day8/input.txt")));
		ArrayList<String> lines = new ArrayList<String>();
		String line;
		int total = 0;

		while ((line = br.readLine()) != null) {
			lines.add(line);
		}

		char[][] grid = new char[lines.size()][lines.get(0).length()];
		char[][] markedGrid = new char[lines.size()][lines.get(0).length()];
		boolean[][] matched = new boolean[lines.size()][lines.get(0).length()];
		
		for (int i = 0; i < lines.size(); i++) {
			String nextLine = lines.get(i);

			for (int j = 0; j < nextLine.length(); j++) {
				grid[i][j] = nextLine.charAt(j);
				markedGrid[i][j] = grid[i][j];
				matched[i][j] = false;
			}
		}

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				char cur = grid[i][j];
				if (cur == '.' || cur == '#') {
					continue;
				} else {
					for (int k = i; k < grid.length; k++) {
						for (int l = 0; l < grid[k].length; l++) {
							if (k == i && l <= j) {
								continue;
							}
							if (grid[i][j] == grid[k][l]) {
								matched[i][j] = true;
								matched[k][l] = true;
								
								if (i == k) {// right
									int dist = l - j;
									for (int incrDist = dist; j - incrDist >= 0; incrDist += dist) {
										markedGrid[i][j - incrDist] = '#';
									}
									for (int incrDist = dist; l + dist < grid[i].length; incrDist += dist) {
										markedGrid[i][l + incrDist] = '#';
									}
								} else if (i < k && j == l) {// down
									int dist = k - i;
									for (int incrDist = dist; i - incrDist >= 0; incrDist += dist) {
										markedGrid[i - incrDist][j] = '#';
									}
									for (int incrDist = dist; k + incrDist < grid.length; incrDist += dist) {
										markedGrid[k + incrDist][j] = '#';
									}

								} else if (i < k && j > l) {// downleft
									int distr = k - i;
									int distc = j - l;

									for (int incrDistr = distr, incrDistc = distc; i - incrDistr >= 0
											&& j + incrDistc < grid[i].length; incrDistr += distr, incrDistc += distc) {
										markedGrid[i - incrDistr][j + incrDistc] = '#';
									}
									for (int incrDistr = distr, incrDistc = distc; k + incrDistr < grid.length
											&& l - incrDistc >= 0; incrDistr += distr, incrDistc += distc) {
										markedGrid[k + incrDistr][l - incrDistc] = '#';
									}

								} else if (i < k && j < l) {// downright
									int distr = k - i;
									int distc = l - j;

									for (int incrDistr = distr, incrDistc = distc; i - incrDistr >= 0
											&& j - incrDistc >= 0; incrDistr += distr, incrDistc += distc) {
										markedGrid[i - incrDistr][j - incrDistc] = '#';
									}
									for (int incrDistr = distr, incrDistc = distc; k + incrDistr < grid.length
											&& l + incrDistc < grid[i].length; incrDistr += distr, incrDistc += distc) {
										markedGrid[k + incrDistr][l + incrDistc] = '#';
									}

								} else {
									throw new RuntimeException("WTF");
								}
							}
						}
					}
				}
			}
		}

		for (int i = 0; i < markedGrid.length; i++) {
			for (int j = 0; j < markedGrid[i].length; j++) {
				if (markedGrid[i][j] == '#') {
					total++;
				}else if(matched[i][j]) {
					total++;
				}
			}
		}
		System.out.println("Total: " + total);

	}
}
