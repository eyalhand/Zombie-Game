import java.awt.*;

public class ShotgunAmmo extends Ammo {

    public ShotgunAmmo(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(150,0,0));
        g.fillOval((int) x, (int) y, 16, 16);
    }
}
