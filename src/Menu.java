import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Menu extends MouseAdapter {

    private Game game;
    private Handler handler;
    private HUD hud;
    private MouseInput mouseInput;

    private BufferedImage zombieImage;

    private float[] startPositionsX = new float[6];
    private float[] startPositionsY = new float[6];

    private Random r = new Random();

    public Menu(Game game,Handler handler,HUD hud){
        this.game = game;
        this.handler = handler;
        this.hud = hud;
        this.mouseInput = null;

        //SpriteSheet spriteSheet1  = new SpriteSheet(Game.spriteSheet);
        //zombieImage = spriteSheet1.grabImage(1,1,(int)game.WIDTH,(int)game.HEIGHT);



        startPositionsX[0] = (float) r.nextInt(10) * -1;
        startPositionsX[1] = (float) Math.random()*11 + (int)game.WIDTH;
        startPositionsX[2] = (float) r.nextInt((int)game.WIDTH);

        startPositionsY[0] = (float) r.nextInt(10) * -1;
        startPositionsY[1] = (float) Math.random()*11 + (int)game.HEIGHT;
        startPositionsY[2] = (float) r.nextInt((int)game.HEIGHT);
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (game.getGameState() == Game.STATE.Menu) {
            //new game button
            if (mouseOver(mx, my, 40, 150, 510, 95)) {
                AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Select);
            }

            //help button
            if (mouseOver(mx, my, (int)game.WIDTH - 170, (int)game.HEIGHT - 130, 120, 35)) {
                AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Help);
            }

            //quit button
            if (mouseOver(mx, my, 40, (int)game.HEIGHT - 130, 120, 35)){
                AudioPlayer.getSound("click_sound").play();

                System.exit(0);
            }
        }

        else if (game.getGameState() == Game.STATE.Help) {
            //back button
            if (mouseOver(mx, my, 40,20,120,35)) {
                AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Menu);
            }
        }

        else if (game.getGameState() == Game.STATE.Select) {
            //easy button
            if (mouseOver(mx, my, 430, 200, 230, 90)) {
                AudioPlayer.getSound("click_sound").play();

                game.DIFF = 0;
                start();

                game.setGameState(Game.STATE.Game);
                AudioPlayer.getMusic("background_music").stop();
                AudioPlayer.getMusic("pursuit").loop();
            }

            //normal button
            if (mouseOver(mx, my, 800, 350, 350, 90)) {
                AudioPlayer.getSound("click_sound").play();

                game.DIFF = 1;
                start();

                game.setGameState(Game.STATE.Game);
                AudioPlayer.getMusic("background_music").stop();
                AudioPlayer.getMusic("pursuit").loop();
            }

            //hard button
            if (mouseOver(mx, my, 600, 550, 230, 90)) {
                AudioPlayer.getSound("click_sound").play();

                game.DIFF = 2;
                start();

                game.setGameState(Game.STATE.Game);
                AudioPlayer.getMusic("background_music").stop();
                AudioPlayer.getMusic("pursuit").loop();
            }

            //back button
            if (mouseOver(mx, my, 40, (int)game.HEIGHT - 130, 120, 35)){
                AudioPlayer.getSound("click_sound").play();

                game.setGameState(Game.STATE.Menu);
            }
        }

        else if (game.getGameState() == Game.STATE.GameOver) {
            //back button
            if (mouseOver(mx, my, (int)game.WIDTH/2 - 100, 640, 175, 120)) {

                game.setGameState(Game.STATE.Menu);
                AudioPlayer.getSound("click_sound").play();

                newGame();
                handler.getLst().clear();
            }
        }
    }

    public void mouseReleased(MouseEvent e) {}

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            if (my > y && my < y + height) return true;
            else return false;
        } else return false;
    }

    public void tick() {}

    public void render(Graphics g) {
        g.drawImage(zombieImage,0,0,null);
        if (game.getGameState() == Game.STATE.Menu) {
            Font font = new Font("AR DARLING", 1, 135);
            Font font2 = new Font("AR DARLING", 1, 100);
            Font font3 = new Font("AR DARLING", 1, 50);

            g.setFont(font);
            g.setColor(new Color(100,0,0));
            g.drawString("Zombies   Attack", (int)(game.WIDTH / 2) - 550, 100);

            g.setFont(font2);
            g.setColor(new Color(0,255,100));
            g.drawString("New Game", 50, 225);

            g.setFont(font3);
            g.setColor(new Color(0,200,200));
            g.drawString("Help", (int)game.WIDTH - 160, (int)game.HEIGHT - 100);

            g.setColor(new Color(250,100,100));
            g.drawString("Settings", (int)game.WIDTH/2 - 130, (int)game.HEIGHT - 100);

            g.setColor(new Color(0,200,200));
            g.drawString("Quit", 50, (int)game.HEIGHT - 100);
        }

        else if (game.getGameState() == Game.STATE.Select) {
            Font font = new Font("AR DARLING", 1, 135);
            Font font2 = new Font("AR DARLING", 1, 100);
            Font font3 = new Font("AR DARLING", 1, 50);

            g.setFont(font);
            g.setColor(Color.green);
            g.drawString("Select Difficulty...", (int)(game.WIDTH / 2) - 600, 100);

            g.setFont(font2);
            g.setColor(new Color(255,255,135));
            g.drawString("Easy", 430, 275);

            g.setColor(Color.yellow);
            g.drawString("Normal", 800, 425);

            g.setColor(Color.red);
            g.drawString("Hard", 600, 650);

            g.setFont(font3);
            g.setColor(Color.white);
            g.drawString("Back", 40, (int)(game.HEIGHT) - 100);
        }

        else if (game.getGameState() == Game.STATE.Help) {
            Font font = new Font("AR DARLING",1,135);
            Font font2 = new Font("AR DARLING",1,50);

            g.setFont(font);
            g.setColor(Color.white);
            g.drawString("Help",(int)game.WIDTH/2 - 150,110);

            g.setFont(font2);
            g.setColor(new Color(100,0,0));
            g.drawRect(175,150,1250,650);
            g.setColor(new Color(0,255,100));
            g.drawString("Hey! Dude?!",200,200);
            g.drawString("Avoid The Zombies !",200,270);
            g.drawString("Press W,S,A,D To Move",200,340);
            g.drawString("Press Esc To Pause",200,410);
            g.drawString("Press Space To Shop For Upgrades",200,480);
            g.drawString("The Little Dots Are Your Ammo, Collect Them!",200,550);
            g.drawString("If you got Grenades, Press G to switch to it",200,620);
            g.drawString("Good Luck !",600,750);

            g.setColor(new Color(200,200,100));
            g.drawString("Back",40,55);
        }

        else if (game.getGameState() == Game.STATE.GameOver) {
            Font font = new Font("AR DARLING",1,135);
            Font font2 = new Font("AR DARLING",1,70);

            g.setFont(font);
            g.setColor(Color.red);
            g.drawString("You Lost, Loser !",(int)game.WIDTH/2 - 600,170);

            g.setFont(font2);
            g.setColor(Color.white);
            g.drawRect(400,220,800,310);
            g.setColor(Color.red);
            g.drawString("Score: " + HUD.score, 450, 300);
            g.drawString("Zombies Killed: " + HUD.zombiesKilled, 450, 500);

            g.setColor(Color.white);
            g.drawString("Back",(int)game.WIDTH/2 - 100,750);
        }
    }

    private void start() {
        Player player = new Player((int)game.WIDTH / 2 - 32, (int)game.HEIGHT / 2 - 32, ID.Player, handler,game);
        handler.addObject(player);
        player.initialize();
        if (Game.DIFF == 0) {
            handler.addObject(new EasyZombie(game,chooseCord().getKey(), chooseCord().getValue(), ID.Zombie, player, handler, 0));
        } else if (Game.DIFF == 1) {
            handler.addObject(new NormalZombie(game,chooseCord().getKey(), chooseCord().getValue(), ID.Zombie, player, handler, 0));
        } else if (Game.DIFF == 2) {
            handler.addObject(new HardZombie(game,chooseCord().getKey(), chooseCord().getValue(), ID.Zombie, player, handler, 0));
        }

        game.setSpawner(new Spawn(handler, player,game));
        if (mouseInput != null) {
            game.removeMouseListener(mouseInput);
        }
        mouseInput = new MouseInput(handler,game,player);
        game.setMouseInput(mouseInput);
        game.addMouseListener(mouseInput);
    }

    private Pair<Float,Float> chooseCord(){
        float x = startPositionsX[r.nextInt(3)];
        float y;
        if (x < (int)game.WIDTH && x > 0) {
            y = startPositionsY[r.nextInt(2)];
        } else {
            y = startPositionsY[r.nextInt(3)];
        }
        return new Pair<>(x,y);
    }

    private void newGame() {
        HUD.initialize();
        Spawn.initialize();
        Shop.initialize();

        handler.speed = 5;
        handler.getLst().clear();
        game.setGameAmmo(Game.Ammo.Pistol);
        game.setCond(false);
    }
}