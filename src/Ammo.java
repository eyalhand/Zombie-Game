import java.awt.*;

public abstract class Ammo extends GameObject {

    private Handler handler;

    private int seconds = 0;

    public Ammo(float x, float y, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        velX = 0;
        velY = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 13, 13);
    }

    @Override
    public void tick() {
        seconds++;

        if (seconds >= 320) handler.removeObject(this);
    }

    @Override
    public abstract void render(Graphics g);
}
