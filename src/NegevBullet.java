import java.awt.*;

public class NegevBullet extends Bullet {

    public NegevBullet(Game game,Player player,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,player,x, y, mx, my, id, handler,17);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(180,60,0));
        g.fillOval((int) x, (int) y, 12, 12);
    }
}
