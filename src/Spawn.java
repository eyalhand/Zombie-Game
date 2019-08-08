import javafx.util.Pair;
import java.util.Random;

public class Spawn {

    private Player player;
    private Handler handler;
    private Game game;

    private Random r = new Random();
    private static int time,x,lastTime;

    private float[] startPositionsX = new float[6];
    private float[] startPositionsY = new float[6];

    public Spawn(Handler handler, Player player,Game game) {
        this.handler = handler;
        this.player = player;
        this.game = game;

        initialize();

        startPositionsX[0] = (float) r.nextInt(10) * -1;
        startPositionsX[1] = (float) Math.random()*11 + (int)game.getWIDTH();
        startPositionsX[2] = (float) r.nextInt((int)game.getWIDTH());

        startPositionsY[0] = (float) r.nextInt(10) * -1;
        startPositionsY[1] = (float) Math.random()*11 + (int)game.getHEIGHT();
        startPositionsY[2] = (float) r.nextInt((int)game.getHEIGHT());
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
                handler.addObject(new EasyZombie(game,chooseCord().getKey(), chooseCord().getValue(), ID.Zombie, player, handler, checkAmmo()));
            else if (Game.DIFF == 1)
                handler.addObject(new NormalZombie(game,chooseCord().getKey(),chooseCord().getValue(),ID.Zombie,player,handler, checkAmmo()));
            else if (Game.DIFF == 2)
                handler.addObject(new HardZombie(game,chooseCord().getKey(),chooseCord().getValue(),ID.Zombie,player,handler, checkAmmo()));
        }
    }

    public void render(){}

    private Pair<Float,Float> chooseCord(){
        float x = startPositionsX[r.nextInt(3)];
        float y;
        if (x < (int)game.getWIDTH() && x > 0) {
            y = startPositionsY[r.nextInt(2)];
        } else {
            y = startPositionsY[r.nextInt(3)];
        }
        return new Pair<>(x,y);
    }

    private int checkAmmo() {
        if (game.getGameAmmo() == Game.Ammo.Pistol)
            return 0;
        else if (game.getGameAmmo() == Game.Ammo.Shotgun)
            return 2;
        else if (game.getGameAmmo() == Game.Ammo.Uzi)
            return 1;
        else if (game.getGameAmmo() == Game.Ammo.AK47)
            return 3;
        return 5;
    }

    private void spawnAmmo() {
        int spawnBulletCollector;
        if (game.getGameAmmo() == Game.Ammo.Pistol) {
            spawnBulletCollector = r.nextInt(230);
            if (spawnBulletCollector == 0) {
                handler.addObject(new PistolAmmo(r.nextInt((int)game.getWIDTH() - 60), r.nextInt((int)game.getHEIGHT() - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Shotgun) {
            spawnBulletCollector = r.nextInt(300);
            if (spawnBulletCollector == 0) {
                handler.addObject(new ShotgunAmmo(r.nextInt((int)game.getWIDTH() - 60), r.nextInt((int)game.getHEIGHT() - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Uzi) {
            spawnBulletCollector = r.nextInt(250);
            if (spawnBulletCollector == 0) {
                handler.addObject(new UziAmmo(r.nextInt((int)game.getWIDTH() - 60), r.nextInt((int)game.getHEIGHT() - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.AK47) {
            spawnBulletCollector = r.nextInt(300);
            if (spawnBulletCollector == 0) {
                handler.addObject(new AK47Ammo(r.nextInt((int)game.getWIDTH() - 60), r.nextInt((int)game.getHEIGHT() - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Negev) {
            spawnBulletCollector = r.nextInt(300);
            if (spawnBulletCollector == 0) {
                handler.addObject(new NegevAmmo(r.nextInt((int)game.getWIDTH() - 60), r.nextInt((int)game.getHEIGHT() - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.AWP) {
            spawnBulletCollector = r.nextInt(320);
            if (spawnBulletCollector == 0) {
                handler.addObject(new AWPAmmo(r.nextInt((int)game.getWIDTH() - 60), r.nextInt((int)game.getHEIGHT() - 60), ID.Ammo, handler));
            }
        }
        else if (game.getGameAmmo() == Game.Ammo.Blazer) {
            spawnBulletCollector = r.nextInt(320);
            int spawnBulletCollector2 = r.nextInt(300);
            if (spawnBulletCollector == 0) {
                handler.addObject(new AWPAmmo(r.nextInt((int)game.getWIDTH() - 60), r.nextInt((int)game.getHEIGHT() - 60), ID.Ammo, handler));
            }
            if (spawnBulletCollector2 == 0) {
                handler.addObject(new BlazersAmmo(r.nextInt((int)game.getWIDTH() - 60), r.nextInt((int)game.getHEIGHT() - 60), ID.Ammo, handler));
            }
        }
    }
}
