import java.awt.*;

public class GrenadeAmmo extends Ammo {

    public GrenadeAmmo(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(115,255,50));
        g.fillOval((int) x, (int) y, 13, 13);
    }
}