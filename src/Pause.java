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
            if (mouseOver(mx, my, 110, 210, 230, 50)) {
                AudioPlayer.getMusic("pause").stop();
                AudioPlayer.getMusic("pursuit").loop();

                for (int i = 0; i < temporary.size(); i++) {
                    GameObject tempObject = temporary.get(i);
                    handler.getLst().add(tempObject);
                }
                MouseInput.setLastState(Game.STATE.Pause);
                game.setGameState(Game.STATE.Game);
            }

            //back to main menu button
            if (mouseOver(mx, my, (int)game.WIDTH - 650, (int)game.HEIGHT - 150, 600, 90)) {
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
            g.drawString("Pause", (int)game.WIDTH/2 - 200, 135);

            g.setFont(font2);
            g.setColor(Color.white);
            g.drawString("Resume", 110, 250);

            g.setColor(Color.white);
            g.drawString("Back to Main Menu", (int)game.WIDTH - 650, (int)game.HEIGHT - 100);
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
