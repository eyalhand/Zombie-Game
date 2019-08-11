import java.awt.*;

public class NormalZombie extends Zombie {

    private Player player;
    private Handler handler;

    private float health = 30, colorValue = 255;

    private Color color;

    public NormalZombie(Game game, float x, float y, ID id, Player player, Handler handler,int ad) {
        super(game,x, y, id,ad);
        this.player = player;
        this.handler = handler;

        velX = 0;
        velY = 0;
    }

    @Override
    public void tick() {
        health = Game.clamp(health, 0, 50);
        colorValue = Game.clamp(colorValue, 0, 255);

        colorValue = health * 8;

        float diffX = x - player.x - 8;
        float diffY = y - player.y - 8;

        float distance = (float) Math.sqrt((diffX + 8) * (diffX + 8) + (diffY + 8) * (diffY + 8));

        velX = (float) (-1.0 / distance) * diffX;
        velY = (float) (-1.0 / distance) * diffY;

        x += velX;
        y += velY;

        if (y <= 0 || y >= (int) game.getHEIGHT() - 32) velY *= -1;
        if (x <= 0 || x >= (int) game.getWIDTH() - 32) velX *= -1;

        chooseColor();

        handler.addObject(new Trail(x, y, 23, 23, 0.04f, ID.Trail, color, handler));
        health = collision(handler, health, 6);

        comeHere();
    }

    private void chooseColor () {
        if (game.getZombieColor() == Game.ZombieColor.Blue)
            color = new Color(0, 0, (int) colorValue);
        else if (game.getZombieColor() == Game.ZombieColor.Yellow)
            color = new Color((int)colorValue,249,0);
        else if (game.getZombieColor() == Game.ZombieColor.White)
            color = new Color(255, 255, (int) colorValue);
        else if (game.getZombieColor() == Game.ZombieColor.Green)
            color = new Color(100, (int) colorValue, 0);
        else if (game.getZombieColor() == Game.ZombieColor.Purple)
            color = new Color(100, 0, (int) colorValue);
        else
            color = new Color((int) colorValue, 0, 0);
    }

    @Override
    public void render(Graphics g) {}

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }
}