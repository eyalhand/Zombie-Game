import java.util.Random;

public abstract class Zombie extends GameObject {

    private int AD;
    protected Game game;

    public Zombie(Game game, float x, float y, ID id,int AD) {
        super(x, y, id);
        this.AD = AD;

        this.game = game;
    }

    public float collision(Handler handler,float health, int changeOfHealth) {
        for (int i = 0; i < handler.getLst().size(); i++) {
            GameObject tempObject = handler.getLst().get(i);
            if (tempObject.getId() == ID.Bullet) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    if (game.getCond() && game.getGameAmmo() == Game.Ammo.Blazer) {
                        //collision code
                        health -= changeOfHealth + 6;
                        if (health <= 0) {
                            if (!game.isMute())
                                AudioPlayer.getSound("zombie_death").play();
                            HUD.score += 100;
                            HUD.zombiesKilled++;
                            handler.removeObject(this);
                        }
                    }
                    else {
                        //collision code
                        handler.removeObject(tempObject);
                        health -= changeOfHealth + AD;
                        if (health <= 0) {
                            if (!game.isMute())
                                AudioPlayer.getSound("zombie_death").play();
                            HUD.score += 100;
                            HUD.zombiesKilled++;
                            handler.removeObject(this);
                        }
                    }
                }
            }
        }
        return health;
    }

    public void comeHere(){
        Random r = new Random();
        int i = r.nextInt(6000);
        if (i == 0) {
            AudioPlayer.getSound("zombie_come_here").play();
        }
    }
}
