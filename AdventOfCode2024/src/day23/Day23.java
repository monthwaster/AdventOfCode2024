package day23;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Day23 {

	public static void main(String[] args) throws IOException {
		part1();
		part2();
	}

	private static void part1() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day23/input.txt")));

		String line;
		HashMap<String, ArrayList<String>> connections = new HashMap<String, ArrayList<String>>();
		while ((line = br.readLine()) != null) {
			String pc1 = line.substring(0, 2);
			String pc2 = line.substring(3, 5);
			if (connections.containsKey(pc1)) {
				connections.get(pc1).add(pc2);
			} else {
				ArrayList<String> al = new ArrayList<String>();
				al.add(pc2);
				connections.put(pc1, al);
			}
			if (connections.containsKey(pc2)) {
				connections.get(pc2).add(pc1);
			} else {
				ArrayList<String> al = new ArrayList<String>();
				al.add(pc1);
				connections.put(pc2, al);
			}
		}

		int total = 0;
		ArrayList<String> alreadyFound = new ArrayList<String>();
		for (Iterator<String> it = connections.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			if (key.charAt(0) == 't') {
				ArrayList<String> peers = connections.get(key);
				for (int i = 0; i < peers.size(); i++) {
					String peer1 = peers.get(i);
					ArrayList<String> peer1peers = connections.get(peer1);
					for (int j = i + 1; j < peers.size(); j++) {
						if (peer1peers.contains(peers.get(j))) {
							String[] s = new String[3];
							s[0] = key;
							s[1] = peer1;
							s[2] = peers.get(j);
							Arrays.sort(s);
							String nw = s[0] + s[1] + s[2];
							if (!alreadyFound.contains(nw)) {
								alreadyFound.add(nw);
								total++;
							}
						}
					}
				}
			}
		}
		System.out.println("Total: " + total);

	}

	private static void part2() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/day23/input.txt")));

		String line;
		HashMap<String, ArrayList<String>> connections = new HashMap<String, ArrayList<String>>();
		while ((line = br.readLine()) != null) {
			String pc1 = line.substring(0, 2);
			String pc2 = line.substring(3, 5);
			if (connections.containsKey(pc1)) {
				connections.get(pc1).add(pc2);
			} else {
				ArrayList<String> al = new ArrayList<String>();
				al.add(pc2);
				connections.put(pc1, al);
			}
			if (connections.containsKey(pc2)) {
				connections.get(pc2).add(pc1);
			} else {
				ArrayList<String> al = new ArrayList<String>();
				al.add(pc1);
				connections.put(pc2, al);
			}
		}

		ArrayList<String> lanParty = null;
		
		for (Iterator<String> it = connections.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			ArrayList<String> peers = connections.get(key);
			ArrayList<String> in = new ArrayList<String>();
			in.add(key);
			ArrayList<String> potential = findBiggestNetworkWith(in, peers, connections);
			if (lanParty == null || potential.size() > lanParty.size()) {
				lanParty = potential;
			}
		}

		String name = "";
		String[] ordered = new String[lanParty.size()];
		for (int i = 0; i < ordered.length; i++) {
			ordered[i] = lanParty.get(i);
		}
		Arrays.sort(ordered);
		for (int i = 0; i < ordered.length; i++) {
			name += ordered[i] + ",";
		}
		name = name.substring(0, name.length() - 1);
		System.out.println("Name: " + name);
	}

	private static ArrayList<String> findBiggestNetworkWith(ArrayList<String> in, ArrayList<String> peers,
			HashMap<String, ArrayList<String>> connections) {
		if (peers.size() == 0) {
			return in;
		} else if (peers.size() == 1) {
			in.add(peers.get(0));
			return in;
		}
		String peer = peers.get(0);
		ArrayList<String> peerConnections = connections.get(peer);
		boolean matches = peerConnections.containsAll(peers);
		if (matches) {
			in.add(peer);
			peers.remove(0);
			return findBiggestNetworkWith(new ArrayList<String>(in), peers, connections);
		} else {
			ArrayList<String> tempIn = new ArrayList<String>();
			tempIn.addAll(in);
			tempIn.add(peer);
			ArrayList<String> tempPeers = new ArrayList<String>();
			for (int j = 0; j < peers.size(); j++) {
				if (peerConnections.contains(peers.get(j))) {
					tempPeers.add(peers.get(j));
				}
			}
			// check w/o missing peers
			ArrayList<String> nwWithCommonsOnly = findBiggestNetworkWith(tempIn, tempPeers, connections);
			// check w/o peer
			tempPeers = new ArrayList<String>(peers);
			tempPeers.remove(0);
			ArrayList<String> nwWithoutPeer = findBiggestNetworkWith(new ArrayList<String>(in), tempPeers, connections);

			if (nwWithCommonsOnly.size() > nwWithoutPeer.size()) {
				// nwWithCommonsOnly.addAll(in);
				return nwWithCommonsOnly;
			} else {
				// nwWithoutPeer.addAll(in);
				return nwWithoutPeer;
			}
		}

	}

}
