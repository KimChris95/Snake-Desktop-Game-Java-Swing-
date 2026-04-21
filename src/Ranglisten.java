import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ranglisten {
	
	
	
	public static void insertData(String name, int highscore) throws IOException  {
		try {
			FileWriter writer = new FileWriter("highscore.txt",true);
			writer.write(name + "," + highscore + "\n");
			writer.close();
			System.out.println("Daten erfolgreich gespeichert.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readHighscoreFromFile() {
        List<String> highscoreLines = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        try {
            FileReader reader = new FileReader("highscore.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                highscoreLines.add(line);
            }
            bufferedReader.close();

            Collections.sort(highscoreLines, new Comparator<String>() {
                @Override
                public int compare(String line1, String line2) {
                    int highscore1 = Integer.parseInt(line1.split(",")[1]);
                    int highscore2 = Integer.parseInt(line2.split(",")[1]);
                    return Integer.compare(highscore2, highscore1); 
                }
            });

            for (String highscoreLine : highscoreLines) {
                String[] parts = highscoreLine.split(",");
                String name = parts[0];
                int highscore = Integer.parseInt(parts[1]);
                result.append("Name: ").append(name).append("\t ");
                result.append("Highscore: ").append(highscore).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            result.append("Fehler beim Lesen der Datei.");
        }
        return result.toString();
    }
}
