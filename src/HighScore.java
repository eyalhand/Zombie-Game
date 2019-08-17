import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScore {

    private ArrayList<Score> leagueLeaders;
    private File fileEasy,fileNormal,fileHard;
    private Game game;

    private static final String highScoreFileEasy = System.getProperty("user.home") + "/highScoreFileEasy.dat";
    private static final String highScoreFileNormal = System.getProperty("user.home") + "/highScoreFileNormal.dat";
    private static final String highScoreFileHard = System.getProperty("user.home") + "/highScoreFileHard.dat";

    public HighScore(Game game) throws IOException {
        this.game = game;
        leagueLeaders = new ArrayList<>();

        fileEasy = new File(highScoreFileEasy);
        fileNormal = new File(highScoreFileNormal);
        fileHard = new File(highScoreFileHard);
        fileEasy.createNewFile();
        fileNormal.createNewFile();
        fileHard.createNewFile();
    }

    public ArrayList<Score> getScores(int i) {
        loadScoreFile(i);
        sort();
        return leagueLeaders;
    }

    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(leagueLeaders, comparator);
    }

    public void addScore(String name, int score, int i) {
        loadScoreFile(i);
        updateScoreFile(new Score(name, score), i);
    }

    public void loadScoreFile(int i) {
        String st;
        FileReader input = null;
        BufferedReader reader = null;
        ArrayList<Score> tmp = new ArrayList<>();

        try {
            if (i == 0)
                input = new FileReader(highScoreFileEasy);
            else if (i == 1)
                input = new FileReader(highScoreFileNormal);
            else if (i == 2)
                input = new FileReader(highScoreFileHard);
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

    public void updateScoreFile(Score score, int i) {
        FileWriter output = null;
        BufferedWriter writer = null;

        try {
            if (i == 0)
                output = new FileWriter(fileEasy,true);
            else if (i == 1)
                output = new FileWriter(fileNormal,true);
            else if (i == 2)
                output = new FileWriter(fileHard,true);
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

    public String getHighScoreString(int p) {
        String highScoreString = "";
        int max = 8;

        ArrayList<Score> scores;
        scores = getScores(p);

        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            highScoreString += (i + 1) + ". " + scores.get(i).getName() + "-"
                                + scores.get(i).getScore() + ":";
            i++;
        }
        return highScoreString;
    }

    public int findMax() {
        if (leagueLeaders.size() > 0)
            return leagueLeaders.get(0).score;
        else
            return -1;
    }

    public int findMin(int p) {
        int max = 8;
        int output = Integer.MAX_VALUE;

        ArrayList<Score> scores;
        scores = getScores(p);

        int i = 0;
        int x = scores.size();
        if (x > max) {
            x = max;
        }
        while (i < x) {
            output = Integer.min(output,leagueLeaders.get(i).score);
            i++;
        }
        return output;
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
