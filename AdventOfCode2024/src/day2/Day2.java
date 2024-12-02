package day2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class Day2 {

	public static void main(String[] args) throws IOException {

//		partOne();

		partTwo();
	}

	private static void partOne() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day2/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		int answer = 0;
		while ((line = br.readLine()) != null) {

			StringTokenizer st = new StringTokenizer(line);

			int first = Integer.parseInt(st.nextToken());
			if (!st.hasMoreTokens()) {
				answer++;
			} else {
				int cur = Integer.parseInt(st.nextToken());
				boolean good = true;
				if (cur < first && first - cur <= 3) {
					while (st.hasMoreTokens()) {
						int next = Integer.parseInt(st.nextToken());
						if (next < cur && cur - next <= 3) {
							cur = next;
						} else {
							good = false;
							break;
						}
					}
				} else if (cur > first && cur - first <= 3) {
					while (st.hasMoreTokens()) {
						int next = Integer.parseInt(st.nextToken());
						if (next > cur && next - cur <= 3) {
							cur = next;
						} else {
							good = false;
							break;
						}
					}

				} else {
					good = false;
				}
				if (good) {
					answer++;
				}
			}
		}

		System.out.println("Total:" + answer);

	}

	private static void partTwo() throws IOException {
		FileInputStream in = new FileInputStream(new File("src/day2/input.txt"));
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		int answer = 0;
		while ((line = br.readLine()) != null) {

			StringTokenizer st = new StringTokenizer(line);
			ArrayList<Integer> ints = new ArrayList<Integer>();

			while (st.hasMoreTokens()) {
				ints.add(Integer.valueOf(st.nextToken()));
			}
			for (int i = -1; i < ints.size(); i++) {
				if (isValid(convert(ints, i))) {
					answer++;
					break;
				}
			}
		}
		System.out.println("Total:" + answer);

	}

	private static boolean isValid(int[] arr) {
		if (arr.length == 0 || arr.length == 1) {
			return true;
		}

		boolean decreasing = arr[0] > arr[1];

		if (decreasing) {
			for (int i = 0; i < arr.length - 1; i++) {
				if (arr[i] <= arr[i + 1] || arr[i] - arr[i + 1] > 3) {
					return false;
				}
			}
			return true;
		} else {
			for (int i = 0; i < arr.length - 1; i++) {
				if (arr[i] >= arr[i + 1] || arr[i + 1] - arr[i] > 3) {
					return false;
				}
			}
			return true;

		}
	}

	private static int[] convert(ArrayList<Integer> al, int indexToExclude) {
		if (indexToExclude < 0) {
			return al.stream().mapToInt(i -> i).toArray();
		} else {
			int arr[] = new int[al.size() - 1];
			for (int i = 0; i < indexToExclude; i++) {
				arr[i] = al.get(i).intValue();
			}
			for (int i = indexToExclude; i < arr.length; i++) {
				arr[i] = al.get(i + 1).intValue();
			}
			return arr;
		}
	}
}
