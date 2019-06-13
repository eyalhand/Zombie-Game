import javafx.util.Pair;
import java.util.Random;

public class Spawn {

    private Player player;
    private Handler handler;
    private Block block;
    private Game game;

    private Random r = new Random();
    private static int time,x,lastTime;

    private float[] startPositionsX = new float[6];
    private float[] startPositionsY = new float[6];

    public Spawn(Handler handler, Player player,Block block,Game game) {
        this.handler = handler;
        this.player = player;
        this.block = block;
        this.game = game;

        initialize();

        startPositionsX[0] = (float) r.nextInt(10) * -1;
        startPositionsX[1] = (float) Math.random()*11 + Game.WIDTH;
        startPositionsX[2] = (float) r.nextInt(Game.WIDTH);

        startPositionsY[0] = (float) r.nextInt(10) * -1;
        startPositionsY[1] = (float) Math.random()*11 + Game.HEIGHT;
        startPositionsY[2] = (float) r.nextInt(Game.HEIGHT);
    }

    public static void initialize() {
        if (Game.DIFF == 0)
            lastTime = -1000;
        else if (Game.DIFF == 1)
            lastTime = -200;
        else if (Game.DIFF == 2)
            lastTime = 0;
        x = 200;
        time = 0;
    }

    public void tick() {
        time++;
        spawnAmmo();

        Random r = new Random();
        if (time >= lastTime + 1000) {//the longer the game gets, the harder it gets!
            if (x > 50) {
                x -= 10;
            }
            lastTime = time;
        }
        int k = r.nextInt(x);
        if (k == 0) {
            if (Game.DIFF == 0)
                handler.addObject(new EasyZombie(chooseCord().getKey(), chooseCord().getValue(), ID.Zombie, player, handler, block,checkAmmo()));
            else if (Game.DIFF == 1)
                handler.addObject(new NormalZombie(chooseCord().getKey(),chooseCord().getValue(),ID.Zombie,player,handler,block,checkAmmo()));
            else if (Game.DIFF == 2)
                handler.addObject(new HardZombie(chooseCord().getKey(),chooseCord().getValue(),ID.Zombie,player,handler,block,checkAmmo()));
        }
    }

    public void render(){}

    private Pair<Float,Float> chooseCord(){
        float x = startPositionsX[r.nextInt(3)];
        float y;
        if (x < Game.WIDTH && x > 0) {
            y = startPositionsY[r.nextInt(2)];
        } else {
            y = startPositionsY[r.nextInt(3)];
        }
        return new Pair<>(x,y);
    }

    /*private Pair<Float,Float> chooseCord2() {
        float x = r.nextInt(Game.WIDTH - 48);
        while (x < player.getX() + 58 && x > player.getX() - 58)
            x = r.nextInt(Game.WIDTH - 48);
        float y = r.nextInt(Game.HEIGHT - 48);
        while (x < player.getX() + 58 && x > player.getX() - 58)
            y = r.nextInt(Game.HEIGHT - 48);
        return new Pair<>(x,y);
    }*/

    private int checkAmmo() {
        if (game.getGameAmmo() == Game.Ammo.Pistol)
            return 0;
        else if (game.getGameAmmo() == Game.Ammo.Shotgun)
            return 2;
        else if (game.getGameAmmo() == Game.Ammo.Uzi)
            return 1;
        else if (game.getGameAmmo() == Game.Ammo.AK47)
            return 3;
        else if (game.getGameAmmo() == Game.Ammo.AWP)
            return 4;
        return 0;
    }

    private void spawnAmmo() {
        int spawnBulletCollector;
        if (game.getGameAmmo() == Game.Ammo.Pistol) {
            spawnBulletCollector = r.nextInt(230);
            if (spawnBulletCollector == 0) {
                handler.addObject(new PistolAmmo(r.nextInt(Game.WIDTH - 60), r.nextInt(Game.HEIGHT - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Shotgun) {
            spawnBulletCollector = r.nextInt(240);
            if (spawnBulletCollector == 0) {
                handler.addObject(new ShotgunAmmo(r.nextInt(Game.WIDTH - 60), r.nextInt(Game.HEIGHT - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Uzi) {
            spawnBulletCollector = r.nextInt(300);
            if (spawnBulletCollector == 0) {
                handler.addObject(new UziAmmo(r.nextInt(Game.WIDTH - 60), r.nextInt(Game.HEIGHT - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.AK47) {
            spawnBulletCollector = r.nextInt(250);
            if (spawnBulletCollector == 0) {
                handler.addObject(new AK47Ammo(r.nextInt(Game.WIDTH - 60), r.nextInt(Game.HEIGHT - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Negev) {
            spawnBulletCollector = r.nextInt(250);
            if (spawnBulletCollector == 0) {
                handler.addObject(new NegevAmmo(r.nextInt(Game.WIDTH - 60), r.nextInt(Game.HEIGHT - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.AWP) {
            spawnBulletCollector = r.nextInt(250);
            if (spawnBulletCollector == 0) {
                handler.addObject(new AWPAmmo(r.nextInt(Game.WIDTH - 60), r.nextInt(Game.HEIGHT - 60), ID.Ammo, handler));
            }
        }
    }
}
