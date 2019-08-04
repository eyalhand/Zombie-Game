import java.awt.*;

public class AWPBullet extends Bullet {

    public AWPBullet(Game game,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,x, y, mx, my, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(100,200,0));
        g.fillOval((int) x, (int) y, 12, 12);
    }
}
