import org.newdawn.slick.SlickException;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

public class Game extends Canvas implements Runnable {

    public static int WIDTH = 640, HEIGHT = WIDTH/12*9;
    private String title = "Zombies Attack";

    private Thread thread;

    private boolean isRunning = false;
    public static int DIFF = 0;
    //0 = easy
    //1 = normal
    //2 = hard
    public static int MODE = 0;
    //0 = one player mode
    //1 = two player mode

    private LinkedList<Block> blocks = new LinkedList<>();

    //Instances
    private Handler handler;
    private HUD hud;
    private Spawn spawner;
    private Menu menu;
    private Pause pause;
    private MouseInput mouseInput;
    private Shop shop;

    public enum STATE {
        Menu,
        Help,
        GameOver,
        Game,
        Pause,
        Shop,
        Select
    }

    public enum Ammo {
        Pistol,
        Shotgun,
        Uzi,
        AK47,
        Negev,
        AWP
    }

    private STATE gameState = STATE.Menu;
    private Ammo gameAmmo = Ammo.Pistol;
    public static BufferedImage spriteSheet;

    public Game() throws SlickException, IOException {
        init();

        new Window(WIDTH,HEIGHT,title,this);

        start();
    }

    private void init() throws SlickException, IOException {
        AudioPlayer.load();
        AudioPlayer.getMusic("background_music").loop();
        BufferedImageLoader loader = new BufferedImageLoader();
        spriteSheet = loader.loadImage("/res/zombie.jpg");

        spawner = null;
        mouseInput = null;
        handler = new Handler();
        hud = new HUD();
        shop = new Shop(handler,hud,this);
        menu = new Menu(this,handler,hud);
        pause = new Pause(this,handler);
        this.addMouseListener(menu);
        this.addMouseListener(pause);
        this.addMouseListener(shop);
        this.addKeyListener(new KeyInput(handler,this,pause,shop));
    }

    private synchronized void start() {
        if (isRunning) return;

        thread = new Thread(this);
        thread.start();
        isRunning = true;
    }

    private synchronized void stop() {
        if (!isRunning) return;

        try {
            thread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        isRunning = false;
    }

    //gameloop
    public void run() {
        this.requestFocus();
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        //updates the game
        handler.tick();
        if (gameState == STATE.Game) {
            spawner.tick();
            hud.tick();
            if (HUD.Health == 0) {
                handler.getLst().clear();
                gameState = STATE.GameOver;
                AudioPlayer.getMusic("pursuit").stop();
                AudioPlayer.getSound("haha").play();
            }
        }
        else if (gameState == STATE.GameOver) {
            this.removeMouseListener(mouseInput);
            menu.tick();
        }
        else if (gameState == STATE.Menu || gameState == STATE.Help
                || gameState == STATE.Select) menu.tick();
        else if (gameState == STATE.Pause)
            pause.tick();
        else if (gameState == STATE.Shop)
            shop.tick();
    }

    private void render(){
        //renders the game
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bs.getDrawGraphics();

        graphics.setColor(Color.black);
        graphics.fillRect(0,0,WIDTH,HEIGHT);

        handler.render(graphics);
        if (gameState == STATE.Game) hud.render(graphics);
        else if (gameState == STATE.Menu || gameState == STATE.Help
                || gameState == STATE.GameOver
                || gameState == STATE.Select) menu.render(graphics);
        else if (gameState == STATE.Pause) pause.render(graphics);
        else if (gameState == STATE.Shop)
            shop.render(graphics);

        bs.show();
        graphics.dispose();
    }

    public static float clamp(float var, float min, float max) {
        if (var >= max)
            return max;
        else if (var <= min)
            return min;
        else
            return var;
    }

    public void setSpawner(Spawn spawner) { this.spawner = spawner; }

    public void setMouseInput(MouseInput mouseInput) { this.mouseInput = mouseInput; }

    public STATE getGameState() { return gameState; }

    public void setGameState(STATE gameState) { this.gameState = gameState; }

    public Ammo getGameAmmo() { return gameAmmo; }

    public void setGameAmmo(Ammo gameAmmo) { this.gameAmmo = gameAmmo; }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public static void main(String[]args) throws SlickException, IOException {
        new Game();
    }
}
