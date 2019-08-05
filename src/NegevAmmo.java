import java.awt.*;

public class NegevAmmo extends Ammo {

    public NegevAmmo(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(180,60,0));
        g.fillOval((int) x, (int) y, 12, 12);
        g.fillOval((int) x + 6, (int) y + 6, 12, 12);
        g.fillOval((int) x + 12, (int) y + 12, 12, 12);

        Font font = new Font("AR DARLING",1,11);
        g.setFont(font);
        g.setColor(new Color(180,60,0));
        g.drawString("Negev",(int) x + 3, (int) y - 5);
    }
}
