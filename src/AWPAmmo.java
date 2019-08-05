import java.awt.*;

public class AWPAmmo extends Ammo {

    public AWPAmmo(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(5,150,150));
        g.fillOval((int) x, (int) y, 14, 14);

        Font font = new Font("AR DARLING",1,11);
        g.setFont(font);
        g.setColor(new Color(5,150,150));
        g.drawString("AWP",(int) x + 3, (int) y - 5);
    }
}
