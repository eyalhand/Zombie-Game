import java.awt.*;

public class AK47Ammo extends Ammo {

    public AK47Ammo(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(255,10,0));
        g.fillOval((int) x, (int) y, 14, 14);

        Font font = new Font("AR DARLING",1,14);
        g.setFont(font);
        g.setColor(new Color(255,10,0));
        g.drawString("AK47",(int) x + 3, (int) y - 5);
    }
}
