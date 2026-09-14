package jpvz.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class ScoreManager {

	// ---------------------------------------------------------------
	// STATIC CONSTANTS
	// ---------------------------------------------------------------
    
    private static final String FILE_PATH = "conf/score.txt";

	// ---------------------------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------------------------

    private ScoreManager(){
        
    }

	// ---------------------------------------------------------------
	// STATIC METHODS
	// ---------------------------------------------------------------

    public static List<Score> loadScore() {
        List<Score> scores = new ArrayList<>();
        File f = Config.getInstance().getFile(FILE_PATH);
        if (!f.exists()) {
            return scores;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split(";");
                if (parts.length >= 2) {
                    String name = parts[0].trim();
                    int scoreVal = Integer.parseInt(parts[1].trim());
                    scores.add(new Score(name, scoreVal));
                }
            }
        } catch (Exception e) {
            System.err.println("JPvZ warning: Error reading score file.");
            e.printStackTrace();
        }

        scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));
        return scores;
    }

    public static void saveScore(String playerName, int scoreValue) {
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Giocatore";
        }

        List<Score> scores = loadScore();
        scores.add(new Score(playerName, scoreValue));
        scores.sort((s1, s2) -> Integer.compare(s2.getScore(), s1.getScore()));

        File f = Config.getInstance().getFile(FILE_PATH);
        if (f.getParentFile() != null && !f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
            for (Score s : scores) {
                writer.write(s.getName() + ";" + s.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("JPvZ warning: Error writing score file.");
            e.printStackTrace();
        }
    }
}
