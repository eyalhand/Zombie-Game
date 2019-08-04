import java.awt.*;

public class AK47Bullet extends Bullet {

    public AK47Bullet(Game game,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,x, y, mx, my, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(255,10,0));
        g.fillOval((int) x, (int) y, 14, 14);
    }
}
