import java.awt.*;

public class UziBullet extends Bullet {

    public UziBullet(Game game,Player player,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,player,x, y, mx, my, id, handler,20);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(10,100,50));
        g.fillOval((int) x, (int) y, 10, 10);
    }
}
