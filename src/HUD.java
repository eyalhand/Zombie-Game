import java.awt.*;

public class HUD {

    public static int bounds;
    public static float Health, greenValue;
    public static int score, level, zombiesKilled;
    public Game game;

    public HUD(Game game) {
        initialize();
        this.game = game;
    }

    public static void initialize(){
        Health = 100;
        greenValue = 255f;

        bounds = 0;
        score = 0;
        zombiesKilled = 0;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        HUD.score = score;
    }

    public void tick() {
        Health = Game.clamp(Health, 0, 100 + (bounds/2));

        greenValue = Health * 2;
        greenValue = Game.clamp(greenValue, 0, 255);
    }

    public void render(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200 + bounds, 32);
        g.setColor(new Color(100, (int) greenValue, 0));
        g.fillRect(15, 15, (int) Health * 2, 32);
        g.setColor(Color.white);
        g.drawRect(15, 15, 200 + bounds, 32);

        g.setFont(new Font("arial", 8, 13));
        g.drawString("Score: " + score + "yal's", 15, 64);
        g.drawString("Zombies Killed: " + zombiesKilled, 15, 80);

        g.setFont(new Font("Times New Roman", 15, 13));
        g.drawString("Press Space For Shop", (int)game.WIDTH - 132, 25);

    }
}
