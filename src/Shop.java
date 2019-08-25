import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Shop extends MouseAdapter {

    private Handler handler;
    private HUD hud;
    private Game game;

    private LinkedList<GameObject> temporary = new LinkedList<>(); //a list of Game Objects that is currently on the game.
    private LinkedList<GameObject> temporary2 = new LinkedList<>(); //a list of Ammo objects to be removed

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
        B1 = 800;
        B2 = 500;
        B3 = 200;
        B4 = 400;
        B5 = 600;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.getGameState() == Game.STATE.Shop) {
            //item 1
            if (mouseOver(mx, my, 385, 270, 780, 42)) {
                B1Counter++;
                if (hud.getScore() >= B1 && B1Counter <= 6) {
                    hud.setScore(hud.getScore() - B1);
                    B1 += B1;
                    hud.bounds += 30;
                    hud.Health = 100 + (hud.bounds / 2);
                }
            }
            //item 2
            if (mouseOver(mx, my, 385, 340, 780, 42)) {
                B2Counter++;
                if (hud.getScore() >= B2 && B2Counter <= 6) {
                    hud.setScore(hud.getScore() - B2);
                    B2 += B2;
                    if (game.getGameAmmo() == Game.Ammo.Pistol) {
                        game.setGameAmmo(Game.Ammo.Shotgun);
                        removeAmmo();
                    } else if (game.getGameAmmo() == Game.Ammo.Shotgun) {
                        game.setGameAmmo(Game.Ammo.Uzi);
                        removeAmmo();
                    } else if (game.getGameAmmo() == Game.Ammo.Uzi) {
                        game.setGameAmmo(Game.Ammo.AK47);
                        removeAmmo();
                    } else if (game.getGameAmmo() == Game.Ammo.AK47) {
                        game.setGameAmmo(Game.Ammo.Negev);
                        removeAmmo();
                    } else if (game.getGameAmmo() == Game.Ammo.Negev) {
                        game.setGameAmmo(Game.Ammo.AWP);
                        removeAmmo();
                    } else if (game.getGameAmmo() == Game.Ammo.AWP) {
                        game.setGameAmmo(Game.Ammo.Blazer);
                    }
                }
            }
            //item 3
            if (mouseOver(mx, my, 385, 410, 780, 42)) {
                if (hud.getScore() >= B3) {
                    hud.setScore(hud.getScore() - B3);
                    B3 += B3;
                    hud.Health = 100 + (hud.bounds / 2);
                }
            }
            //item 4
            if (mouseOver(mx, my, 385, 480, 780, 42)) {
                B4Counter++;
                if (hud.getScore() >= B4 && B4Counter <= 3) {
                    hud.setScore(hud.getScore() - B4);
                    B4 += B4;
                    handler.setSpeed(handler.getSpeed() + 1);
                }
            }
            if (mouseOver(mx, my, 385, 550, 780, 42)) {
                if (hud.getScore() >= B5) {
                    hud.setScore(hud.getScore() - B5);
                    B5 += B5;
                    Player.setAmmu(50);
                }
            }
        }
    }

    private void removeAmmo() {
        for (GameObject tempObject: temporary) {
            if (tempObject.getId() == ID.Ammo) {
                temporary2.add(tempObject);
            }
        }
        for (GameObject tempObject: temporary2) {
            temporary.remove(tempObject);
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) return true;
            else return false;
        } else return false;
    }

    public void render(Graphics g) {
        Font font = new Font("AR DARLING", 1, 135);
        Font font2 = new Font("AR DARLING", 1, 50);

        g.setFont(font);
        g.setColor(Color.ORANGE);
        g.drawString("Shop", (int)game.getWIDTH()/2 - 155, 120);

        //item 1
        g.setFont(font2);
        if (color(1,g,B1Counter,5,B1,385,300)) {
            g.drawString("Upgrade Health", 385, 300);
            g.drawString("Cost: " + B1 + "yal's", 800, 300);
        }

        //item 2
        if (color(2,g,B2Counter,5,B2,385,370)) {
            if (B2Counter == 5) {
                g.drawString("Unlock Blazers", 385, 370);
            } else {
                g.drawString("Upgrade Weapon", 385, 370);
            }
            g.drawString("Cost: " + B2 + "yal's", 800, 370);
        }

        //item 3
        if (color(3,g,B2Counter,0,B3,385,440)) {
            g.drawString("Refill Health", 385, 440);
            g.drawString("Cost: " + B3 + "yal's", 800, 440);
        }

        //item 4
        if (color(4,g,B4Counter,2,B4,385,510)) {
            g.drawString("Upgrade Speed", 385, 510);
            g.drawString("Cost: " + B4 + "yal's", 800, 510);
        }

        //item 5
        if (color(5,g,B2Counter,0,B5,385,580)) {
            g.drawString("Buy 50 Bullets", 385, 580);
            g.drawString("Cost: " + B5 + "yal's", 800, 580);
        }

        g.setFont(new Font("Stencil",1,25));
        g.setColor(Color.black);
        g.drawString("Score: " + hud.getScore() + "yal's", (int)game.getWIDTH()/2 - 100, 150);
        g.drawString("(Press Space To Resume Game)",(int)game.getWIDTH()/2 - 210,200);
    }

    public void tick() {
        for (int i = 0; i < handler.getLst().size(); i++) {
            GameObject tempObject = handler.getLst().get(i);
            if (!temporary.contains(tempObject))
                temporary.add(tempObject);
        }
        handler.getLst().clear();
    }

    private boolean color(int B,Graphics g,int counter,int limit,int price,int x, int y) {
        if (B == 3 || B == 5) {
            if (hud.getScore() >= price)
                g.setColor(new Color(0,150,255));
            else
                g.setColor(Color.black);
        }
        else {
            if (counter <= limit) {
                if (hud.getScore() >= price) {
                    g.setColor(new Color(0,150,255));
                }
                else {
                    g.setColor(Color.black);
                }
            }
            else {
                g.setColor(Color.black);
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
