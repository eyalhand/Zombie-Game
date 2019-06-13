import javafx.util.Pair;
import java.awt.*;
import java.util.Random;

public abstract class Bullet extends GameObject {

    private Handler handler;

    private float SPEED = 13;//constant speed of a bullet

    public Bullet(float x, float y, float mx, float my, ID id, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        this.velX = gunShot(mx,my,x,y).getKey();
        this.velY = gunShot(mx,my,x,y).getValue();
    }

    @Override
    public void tick() {

        x += velX;
        y += velY;

        if (y >= Game.HEIGHT || x >= Game.WIDTH || x < 0 || y < 0) handler.removeObject(this);
    }

    @Override
    public abstract void render(Graphics g);

    private Pair<Float,Float> gunShot(float mx, float my, float x, float y) {
        float diffX = mx - x;
        float diffY = my - y;

        float theta = (float)Math.atan(diffY/diffX);

        float velX, velY;

        if (diffX >= 0) {
            velX = SPEED * (float) Math.cos(theta);
            velY = SPEED * (float) Math.sin(theta);
        } else {
            velX = -SPEED * (float) Math.cos(theta);
            velY = -SPEED * (float) Math.sin(theta);
        }

        return new Pair<>(velX,velY);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 8, 8);
    }

    public float getSPEED() {
        return SPEED;
    }

    public void setSPEED(float SPEED) {
        this.SPEED = SPEED;
    }
}