import java.awt.*;

public class ShotgunBullet extends Bullet {

    public ShotgunBullet(Game game,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,x, y, mx, my, id, handler);
        super.setSPEED(super.getSPEED() - 2);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(200,0,0));
        g.fillOval((int) x, (int) y, 16, 16);
    }
}
