import java.awt.*;

public class AK47Ammo extends Ammo {

    public AK47Ammo(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(255,10,0));
        g.fillOval((int) x, (int) y, 14, 14);
    }
}