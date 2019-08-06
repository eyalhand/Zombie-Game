package notinuse;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Help extends MouseAdapter {

    private Game game;

    private String[] text = new String[6];

    public Help(Game game) {
        text[0] = "Goal: Avoid The Zombies !";
        text[1] = "Means: Press W,S,A,D To Move";
        text[2] = "Press Esc To Pause The Game At Any Time";
        text[3] = "";
        text[4] = "There Are 8 levels And 4 Different Zombies";
        text[5] = "Good Luck And Make America Great Again !!";
        this.game = game;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.getGameState() == Game.STATE.Help) {
            //back button
            if (mouseOver(mx, my, 220, 360, 130, 48)) {
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

    public void tick() {}

    public void render(Graphics g) {
        Font font = new Font("arial",2,50);
        Font font2 = new Font("arial",3,17);
        Font font3 = new Font("arial",2,30);

        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("Help",235,65);

        g.setFont(font2);
        g.setColor(Color.red);
        g.drawRect(120,90,400,220);
        g.drawString(text[0],135,115);
        g.drawString(text[1],135,150);
        g.drawString(text[2],135,185);
        g.drawString(text[3],135,220);
        g.drawString(text[4],135,255);
        g.drawString(text[5],135,290);

        g.setFont(font3);
        g.setColor(Color.blue);
        g.drawRect(220,360,130,48);
        g.drawString("Back",250,395);
    }
}
