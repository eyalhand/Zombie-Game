import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Shop extends MouseAdapter {

    private Handler handler;
    private HUD hud;
    private Game game;

    private LinkedList<GameObject> temporary = new LinkedList<>();//a list of Game Objects that is currently on the game.

    private static int B1,B1Counter;
    private static int B2,B2Counter;
    private static int B3;
    private static int B4,B4Counter;
    private static int B5;

    public Shop(Handler handler, HUD hud,Game game) {
        this.handler = handler;
        this.hud = hud;
        this.game = game;

        initialize();
    }

    public static void initialize() {
        B1Counter = 0;
        B2Counter = 0;
        B4Counter = 0;
        B1 = 100;
        B2 = 100;
        B3 = 100;
        B4 = 100;
        B5 = 100;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.getGameState() == Game.STATE.Shop) {
            //item 1
            if (mouseOver(mx, my, 85, 130, 190, 20)) {
                B1Counter++;
                if (hud.getScore() >= B1 && B1Counter <= 6) {
                    hud.setScore(hud.getScore() - B1);
                    B1 += B1;
                    hud.bounds += 30;
                    hud.Health = 100 + (hud.bounds / 2);
                }
            }
            //item 2
            if (mouseOver(mx, my, 85, 180, 210, 20)) {
                B2Counter++;
                if (hud.getScore() >= B2 && B2Counter <= 8) {
                    hud.setScore(hud.getScore() - B2);
                    B2 += B2;
                    if (game.getGameAmmo() == Game.Ammo.Pistol) {
                        game.setGameAmmo(Game.Ammo.Shotgun);
                    }
                    else if (game.getGameAmmo() == Game.Ammo.Shotgun) {
                        game.setGameAmmo(Game.Ammo.Uzi);
                    }
                    else if (game.getGameAmmo() == Game.Ammo.Uzi) {
                        game.setGameAmmo(Game.Ammo.AK47);
                    }
                    else if (game.getGameAmmo() == Game.Ammo.AK47) {
                        game.setGameAmmo(Game.Ammo.Negev);
                    }
                    else if (game.getGameAmmo() == Game.Ammo.Negev) {
                        game.setGameAmmo(Game.Ammo.AWP);
                    }
                    else if (game.getGameAmmo() == Game.Ammo.AWP) {
                        game.setGameAmmo(Game.Ammo.Blazer);
                    }
                }
            }
            //item 3
            if (mouseOver(mx, my, 85, 230, 190, 20)) {
                if (hud.getScore() >= B3) {
                    hud.setScore(hud.getScore() - B3);
                    B3 += B3;
                    hud.Health = 100 + (hud.bounds / 2);
                }
            }
            //item 4
            if (mouseOver(mx, my, 85, 280, 190, 20)) {
                B4Counter++;
                if (hud.getScore() >= B4 && B4Counter <= 4) {
                    hud.setScore(hud.getScore() - B4);
                    B4 += B4;
                    handler.speed++;
                }
            }
            if (mouseOver(mx, my, 85, 330, 190, 20)) {
                if (hud.getScore() >= B5) {
                    hud.setScore(hud.getScore() - B5);
                    B5 += B5;
                    Player.setAmmu(50);
                }
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
        Font font = new Font("AR DARLING", 1, 50);
        Font font2 = new Font("AR DARLING", 1, 25);
        boolean more;

        g.setFont(font);
        g.setColor(Color.red);
        g.drawString("Shop", 240, 65);

        //item 1
        g.setFont(font2);
        more = color(B1,g,B1Counter,5,100,150);
        if (more == true) {
            g.drawString("Upgrade Health", 100, 150);
        }
        g.drawString("Cost: " + B1 + "yal's", 320, 150);

        //item 2
        more = color(B2,g,B2Counter,5,100,200);
        if (more == true) {
            if (B2 == 5) {
                g.drawString("Unlock grenades", 100, 200);
            }
            else {
                g.drawString("Upgrade Weapon", 100, 200);
            }
        }
        g.drawString("Cost: " + B2 + "yal's", 320, 200);

        //item 3
        more = color(B3,g,B2Counter,0,100,250);
        if (more == true) {
            g.drawString("Refill Health", 100, 250);
        }
        g.drawString("Cost: " + B3 + "yal's", 320, 250);

        //item 4
        more = color(B4,g,B4Counter,4,100,300);
        if (more == true) {
            g.drawString("Upgrade Speed", 100, 300);
        }
        g.drawString("Cost: " + B4 + "yal's", 320, 300);

        //item 5
        more = color(B5,g,B2Counter,0,100,350);
        if (more == true) {
            g.drawString("Buy 50 Bullets", 100, 350);
        }
        g.drawString("Cost: " + B5 + "yal's", 320, 350);

        g.setFont(new Font("Stencil",1,16));
        g.setColor(Color.green);
        g.drawString("Score: " + hud.getScore() + "yal's", 220, 90);
        g.drawString("(Press Space To Go Back)",190,400);
    }

    public void tick() {
        for (int i = 0; i < handler.getLst().size(); i++) {
            GameObject tempObject = handler.getLst().get(i);
            if (!temporary.contains(tempObject))
                temporary.add(tempObject);
        }
        handler.getLst().clear();
    }

    private boolean color(int B,Graphics g,int counter,int limit,int x, int y) {
        if (B == B3 || B == B5) {
            if (hud.getScore() >= B)
                g.setColor(Color.white);
            else
                g.setColor(Color.gray);
        }
        else {
            if (counter <= limit) {
                if (hud.getScore() >= B) {
                    g.setColor(Color.white);
                    return true;
                }
                else {
                    g.setColor(Color.gray);
                    return true;
                }
            }
            else {
                g.setColor(Color.gray);
                g.drawString("No More Upgrades", x, y);
                return false;
            }
        }
        return true;
   }

    public LinkedList<GameObject> getTemporary() {
        return temporary;
    }
}
