import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

public class Pause extends MouseAdapter {

    private Game game;
    private Handler handler;

    private BufferedImage muteOn;
    private BufferedImage muteOff;

    private LinkedList<GameObject> temporary = new LinkedList<>();//a list of Game Objects that is currently on the game.

    public Pause(Game game,Handler handler) throws IOException {
        this.game = game;
        this.handler = handler;

        muteOn = ImageIO.read(getClass().getResource("/res/mute_on.jpg"));
        muteOff = ImageIO.read(getClass().getResource("/res/mute_off.jpg"));
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.getGameState() == Game.STATE.Pause) {
            //resume button
            if (mouseOver(mx, my, 110, 210, 230, 50)) {
                if (!game.isMute()) {
                    AudioPlayer.getMusic("pause").stop();
                    AudioPlayer.getMusic("pursuit").loop();
                }

                for (int i = 0; i < temporary.size(); i++) {
                    GameObject tempObject = temporary.get(i);
                    handler.getLst().add(tempObject);
                }
                MouseInput.setLastState(Game.STATE.Pause);
                game.setGameState(Game.STATE.Game);
            }
            //mute button
            else if (mouseOver(mx, my, (int) game.getWIDTH() - 100, 30, 70, 70)) {
                if (game.isMute()) {
                    game.setMute(false);
                    AudioPlayer.getMusic("pause").loop();
                } else {
                    game.setMute(true);
                    AudioPlayer.getMusic("pause").stop();
                }
            }

            //back to main menu button
            else if (mouseOver(mx, my, (int) game.getWIDTH() - 650, (int) game.getHEIGHT() - 150, 600, 90)) {
                AudioPlayer.getMusic("pause").stop();
                AudioPlayer.getMusic("background_music").loop();

                game.setGameState(Game.STATE.Menu);
                newGame();
                temporary.clear();
            }
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) return true;
            else return false;
        } else return false;
    }

    public void tick() {
        for (int i = 0; i < handler.getLst().size(); i++) {
            GameObject tempObject = handler.getLst().get(i);
            if (!temporary.contains(tempObject))
                temporary.add(tempObject);
        }
        handler.getLst().clear();
    }

    public void render(Graphics g) {
        if (game.getGameState() == Game.STATE.Pause) {
            Font font = new Font("AR DARLING", 1, 135);
            Font font2 = new Font("AR DARLING", 1, 65);

            g.setFont(font);
            g.setColor(Color.green);
            g.drawString("Pause", (int)game.getWIDTH()/2 - 200, 135);

            if (game.isMute())
                g.drawImage(muteOn,(int)game.getWIDTH() - 100,30,70,70,null);
            else
                g.drawImage(muteOff,(int)game.getWIDTH() - 100,30,70,70,null);

            g.setFont(font2);
            g.setColor(Color.white);
            g.drawString("Resume", 110, 250);

            g.setColor(Color.white);
            g.drawString("Back to Main Menu", (int)game.getWIDTH() - 650, (int)game.getHEIGHT() - 100);
        }
    }

    public LinkedList<GameObject> getTemporary() { return temporary; }

    private void newGame() {
        HUD.initialize();
        Spawn.initialize();
        Shop.initialize();
        handler.setSpeed(5);
        handler.getLst().clear();
        game.setGameAmmo(Game.Ammo.Pistol);
    }
}
