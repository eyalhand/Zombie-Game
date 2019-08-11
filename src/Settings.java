import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Settings extends MouseAdapter {

    private Game game;

    private BufferedImage triangle;
    private BufferedImage triangle2;
    private BufferedImage muteOn;
    private BufferedImage muteOff;

    public Settings(Game game) throws IOException {
        this.game = game;

        triangle = ImageIO.read(getClass().getResource("/res/triangle.png"));
        triangle2 = ImageIO.read(getClass().getResource("/res/triangle2.png"));
        muteOn = ImageIO.read(getClass().getResource("/res/mute_on.jpg"));
        muteOff = ImageIO.read(getClass().getResource("/res/mute_off.jpg"));

    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.getGameState() == Game.STATE.Settings) {
            //mute button
            if (mouseOver(mx, my, (int)game.getWIDTH() - 100,30,70,70)) {
                if (game.isMute()) {
                    game.setMute(false);
                    AudioPlayer.getMusic("background_music").loop();
                } else {
                    game.setMute(true);
                    AudioPlayer.getMusic("background_music").stop();
                }
            }
            //back button
            if (mouseOver(mx, my, 40,20,120,35)) {
                if (!game.isMute())
                    AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Menu);
            }
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) return true;
            else return false;
        } else return false;
    }

    public void render(Graphics g) {
        Font font = new Font("AR DARLING",1,135);
        Font font2 = new Font("AR DARLING",1,50);

        g.setFont(font);
        g.setColor(Color.orange);
        g.drawString("Settings",(int)game.getWIDTH()/2 - 300,120);

        if (game.isMute())
            g.drawImage(muteOn,(int)game.getWIDTH() - 100,30,70,70,null);
        else
            g.drawImage(muteOff,(int)game.getWIDTH() - 100,30,70,70,null);

        g.setFont(font2);
        color(g,Game.SettingOption.PlayerColors);
        g.drawString("Player's Color:   " + game.getPlayerColor(), 200, 220);
        g.drawImage(triangle, 820, 180,45,45,null);
        g.drawImage(triangle2, 140, 180,45,45,null);
        color(g, Game.SettingOption.ZombieColors);
        g.drawString("Zombie's Color:   " + game.getZombieColor(), 200, 300);
        g.drawImage(triangle, 820, 260,45,45,null);
        g.drawImage(triangle2, 140, 260,45,45,null);
        color(g, Game.SettingOption.Backgrounds);
        g.drawString("Game's Background:   " + game.getBackgrounD(), 200, 380);
        g.drawImage(triangle, 1060, 340,45,45,null);
        g.drawImage(triangle2, 140, 340,45,45,null);
        color(g, Game.SettingOption.Mode);
        g.drawString("Mode:   " + game.getMode(), 200, 460);
        g.drawImage(triangle, 700, 420,45,45,null);
        g.drawImage(triangle2, 140, 420,45,45,null);

        g.setColor(new Color(200,200,100));
        g.drawString("Back",40,55);
    }

    public void tick() {}

    private void color(Graphics g, Game.SettingOption o) {
        g.setColor(Color.gray);
        if (game.getSettingOption() == o)
            g.setColor(Color.red);
    }
}
