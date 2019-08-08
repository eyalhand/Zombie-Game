import java.awt.*;

public class BlazersAmmo extends Ammo {

    public BlazersAmmo(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(200,150,20));
        g.fillOval((int) x, (int) y, 13, 13);

        Font font = new Font("AR DARLING",1,14);
        g.setFont(font);
        g.setColor(new Color(200,150,20));
        g.drawString("Blazers",(int) x + 3, (int) y - 5);
    }
}