package notinuse;

import java.awt.*;
import java.util.Random;

public class MenuParticle extends GameObject {

    private Handler handler;

    private Color col;
    private Random r = new Random();

    public MenuParticle(float x, float y, ID id, Handler handler) {
        super(x, y, id);

        this.handler = handler;

        velX = r.nextInt(5 - -5) + -6;
        velY = r.nextInt(5 - -5) + -10;

        col = new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255));
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y, 16, 16);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y <= 0 || y >= Game.HEIGHT - 50) velY *= -1;
        if (x <= 0 || x >= Game.WIDTH - 50) velX *= -1;

        handler.addObject(new Trail(x,y,16,16,0.04f, ID.Trail,col,handler));
    }

    @Override
    public void render(Graphics g) {
        g.setColor(col);
        g.fillRect((int)x,(int)y, 16, 16);
    }
}
