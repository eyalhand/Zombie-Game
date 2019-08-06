package notinuse;

import java.awt.*;
import java.util.Random;

public class GreatZombie extends GameObject {

    private Handler handler;

    private Random r = new Random();
    private int timer = 80;
    private int timer2 = 50;
    private int timer3 = 1000;

    public GreatZombie(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        velX = 0;
        velY = 2;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 120, 96);
    }


    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (timer <= 0) {
            velY = 0;
            timer2--;
        } else timer--;

        if (timer2 <= 0 && timer3 > 0) {
            if (velX == 0) velX = 3;
            int spawn = r.nextInt(10);
            if (spawn == 0) handler.addObject(new PistolBullet((int)x+48,(int)y+96,0,0,ID.Bullet,handler));
        }

        if (timer3 <= 0) {
            velY = 2;
            velX = 0;
        }
        else timer3--;

        if (x <= 0 || x >= Game.WIDTH - 96) velX *= -1;
        //if (y >= Game.HEIGHT - 48) { handler.removeObject(this); Spawn.setGreatLevel(); }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, 120, 105);
    }
}
