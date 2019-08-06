package notinuse;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SmartZombie extends GameObject {

    private Player player;
    private Handler handler;
    private Block block;

    private float health = 20, greenValue = 255;

    private BufferedImage zombieImage;

    public SmartZombie(float x, float y, ID id, Player player, Handler handler, Block block) {
        super(x, y, id);
        this.player = player;
        this.handler = handler;
        this.block = block;

        SpriteSheet spriteSheet  = new SpriteSheet(Game.spriteSheet);
        zombieImage = spriteSheet.grabImage(1,1,25,25);

        velX = 0;
        velY = 0;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }

    private void collision() {
        for (int i = 0; i < handler.getLst().size(); i++) {
            GameObject tempObject = handler.getLst().get(i);
            if (tempObject.getId() == ID.Bullet) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    //collision code
                    handler.removeObject(tempObject);
                    health -= 8;
                    if (health <= 0)
                        handler.removeObject(this);
                }
            }
        }
    }

    @Override
    public void tick() {
        health = Game.clamp(health, 0, 50);
        greenValue = Game.clamp(greenValue, 0, 255);

        greenValue = health * 12;

        if (block.velY !=0 || block.velX != 0) {
            velX = 0;
            velY = 0;
        }
        else {
            float diffX = x - player.x - 8;
            float diffY = y - player.y - 8;

            float distance = (float) Math.sqrt((diffX + 8) * (diffX + 8) + (diffY + 8) * (diffY + 8));

            velX = (float) (-1.0 / distance) * diffX;
            velY = (float) (-1.0 / distance) * diffY;

            x += velX;
            y += velY;

            if (y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
            if (x <= 0 || x >= Game.WIDTH - 32) velX *= -1;

            handler.addObject(new Trail(x, y, 23, 23, 0.04f, ID.Trail, new Color(100, (int) greenValue, 0), handler));

            collision();
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(100,(int)greenValue,0));
        g.fillRect((int) x, (int) y, 23, 23);

        //g.drawImage(zombieImage,(int)x,(int)y,null);
    }
}
