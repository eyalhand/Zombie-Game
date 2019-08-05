import java.awt.*;

public class BlazersBullet extends Bullet {

    public BlazersBullet(Game game, Player player, float x, float y, float mx, float my, ID id, Handler handler) {
        super(game,player,x, y, mx, my, id, handler,1);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(110,255,50));
        g.fillOval((int) x, (int) y, 14, 14);
    }
}
