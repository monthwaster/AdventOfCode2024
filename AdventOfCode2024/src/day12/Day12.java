package day12;



import java.io.BufferedReader;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStreamReader;

import java.util.ArrayList;

import java.util.Collections;



public class Day12 {



	public static void main(String args[]) throws IOException {

		part1();

		part2();

	}



	private static void part1() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day12/input.txt")));



		ArrayList<String> lines = new ArrayList<String>();



		String line;

		while ((line = br.readLine()) != null) {

			lines.add(line);

		}



		Plot[][] plots = new Plot[lines.size()][lines.get(0).length()];



		for (int i = 0; i < lines.size(); i++) {

			line = lines.get(i);

			for (int j = 0; j < line.length(); j++) {

				plots[i][j] = new Plot(line.charAt(j), i, j);

			}

		}

		ArrayList<Area> areas = findAreas(plots);



		int total = 0;

		for (int i = 0; i < areas.size(); i++) {

			total += areas.get(i).price();

		}

		System.out.println("Total price:" + total);

	}

	private static void part2() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day12/input.txt")));



		ArrayList<String> lines = new ArrayList<String>();



		String line;

		while ((line = br.readLine()) != null) {

			lines.add(line);

		}



		Plot[][] plots = new Plot[lines.size()][lines.get(0).length()];



		for (int i = 0; i < lines.size(); i++) {

			line = lines.get(i);

			for (int j = 0; j < line.length(); j++) {

				plots[i][j] = new Plot(line.charAt(j), i, j);

			}

		}

		ArrayList<Area> areas = findAreas(plots);



		int total = 0;

		for (int i = 0; i < areas.size(); i++) {

			total += areas.get(i).bulkPrice();

		}

		System.out.println("Total price:" + total);

	}



	private static ArrayList<Area> findAreas(Plot[][] plots) {

		ArrayList<Area> areas = new ArrayList<Area>();



		for (int i = 0; i < plots.length; i++) {

			for (int j = 0; j < plots[i].length; j++) {

				if (!plots[i][j].partOfArea) {

					Area a = new Area();

					findFrom(plots, i, j, a);

					areas.add(a);

				}

			}

		}

		return areas;

	}



	private static void findFrom(Plot[][] plots, int r, int c, Area a) {

		Plot start = plots[r][c];

		start.partOfArea = true;

		a.plots.add(start);

		if (c > 0 && !plots[r][c - 1].partOfArea && plots[r][c - 1].type == start.type) {

			findFrom(plots, r, c - 1, a);

		}

		if (c < plots[r].length - 1 && !plots[r][c + 1].partOfArea && plots[r][c + 1].type == start.type) {

			findFrom(plots, r, c + 1, a);

		}

		if (r > 0 && !plots[r - 1][c].partOfArea && plots[r - 1][c].type == start.type) {

			findFrom(plots, r - 1, c, a);

		}

		if (r < plots.length - 1 && !plots[r + 1][c].partOfArea && plots[r + 1][c].type == start.type) {

			findFrom(plots, r + 1, c, a);

		}

	}

}



class Plot {

	char type;

	boolean partOfArea;

	int r, c;



	boolean hasN, hasS, hasE, hasW;

	boolean countedN, countedS, countedE, countedW;



	public Plot(char t, int r, int c) {

		type = t;

		this.r = r;

		this.c = c;

		partOfArea = false;

		hasN = hasS = hasE = hasW = countedN = countedS = countedE = countedW = false;

	}

}



class Area {

	ArrayList<Plot> plots;



	public Area() {

		plots = new ArrayList<Plot>();

	}



	public int price() {

		int area = plots.size();



		int perimeter = 0;



		for (int i = 0; i < plots.size(); i++) {

			Plot p = plots.get(i);

			perimeter += 4;

			for (int j = 0; j < plots.size(); j++) {

				Plot p2 = plots.get(j);

				int rDiff = Math.abs(p.r - p2.r);

				int cDiff = Math.abs(p.c - p2.c);

				if ((rDiff == 0 && cDiff == 1) || (rDiff == 1 && cDiff == 0)) {

					perimeter--;

				}

			}



		}

//		System.out.println(plots.get(0).type + ": " + area + " * " + perimeter + " = " + (area * perimeter));

		return area * perimeter;

	}



