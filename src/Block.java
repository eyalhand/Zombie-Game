import java.awt.*;

public class Block extends GameObject {

    private Player player;

    private int width, height;
    private boolean w,s,a,d;//key pressed

    public Block(float x, float y, int width, int height, ID id, Player player) {
        super(x, y, id);
        this.player = player;

        this.width = width;
        this.height = height;

        this.velX = 0;
        this.velY = 0;
    }

    @Override
    public void tick() {
        if (player.x >= Game.WIDTH - 70 && d) {
            if (player.y >= Game.HEIGHT - 70 && s) {
                velX = -5;
                velY = -5;
            } else if (player.y <= 0 && w) {
                velX = -5;
                velY = 5;
            }
            else
                velX = -5;
        }
        else if (player.y >= Game.HEIGHT - 70 && s) {
            if (player.x <= 0 && a) {
                velX = 5;
                velY = -5;
            }
            else
                velY = -5;
        }
        else if (player.x <= 0 && a) {
            if (player.y <= 0 && w) {
                velX = 5;
                velY = 5;
            } else
                velX = 5;
        } else if (player.y <= 0 && w)
            velY = 5;
        else {
            velX = 0;
            velY = 0;
        }

        x += velX;
        y += velY;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(120,20,20));
        g.fillRect((int)x,(int)y,width,height);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    /*private void generateBlocks(){
        handler.addObject(new Block(120,70,20,80, ID.Block,player));
        handler.addObject(new Block(120,70,20,80, ID.Block,player));
        handler.addObject(new Block(120,70,20,80, ID.Block,player));
        handler.addObject(new Block(120,70,20,80, ID.Block,player));
        handler.addObject(new Block(120,70,20,80, ID.Block,player));
        handler.addObject(new Block(120,70,20,80, ID.Block,player));
    }*/

    public void setW(boolean w) {
        this.w = w;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public void setD(boolean d) {
        this.d = d;
    }
}
