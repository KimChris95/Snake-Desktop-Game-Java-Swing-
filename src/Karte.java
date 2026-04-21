import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class Karte {



	public int[][] loadMap(String filename) {

		Path pathToFile = Paths.get(filename);
		ArrayList<String> lines = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}

		if (!lines.isEmpty()) {
			int[][] map = new int[lines.size()][lines.get(0).length()];
			for (int i = 0; i < lines.size(); i++) {
				for (int j = 0; j < lines.get(i).length(); j++) {
					map[i][j] = Character.getNumericValue(lines.get(i).charAt(j));
				}
			}
			return map;
		} else {
			return null;
		}
	}

	public String toString(int[][] array) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				sb.append(array[i][j]);
				if (j < array[i].length - 1) {
					sb.append(" ");
				}
			}
			if (i < array.length - 1) {
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	
}