package notinuse;

import java.awt.*;

public class DVDZombie extends GameObject {

    public DVDZombie(float x, float y, ID id) {
        super(x, y, id);

        velX = 3;
        velY = 3;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,16,26);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT - 58) velY *= -1;
        if (x <= 0 || x >= Game.WIDTH - 32) velX *= -1;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect((int)x,(int)y,16,26);
    }
}
