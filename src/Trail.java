import java.awt.*;

public class Trail extends GameObject {

    private Handler handler;

    private float health = 20, greenValue = 255,alpha = 1,life;
    private int width, height;
    private Color color;

    public Trail(float x, float y, int width, int height, float life, ID id, Color color, Handler handler) {
        super(x, y, id);
        this.handler = handler;

        this.width = width;
        this.height = height;
        this.life = life;
        this.color = color;
    }

    @Override
    public void tick() {
        health = Game.clamp(health, 0, 50);
        greenValue = Game.clamp(greenValue, 0, 255);

        greenValue = health * 12;

        if (alpha > life)
            alpha -= (life - 0.00001f);
        else handler.removeObject(this);
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setComposite(makeTransparent(alpha));

        g.setColor(color);
        g.fillRect((int)x,(int)y,width,height);

        g2d.setComposite(makeTransparent(1));
    }

    public Rectangle getBounds() {
        return null;
    }

    private AlphaComposite makeTransparent(float alpha){
        int type = AlphaComposite.SRC_OVER;
        return AlphaComposite.getInstance(type,alpha);
    }
}
