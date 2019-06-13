package notinuse;

import java.awt.*;

public class FastZombie extends GameObject {

    public FastZombie(float x, float y, ID id) {
        super(x, y, id);

        velX = 4;
        velY = 9;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y, 16, 16);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
        if (x <= 0 || x >= Game.WIDTH - 32) velX *= -1;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect((int)x,(int)y, 16, 16);
    }
}
