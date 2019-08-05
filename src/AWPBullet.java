import java.awt.*;

public class AWPBullet extends Bullet {

    public AWPBullet(Game game,Player player,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,player,x, y, mx, my, id, handler, 10);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(5,150,180));
        g.fillOval((int) x, (int) y, 12, 12);
    }
}
