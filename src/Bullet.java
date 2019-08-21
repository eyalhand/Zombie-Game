import java.awt.*;

public abstract class Bullet extends GameObject {

    private Handler handler;
    private Game game;
    private Player player;

    private float SPEED;

    public Bullet(Game game,Player player,float x, float y, float mx, float my, ID id, Handler handler, float SPEED) {
        super(x, y, id);
        this.handler = handler;

        this.SPEED = SPEED;
        this.game = game;
        this.player = player;

        this.velX = gunShot(mx,my,x,y).getKey();
        this.velY = gunShot(mx,my,x,y).getValue();
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (y >= (int)game.getHEIGHT() || x >= (int)game.getWIDTH() || x < 0 || y < 0) handler.removeObject(this);
    }

    @Override
    public abstract void render(Graphics g);

    private Pair<Float,Float> gunShot(float mx, float my, float x, float y) {
        float diffX = mx - x;
        float diffY = my - y;

        float theta = (float)Math.atan(diffY/diffX);

        float velX, velY;

        if (diffX >= 0) {
            velX = SPEED * (float) Math.cos(theta);
            velY = SPEED * (float) Math.sin(theta);
        } else {
            velX = -SPEED * (float) Math.cos(theta);
            velY = -SPEED * (float) Math.sin(theta);
        }

        return new Pair<>(velX,velY);
    }

    public Rectangle getBounds() {
        if (!(game.getGameAmmo() == Game.Ammo.Blazer && game.getCond() && player.getBlazers() > 0)) {
            return new Rectangle((int) x, (int) y, 8, 8);
        }
        else {
            return new Rectangle((int) x - 50, (int) y - 50, 100, 100);
        }
    }

    public float getSPEED() {
        return SPEED;
    }

    public void setSPEED(float SPEED) {
        this.SPEED = SPEED;
    }
}