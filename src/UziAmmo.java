import java.awt.*;

public class UziAmmo extends Ammo {

    public UziAmmo(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(10,100,50));
        g.fillOval((int) x, (int) y, 10, 10);
        g.fillOval((int) x + 6, (int) y + 6, 10, 10);
        g.fillOval((int) x + 12, (int) y + 12, 10, 10);

        Font font = new Font("AR DARLING",1,11);
        g.setFont(font);
        g.setColor(new Color(10,100,50));
        g.drawString("Uzi",(int) x + 3, (int) y - 5);
    }
}
