import org.newdawn.slick.SlickException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends Canvas implements Runnable {

    Toolkit toolkit = Toolkit.getDefaultToolkit();

    private double WIDTH = toolkit.getScreenSize().getWidth(), HEIGHT = toolkit.getScreenSize().getHeight() - 30;
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
    private Settings settings;
    private HighScore highScore;

    public enum STATE {
        Menu,
        Help,
        GameOver,
        Game,
        Pause,
        Shop,
        Select,
        Settings
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

    public enum SettingOption {
        PlayerColors,
        ZombieColors,
        Backgrounds,
        Mode
    }

    public enum PlayerColor {
        Blue,
        Green,
        Yellow,
        Black,
        Gray,
        Red
    }

    public enum ZombieColor {
        Blue,
        Green,
        Yellow,
        Purple,
        White,
        Red
    }

    public enum Background {
        Background1,
        Background2,
        Background3,
        Background4
    }

    public enum Mode {
        Regular,
        MaxDamage
    }

    private STATE gameState = STATE.Menu;
    private Ammo gameAmmo = Ammo.Pistol;
    private SettingOption settingOption = SettingOption.PlayerColors;
    private PlayerColor playerColor = PlayerColor.Blue;
    private ZombieColor zombieColor = ZombieColor.Green;
    private Background background = Background.Background1;
    private Mode mode = Mode.Regular;

    private boolean cond = false;
    private boolean mute = false;
    private boolean maxDamageOneTime = false;
    private int onoffCounter = 0;
    private int highScoreLevel = 0;

    public static BufferedImage spriteSheet;
    public static BufferedImage shopImg;
    public static BufferedImage background1;
    public static BufferedImage background2;
    public static BufferedImage background3;
    public static BufferedImage background4;

    public Game() throws SlickException, IOException {
        init();

        new Window((int)WIDTH,(int)HEIGHT,title,this);

        start();
    }

    private void init() throws SlickException, IOException {
        AudioPlayer.load();
        AudioPlayer.getMusic("background_music").loop();

        spriteSheet = ImageIO.read(getClass().getResource("/res/z.jpg"));
        shopImg = ImageIO.read(getClass().getResource("/res/shopImg.jpg"));
        background1 = ImageIO.read(getClass().getResource("/res/stage1.jpg"));
        background2 = ImageIO.read(getClass().getResource("/res/stage2.jpg"));
        background3 = ImageIO.read(getClass().getResource("/res/stage3.jpg"));
        background4 = ImageIO.read(getClass().getResource("/res/stage4.jpg"));

        spawner = null;
        mouseInput = null;
        handler = new Handler();
        hud = new HUD(this);
        shop = new Shop(handler,hud,this);
        pause = new Pause(this,handler);
        settings = new Settings(this);
        highScore = new HighScore(this);
        menu = new Menu(this,handler,highScore);
        this.addMouseListener(menu);
        this.addMouseListener(pause);
        this.addMouseListener(shop);
        this.addMouseListener(settings);
        this.addKeyListener(new KeyInput(handler,this,pause,shop,menu,highScore));
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

            try {
                render();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            if (mode == Mode.MaxDamage && !maxDamageOneTime) {
                maxDamageOneTime = true;
                gameAmmo = Ammo.Blazer;
                hud.bounds = 250;
                hud.Health = 100 + (hud.bounds / 2);
                handler.setSpeed(8);
            }
            spawner.tick();
            hud.tick();
            if (HUD.Health == 0) {
                handler.getLst().clear();
                gameState = STATE.GameOver;
                if (!mute) {
                    AudioPlayer.getMusic("pursuit").stop();
                    AudioPlayer.getMusic("background_music").loop();
                }
            }
        }
        else if (gameState == STATE.GameOver) {
            this.removeMouseListener(mouseInput);
            maxDamageOneTime = false;
        }
        else if (gameState == STATE.Menu || gameState == STATE.Help
                || gameState == STATE.Select) menu.tick();
        else if (gameState == STATE.Pause)
            pause.tick();
        else if (gameState == STATE.Shop)
            shop.tick();
    }

    private void render() throws IOException {
        //renders the game
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bs.getDrawGraphics();

        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, (int) WIDTH, (int) HEIGHT);

        if (gameState == STATE.Game) {
            if (background == Background.Background1)
                graphics.drawImage(background1, 0, 0, (int) WIDTH, (int) HEIGHT, null);
            else if (background == Background.Background2)
                graphics.drawImage(background2, 0, 0, (int) WIDTH, (int) HEIGHT, null);
            else if (background == Background.Background3)
                graphics.drawImage(background3, 0, 0, (int) WIDTH, (int) HEIGHT, null);
            else {
                graphics.drawImage(background4, 0, 0, (int) WIDTH, (int) HEIGHT, null);
            }

            handler.render(graphics);
            hud.render(graphics);
            graphics.setColor(new Color(255, 200, 100));
            graphics.setFont(new Font("arial", 5, 50));
            if (cond && onoffCounter < 50) {
                graphics.drawString("Blazers On", (int) (WIDTH / 2) - 150, (int) (HEIGHT / 2) - 50);
                onoffCounter++;
            }
            if (gameAmmo == Ammo.Blazer && !cond && onoffCounter < 50) {
                graphics.drawString("Blazers Off", (int) (WIDTH / 2) - 150, (int) (HEIGHT / 2) - 50);
                onoffCounter++;
            }
        } else {
            graphics.drawImage(spriteSheet, 0, 0, (int) WIDTH, (int) HEIGHT, null);
            if (gameState == STATE.Menu || gameState == STATE.Help
                    || gameState == STATE.GameOver
                    || gameState == STATE.Select) menu.render(graphics);
            else if (gameState == STATE.Pause) pause.render(graphics);
            else if (gameState == STATE.Shop) {
                graphics.drawImage(shopImg, 0, 0, (int) WIDTH, (int) HEIGHT, null);
                shop.render(graphics);
            } else if (gameState == STATE.Settings) settings.render(graphics);
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

    public boolean isMute() { return mute; }

    public void setMute(boolean m) { mute = m; }

    public int getOnoffCounter() { return onoffCounter; }

    public void setOnoffCounter(int x) { onoffCounter = x; }

    public boolean getCond() { return cond; }

    public void setCond(boolean b) { cond = b; }

    public SettingOption getSettingOption() { return settingOption; }

    public void setSettingOption(SettingOption x) { settingOption = x; }

    public PlayerColor getPlayerColor() { return playerColor; }

    public void setPlayerColor(PlayerColor c) { playerColor = c; }

    public ZombieColor getZombieColor() { return zombieColor; }

    public void setZombieColor(ZombieColor c) { zombieColor = c; }

    public Background getBackgrounD() { return background; }

    public void setBackground(Background b) { background = b; }

    public Mode getMode() { return mode; }

    public void setMode(Mode m) { mode = m; }

    public void setSpawner(Spawn spawner) { this.spawner = spawner; }

    public void setMouseInput(MouseInput mouseInput) { this.mouseInput = mouseInput; }

    public STATE getGameState() { return gameState; }

    public void setGameState(STATE gameState) { this.gameState = gameState; }

    public Ammo getGameAmmo() { return gameAmmo; }

    public void setGameAmmo(Ammo gameAmmo) { this.gameAmmo = gameAmmo; }

    public static int getDIFF() { return DIFF; }

    public int getHighScoreLevel() { return highScoreLevel; }

    public void setHighScoreLevel(int highScoreLevel) { this.highScoreLevel = highScoreLevel; }

    public static void main(String[]args) throws SlickException, IOException {
        new Game();
    }
}