	public int bulkPrice() {

		ArrayList<Plot> edgePlots = new ArrayList<Plot>();

		for (int i = 0; i < plots.size(); i++) {

			Plot p = plots.get(i);

			boolean n, s, e, w;

			n = s = e = w = false;

			for (int j = 0; j < plots.size(); j++) {

				Plot p2 = plots.get(j);

				if (p2.r == p.r - 1 && p2.c == p.c) {

					n = true;

				} else if (p2.r == p.r + 1 && p2.c == p.c) {

					s = true;

				} else if (p2.r == p.r && p2.c == p.c - 1) {

					w = true;

				} else if (p2.r == p.r && p2.c == p.c + 1) {

					e = true;

				}

			}

			if (!n || !e || !s || !w) {

				edgePlots.add(p);

				if (!n) {

					p.hasN = true;

				}

				if (!s) {

					p.hasS = true;

				}

				if (!e) {

					p.hasE = true;

				}

				if (!w) {

					p.hasW = true;

				}

			}



		}

		int edges = 0;

		for (int i = 0; i < edgePlots.size(); i++) {

			Plot p = edgePlots.get(i);

			if (p.hasW && !p.countedW) {

				ArrayList<Plot> al = new ArrayList<Plot>();

				al.add(p);

				p.countedW = true;

				for (int j = 0; j < edgePlots.size(); j++) {

					Plot p2 = edgePlots.get(j);

					if (p2.c == p.c && p2.hasW && !p2.countedW) {

						al.add(p2);

						p2.countedW = true;

					}

				}

				edges += countByRow(al);

			}

			if (p.hasE && !p.countedE) {

				ArrayList<Plot> al = new ArrayList<Plot>();

				al.add(p);

				p.countedE = true;

				for (int j = 0; j < edgePlots.size(); j++) {

					Plot p2 = edgePlots.get(j);

					if (p2.c == p.c && p2.hasE && !p2.countedE) {

						al.add(p2);

						p2.countedE = true;

					}

				}

				edges += countByRow(al);

			}

			if (p.hasN && !p.countedN) {

				ArrayList<Plot> al = new ArrayList<Plot>();

				al.add(p);

				p.countedN = true;

				for (int j = 0; j < edgePlots.size(); j++) {

					Plot p2 = edgePlots.get(j);

					if (p2.r == p.r && p2.hasN && !p2.countedN) {

						al.add(p2);

						p2.countedN = true;

					}

				}

				edges += countByColumn(al);

			}

			if (p.hasS && !p.countedS) {

				ArrayList<Plot> al = new ArrayList<Plot>();

				al.add(p);

				p.countedS = true;

				for (int j = 0; j < edgePlots.size(); j++) {

					Plot p2 = edgePlots.get(j);

					if (p2.r == p.r && p2.hasS && !p2.countedS) {

						al.add(p2);

						p2.countedS = true;

					}

				}

				edges += countByColumn(al);

			}



		}

		int area = plots.size();

		return edges * area;

	}



	private int countByRow(ArrayList<Plot> al) {

		ArrayList<Integer> we = new ArrayList<Integer>();

		for (int i = 0; i < al.size(); i++) {

			we.add(Integer.valueOf(al.get(i).r));

		}

		Collections.sort(we);

		int edgeCount = 1;

		for (int i = 0; i < we.size() - 1; i++) {

			if (we.get(i + 1).intValue() - we.get(i).intValue() > 1) {

				edgeCount++;

			}

		}

		return edgeCount;

	}



	private int countByColumn(ArrayList<Plot> al) {

		ArrayList<Integer> we = new ArrayList<Integer>();

		for (int i = 0; i < al.size(); i++) {

			we.add(Integer.valueOf(al.get(i).c));

		}

		Collections.sort(we);

		int edgeCount = 1;

		for (int i = 0; i < we.size() - 1; i++) {

			if (we.get(i + 1).intValue() - we.get(i).intValue() > 1) {

				edgeCount++;

			}

		}

		return edgeCount;

	}

}



