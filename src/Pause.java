import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Pause extends MouseAdapter {

    private Game game;
    private Handler handler;

    private LinkedList<GameObject> temporary = new LinkedList<>();//a list of Game Objects that is currently on the game.

    public Pause(Game game,Handler handler) {
        this.game = game;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.getGameState() == Game.STATE.Pause) {
            //resume button
            if (mouseOver(mx, my, 50, 100, 160, 48)) {
                AudioPlayer.getMusic("pause").stop();
                AudioPlayer.getMusic("pursuit").loop();

                for (int i = 0; i < temporary.size(); i++) {
                    GameObject tempObject = temporary.get(i);
                    handler.getLst().add(tempObject);
                }
                MouseInput.setLastState(Game.STATE.Pause);
                game.setGameState(Game.STATE.Game);
            }
            //settings button
            if (mouseOver(mx, my, 50, 170, 185, 48)) {
                AudioPlayer.getSound("click_sound").play();


            }
            //back to main menu button
            if (mouseOver(mx, my, 320, 350, 300, 48)) {
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
            Font font = new Font("AR DARLING", 1, 50);
            Font font2 = new Font("AR DARLING", 1, 40);
            Font font3 = new Font("AR DARLING", 1, 30);

            g.setFont(font);
            g.setColor(Color.green);
            g.drawString("Pause", 240, 65);

            g.setFont(font2);
            g.setColor(Color.white);
            g.drawString("Resume", 60, 135);

            g.drawString("Settings", 60, 205);

            g.setFont(font3);
            g.setColor(Color.white);
            g.drawString("Back to Main Menu", 330, 385);
        }
    }

    public LinkedList<GameObject> getTemporary() { return temporary; }

    private void newGame() {
        HUD.initialize();
        Spawn.initialize();
        Shop.initialize();
        handler.speed = 5;
        handler.getLst().clear();
        game.setGameAmmo(Game.Ammo.Pistol);
    }
}
