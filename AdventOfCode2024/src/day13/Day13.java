package day13;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Day13 {

	public static void main(String[] args) throws IOException {
		part1();
		part2();
	}

	/*
	 * Button A: X+94, Y+34 Button B: X+22, Y+67 Prize: X=8400, Y=5400
	 */
	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day13/input.txt")));

		ArrayList<Machine> al = new ArrayList<Machine>();

		String buttonA;
		int total = 0;
		while ((buttonA = br.readLine()) != null) {
			String buttonB = br.readLine();
			String prize = br.readLine();
			int cost = (new Machine(buttonA, buttonB, prize)).solve();
			if (cost > 0) {
				total += cost;
			}
			br.readLine();// blank
		}

		System.out.println("Total price:" + total);

	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day13/input.txt")));

		ArrayList<Machine> al = new ArrayList<Machine>();

		String buttonA;
		long total = 0;
		while ((buttonA = br.readLine()) != null) {
			String buttonB = br.readLine();
			String prize = br.readLine();
			long cost = (new Machine2(buttonA, buttonB, prize)).solve();
			if (cost > 0) {
				total += cost;
			}
			br.readLine();// blank
		}

		System.out.println("Total price:" + total);
	}

}

class Machine {

	// a costs 3, b costs 1
	int ax, ay, bx, by, px, py;

	public Machine(String a, String b, String p) {
		StringTokenizer st = new StringTokenizer(a.substring(9), "XY+, ");
		ax = Integer.valueOf(st.nextToken());
		ay = Integer.valueOf(st.nextToken());
		st = new StringTokenizer(b.substring(9), "XY+, ");
		bx = Integer.valueOf(st.nextToken());
		by = Integer.valueOf(st.nextToken());
		st = new StringTokenizer(p.substring(7), "XY=, ");
		px = Integer.valueOf(st.nextToken());
		py = Integer.valueOf(st.nextToken());
	}

	public int solve() {
		int cost = 0;
		int maxB = Math.min(100, Math.min((int) (px / bx), (int) (py / by)));
//		int maxA = Math.min(100, Math.min((int) (px / ax), (int) (py / ay)));
		for (int b = maxB; b >= 0; b--) {
			int remainderx = px - bx * b;
			int remaindery = py - by * b;
			if (remainderx % ax == 0 && remaindery % ay == 0) {
				int axcount = remainderx / ax;
				int aycount = remaindery / ay;
				if (axcount == aycount) {
					// working solution
					if (cost == 0 || axcount * 3 + b < cost) {
						// new cheapest option
						cost = axcount * 3 + b;
					}
				}
			}
		}
		return cost;
	}
}

class Machine2 {

	// a costs 3, b costs 1
	long ax, ay, bx, by, px, py;

	public Machine2(String a, String b, String p) {
		StringTokenizer st = new StringTokenizer(a.substring(9), "XY+, ");
		ax = Integer.valueOf(st.nextToken());
		ay = Integer.valueOf(st.nextToken());
		st = new StringTokenizer(b.substring(9), "XY+, ");
		bx = Integer.valueOf(st.nextToken());
		by = Integer.valueOf(st.nextToken());
		st = new StringTokenizer(p.substring(7), "XY=, ");
		long extra = 10000000000000l;
		px = extra + Integer.valueOf(st.nextToken());
		py = extra + Integer.valueOf(st.nextToken());
	}

	public long solve() {
		long amult = (px * by - py * bx) / (ax * by - ay * bx);
		long bmult = (ax * py - ay * px) / (ax * by - ay * bx);
		if (ax * amult + bx * bmult == px && ay * amult + by * bmult == py) {
			return amult * 3 + bmult;
		} else {
			return 0;
		}
	}

}