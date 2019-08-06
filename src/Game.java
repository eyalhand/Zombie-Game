import org.newdawn.slick.SlickException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    Toolkit toolkit = Toolkit.getDefaultToolkit();

    public double WIDTH = toolkit.getScreenSize().getWidth() + 10, HEIGHT = toolkit.getScreenSize().getHeight() + 10;
    private String title = "Zombies Attack";

    private Thread thread;

    private boolean isRunning = false;
    public static int DIFF = 0;
    //0 = easy
    //1 = normal
    //2 = hard

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
        AWP,
        Blazer
    }

    private STATE gameState = STATE.Menu;
    private Ammo gameAmmo = Ammo.Pistol;

    private boolean cond = false;
    public int onoffCounter = 0;
    public static BufferedImage spriteSheet;
    public static BufferedImage shopImg;

    public Game() throws SlickException, IOException {
        init();

        new Window((int)WIDTH,(int)HEIGHT,title,this);

        start();
    }

    private void init() throws SlickException, IOException {
        AudioPlayer.load();
        AudioPlayer.getMusic("background_music").loop();
        //BufferedImageLoader loader = new BufferedImageLoader();
        //spriteSheet = loader.loadImage("/res/d.jpg");
        spriteSheet = ImageIO.read(getClass().getResource("/res/z.jpg"));
        shopImg = ImageIO.read(getClass().getResource("/res/shopImg.jpg"));

        spawner = null;
        mouseInput = null;
        handler = new Handler();
        hud = new HUD(this);
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
                AudioPlayer.getMusic("background_music").loop();
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
        graphics.fillRect(0,0,(int)WIDTH,(int)HEIGHT);

        handler.render(graphics);
        if (gameState == STATE.Game) {
            hud.render(graphics);
            graphics.setColor(new Color(255,200,100));
            graphics.setFont(new Font("arial", 5, 50));
            if (cond && onoffCounter < 50) {
                graphics.drawString("Blazers On", (int) (WIDTH / 2) - 150, (int) (HEIGHT / 2) - 50);
                onoffCounter++;
            }
            if (gameAmmo == Ammo.Blazer && !cond && onoffCounter < 50) {
                graphics.drawString("Blazers Off", (int) (WIDTH / 2) - 150, (int) (HEIGHT / 2)-50);
                onoffCounter++;
            }
        }
        else {
            graphics.drawImage(spriteSheet,0,0,(int)WIDTH,(int)HEIGHT,null);
            if (gameState == STATE.Menu || gameState == STATE.Help
                    || gameState == STATE.GameOver
                    || gameState == STATE.Select) menu.render(graphics);
            else if (gameState == STATE.Pause) pause.render(graphics);
            else if (gameState == STATE.Shop) {
                graphics.drawImage(shopImg, 0, 0, (int) WIDTH, (int) HEIGHT, null);
                shop.render(graphics);
            }
        }

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

    public double getWIDTH() { return WIDTH; }

    public double getHEIGHT() { return HEIGHT; }

    public boolean getCond() { return cond; }

    public void setCond(boolean b) { cond = b; }

    public void setSpawner(Spawn spawner) { this.spawner = spawner; }

    public void setMouseInput(MouseInput mouseInput) { this.mouseInput = mouseInput; }

    public STATE getGameState() { return gameState; }

    public void setGameState(STATE gameState) { this.gameState = gameState; }

    public Ammo getGameAmmo() { return gameAmmo; }

    public void setGameAmmo(Ammo gameAmmo) { this.gameAmmo = gameAmmo; }

    public static void main(String[]args) throws SlickException, IOException {
        new Game();
    }
}
