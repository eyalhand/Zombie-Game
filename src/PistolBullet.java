import java.awt.*;

public class PistolBullet extends Bullet {

    public PistolBullet(Game game,Player player,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,player,x, y, mx, my, id, handler,12);
    }

    public void render(Graphics g) {
        g.setColor(new Color(100,0,200));
        g.fillOval((int) x, (int) y, 12, 12);
    }
}
