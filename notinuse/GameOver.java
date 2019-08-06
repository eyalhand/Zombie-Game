package notinuse;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOver extends MouseAdapter {

    private Game game;
    private Handler handler;

    public GameOver(Game game,Handler handler) {
        this.game = game;
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.getGameState() == Game.STATE.GameOver) {
            //back button
            if (mouseOver(mx, my, 220, 360, 130, 48)) {
                AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Menu);
                HUD.initialize();
                Spawn.initialize();
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
        handler.getLst().clear();
    }

    public void render(Graphics g) {
        Font font = new Font("arial",2,50);
        Font font2 = new Font("arial",5,30);
        Font font3 = new Font("arial",2,30);

        g.setFont(font);
        g.setColor(Color.red);
        g.drawString("Ha Ha, You Lost !",100,65);

        g.setFont(font2);
        g.setColor(Color.white);
        g.drawRect(150,90,300,150);
        g.setColor(Color.red);
        g.drawString("Score: " + HUD.score, 220, 130);
        g.drawString("Level: " + HUD.level, 250, 200);

        g.setFont(font3);
        g.setColor(Color.white);
        g.drawRect(225,360,130,48);
        g.drawString("Back",255,395);
    }
}
