import java.awt.*;

public class PistolBullet extends Bullet {

    public PistolBullet(Game game,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,x, y, mx, my, id, handler);
    }

    public void render(Graphics g) {
        g.setColor(new Color(100,0,200));
        g.fillOval((int) x, (int) y, 12, 12);
    }
}
