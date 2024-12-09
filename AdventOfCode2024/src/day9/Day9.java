package day9;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Day9 {

	public static void main(String args[]) throws IOException {

		part1();
		part2();
	}

	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day9/input.txt")));

		String line = br.readLine();
		int[] disk = new int[line.length()];
		int diskSize = 0;
		for (int i = 0; i < line.length(); i++) {
			disk[i] = Character.getNumericValue(line.charAt(i));
			diskSize += disk[i];
		}

		int[] expandedDisk = new int[diskSize];
		int expandedIndex = 0;
		for (int i = 0; i < disk.length; i++) {
			if (i % 2 == 0) {
				for (int j = 0; j < disk[i]; j++) {
					expandedDisk[expandedIndex++] = i / 2;
				}
			} else {
				for (int j = 0; j < disk[i]; j++) {
					expandedDisk[expandedIndex++] = -1;
				}
			}
		}

		int cursor = 0;

		for (int i = expandedDisk.length - 1; i > 0; i--) {
			if (expandedDisk[i] != -1) {
				while (cursor < expandedDisk.length && expandedDisk[cursor] != -1) {
					cursor++;
				}
				if (cursor >= i) {
					break;
				}
				expandedDisk[cursor++] = expandedDisk[i];
				expandedDisk[i] = -1;
			}
		}

		long value = 0;
		for (int i = 0; i < expandedDisk.length; i++) {
			if (expandedDisk[i] == -1) {
				break;
			}
			value += expandedDisk[i] * i;
		}

		System.out.println("Value: " + value);

	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day9/input.txt")));

		String line = br.readLine();
		ArrayList<DiskSpace> disk = new ArrayList<DiskSpace>();
		for (int i = 0; i < line.length(); i++) {
			if (i % 2 == 0) {
				disk.add(new DiskSpace(Character.getNumericValue(line.charAt(i)), i / 2));
			} else {
				disk.add(new DiskSpace(Character.getNumericValue(line.charAt(i)), -1));
			}
		}

		for (int i = disk.size() - 1; i > 0; i--) {
			DiskSpace cur = disk.get(i);
			if (cur.value != -1) {
				for (int j = 0; j < i; j++) {
					if (disk.get(j).fits(cur)) {
						disk.get(j).size -= cur.size;
						disk.remove(i);
						disk.add(i, new DiskSpace(cur.size, -1));
						disk.add(j, cur);
						i++;
						break;
					}
				}
			}
		}

		long value = 0;
		int indexCounter = 0;
		for (int i = 0; i < disk.size(); i++) {
			value += disk.get(i).getChecksum(indexCounter);
			indexCounter += disk.get(i).size;
		}
		System.out.println("Value: " + value);
	}
}

class DiskSpace {
	int size, value;

	public DiskSpace(int size, int value) {
		this.size = size;
		this.value = value;
	}

	public boolean fits(DiskSpace ds) {
		return value == -1 && ds.size <= size;
	}

	public long getChecksum(int fromIndex) {
		if (value == -1) {
			return 0;
		}
		long returned = 0;
		for (int i = 0; i < size; i++) {
			returned += (fromIndex + i) * value;
		}
		return returned;
	}
}
