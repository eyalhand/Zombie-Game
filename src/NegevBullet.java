import java.awt.*;

public class NegevBullet extends Bullet {

    public NegevBullet(Game game,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,x, y, mx, my, id, handler);
        super.setSPEED(super.getSPEED() + 8);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(180,60,0));
        g.fillOval((int) x, (int) y, 12, 12);
    }
}
