import java.awt.*;

public class AWPAmmo extends Ammo {

    public AWPAmmo(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(100,200,0));
        g.fillOval((int) x, (int) y, 12, 12);
    }
}
