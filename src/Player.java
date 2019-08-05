import java.awt.*;

public class Player extends GameObject {

    private Game game;
    private Handler handler;

    private static int ammo = 0;
    private static int Blazers = 0;

    public Player(float x, float y, ID id, Handler handler,Game game) {
        super(x, y, id);
        this.handler = handler;
        this.game = game;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x,0,(int)game.WIDTH - 50);
        y = Game.clamp(y,0,(int)game.HEIGHT - 70);

        collision();
    }

    @Override
    public void render(Graphics g) {
       if (id == ID.Player) g.setColor(Color.blue);
        else g.setColor(Color.white);
        g.fillOval((int)x,(int)y,32,32);

        Color c,c2;
        if (game.getCond()) {
            c = Color.gray;
            c2 = Color.white;
        }
        else {
            c2 = Color.gray;
            c = Color.white;
        }
        g.setColor(c);
        g.setFont(new Font("arial",8,13));
        g.drawString("Ammo: " + ammo, 15, 94);
        if (game.getGameAmmo() == Game.Ammo.Blazer) {
            g.setColor(c2);
            g.drawString("Blazers: " + Blazers, 15, 110);
        }
        g.drawString("(" + game.getGameAmmo() + ")", 15, 125);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,32,32);
    }

    private void collision() {
        for (int i = 0; i < handler.getLst().size(); i++) {
            GameObject tempObject = handler.getLst().get(i);
            if (tempObject.getId() == ID.Zombie) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    HUD.Health -= 4;
                }
            } else if (tempObject.getId() == ID.Ammo) {
                ammoCollision(tempObject);
            }
        }
    }

    public static int getAmmo() { return ammo; }

    public static void setAmmu(int inc) { ammo += inc; }

    public static int getBlazers() { return Blazers; }

    public static void setBlazers(int inc) { Blazers += inc; }

    private void ammoCollision(GameObject tempObject) {
        if (game.getGameAmmo() == Game.Ammo.Pistol) {
            if (getBounds().intersects(tempObject.getBounds())) {
                //PistolAmmo code
                ammo += 10;
                handler.removeObject(tempObject);
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Shotgun) {
            if (getBounds().intersects(tempObject.getBounds())) {
                //ShotgunAmmo code
                ammo += 7;
                handler.removeObject(tempObject);
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Uzi) {
            if (getBounds().intersects(tempObject.getBounds())) {
                //UziAmmo code
                ammo += 30;
                handler.removeObject(tempObject);
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.AK47) {
            if (getBounds().intersects(tempObject.getBounds())) {
                //AK47Ammo code
                ammo += 12;
                handler.removeObject(tempObject);
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Negev) {
            if (getBounds().intersects(tempObject.getBounds())) {
                //NegevAmmo code
                ammo += 20;
                handler.removeObject(tempObject);
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.AWP) {
            if (getBounds().intersects(tempObject.getBounds())) {
                //AWP code
                ammo += 10;
                handler.removeObject(tempObject);
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Blazer) {
            if (getBounds().intersects(tempObject.getBounds())) {
                if (game.getCond() && tempObject instanceof BlazersAmmo) {
                    //BlazersBullet code
                    Blazers += 2;
                    handler.removeObject(tempObject);
                }
                else {
                    //AWP code
                    ammo += 10;
                    handler.removeObject(tempObject);
                }
            }
        }
    }
    public void initialize() { ammo = 0; Blazers = 0; }
}
