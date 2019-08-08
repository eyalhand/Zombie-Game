import java.awt.*;

public class AK47Bullet extends Bullet {

    public AK47Bullet(Game game,Player player,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,player,x, y, mx, my, id, handler,11);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(255,10,0));
        g.fillOval((int) x, (int) y, 14, 14);
    }
}
