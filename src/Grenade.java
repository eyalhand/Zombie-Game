import java.awt.*;

public class Grenade extends Bullet {

    public Grenade(Game game,float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,x, y, mx, my, id, handler);

        this.setSPEED(1);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(110,255,50));
        g.fillOval((int) x, (int) y, 14, 14);
    }
}
