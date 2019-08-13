import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScore {

    private ArrayList<Score> leagueLeaders;
    private File file;

    private static final String highScoreFile = "res/leagueLeaders.txt";

    public HighScore() throws IOException {

        leagueLeaders = new ArrayList<>();
        file = new File(highScoreFile);
        file.createNewFile();

    }

    public ArrayList<Score> getScores() {
        loadScoreFile();
        sort();
        return leagueLeaders;
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(leagueLeaders, comparator);
    }

    public void addScore(String name, int score) {
        loadScoreFile();
        updateScoreFile(new Score(name, score));
    }

    public void loadScoreFile() {
        String st;
        FileReader input = null;
        BufferedReader reader = null;
        ArrayList<Score> tmp = new ArrayList<>();

        try {
            input = new FileReader(highScoreFile);
            reader = new BufferedReader(input);
            st = reader.readLine();
            while (st != null) {
                st.split(":");
                String name = st.split(":")[0];
                int scoreNum = Integer.parseInt(st.split(":")[1]);
                tmp.add(new Score(name,scoreNum));
                st = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage() + " load");
        } finally {
            leagueLeaders = tmp;
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                System.out.println("[Naad] IO Error: " + e.getMessage());
            }
        }
    }

    public void updateScoreFile(Score score) {
        FileWriter output = null;
        BufferedWriter writer = null;

        try {
            output = new FileWriter(file,true);
            writer = new BufferedWriter(output);
            String st = score.name + ":" + score.score + System.getProperty("line.separator");
            writer.append(st);
        } catch (FileNotFoundException e) {
            System.out.println("[Update] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.flush();
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] Error: " + e.getMessage());
            }
        }
    }

    public String getHighScoreString() {
        String highScoreString = "";
        int max = 8;

        ArrayList<Score> scores;
        scores = getScores();

        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highScoreString += (i + 1) + ". " + scores.get(i).getName() + "             "
                                + scores.get(i).getScore();
            highScoreString += System.getProperty("line.separator");
            i++;
        }
        return highScoreString;
    }

    public File getFile() {
        return file;
    }


    private class Score {

        private int score;
        private String name;

        public Score(String name, int score) {
            this.score = score;
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public String getName() {
            return name;
        }
    }

    private class ScoreComparator implements Comparator<Score> {

        public int compare(Score score1, Score score2) {

            int sc1 = score1.getScore();
            int sc2 = score2.getScore();

            if (sc1 > sc2) {
                return -1;                   // -1 means first score is bigger then second score
            } else if (sc1 < sc2) {
                return +1;                   // +1 means that score is lower
            } else {
                return 0;                     // 0 means score is equal
            }
        }
    }
}
