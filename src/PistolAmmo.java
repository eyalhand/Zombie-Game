import java.awt.*;

public class PistolAmmo extends Ammo {

    public PistolAmmo(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(100,0,200));
        g.fillOval((int) x, (int) y, 13, 13);

        Font font = new Font("AR DARLING",1,14);
        g.setFont(font);
        g.setColor(new Color(100,0,200));
        g.drawString("Pistol",(int) x + 3, (int) y - 5);
    }
}
